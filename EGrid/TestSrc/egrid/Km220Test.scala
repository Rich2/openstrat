/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => *, *}, prid.phex.*, eg220.*

object Km220Test extends TestSuite
{
  val tests = Tests {
    val gs = Scen220Europe.gridSys
    test("test1")
    {
      gs.hSepExists(194, 1532) ==> true
      gs.hSepExists(195, 1535) ==> true
    }
  }
}