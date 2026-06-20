/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import geom.pgui.*, pParse.*

object PlanetLaunch  extends GuiLaunchMore
{ override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) = (PlanetsGui(_), "JavaFx Planets")
  override def settingStr: String = "planets"
  override def default: (CanvasPlatform => Any, String) = (PlanetsGui(_), "JavaFx Planets")
}