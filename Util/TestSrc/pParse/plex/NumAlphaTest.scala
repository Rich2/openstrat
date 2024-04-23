/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

object NumAlphaTest extends TestSuite
{
  val tests = Tests {
    val i1 = "86km".parseTokens
    test("Num Alpha 1")
    {
      i1.isGood ==> true
    }
  }
}
