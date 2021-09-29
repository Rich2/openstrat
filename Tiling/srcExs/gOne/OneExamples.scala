/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._, pCanv._

object OneLaunch extends GuiLaunchStd
{
  override def settingStr: String = "gOne"

  override def default: (CanvasPlatform => Any, String) = (GOneGui(_, OneScen1), "JavaFx Game One")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match {
    case 1 => (GOneGui(_, OneScen1), "JavaFx Game One")
    case 2 => (GOneGui(_, OneScen2), "JavaFx Game One")
    case _ => (GOneGui(_, OneScen1), "JavaFx Game One")
  }
}

/** 1st example Turn 0 scenario state for Game One. */
object OneScen1 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 6, 2, 10)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

/** 2nd example Turn 0 scenario state for Game One. */
object OneScen2 extends OneScenStart
{ implicit val grid: HGridReg = HGridReg(2, 10, 4, 8)
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSome(4, 4, PlayerA)
}