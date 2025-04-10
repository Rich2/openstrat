/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import utest.{Show => _, _}, prid.phex.*, eg320.*

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
    test("findStep")
    { sys.stepFind(138, 526, 138, 1526) ==> Some(HexRt)
      sys.stepFind(142, 1526, 142, 522) ==> Some(HexLt)
      sys.stepFind(140, 524, 142, 1526) ==> Some(HexUR)
      sys.stepFind(142, 1526, 142, 1530) ==> Some(HexRt)
      sys.stepFind(144, 1528, 146, 522) ==> Some(HexUL)
      sys.stepFind(134, 526, 132, 1524) ==> Some(HexDR)
      sys.stepFind(136, 1524, 134, 526) ==> Some(HexDL)
      sys.stepFind(144, 1528, 142, 1526) ==> Some(HexDL)
    }

    test("findStepEnd")
    { sys.stepEndFind(138, 526, HexRt) ==> Some(HCen(138, 1526))
      sys.stepEndFind(142, 1526, HexLt) ==> Some(HCen(142, 522))
      sys.stepEndFind(140, 524, HexUR) ==> Some(HCen(142, 1526))
      sys.stepEndFind(142, 1534, HexRt) ==> Some(HCen(142, 1538))
      sys.stepEndFind(144, 1528, HexUL) ==> Some(HCen(146, 522))
      sys.stepEndFind(134, 526, HexDR) ==> Some(HCen(132, 1524))
      sys.stepEndFind(136, 1524, HexDL) ==> Some(HCen(134, 526))
      sys.stepEndFind(144, 1528, HexDL) ==> Some(HCen(142, 1526))

      sys.stepEndFind(140, 524, HexUR) ==> Some(HCen(142, 1526))
      sys.stepEndFind(142, 1526, HexRt)==> Some(HCen(142, 1530))
    }
  }
}