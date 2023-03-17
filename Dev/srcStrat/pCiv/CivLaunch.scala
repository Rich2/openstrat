/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._, pgui._

object CivLaunch extends GuiLaunchStd
{
  override def settingStr: String = "civ"

  override def default: (CanvasPlatform => Any, String) = (CivGui(_, Civ1), "JavaFx Civ")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match
  { case 1 => (CivGui(_, Civ1), "JavaFx Civ Scen 1")
    case 2 => (CivGui(_, Civ2), "JavaFx Civ Scen 2")
    case _ => (CivGui(_, Civ1), "JavaFx Civ Scen 1")
  }
}