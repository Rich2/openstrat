/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._, gOne.h1p._

object G1HScen2Test  extends TestSuite
{
  val os1 = G1HScen2
  val g1 = os1.gridSys
//  val os2 = os1.endTurn(RArr())
//  val g2 = os2.gridSys
//  val os3 = os1.endTurn(RArr((PlayerA, HStepUL), (PlayerB, HStepUL), (PlayerC, HexLt)))
//  val g3 = os3.gridSys
//  val os4 = os3.endTurn(RArr((PlayerA, HexLt)))

  val tests = Tests {
    test("os1")
    {
      g1.numTileRows ==> 6
      g1.bottomCenR ==> 2
      g1.topCenR ==> 12
      g1.gridLeftCenC ==> 2
      g1.gridRightCenC ==> 60
      g1.numTiles ==> 90
//      implicit val grid = os1.gridSys
//      os1.oPlayers.numSomes ==> 3
//      os1.oPlayers(4, 4) ==> Some(PlayerA)
    }

//    test("os2")
//    { g2.numTileRows ==> 5
//      g2.bottomCenR ==> 2
//      g2.topCenR ==> 10
//      g2.leftCenC ==> 2
//      g2.rightCenC ==> 10
//      g2.numTiles ==> 9
//    }
//
//    test("os3")
//    { g3.numTiles ==> 9
//      implicit val grid = os3.grider
//      os3.oPlayers(2, 2) ==> None
//      os3.oPlayers(4, 4) ==> None
//      os3.oPlayers(6, 2) ==> Some(PlayerA)
//      os3.oPlayers(6, 6) ==> None
//      os3.oPlayers(10, 6) ==> Some(PlayerB)
//    }

    test("os4")
    {
//      os4.oPlayers.numSomes ==> 3
//      implicit val grid = os4.gridSys
//      os4.oPlayers.find(PlayerA) ==> Some(HCen(6, 2))
    }
  }
}