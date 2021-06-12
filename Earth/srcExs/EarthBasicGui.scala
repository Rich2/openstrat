/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pCanv._, geom._

/** Basic map of the Earth using irregular areas. */
case class EarthBasicGui(canv: CanvasPlatform) extends CmdBarGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map, constructed from irregular areas."
  def thisTop(): Unit = reTop(Arr())
  thisTop()

  val eas: Arr[EarthLevel2] =  EarthAreas.allTops.flatMap(_.a2Arr)//.flatMap(a => a.disp2(this))
  val af: PolygonLL = WestAfrica.polygonLL
  val af2: PolygonMs3 = af.metres3Default
  val af3: PolygonMs = af2.xyPlane
  val rect = Rect(200, 100).fill(Colour.Red)

  mainRepaint(Arr(rect))
}

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchSimple("earth", (EarthBasicGui.apply(_), "JavaFx Earth"))