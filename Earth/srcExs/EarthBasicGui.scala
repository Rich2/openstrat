/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pCanv._, geom._

/** Basic map of the Earth using irregular areas. */
case class EarthBasicGui(canv: CanvasPlatform, startScale: Option[Metres] = None, startFocus: Option[LatLong] = None) extends
  CmdBarGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map, constructed from irregular areas."
  def thisTop(): Unit = reTop(Arr())
  thisTop()
  var scale: Metres = startScale.getOrElse(8000.metres)

  val eas: Arr[EarthLevel2] =  Arr(AfricaWest, AfricaEast, AfricaSouthern).flatMap(_.a2Arr)
  val af0 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon(_ / scale).fill(a.colour) }
  val af1 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon(_ / scale).draw() }
 // val af2 = eas.map{a => a.polygonLL.metres3Default.xyPlane.mapPolygon(_ / 10.km).drawT draw() }

  mainRepaint(af0 ++ af1)
}

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchSimple("earth", (EarthBasicGui.apply(_), "JavaFx Earth"))