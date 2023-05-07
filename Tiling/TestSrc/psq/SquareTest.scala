/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import utest._

object SquareTest  extends TestSuite
{
  val tests = Tests {
    test("test1") {
      SqUR.sqCenDelta ==> SqCen(2, 2)
      SqRt.sqCenDelta ==> SqCen(0, 2)
      SqDR.sqCenDelta ==> SqCen(-2, 2)
      SqDn.sqCenDelta ==> SqCen(-2, 0)
      SqDL.sqCenDelta ==> SqCen(-2, -2)
      SqLt.sqCenDelta ==> SqCen(0, -2)
      SqUL.sqCenDelta ==> SqCen(2, -2)
    }
  }
}
