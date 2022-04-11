/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package prid; package psq
import utest._

object SquareTest  extends TestSuite
{
  val tests = Tests {
    test("test1") {
      SqUR.sqCen ==> SqCen(2, 2)
      SqRt.sqCen ==> SqCen(0, 2)
      SqDR.sqCen ==> SqCen(-2, 2)
      SqDn.sqCen ==> SqCen(-2, 0)
      SqDL.sqCen ==> SqCen(-2, -2)
      SqLt.sqCen ==> SqCen(0, -2)
      SqUL.sqCen ==> SqCen(2, -2)
    }
  }
}
