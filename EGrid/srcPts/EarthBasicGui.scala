/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._, Colour._

/** Basic map of the Earth using irregular areas / tiles. */
case class EarthBasicGui(canv: CanvasPlatform, viewIn: EarthView = EarthView(40, 0, 10)) extends GlobeGui("The Earth in irregular tiles")
{ statusText = "Welcome to world map, constructed from irregular areas."

  /** Scale in km / pixel */
  var scale: LengthMetric = viewIn.scale

  debvar(scale)

  /** Scale accounting for whether the display has north up or down. */
  def dirnScale: LengthMetric = nthSth.fld(scale, -scale)

  val scaleMin: LengthMetric = 0.2.kilometres
  val scaleMax: LengthMetric = 100.kilometres
  var focus: LatLongDirn = viewIn.latLongDirn

  val eas: RArr[EarthPoly] = earthAllRegions.flatMap(_.ePolys)

  val ps1: PolygonLLPairArr[EarthPoly] = eas.map(ea => PolygonLLPair[EarthPoly](ea.polygonLL, ea))
  val lc1: LocationLLArr = eas.flatMap(_.places)
  val lc2: PtM3PairArr[Place] = lc1.mapOnA1(_.toMetres3)

  /** This compiles without type annotation. */
  val ps2: PolygonM3PairArr[EarthPoly] = ps1.polygonMapToPair(_.toMetres3)

  import pEurope._
  val london: LatLong = EnglandNorth.london.a1
  val paris: LatLong = FranceNorth.paris.a1
  val berlin: LatLong = Germania.berlin.a1
  val conns1: LineSegLLArr = LineSegLLArr(london.lineSegTo(paris), paris.lineSegTo(berlin))
  val conns2: LineSegM3Arr = conns1.map(_.map(_.toMetres3))


  def repaint(): Unit =
  { val ps3: PolygonM3PairArr[EarthPoly] = ps2.polygonMapToPair(_.fromLatLongFocus(focus))

    val ps4: PolygonM2PairArr[EarthPoly] = ps3.optMapOnA1 {
      case p if p.zAllNonNeg => Some(p.map(_.xy))
      case p if p.zAllNeg => None
      case p => {
        val newPoly = p.map { case v if v.zNeg => v.xy.mapGeom2(v.xyLengthFrom()).toMetres(EarthAvRadius)
          case v => v.xy
        }
        Some(newPoly)
      }
    }

    val ps5: PolygonGenPairArr[EarthPoly] = ps4.polygonMapToPair(_.mapGeom2(dirnScale))

    val fillActiveTexts: RArr[PolygonCompound] = ps5.pairMap { (p, a2) =>
      val str: String = a2 match
      { case isle: IslandPoly => isle.strWithGroups
        case lake: LakePoly => lake.name -- lake.area.str
        case ea => ea.name -- ea.terr.str
      }
      p.fillActiveText(a2.colour, str, a2.name, 10, a2.contrastBW)
    }
    
    val graphicPairs: RPairArr[Graphic2Elem, TextFixed] = ps5.pairMap{ (poly, a2) =>
      val str: String = a2 match
      { case isle: IslandPoly => isle.strWithGroups
        case lake: LakePoly => lake.name -- lake.area.str
        case ea => ea.name -- ea.terr.str
      }
      RPairElem(poly.fillActive(a2.colour, a2), poly.textSized(a2.name, a2.contrastBW))
    }
    def polyTexts = graphicPairs.a2Arr.filter(_.fontSize >= 6)

    val sideLines: RArr[PolygonDraw] = ps5.a1Map { _.draw() }

    val locs1: PtM3PairArr[Place] = lc2.mapOnA1(_.fromLatLongFocus(focus))
    val locs2: PtM3PairArr[Place] = locs1.filterOnA1(_.zNonNeg)
    val locs3: Pt2PairArr[Place] = locs2.mapOnA1(_.xy.mapGeom2(scale))

    val locTexts = locs3.map{ p => val col = p.a2.level match { case 1 => DarkBlue; case 2 => DarkGreen; case 3 => Pink }
      p.a1.textAt(p.a2.name, 10, col) }

    val conns3 = conns2.map(_.fromLatLongFocus(focus))
    val conns4 = conns3.filter(_.zsPos)
    val conns5 = conns4.map(_.xy.mapGeom2(scale))
    val conns6 = conns5.draw(2, Orange)

    def seas: EllipseFill = earth2DEllipse(scale).fill(DarkBlue)

    mainRepaint(seas %: graphicPairs.a1Arr ++ sideLines.+%(conns6) ++ polyTexts ++ locTexts)
  }

  /*override def selectedStr: String = selectStack.mkStrSemis {    
    case eai: IslandPoly => eai.strWithGroups
    case ipp: IslandPartPoly => ipp.name -- ipp.area.str
    case obj => obj.toString
  }*/

  mainMouseUp = (b, cl, _) => (b, selected, cl) match
  { case (LeftButton, _, cl) =>
    { selected = cl.headOrNone
      statusText = selectedStr.oneLine
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  canv.onScroll = b => { scale = ife(b, (scale / 1.2).max(scaleMin), (scale * 1.2).min(scaleMax)); repaint() }

  def thisTop(): Unit = reTop(RArr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))

  repaint()
  thisTop()
}