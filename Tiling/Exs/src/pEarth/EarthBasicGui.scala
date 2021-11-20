/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import geom._, pglobe._, pgui._

/** Basic map of the Earth using irregular areas / tiles. */
case class EarthBasicGui(canv: CanvasPlatform, startScale: Option[Metres] = None, startFocus: Option[LatLong] = None) extends
  CmdBarGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map, constructed from irregular areas."

  /** Scale in km / pixel */
  var scale: Metres = startScale.getOrElse(12.kMetres)
  var focus: LatLong = startFocus.sget
  def lat: Latitude = focus.lat
  def long: Longitude = focus.long

  def scaleStr = s"scale = ${scale.kMetresNum.str2} km/pixel"

  def repaint(): Unit = {
    val eas: Arr[EArea2] = EarthAreas.allTops.flatMap(_.a2Arr)
    val afps: Arr[(EArea2, PolygonMetre)] = eas.map { a => (a, a.polygonLL.subLong(long).metres3Default.earthZPosXYModify) }
    val af0 = afps.map { p => p._2.map(_ / scale).fillActive(p._1.colour, p._1) }
    val af1 = afps.map { a => a._2.map(_ / scale).draw() }

    def seas = earth2DEllipse(scale).fill(Colour.DarkBlue)

    mainRepaint(seas %: af0 ++ af1)
  }

  def zoomIn: PolygonCompound = clickButton("+"){_ =>
    scale /= 1.1
    repaint()
    statusText = scaleStr
    thisTop()
  }

  def zoomOut: PolygonCompound = clickButton("-"){_ =>
    scale *= 1.1
    repaint()
    statusText = scaleStr
    thisTop()
  }

  def goDirn(str: String, f: Double => Unit): PolygonCompound = clickButton(str){b =>
    def delta = b.apply(1, 10, 60, 0)
    f(delta)
    repaint()
    statusText = s"focus $focus"
    thisTop()
  }

  def goNorth: PolygonCompound = clickButton("\u2191"){b =>
    def delta = b.apply(1, 10, 60, 0)
    focus = focus.addLat(delta.degs)
    repaint()
    statusText = s"Latitude $lat"
    thisTop()
  }

  def goEast: PolygonCompound = goDirn("\u2192", delta => focus = focus.addLong(delta.degs))
  def goWest: PolygonCompound = goDirn("\u2190", delta => focus = focus.subLong(delta.degs))

  mainMouseUp = (b, cl, _) => (b, selected, cl) match {
    case (LeftButton, _, cl) => {
      selected = cl
      statusText = selected.headFoldToString("Nothing Selected")
      thisTop()
    }

    case (_, _, h) => deb("Other; " + h.toString)
  }

  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut, goNorth, goWest, goEast))

  repaint()
  thisTop()
}