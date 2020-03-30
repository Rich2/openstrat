/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package gOne
import utest._

object Irr1Test  extends TestSuite
{
  val g = Irr1.grid
  val tests = Tests
  {
    "test1" -
    { g.numOfRows ==> 4
      g.yTileMin ==> 2
      g.yTileMax ==> 8
      g.cTileMin ==> 2
      g.cTileMax ==> 10
      g.xRowStart(2) ==> 2
      g.xRowLen(2) ==> 3
      g.xRowEnd(2) ==> 10
      g.xRowLen(4) ==> 2
      g.xRowLen(6) ==> 1
      g.xRowEnd(6) ==> 6
      g.xRowLen(8) ==> 2
      g.xRowEnd(8) ==> 8
      g.numOfTiles ==> 8
    }

    "Sides" -
    {
      g.sideRoords.length ==> 36
    }
   }
}