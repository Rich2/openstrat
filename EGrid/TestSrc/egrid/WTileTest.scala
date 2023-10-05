/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest._, prid.phex._, WTile._

object WTileTest
  extends TestSuite {

  val tests = Tests {
    test("Adjacents") {
      forest.str ==> "WTile(Level; Temperate; Forest)"
    }
  }
}