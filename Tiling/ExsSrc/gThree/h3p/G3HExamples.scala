/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree; package h3p
import prid._, phex._, gOne.h1p.GSys

object G3HScen1 extends G3HScen(0)
{ override implicit val gridSys: HGridReg = GSys.g1
  val lunits: HCenArrLayer[LunitState] = HCenArrLayer()
  lunits.set1(4, 4, LunitState(Lunit(TeamA, 1), HexDR))
  lunits.setArr(4, 8, LunitState(Lunit(TeamB, 1), HexLt, HexDL), LunitState(Lunit(TeamB, 2), HexLt))

  override val teamSet: RArr[Team] = RArr()
}

object G3HScen2 extends G3HScen(0)
{ override implicit val gridSys: HGrid = HGridReg(2, 8, 2, 22)
  val lunits: HCenArrLayer[LunitState] = HCenArrLayer()
  lunits.setArr(4, 12, LunitState(TeamA, 1, HexDR), LunitState(TeamA, 2, HexRt), LunitState(TeamA, 3, HexUR))
  lunits.setArr(6, 18, LunitState(TeamA, 4, HexLt))
  lunits.setArr(4, 8, LunitState(TeamB, 1, HexLt, HexDL), LunitState(TeamB, 2))
  override val teamSet: RArr[Team] = RArr(TeamA, TeamB)
}

/** 2nd Scenario of Game Four. Has a larger number of hexs. */
object G3HScen3 extends G3HScen(0)
{ override implicit val gridSys: HGrid = HGridReg(2, 20, 4, 60)
  val lunits: HCenArrLayer[LunitState] = HCenArrLayer()
 // lunits.setSomeMut(4, 4, LunitState(TeamA))
 override val teamSet: RArr[Team] = RArr()
}

/** 3rd Scenario of Game Four. Has a larger number of hexs. */
object G3HScen4 extends G3HScen(0)
{ override implicit val gridSys: HGridReg = HGridReg(2, 6, 2, 10)

  val lunits: HCenArrLayer[LunitState] = HCenArrLayer()
  override val teamSet: RArr[Team] = RArr()
}