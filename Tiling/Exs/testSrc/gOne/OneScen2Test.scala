/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gOne
import utest._

object OneScen2Test  extends TestSuite
{
  val g = OneScen2.grid
  val tests = Tests {
    "test1" -
    { g.bottomCenRow ==> 2
      g.topCenRow ==> 10
      g.leftCenCol ==> 4
      g.rightCenCol ==> 8
      g.numRow0s ==> 2
      g.bottomRem2Row ==> 2
      g.topRem2Row ==> 10
      g.numRow2s ==> 3
      g.numTileRows ==> 5
      g.row2sStart ==> 6
      g.row0sStart ==> 4
//      g. cRowStart(2) ==> 2
//      g.cRowLen(2) ==> 3
//      g.cRowEnd(2) ==> 10
//      g.cRowLen(4) ==> 2
//      g.cRowLen(6) ==> 1
//      g.cRowEnd(6) ==> 6
//      g.cRowLen(8) ==> 2
//      g.cRowEnd(8) ==> 8
//      g.numOfTiles ==> 8
    }

    "Sides" -
    {
      //g.sideRoords.elemsLen ==> 36
    }
   }
}