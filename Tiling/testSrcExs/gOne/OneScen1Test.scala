/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package gOne
import utest._, prid._

object OneScen1Test  extends TestSuite
{
  val os1 = OneScen1
  val g1 = os1.grid
  val os2 = os1.turn(Arr())
  val g2 = os2.grid
  val os3 = os1.turn(Arr(HexAndStep(4, 4, HexStepUL), HexAndStep(4, 8, HexStepUL), HexAndStep(6, 10, HexStepLt)))
  val g3 = os3.grid

  val tests = Tests
  {
    "os1" -
      { g1.numOfTileRows ==> 3
        g1.rTileMin ==> 2
        g1.rTileMax ==> 6
        g1.cTileMin ==> 2
        g1.cTileMax ==> 10
        g1.numOfTiles ==> 8
      }

    "os2" -
      { g2.numOfTileRows ==> 3
        g2.rTileMin ==> 2
        g2.rTileMax ==> 6
        g2.cTileMin ==> 2
        g2.cTileMax ==> 10
        g2.numOfTiles ==> 8
      }

    "os3" -
    {
      g3.numOfTiles ==> 8
//      g1.cSideRowMin(1) ==> 1
//      g1.cSideRowMin(2) ==> 0
//      g1.cSideRowMin(5) ==> 1
      //os3.oPlayers(1) ==> NoRef
//      os3.oPlayers(4, 4)(os3.grid) ==> NoRef
//      os3.oPlayersOld(os3.grid.index(6, 2)) ==> OptRef(PlayerA)
//      os3.oPlayersOld(os3.grid.index(6, 6)) ==> NoRef
//      os3.oPlayersOld(os3.grid.index(4, 8)) ==> OptRef(PlayerB)
    }

    "Sides" -
      {
        //g1.numOfSides ==> 35
      }
  }
}