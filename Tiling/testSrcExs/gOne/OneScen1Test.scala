/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import utest._, prid._

object OneScen1Test  extends TestSuite
{
  val os1 = OneScen1
  val g1 = os1.grid
  val os2 = os1.endTurn(Arr())
  val g2 = os2.grid
  val os3 = os1.endTurn(Arr(HexAndStep(4, 4, HStepUL), HexAndStep(4, 8, HStepUL), HexAndStep(6, 10, HStepLt)))
  val g3 = os3.grid

  val tests = Tests
  {
    "os1" -
      { g1.numCenRows ==> 3
        g1.cenRowMin ==> 2
        g1.cenRMax ==> 6
        g1.cenColMin ==> 2
        g1.cenColMax ==> 10
        g1.numCens ==> 8
      }

    "os2" -
      { g2.numCenRows ==> 3
        g2.cenRowMin ==> 2
        g2.cenRMax ==> 6
        g2.cenColMin ==> 2
        g2.cenColMax ==> 10
        g2.numCens ==> 8
      }

    "os3" -
    {
      g3.numCens ==> 8
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