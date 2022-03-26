/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import utest._, prid._, phex._, gPlay._

object OneScen1Test  extends TestSuite
{
  val os1 = OneScen1
  val g1 = os1.grider
  val os2 = os1.endTurn(Arr())
  val g2 = os2.grider
  val os3 = os1.endTurn(Arr((PlayerA, HStepUL), (PlayerB, HStepUL), (PlayerC, HStepLt)))
  val g3 = os3.grider
  val os4 = os1.endTurn(Arr((PlayerA, HStepLt)))

  val tests = Tests {

    test("os1")
    { g1.numTileRows ==> 3
      g1.bottomCenR ==> 2
      g1.topCenRow ==> 6
      g1.leftCenC ==> 2
      g1.rightCenC ==> 10
      g1.numTiles ==> 8
    }

    test("os2")
    { g2.numTileRows ==> 3
      g2.bottomCenRow ==> 2
      g2.topCenRow ==> 6
      g2.leftCenCol ==> 2
      g2.rightCenCol ==> 10
      g2.numTiles ==> 8
    }

    test("os3")
    { g3.numTiles ==> 8
      g1.leftRem0CenC ==> 4
      g1.rightRem0CenC ==> 8
      implicit val grid = os3.grider
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