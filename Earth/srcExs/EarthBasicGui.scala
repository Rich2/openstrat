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
  val af0 = WestAfrica
  val af1: PolygonLL = af0.polygonLL
  val af2: PolygonMs3 = af1.metres3Default
  val af3: PolygonMs = af2.xyPlane
  val af4 = af3.mapPolygon(_ / 10.km)

  mainRepaint(Arr(af4.fill(af0.colour)))
}

/** object to launch EarthBasic Gui. */
object EarthBasicLaunch extends GuiLaunchSimple("earth", (EarthBasicGui.apply(_), "JavaFx Earth"))