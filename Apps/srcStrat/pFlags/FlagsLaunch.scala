/* Copyright 2025-6 Richard Oliver and w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom.pgui.*, pParse.*

object FlagsLaunch  extends GuiLaunchMore
{ override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) = (FlagsGui(_), "JavaFx Flags")
  override def settingStr: String = "PhysChem"
  override def default: (CanvasPlatform => Any, String) = (FlagsGui(_), "JavaFx Flags")
}