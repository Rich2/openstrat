/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => _, _}, WTiles._

object WTileTest extends TestSuite
{
  val tests = Tests {
    val ld1 = Land(Mountains, DesertHot)
    val lds1: RArr[Land] = RArr(oceForest, hillyOce, Land(Mountains, DesertHot))
    val lStr1 = "Seq(oceForest; hillyOce; Mountains, Deshot)"

    test("show")
    { oceForest.str ==> "Land(Plain; Oceanic; Forest)"
      RArr(Lake, Sea).str ==> "Seq(Lake; Sea)"
      Land.persistEv.show(ld1, ShowSemis) ==> "Mountains; Deshot"
      Land(Mountains, DesertHot).strSemi ==> "Mountains; Deshot"
      lds1.str ==> lStr1
    }

    test("Unshow")
    {  
//      assert("Seq(Lake; Sea)".asType[RArr[Water]] === Right(RArr(Lake, Sea)))
//      "Land(Plain; Oceanic; Forest)".asType[Land] ==> Right(oceForest)
//      "Land(Hilly; Oceanic; MixedUse)".asType[Land] ==> Right(Land(Hilly, Oceanic))
//      "Land(Hilly; Oceanic)".asType[Land] ==> Right(Land(Hilly, Oceanic))
//      "Land(Hilly; Oceanic)".asType[Land] ==> Right(Land(Hilly, Oceanic))
//      "Land(Plain; Oceanic)".asType[Land] ==> Right(Land(Plain, Oceanic))
//      "Land(use = Forest; Plain; Oceanic)".asType[Land] ==> Right(Land(Plain, Oceanic, Forest))
//      "Land(use = Forest; elev = Hilly; Oceanic)".asType[Land] ==> Right(Land(Hilly, Oceanic, Forest))
//      "Land(use = Forest; climate = Savannah; elev = Hilly)".asType[Land] ==> Right(Land(Hilly, Savannah, Forest))
//      "Land(use = Forest; climate = Savannah; Hilly)".asType[Land] ==> Right(Land(Hilly, Savannah, Forest))
//      assert("Seq(Lake; Land(Hilly; Oceanic))".asType[RArr[WTile]] === Right(RArr(Lake, hillyOce)))
    }

    test("Multiple")
    { "Multiple(Lake; 3)".asType[Multiple[Water]] ==> Right(Multiple(Lake, 3))
      "Multiple[Land](Land(Hilly; Sahel); 3)".asType[Multiple[Land]] ==> Right(Multiple(Land(Hilly, Sahel), 3))
      "Land(Hilly; Sahel)".asType[Multiple[Land]] ==> Right(Multiple(Land(Hilly, Sahel), 1))
      "Sea * 5".asType[Multiple[Water]] ==> Right(Multiple(Sea, 5))
      "Lake * 3".asType[Multiple[Water]] ==> Right(Multiple(Lake, 3))
      "Land(Plain; Oceanic) * 3".asType[Multiple[Land]] ==> Right(Multiple(Land(Plain, Oceanic), 3))
      "Land(Hilly; Oceanic) * 3".asType[Multiple[WTile]] ==> Right(Multiple(Land(Hilly, Oceanic), 3))
      "hillyOce * 3".asType[Multiple[Land]] ==> Right(Multiple(Land(Hilly, Oceanic), 3))
      "oceForest * 2".asType[Multiple[Land]] ==> Right(Multiple(oceForest, 2))
      "sea * 2".asType[Multiple[Water]] ==> Right(Multiple(sea, 2))
    }

    val er1 = "Seq(oceanic; lake; Land(Hilly; Savannah))".asType[RArr[WTile]]

    test("W Seqs")
    { 
//      assert("Seq(sea; oceForest)".asType[RArr[WTile]] === Right(RArr(sea, oceForest)))
//      assert(RArr(oceanic, lake, Land(Hilly, Savannah)) === RArr(oceanic, lake , Land(Hilly, Savannah)))
//      assert(Right(RArr(oceanic, lake, Land(Hilly, Savannah))) === Right(RArr(oceanic, lake , Land(Hilly, Savannah))))
//      assert(er1 === Right(RArr(oceanic, lake , hillySavannah)))
//      assert("Seq(sea * 2; lake)".asType[RArr[Water]] === Right(RArr(sea, sea, lake)))
//      assert("Seq(hillyOce * 2; oceanic * 3)".asType[RArr[Land]] === Right(RArr(hillyOce, hillyOce, oceanic, oceanic, oceanic)))
//      assert("Seq(hillyOce * 2; lake * 2; oceForest)".asType[RArr[WTile]] === Right(RArr(hillyOce, hillyOce, lake, lake, oceForest)))
    }
  }
}