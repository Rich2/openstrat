/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest._, WTile._

object WTileTest
  extends TestSuite {

  val tests = Tests {
    test("show")
    { forest.str ==> "Land(Level; Temperate; Forest)"
      RArr(forest, hills).str ==> "Seq(Level, Temperate, Forest; Hilly, Temperate, Mixed use)"
    }
  }
}