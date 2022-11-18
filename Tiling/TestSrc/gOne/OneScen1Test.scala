/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import utest._, prid._, phex._, gPlay._

object OneScen1Test  extends TestSuite
{
  val os1: OneScen1.type = OneScen1
  val g1: HGridReg = os1.gridSys
  val os2: OneScen = os1.endTurn(RArr())
  val g2: HGridSys = os2.gridSys
  val os3: OneScen = os1.endTurn(RArr((PlayerA, HStepUL), (PlayerB, HStepUL), (PlayerC, HexLt)))
  val g3: HGridSys = os3.gridSys
  val os4: OneScen = os1.endTurn(RArr((PlayerA, HexLt)))

  val tests = Tests {

    test("os1")
    { g1.numTileRows ==> 3
      g1.bottomCenR ==> 2
      g1.topCenR ==> 6
      g1.leftCenC ==> 2
      g1.rightCenC ==> 10
      g1.numTiles ==> 8
    }

    val hexs: IntArr = g1.map(hc => hc.r * 10 + hc.c)
    val sh: IntArr = g1.sidesMap(hc => hc.r * 10 + hc.c)
    //val isd = g1.innerSides
    //val sha = sh.take(35)
    test("os2")
    {
      (hexs ===  IntArr(22, 26, 30, 44, 48, 62, 66, 70)) ==> true
      sh(10) ==> 31
      (sh === IntArr(11, 13, 15, 17, 19, 21, 20, 24, 28, 32, 31, 33, 35, 37, 39, 41, 42, 46, 50, 51, 53, 55, 57, 59, 61, 60, 64, 68, 72,
        71, 73, 75, 77, 79, 81)) ==> true
      g2.numTileRows ==> 3

      g2.numTiles ==> 8
    }


    test("os3")
    { g3.numTiles ==> 8
      g1.leftRem0CenC ==> 4
      g1.rightRem0CenC ==> 8
      implicit val grid = os3.gridSys
      os3.oPlayers(2, 2) ==> None
      os3.oPlayers(4, 4) ==> None
      os3.oPlayers(6, 2) ==> Some(PlayerA)
      os3.oPlayers(6, 6) ==> None
      os3.oPlayers(4, 8) ==> Some(PlayerB)
      os1.oPlayers.numSomes ==> 3
      os4.oPlayers.numSomes ==> 3
      os1.oPlayers(4, 4) ==> Some(PlayerA)
      os4.oPlayers.find(PlayerA) ==> Some(HCen(4, 4))
    }

    test("Sides")
    {
      g1.numSides ==> 35
    }
  }
}