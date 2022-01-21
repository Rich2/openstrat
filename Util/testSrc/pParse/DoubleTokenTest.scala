/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

/** Also tests the srcToETokens function object. */
object DoubleTokenTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val Sp2 = StrPosn(1, 2)
  val Sp3 = StrPosn(1, 3)
  val Sp44 = StrPosn(4, 4)

  val tests = Tests {
    "Test1" - {
      4 ==> 4
      assertMatch("4.5".parseTokens){ case Good(Arr1(DeciFracToken(Sp1, "4", "5", ""))) => }
    }
  }

}
