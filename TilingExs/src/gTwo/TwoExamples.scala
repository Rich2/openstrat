/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo
import prid._, psq._, pgui._, gPlay._

object TwoLaunch extends GuiLaunchStd
{
  override def settingStr: String = "gTwo"

  override def default: (CanvasPlatform => Any, String) = (GTwoGui(_, gTwo.TwoScen1), "JavaFx Game Two")
  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match {
    case 1 => (GTwoGui(_, gTwo.TwoScen1), "JavaFx Game Two")
    case 2 => (gTwo.GTwoGui(_, gTwo.TwoScen2), "JavaFx Game Two")
    case _ => (gTwo.GTwoGui(_, gTwo.TwoScen1), "JavaFx Game Two")
  }
}

object TwoScen1 extends TwoScenStart
{ implicit val grid = SqGrid(2, 6, 2, 8)
  val oPlayers: SqCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 6, PlayerB), (6, 8, PlayerC))
}

object TwoScen2 extends TwoScenStart
{ implicit val grid = SqGrid(2, 16, 2, 20)
  val oPlayers: SqCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 6, PlayerB), (6, 8, PlayerC))
}