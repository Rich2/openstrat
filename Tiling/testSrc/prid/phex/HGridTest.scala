/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._

object HGridTest extends TestSuite
{
  val g1: HGridReg = HGridReg(2, 6, 2, 10)

  val tests = Tests {
    test("test1") {
      g1.findStep(2, 2, 4, 4) ==> Some(HStepUR)
      g1.findStep(2, 2, 2, 6) ==> Some(HStepRt)
      g1.findStep(2, 2, 4, 0) ==> None
      g1.findStep(2, 2, 0, 4) ==> None
    }
  }
}