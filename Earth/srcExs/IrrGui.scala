/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pCanv._, geom._

case class IrrGui(canv: CanvasPlatform) extends CmdBarGui("The Earth in irregular tiles")
{
  statusText = "Welcome to world map"
  /** The frame to refresh the top command bar. Note it is a ref so will change with scenario state. */
  def thisTop(): Unit = reTop(Arr())
  thisTop()


  val eas: Arr[EarthLevel2] =  EarthAreas.allTops.flatMap(_.a2Arr)//.flatMap(a => a.disp2(this))



}

object IrrLaunch extends GuiLaunchSimple("earth", (IrrGui.apply(_), "JavaFx Earth"))