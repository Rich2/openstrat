/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pParse._
import pgui._

case class E80GridGui(canv: CanvasPlatform) extends CmdBarGui("North West Europe Gui") {

}

/** object to launch EarthBasic Gui. */
object E80BasicLaunch extends GuiLaunchMore {
  override def settingStr: String = "E80Grid"

  override def default: (CanvasPlatform => Any, String) = (cv => E80GridGui(cv), "JavaFx Eath 80KM Grid")

  override def fromStatments(sts: Arr[Statement]): (CanvasPlatform => Any, String) = (cv => E80GridGui(cv), "JavaFx Earth")
}
