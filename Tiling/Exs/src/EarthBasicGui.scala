/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pCanv._, geom._

/** Basic map of the Earth using irregular areas / tiles. */
case class EarthBasicGui(canv: CanvasPlatform, startScale: Option[Metres] = None, startFocus: Option[LatLong] = None) extends
  CmdBarGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map, constructed from irregular areas."

  /** Scale in km / pixel */
  var scale: Length = startScale.getOrElse(12.kMetres)

  def scaleStr = s"scale = ${scale.kMetresNum.str2} km/pixel"

  val lp = LinePathLL(0 ll 0, 5 ll 0, 5 ll 5, 10 ll 10)
  val lp2 = lp.map(_.toMetres3)
  val lp3 = lp2.map(_.xy)
  val lp4 = lp3.map(_ / scale)

  def repaint(): Unit = {
    val eas: Arr[EarthLevel2] = EarthAreas.allTops.flatMap(_.a2Arr)
    val afPairs: Arr[(EarthLevel2, PolygonMetre3)] = eas.map { a => (a, a.polygonLL.metres3Default) }.filter(_._2.zNonNeg)
    val af0 = afPairs.map { p => p._2.xyPlane.map(_ / scale).fill(p._1.colour) }
    val af1 = afPairs.map { a => a._2.xyPlane.map(_ / scale).draw() }

    def seas = earth2DEllipse(scale).fill(Colour.DarkBlue)

    mainRepaint((seas +: af0) ++ af1 +- lp4.draw())
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
  def thisTop(): Unit = reTop(Arr(zoomIn, zoomOut))

  repaint()
  thisTop()
}

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchSimple("Earth", (EarthBasicGui.apply(_), "JavaFx Earth"))