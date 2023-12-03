/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => _, _}, prid.phex._, WTiles._

object LayerTest extends TestSuite
{
  val tests = Tests {
    test("layer")
    { assert("HRow(4;)".asType[LayerHcRow[Land]] === Good(LayerHcRow[Land]()))
      assert("HRow(4; hilly)".asType[LayerHcRow[Land]] === Good(LayerHcRow[Land](hilly)))
      assert("HRow(4; hilly; hilly)".asType[LayerHcRow[Land]] === Good(LayerHcRow[Land](hilly, hilly)))
      assert("HRow(4; sea; lake)".asType[LayerHcRow[Water]].isGood)
      assert("HRow(3; sea; lake)".asType[LayerHcRow[Water]].isBad)
    }
  }
}
