/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => _, _}, WTiles._

object WTileTest extends TestSuite
{
  val tests = Tests {
    val ld1 = Land(Mountains, DesertHot)
    val lds1: RArr[Land] = RArr(oceForest, hillyOce, Land(Mountains, DesertHot))
    val lStr1 = "Seq(forest; hilly; Mountains, Desert)"

    test("show")
    { oceForest.str ==> "Land(Level; Temperate; Forest)"
      RArr(Lake, Sea).str ==> "Seq(Lake; Sea)"
      Land.persistEv.show(ld1, ShowSemis) ==> "Mountains; Desert"
      Land(Mountains, DesertHot).strSemi ==> "Mountains; Desert"
      lds1.str ==> lStr1
    }

    test("Unshow")
    {  assert("Seq(Lake; Sea)".asTypeOld[RArr[Water]] === Good(RArr(Lake, Sea)))
      "Land(Level; Temperate; Forest)".asTypeOld[Land] ==> Good(oceForest)
      "Land(Hilly; Temperate; MixedUse)".asTypeOld[Land] ==> Good(Land(Hilly))
      "Land(Hilly; Temperate)".asTypeOld[Land] ==> Good(Land(Hilly))
      "Land(Hilly)".asTypeOld[Land] ==> Good(Land(Hilly))
      "Land()".asTypeOld[Land] ==> Good(Land())
      "Land(use = Forest)".asTypeOld[Land] ==> Good(Land(Plain, Oceanic, Forest))
      "Land(use = Forest; elev = Hilly)".asTypeOld[Land] ==> Good(Land(Hilly, Oceanic, Forest))
      "Land(use = Forest; climate = Savannah; elev = Hilly)".asTypeOld[Land] ==> Good(Land(Hilly, Savannah, Forest))
      "Land(use = Forest; climate = Savannah; Hilly)".asTypeOld[Land] ==> Good(Land(Hilly, Savannah, Forest))
      assert("Seq(Lake; Land(Hilly))".asTypeOld[RArr[WTile]] === Good(RArr(Lake, hillyOce)))
    }

    test("Multiple")
    { "Multiple(Lake; 3)".asTypeOld[Multiple[Water]] ==> Good(Multiple(Lake, 3))
      "Multiple[Land](Land(Hilly; Sahel); 3)".asTypeOld[Multiple[Land]] ==> Good(Multiple(Land(Hilly, Sahel), 3))
      "Land(Hilly; Sahel)".asTypeOld[Multiple[Land]] ==> Good(Multiple(Land(Hilly, Sahel), 1))
      "Sea * 5".asTypeOld[Multiple[Water]] ==> Good(Multiple(Sea, 5))
      "Lake * 3".asTypeOld[Multiple[Water]] ==> Good(Multiple(Lake, 3))
      "Land() * 3".asTypeOld[Multiple[Land]] ==> Good(Multiple(Land(), 3))
      "Land(Hilly) * 3".asTypeOld[Multiple[WTile]] ==> Good(Multiple(Land(Hilly, Oceanic), 3))
      "hilly * 3".asTypeOld[Multiple[Land]] ==> Good(Multiple(Land(Hilly, Oceanic), 3))
      "forest * 2".asTypeOld[Multiple[Land]] ==> Good(Multiple(oceForest, 2))
      "sea * 2".asTypeOld[Multiple[Water]] ==> Good(Multiple(sea, 2))

    }

    val er1 = "Seq(land; lake; Land(Hilly; Savannah))".asTypeOld[RArr[WTile]]
    println(er1)

    test("W Seqs")
    { assert("Seq(sea; forest)".asTypeOld[RArr[WTile]] === Good(RArr(sea, oceForest)))
      assert(RArr(oceanic, lake, Land(Hilly, Savannah)) === RArr(oceanic, lake , Land(Hilly, Savannah)))
      assert(Good(RArr(oceanic, lake, Land(Hilly, Savannah))) === Good(RArr(oceanic, lake , Land(Hilly, Savannah))))
      assert(er1 === Good(RArr(oceanic, lake , hillySavannah)))
      assert("Seq(sea * 2; lake)".asTypeOld[RArr[Water]] === Good(RArr(sea, sea, lake)))
      assert("Seq(hilly * 2; land * 3)".asTypeOld[RArr[Land]] === Good(RArr(hillyOce, hillyOce, oceanic, oceanic, oceanic)))
      assert("Seq(hilly * 2; lake * 2; forest)".asTypeOld[RArr[WTile]] === Good(RArr(hillyOce, hillyOce, lake, lake, oceForest)))
    }
  }
}