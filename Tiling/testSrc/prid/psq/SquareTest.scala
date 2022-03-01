/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import utest._

object SquareTest  extends TestSuite
{
  val tests = Tests {
    test("test1") {
      SqStepUR.sqCen ==> SqCen(2, 2)
      SqStepRt.sqCen ==> SqCen(0, 2)
      SqStepDR.sqCen ==> SqCen(-2, 2)
      SqStepDn.sqCen ==> SqCen(-2, 0)
      SqStepDL.sqCen ==> SqCen(-2, -2)
      SqStepLt.sqCen ==> SqCen(0, -2)
      SqStepUL.sqCen ==> SqCen(2, -2)
    }
  }
}
