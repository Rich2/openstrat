/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._
import ostrat.pEarth.pPts.UsaWest
import pglobe._
import pgui._

/** Basic map of the Earth using irregular areas / tiles. */
case class EarthBasicGui(canv: CanvasPlatform, viewIn: EarthView = EarthView(40, 0, 10)) extends GlobeGui("The Earth in irregular tiles")
{  statusText = "Welcome to world map, constructed from irregular areas."
  debvar(viewIn)
  /** Scale in km / pixel */
  var scale: Length = viewIn.scale

  val scaleMin: Length = 0.2.kMetres
  val scaleMax: Length = 100.kMetres
  var focus: LatLong = viewIn.latLong

  val eas: RArr[EArea2] = earthAllAreas.flatMap(_.a2Arr)

  val ps1: PolygonLLPairArr[EArea2] = eas.map(ea => PolygonLLPair[EArea2](ea.polygonLL, ea))
  val lc1: LocationLLArr = UsaWest.locations
  val lc2 = lc1.mapOnA1(_.toMetres3)

  /** This compiles without type annotation. */
  val ps2: PolygonM3PairArr[EArea2] = ps1.polygonMapToPair(_.toMetres3)
  debvar(ps2.length)

  def repaint(): Unit =
  { val ps3: PolygonM3PairArr[EArea2] = ps2.polygonMapToPair(_.fromLatLongFocus(focus))
    val ps4: PolygonM3PairArr[EArea2] = ps3.filterOn1(_.zAllNonNeg)
    debvar(ps4.length)
    val ps5: PolygonM2PairArr[EArea2] = ps4.polygonMapToPair(_.xy)
    val activeFills: RArr[PolygonCompound] = ps5.pairMap((p, a2) => p.map(_ / scale).fillActive(a2.colour, a2))

    val sideLines: RArr[PolygonDraw] = ps5.a1Map { _.map(_ / scale).draw() }

    val texts: RArr[TextGraphic] = ps5.a2Map { d =>
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
      TextGraphic(d.name, 10, posn, d.colour.contrastBW)
    }

    //val locs = lc2.filterOn1

    def seas: EllipseFill = earth2DEllipse(scale).fill(Colour.DarkBlue)

    mainRepaint(seas %: activeFills ++ sideLines ++ texts)
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