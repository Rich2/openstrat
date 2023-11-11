/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest._, WTile._

object WTileTest extends TestSuite
{
  val tests = Tests {
    val lds1 = RArr(forest, hills, Land(Mountains, Desert))
    val lStr1 = "Seq(Level, Temperate, Forest; Hilly, Temperate, MixedUse; Mountain, Desert, MixedUse)"
    test("show")
    { forest.str ==> "Land(Level; Temperate; Forest)"
      "Land(Level; Temperate; Forest)".asType[Land] ==> Good(forest)
      lds1.str ==> lStr1
     // assert(lStr1.asType[RArr[WTile]].isGood)// === Good(lds1)
      RArr(Lake, Sea).str ==> "Seq(Lake; Sea)"
//      assert(lStr1.asType[RArr[Land]] === Good(RArr(Lake, Sea)))
    }
  }
}