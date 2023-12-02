/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => _, _}, prid.phex._, WTiles._

object LayerTest extends TestSuite
{
  val tests = Tests {
    test("layer")
    { assert("HRow(4; ;)".asType[LayerHcRow[Land]].isGood) // === Good(new LayerHcRow(4)))
      assert("HRow(4; Seq(hilly))".asType[LayerHcRow[Land]].isGood)
    }
  }
}
