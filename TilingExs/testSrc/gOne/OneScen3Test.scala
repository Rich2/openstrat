/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import utest._, prid._, phex._

object OneScen3Test  extends TestSuite
{
  val os1 = OneScen3
  val g1 = os1.grid
  val os2 = os1.endTurn(Arr())
  val g2 = os2.grid
  val os3 = os1.endTurn(Arr((PlayerA, HStepUL), (PlayerB, HStepUL), (PlayerC, HStepLt)))
  val g3 = os3.grid
  val os4 = os3.endTurn(Arr((PlayerA, HStepLt)))

  val tests = Tests {
    test("os1")

    { g1.numTileRows ==> 5
      g1.bottomCenRow ==> 2
      g1.topCenRow ==> 10
      g1.leftCenCol ==> 2
      g1.rightCenCol ==> 10
      g1.numTiles ==> 9
      implicit val grid = os1.grid
      os1.oPlayers.numSomes ==> 3
      os1.oPlayers(4, 4) ==> Some(PlayerA)
    }

    test("os2")
    { g2.numTileRows ==> 5
      g2.bottomCenRow ==> 2
      g2.topCenRow ==> 10
      g2.leftCenCol ==> 2
      g2.rightCenCol ==> 10
      g2.numTiles ==> 9
    }

    test("os3")
    { g3.numTiles ==> 9
      implicit val grid = os3.grid
      os3.oPlayers(2, 2) ==> None
      os3.oPlayers(4, 4) ==> None
      os3.oPlayers(6, 2) ==> Some(PlayerA)
      os3.oPlayers(6, 6) ==> None
      os3.oPlayers(10, 6) ==> Some(PlayerB)
    }

    test("os4")
    { os4.oPlayers.numSomes ==> 3
      implicit val grid = os4.grid
      os4.oPlayers.find(PlayerA) ==> Some(HCen(6, 2))
    }
  }
}
