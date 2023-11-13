/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest._, WTile._, prid.phex._

object WTileTest extends TestSuite
{
  val tests = Tests {
    val lds1: RArr[Land] = RArr(forest, hills, Land(Mountains, Desert))
    val lStr1 = "Seq(Level, Temperate, Forest; Hilly; Mountain, Desert)"

    test("show")
    { forest.str ==> "Land(Level; Temperate; Forest)"
      RArr(Lake, Sea).str ==> "Seq(Lake; Sea)"
    }

    test("Unshow")
    {  assert("Seq(Lake; Sea)".asType[RArr[Water]] === Good(RArr(Lake, Sea)))
      "Land(Level; Temperate; Forest)".asType[Land] ==> Good(forest)
      "Land(Hilly; Temperate; MixedUse)".asType[Land] ==> Good(Land(Hilly))
      "Land(Hilly; Temperate)".asType[Land] ==> Good(Land(Hilly))
      "Land(Hilly)".asType[Land] ==> Good(Land(Hilly))
      "Land()".asType[Land] ==> Good(Land())
      assert("Seq(Lake; Land(Hilly))".asType[RArr[WTile]] === Good(RArr(Lake, hills)))

      //"HRow(4; Sea, Lake)".asType[HCenRowLayer[Water]]
    }
  }
}