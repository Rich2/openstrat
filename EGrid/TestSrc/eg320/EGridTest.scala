/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import utest._, prid.phex._

object EGridTest extends TestSuite
{
  val sys = EGrid320.multi(2, 0, 124)

  val tests = Tests {
    test("Adjacents")
    { sys.hCenExists(138, 510) ==> true
      sys.hCenExists(146, 1546) ==> true
      sys.adjTilesOfTile(142, 1542) === HCenArr.ints(144,1544,  142,1546,  140,1544,  140,1540,  142,1538,  144,1540) ==> true
      sys.adjTilesOfTile(142, 1546) === HCenArr.ints(140,1548,  140,1544,  142,1542,  144,1544) ==> true
    }
    test("Steps"){
      sys.grids(1).findStepEnd(142, 1534, HexRt).nonEmpty ==> true
      sys.gridMans(1).findStepEnd(142, 1534, HexRt).nonEmpty ==> true
    }
  }
}