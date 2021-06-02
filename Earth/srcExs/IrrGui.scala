/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import pCanv._

case class IrrGui(canv: CanvasPlatform) extends CmdBarGui("The Earth in irregular tiles") {

}

object IrrLaunch extends GuiLaunchSimple("earth", (IrrGui.apply(_), "JavaFx Earth"))