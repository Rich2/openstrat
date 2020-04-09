/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package gOne
import utest._, pGrid._

object OneScen1Test  extends TestSuite
{
  val os1 = OneScen1
  val g1 = os1.grid
  val os2 = os1.turn(Refs())
  val g2 = os2.grid
  val os3 = os1.turn(Refs(HTileAndStep(4, 4, HTStepUL), HTileAndStep(4, 8, HTStepUL), HTileAndStep(6, 10, HTStepLt)))
  val g3 = os3.grid

  val tests = Tests
  {
    "os1" -
      { g1.numOfRows ==> 3
        g1.yTileMin ==> 2
        g1.yTileMax ==> 6
        g1.cTileMin ==> 2
        g1.cTileMax ==> 10
        g1.numOfTiles ==> 8
      }

    "os2" -
      { g2.numOfRows ==> 3
        g2.yTileMin ==> 2
        g2.yTileMax ==> 6
        g2.cTileMin ==> 2
        g2.cTileMax ==> 10
        g2.numOfTiles ==> 8
      }

    "os3" -
    {
      g3.numOfTiles ==> 8
      //os3.oPlayers(1) ==> NoRef
//      os3.oPlayers(4, 4)(os3.grid) ==> NoRef
//      os3.oPlayersOld(os3.grid.index(6, 2)) ==> OptRef(PlayerA)
//      os3.oPlayersOld(os3.grid.index(6, 6)) ==> NoRef
//      os3.oPlayersOld(os3.grid.index(4, 8)) ==> OptRef(PlayerB)
    }

    "Sides" -
      {
        g1.sideRoords.length ==> 35
      }
  }
}
