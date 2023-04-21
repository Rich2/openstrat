/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._, Colour._

/** Basic map of the Earth using irregular areas / tiles. */
case class EarthBasicGui(canv: CanvasPlatform, viewIn: EarthView = EarthView(40, 0, 10)) extends GlobeGui("The Earth in irregular tiles")
{ statusText = "Welcome to world map, constructed from irregular areas."

  /** Scale in km / pixel */
  var scale: Length = viewIn.scale

  /** Scale accounting for whether the display has north up or down. */
  def dirnScale: Length = ife(northUp, scale, -scale)

  val scaleMin: Length = 0.2.kMetres
  val scaleMax: Length = 100.kMetres
  var focus: LatLong = viewIn.latLong

  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)

  val ps1: PolygonLLPairArr[EArea2] = eas.map(ea => PolygonLLPair[EArea2](ea.polygonLL, ea))
  val lc1: LocationLLArr = eas.flatMap(_.places)
  val lc2: PtM3PairArr[Place] = lc1.mapOnA1(_.toMetres3)

  /** This compiles without type annotation. */
  val ps2: PolygonM3PairArr[EArea2] = ps1.polygonMapToPair(_.toMetres3)

  import pEurope._
  val london = England.london.a1
  val paris = Frankia.paris.a1
  val berlin = Germania.berlin.a1
  val conns1 = LineSegLLArr(london.lineSegTo(paris), paris.lineSegTo(berlin))
  val conns2 = conns1.map(_.map(_.toMetres3))


  def repaint(): Unit =
  { val ps3: PolygonM3PairArr[EArea2] = ps2.polygonMapToPair(_.fromLatLongFocus(focus))

    val ps4: PolygonM2PairArr[EArea2] = ps3.optMapOnA1 {
      case p if p.zAllNonNeg => Some(p.map(_.xy))
      case p if p.zAllNeg => None
      case p => {
        val newPoly = p.map { case v if v.zNeg => (v.xy / v.xyLengthFrom()).toMetres(EarthAvRadius)
          case v => v.xy
        }
        Some(newPoly)
      }
    }

    val ps5 = ps4.polygonMapToPair{ p => p / dirnScale }

    val activeFills: RArr[PolygonCompound] = ps5.pairMap((p, a2) => p.fillActive(a2.colour, a2))

    val sideLines: RArr[PolygonDraw] = ps5.a1Map { _.draw() }

    val areaNames: RArr[TextGraphic] = ps4.a2Map { d =>
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / dirnScale
      TextGraphic(d.name, 10, posn, d.colour.contrastBW)
    }

    val locs1: PtM3PairArr[Place] = lc2.mapOnA1(_.fromLatLongFocus(focus))
    val locs2: PtM3PairArr[Place] = locs1.filterOnA1(_.zPos)
    val locs3: Pt2PairArr[Place] = locs2.mapOnA1(_.xy / scale)

    val locTexts = locs3.map{ p => val col = p.a2.level match { case 1 => DarkBlue; case 2 => DarkGreen; case 3 => Pink }
      p.a1.textAt(p.a2.name, 10, col) }

    val conns3 = conns2.map(_.fromLatLongFocus(focus))
    val conns4 = conns3.filter(_.zsPos)
    val conns5 = conns4.map(_.xy / scale)
    val conns6 = conns5.draw(2, Orange)

    def seas: EllipseFill = earth2DEllipse(scale).fill(DarkBlue)

    mainRepaint(seas %: activeFills ++ sideLines.+%(conns6) ++ areaNames ++ locTexts)
  }

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected").newLinesToSpaces
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }
  canv.onScroll = b => { scale = ife(b, (scale / 1.2).max(scaleMin), (scale * 1.2).min(scaleMax)); repaint() }

  def thisTop(): Unit = reTop(RArr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))

  repaint()
  thisTop()
}