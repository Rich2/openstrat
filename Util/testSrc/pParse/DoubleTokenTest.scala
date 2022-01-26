/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

/** Also tests the srcToETokens function object. */
object DoubleTokenTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val Sp2 = StrPosn(1, 2)
  val Sp3 = StrPosn(1, 3)
  val Sp5 = StrPosn(4, 5)
  val Sp44 = StrPosn(4, 4)


  val tests = Tests {
    "Test1" - {
      4 ==> 4
      assertMatch("4.5".parseTokens){ case Good(Arr1(DeciFracToken(Sp1, "4", "5", ""))) => }
      assertMatch("0.5".parseTokens){ case Good(Arr1(DeciFracToken(Sp1, "0", "5", ""))) => }
      "0.4".findDouble ==> Good(0.4)
      assertMatch("x = 0.4".parseTokens){ case Good(Arr3(IdentLowerOnlyToken(_, "x"), AsignToken(_), DeciFracToken(_, _, _, _))) => }
      assertMatch("271.562".parseTokens){ case Good(Arr1(DeciFracToken(sp1, "271", "562", ""))) => }
      //Note this not a legal AST but it doesn't matter for the purpose of lexical tests
      assertMatch("4.5 4.5".parseTokens){ case Good(Arr2(DeciFracToken(Sp1, "4", "5", ""), DeciFracToken(sp5, "4", "5", ""))) => }
    }
  }

}
