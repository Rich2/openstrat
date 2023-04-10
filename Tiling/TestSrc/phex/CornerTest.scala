/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._, gOne.hp1._, gPlay._

object CorenrTest  extends TestSuite
{
  implicit val grid: HGridReg = HGridReg(2, 10, 2, 8)
  val cns = grid.newHVertOffsetLayer
  cns.setCornerPair(4, 4, 2, HVRt, HVLt)
  val res: HVOffset = cns.cornerV1(HCen(4, 4), 2)

  val tests = Tests {

    test("Test1") {
      res.hvDirn ==> HVRt
    }


  }
}