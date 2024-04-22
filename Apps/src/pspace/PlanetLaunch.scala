/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pspace
import pgui._, pParse._

object PlanetLaunch  extends GuiLaunchMore
{
  override def fromStatements(sts: RArr[Statement]): (CanvasPlatform => Any, String) = (PlanetsGui(_), "JavaFx Planets")

  override def settingStr: String = "planets"

  override def default: (CanvasPlatform => Any, String) =
  {
    (PlanetsGui(_), "JavaFx Planets")
  }
}
