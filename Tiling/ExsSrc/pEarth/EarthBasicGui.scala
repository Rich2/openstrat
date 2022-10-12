/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._

/** Basic map of the Earth using irregular areas / tiles. */
case class EarthBasicGui(canv: CanvasPlatform, startScale: Option[Length] = None, startFocus: Option[LatLong] = None) extends
  GlobeGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map, constructed from irregular areas."

  /** Scale in km / pixel */
  var scale: Length = startScale.getOrElse(12.kMetres)

  val scaleMin: Length = 0.2.kMetres
  val scaleMax: Length = 100.kMetres
  var focus: LatLong = startFocus.sget

  val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)

  val eaPms: Arr[(EArea2, PolygonM2)] = eas.map(_.withPolygonM(focus, northUp))
  val ps1: PolygonLLPairArr[EArea2] = eas.map(ea => PolygonLLPair[EArea2](ea.polygonLL, ea))

  /** This compiles without type annotation. */
  val ps2: PolygonM3PairArr[EArea2] = ps1.polygonMapToPair(_.toMetres3)

  def repaint(): Unit =
  {
    val ps3 = ps2.polygonMapToPair(_.fromLatLongFocus(focus))

    val eaPms3: Arr[(EArea2, PolygonM2)] = eaPms.filter(_._2.vertsMin3)
    val activeFills: Arr[PolygonCompound] = eaPms3.map { pair =>
      val (d, p) = pair
      p.map(_ / scale).fillActive(d.colour, d)
    }

    val sideLines: Arr[PolygonDraw] = eaPms3.map { a => a._2.map(_ / scale).draw() }
    val texts: Arr[TextGraphic] = eaPms3.map { pair =>
      val (d, _) = pair
      val posn = d.cen.toMetres3.fromLatLongFocus(focus).xy / scale
      TextGraphic(d.name, 10, posn, d.colour.contrastBW)
    }

    def seas = earth2DEllipse(scale).fill(Colour.DarkBlue)

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

  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goNorth, goSouth, goWest, goEast))

  repaint()
  thisTop()
}