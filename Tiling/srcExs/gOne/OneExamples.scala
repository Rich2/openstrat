/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._, pCanv._

object OneLaunch extends GuiLaunchStd
{
  override def settingStr: String = "gOne"

  override def default: (CanvasPlatform => Any, String) = (GOneGui(_, OneScen1), "JavaFx Game One")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) =
  { val scen = s2 match
    { case 1 => OneScen1
      case 2 => OneScen2
      case 3 => OneScen3
      case _ => OneScen1
    }
    (GOneGui(_, scen), "JavaFx Game One")
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

/** 3rd example Turn 0 scenario state for Game One. */
object OneScen3 extends OneScenStart
{ implicit val grid: HGrid = HGridIrrRowLengths(10, (6, 6), (4, 8), (2, 10), (4, 8), (6, 6))
  val oPlayers: HCenArrOpt[Player] = grid.newTileArrOpt
  oPlayers.unsafeSetSome(4, 4, PlayerA)
}