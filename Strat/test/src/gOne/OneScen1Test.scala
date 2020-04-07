/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package gOne
import utest._

object OneScen1Test  extends TestSuite
{
  val g = OneScen1.grid
  val tests = Tests
  {
    "test1" -
      { g.numOfRows ==> 3
        g.yTileMin ==> 2
        g.yTileMax ==> 6
        g.cTileMin ==> 2
        g.cTileMax ==> 10
        g.numOfTiles ==> 8
      }

    "Sides" -
      {
        g.sideRoords.length ==> 35
      }
  }
}
