/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest._, WTile._

object WTileTest extends TestSuite
{
  val tests = Tests {
    val lds1: RArr[Land] = RArr(forest, hills, Land(Mountains, Desert))
    val lStr1 = "Seq(Level, Temperate, Forest; Hilly; Mountain, Desert)"
    test("show")
    { forest.str ==> "Land(Level; Temperate; Forest)"
      "Land(Level; Temperate; Forest)".asType[Land] ==> Good(forest)
      RArr(Lake, Sea).str ==> "Seq(Lake; Sea)"
    }
    test("Unshow"){
      assert("Seq(Lake; Sea)".asType[RArr[Water]] === Good(RArr(Lake, Sea)))
      4 == 4
    }
  }
}