/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => _, _}, WTiles._

object WTileTest extends TestSuite
{
  val tests = Tests {
    val ld1 = Land(Mountains, Desert)
    val lds1: RArr[Land] = RArr(forest, hills, Land(Mountains, Desert))
    val lStr1 = "Seq(Level, Temperate, Forest; Hilly; Mountains, Desert)"

    test("show")
    { forest.str ==> "Land(Level; Temperate; Forest)"
      RArr(Lake, Sea).str ==> "Seq(Lake; Sea)"
      Land.showEv.show(ld1, ShowSemis) ==> "Mountains; Desert"
      Land(Mountains, Desert).strSemi ==> "Mountains; Desert"
      lds1.str ==> lStr1
    }

    test("Unshow")
    {  assert("Seq(Lake; Sea)".asType[RArr[Water]] === Good(RArr(Lake, Sea)))
      "Land(Level; Temperate; Forest)".asType[Land] ==> Good(forest)
      "Land(Hilly; Temperate; MixedUse)".asType[Land] ==> Good(Land(Hilly))
      "Land(Hilly; Temperate)".asType[Land] ==> Good(Land(Hilly))
      "Land(Hilly)".asType[Land] ==> Good(Land(Hilly))
      "Land()".asType[Land] ==> Good(Land())
      "Land(landUse = Forest)".asType[Land] ==> Good(Land(Level, Temperate, Forest))
      "Land(landUse = Forest; elev = Hilly)".asType[Land] ==> Good(Land(Hilly, Temperate, Forest))
      "Land(landUse = Forest; climate = Savannah; elev = Hilly)".asType[Land] ==> Good(Land(Hilly, Savannah, Forest))
      "Land(landUse = Forest; climate = Savannah; Hilly)".asType[Land] ==> Good(Land(Hilly, Savannah, Forest))
      assert("Seq(Lake; Land(Hilly))".asType[RArr[WTile]] === Good(RArr(Lake, hills)))

      //"HRow(4; Sea, Lake)".asType[HCenRowLayer[Water]]
    }
  }
}