/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._

object HGridTest extends TestSuite
{
  val g1: HGridReg = HGridReg(2, 6, 2, 10)
  val g2: HGrids2 = HGrids2(2, 8, 2, 6, 100, 104)

  val tests = Tests {
    test("test1") {
      g1.findStep(2, 2, 4, 4) ==> Some(HStepUR)
      g1.findStep(2, 2, 2, 6) ==> Some(HStepRt)
      g1.findStep(2, 2, 4, 0) ==> None
      g1.findStep(2, 2, 0, 4) ==> None
    }

    test("test2") {
      g2.unsafeGetMan(4, 4).grid.leftCenC ==> 2
      g2.unsafeGetMan(4, 4).grid.hCenExists(4, 100) ==> false
      g2.unsafeGetMan(4, 4).outSteps(4, 4).length ==> 1
      g2.unsafeGetMan(4, 4).outSteps(4, 4)(0) ==> (HStepRt, HCen(4, 100))
      g2.findStep(4, 4, 4, 100) ==> Some(HStepRt)
      g2.findStep(6, 6, 6, 102) ==> Some(HStepRt)
    }
  }
}