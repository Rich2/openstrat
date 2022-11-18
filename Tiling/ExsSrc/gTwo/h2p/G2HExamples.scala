/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gTwo; package h2p
import prid._, phex._, gPlay._

/** 1st example Turn 0 scenario state for Game Three. */
object G2HScen1 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 6, 2, 10)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))

  override def playerOrders: HDirnPathPairArr[Player] = HDirnPathPairArr[Player](PlayerA.hPath(4, 4, HexDR, HexLt),
    PlayerB.hPath(4, 8, HexDL, HexRt), PlayerC.hPath(6, 10, HexLt, HexDR, HexDL))
}

/** 2nd example Turn 0 scenario state for Game Three. */
object G2HScen2 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 10, 4, 8)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (8, 4, PlayerB), (6, 6, PlayerC))
}

/** 3rd example Turn 0 scenario state for Game Three. */
object G2HScen3 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(10, (1, 6), (2, 4), (3, 2), (2, 4), (1, 6))
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (10, 6, PlayerB), (8, 8, PlayerC))
}

object G2HScen4 extends G2HScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 12, 2, 60)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

object G2HScen5 extends G2HScen
{  override def turn: Int = 0
  implicit val gridSys: HGridSys = HGrids2(2, 8, 2, 6, 100, 104)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSome(6, 102, PlayerB)
  oPlayers.unsafeSetSome(8, 100, PlayerC)
}

object G2HScen6 extends G2HScen
{  override def turn: Int = 0
  implicit val gridSys: HGridSys = HGrids2(4, 10, 2, 6, 100, 106)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSome(6, 102, PlayerB)
  oPlayers.unsafeSetSome(10, 102, PlayerC)
}

object G2HScen7 extends G2HScen
{  override def turn: Int = 0
  implicit val gridSys: HGridSys = HGrids2(4, 10, 2, 6, 100, 106)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSome(6, 102, PlayerB)
  oPlayers.unsafeSetSome(10, 102, PlayerC)

  override def playerOrders: HDirnPathPairArr[Player] = HDirnPathPairArr[Player](PlayerA.hPath(4, 4, HexDR, HexRt),
    PlayerB.hPath(6, 102, HexDL, HexRt), PlayerC.hPath(10, 102, HexLt, HexDR, HexDL)
  )
}