/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pzug
import pgui._

/** Scenario selector and launcher for ZugFuhrer. */
object ZugLaunch extends GuiLaunchStd
{
  override def settingStr: String = "zugFuhrer"

  override def default: (CanvasPlatform => Any, String) = (ZugGui(_, Zug1), "JavaFx Zugfuhrer Z1 Britain")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match
  { case 1 => (ZugGui(_, Zug1), "JavaFx Zugfuhrer Z1 Britain")
    case 2 => (ZugGui(_, Zug2), "JavaFx Zugfuhrer Z2 Britain")
    case 3 => (ZugGui(_, Zug3), "JavaFx Zugfuhrer Z3 Britain")
    case _ => (ZugGui(_, Zug1), "JavaFx Zugfuhrer Z1 Britain")
  }
}