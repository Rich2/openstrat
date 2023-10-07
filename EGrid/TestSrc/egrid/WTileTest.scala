/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest._, WTile._

object WTileTest extends TestSuite
{
  val tests = Tests {
    val lStr1 = "Seq(Level, Temperate, Forest; Hilly, Temperate, Mixed use)"
    test("show")
    { forest.str ==> "Land(Level; Temperate; Forest)"
      RArr(forest, hills).str ==> lStr1
      RArr(Lake, Sea).str ==> "Seq(Lake; Sea)"
//      assert(lStr1.asType[RArr[Land]] === Good(RArr(Lake, Sea)))
    }
  }
}