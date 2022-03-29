/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package phex
import utest._

object HGridTest extends TestSuite
{
  val g1: HGridReg = HGridReg(2, 6, 2, 10)

  val tests = Tests {
    test("test1") {
      g1.findStep(HCen(2, 2), HCen(4, 4)).value ==> Some(HStepUR).value
    }
  }
}