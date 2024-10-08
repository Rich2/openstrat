/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => _, _}, prid.phex._, WTiles._

object LayerTest extends TestSuite
{
  val tests = Tests {
    test("layer")
    { assert("HRow(4;)".asTypeOld[LayerHcRefRow[Land]] === Good(LayerHcRefRow[Land](4)))
      assert("HRow(4; hilly)".asTypeOld[LayerHcRefRow[Land]] === Good(LayerHcRefRow[Land](4, hillyOce)))
      assert("HRow(4; hilly; hilly)".asTypeOld[LayerHcRefRow[Land]] === Good(LayerHcRefRow[Land](4, hillyOce, hillyOce)))
      assert("HRow(8; sea; Land(Hilly; Tropical); lake * 2)".asTypeOld[LayerHcRefRow[WTile]] === Good(LayerHcRefRow(8, sea, Land(Hilly, Tropical), lake, lake)))
      assert("8; sea; Land(Hilly; Tropical); lake * 2".asTypeOld[LayerHcRefRow[WTile]] === Good(LayerHcRefRow(8, sea, Land(Hilly, Tropical), lake, lake)))
      assert("8, sea, Land(Hilly; Tropical), lake * 2".asTypeOld[LayerHcRefRow[WTile]] === Good(LayerHcRefRow(8, sea, Land(Hilly, Tropical), lake, lake)))
      assert("HRow(3; sea; lake)".asTypeOld[LayerHcRefRow[Water]].isBad)
      LayerHcRefRow(8, sea, Land(Hilly, Tropical), lake, lake).str ==> "HRow(8; Sea; Land(Hilly; Tropical); Lake; Lake)"
    }
  }
}