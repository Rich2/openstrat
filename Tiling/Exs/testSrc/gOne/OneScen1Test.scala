/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import utest._, prid._

object OneScen1Test  extends TestSuite
{
  val os1 = OneScen1
  val g1 = os1.grid
  val os2 = os1.endTurn(Arr())
  val g2 = os2.grid
  val os3 = os1.endTurn(Arr((PlayerA, HStepUL), (PlayerB, HStepUL), (PlayerC, HStepLt)))
  val g3 = os3.grid
  val os4 = os1.endTurn(Arr((PlayerA, HStepLt)))

  val tests = Tests {
    "os1" -
      { g1.numTileRows ==> 3
        g1.bottomTileRow ==> 2
        g1.topTileRow ==> 6
        g1.tileColMin ==> 2
        g1.tileColMax ==> 10
        g1.numTiles ==> 8
      }

    "os2" -
      { g2.numTileRows ==> 3
        g2.bottomTileRow ==> 2
        g2.topTileRow ==> 6
        g2.tileColMin ==> 2
        g2.tileColMax ==> 10
        g2.numTiles ==> 8
      }

    "os3" -
    {
      g3.numTiles ==> 8
      g1.row0sStart ==> 4
      g1.row0sEnd ==> 8
      implicit val grid = os3.grid
      os3.oPlayers(2, 2) ==> None
      os3.oPlayers(4, 4) ==> None
      os3.oPlayers(6, 2) ==> Some(PlayerA)
      os3.oPlayers(6, 6) ==> None
      os3.oPlayers(4, 8) ==> Some(PlayerB)
      os1.oPlayers(4, 2) ==> Some(PlayerA)
    }

    "Sides" -
      {
        g1.numSides ==> 35
      }
  }
}