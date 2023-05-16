/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._, gOne.hp1.GSys

/** 1st example Turn 0 scenario state for Game Three. */
object G2HScen1 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = GSys.g1
  val playerStates: HCenOptLayer[PlayerState] = HCenOptLayer()
  playerStates.setSomeMut(6, 2, PlayerState(CounterA, HexDR, HexDL))
//  playerStates.setSomeMut(4, 4, PlayerA)
  playerStates.setSomesMut((4, 8, PlayerState(CounterB, HexDR)), (6, 10, PlayerState(CounterC, HexDL, HexDL)))

//  override def playerOrders: HDirnPathPairArr[Player] = HDirnPathPairArr[Player](PlayerA.hPath(6, 2, HexDR, HexUL), PlayerA.hPath(4, 4, HexDR, HexLt),
//    PlayerB.hPath(4, 8, HexDL, HexRt), PlayerC.hPath(6, 10, HexLt, HexDR, HexDL))
}

/** 2nd example Turn 0 scenario state for Game Three. */
object G2HScen2 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 10, 4, 8)
  val playerStates: HCenOptLayer[PlayerState] = HCenOptLayer()
  playerStates.setSomesMut((4, 4, PlayerState(CounterA)), (8, 4, PlayerState(CounterB)), (6, 6, PlayerState(CounterC)))
}

/** 3rd example Turn 0 scenario state for Game Three. */
object G2HScen3 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(10, (1, 6), (2, 4), (3, 2), (2, 4), (1, 6))
  val playerStates: HCenOptLayer[PlayerState] = HCenOptLayer()
  playerStates.setSomesMut((4, 4, PlayerState(CounterA)), (10, 6, PlayerState(CounterB)), (8, 8, PlayerState(CounterC)))
}

object G2HScen4 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 12, 2, 60)
  val playerStates: HCenOptLayer[PlayerState] = HCenOptLayer()
  playerStates.setSomeMut(4, 4, PlayerState(CounterA))
  playerStates.setSomesMut((4, 8, PlayerState(CounterB)), (6, 10, PlayerState(CounterC)), (8, 32, PlayerState(CounterD)))
}