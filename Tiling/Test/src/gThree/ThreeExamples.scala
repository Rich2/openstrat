/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, phex._, gPlay._

/** 1st example Turn 0 scenario state for Game Three. */
object ThreeScen1 extends ThreeScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 6, 2, 10)
  val oPlayers: HCenOptDGrid[Player] = gridSys.newHCenOptDGrid
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))

  override def playersData: Map[Player, HDirnArr] = Map(PlayerA.hSteps(HexDR, HStepLt), PlayerB.hSteps(HexDL, HexRt),
    PlayerC.hSteps(HStepLt, HexDR, HexDL))
}

/** 2nd example Turn 0 scenario state for Game Three. */
object ThreeScen2 extends ThreeScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 10, 4, 8)
  val oPlayers: HCenOptDGrid[Player] = gridSys.newHCenOptDGrid
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (8, 4, PlayerB), (6, 6, PlayerC))
}

/** 3rd example Turn 0 scenario state for Game Three. */
object ThreeScen3 extends ThreeScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(10, (1, 6), (2, 4), (3, 2), (2, 4), (1, 6))
  val oPlayers: HCenOptDGrid[Player] = gridSys.newHCenOptDGrid
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (10, 6, PlayerB), (8, 8, PlayerC))
}

object ThreeScen4 extends ThreeScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 12, 2, 60)
  val oPlayers: HCenOptDGrid[Player] = gridSys.newHCenOptDGrid
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

object ThreeScen5 extends ThreeScen
{  override def turn: Int = 0
  implicit val gridSys: HGridSysFlat = HGrids2(2, 8, 2, 6, 100, 104)
  val oPlayers: HCenOptDGrid[Player] = gridSys.newHCenOptDGrid
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSome(6, 102, PlayerB)
  oPlayers.unsafeSetSome(8, 100, PlayerC)
}

object ThreeScen6 extends ThreeScen
{  override def turn: Int = 0
  implicit val gridSys: HGridSysFlat = HGrids2(4, 10, 2, 6, 100, 106)
  val oPlayers: HCenOptDGrid[Player] = gridSys.newHCenOptDGrid
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSome(6, 102, PlayerB)
  oPlayers.unsafeSetSome(10, 102, PlayerC)
}