/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import prid._, phex._, gPlay._

/** 1st example Turn 0 scenario state for Game One. */
object OneScen1 extends OneScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 6, 2, 10)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSomes((4, 8, PlayerB), (6, 10, PlayerC))
}

/** 2nd example Turn 0 scenario state for Game One. */
object OneScen2 extends OneScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 10, 4, 8)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (8, 4, PlayerB), (6, 6, PlayerC))
}

/** 3rd example Turn 0 scenario state for Game One. */
object OneScen3 extends OneScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(10, (1, 6), (2, 4), (3, 2), (2, 4), (1, 6))
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (10, 6, PlayerB), (8, 8, PlayerC))
}

object OneScen4 extends OneScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(2, 12, 2, 60)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(6, 38, PlayerA)
  oPlayers.unsafeSetSomes((4, 40, PlayerB), (6, 42, PlayerC))
}

object OneScen5 extends OneScen
{  override def turn: Int = 0
  implicit val gridSys: HGridSys = HGrids2(2, 8, 2, 6, 100, 104)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSome(6, 102, PlayerB)
  oPlayers.unsafeSetSome(8, 100, PlayerC)
}

object OneScen6 extends OneScen
{  override def turn: Int = 0
  implicit val gridSys: HGridSys = HGrids2(4, 10, 2, 6, 100, 106)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSome(4, 4, PlayerA)
  oPlayers.unsafeSetSome(6, 102, PlayerB)
  oPlayers.unsafeSetSome(10, 102, PlayerC)
}

/** 3rd example Turn 0 scenario state for Game One. */
object OneScen7 extends OneScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(10, (3, 6), (1, 8), (4, 2), (2, 4), (1, 6))
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
  oPlayers.unsafeSetSomes((4, 4, PlayerA), (10, 6, PlayerB), (8, 8, PlayerC))
}

/** 8th example Turn 0 scenario state for Game One. An empty regular grid containing no hex tiles. */
object OneScen8 extends OneScen
{ override def turn: Int = 0
  implicit val gridSys: HGridReg = HGridReg(4, 2, 4, 2)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
}

/** 8th example Turn 0 scenario state for Game One. An empty irregular grid containing no hex tiles. */
object OneScen9 extends OneScen
{ override def turn: Int = 0
  implicit val gridSys: HGrid = HGridIrr(2)
  val oPlayers: HCenOptLayer[Player] = gridSys.newHCenOptLayer
}