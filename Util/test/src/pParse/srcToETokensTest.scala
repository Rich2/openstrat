/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest.* 

object srcToETokensTest extends TestSuite
{ 
  val tests = Tests {
    val Sp1 = StrPosn(1, 1)
    val Sp2 = StrPosn(1, 2)
    val Sp4 = StrPosn(1, 4)

    test("Single")
    { Sp1 ==> StrPosn(1, 1)

      assertMatch("\'a\'".parseTokens){ case Right(Arr1(CharToken(_, 'a'))) => }
      assertMatch("ZyId".parseTokens){ case Right(Arr1(IdentUpperToken(Sp1, "ZyId"))) => }
      assertMatch("MyId".parseTokens){ case Right(Arr1(IdentUpperToken(Sp1, "MyId"))) => }
      assertMatch("My3".parseTokens){ case Right(Arr1(IdentUpperToken(Sp1, "My3"))) => }
      assertMatch("My3Id".parseTokens){ case Right(Arr1(IdentUpperToken(Sp1, "My3Id"))) => }
      assertMatch("ab3_5fG".parseTokens){ case Right(Arr1(IdentLowerToken(Sp1, "ab3_5fG"))) => }

      assertMatch(",".parseTokens){ case Right(Arr1(CommaToken(Sp1))) => }
      assertMatch("{".parseTokens){ case Right(Arr1(CurlyOpenToken(Sp1))) => }
      assertMatch("}".parseTokens){ case Right(Arr1(CurlyCloseToken(Sp1))) => }
      assertMatch("(".parseTokens){ case Right(Arr1(ParenthOpenToken(Sp1))) => }
      assertMatch(")".parseTokens){ case Right(Arr1(ParenthCloseToken(Sp1))) => }
      assertMatch(" [".parseTokens){ case Right(Arr1(SquareOpenToken(Sp2))) => }
      assertMatch(" ]".parseTokens){ case Right(Arr1(SquareCloseToken(Sp2))) => }
      assertMatch(";".parseTokens){ case Right(Arr1(SemicolonToken(Sp1))) => }
      assertMatch("=".parseTokens){case Right(Arr1(AsignToken(Sp1))) => }

      "#".parseTokens.isLeft ==> true
    }

    val C1 = IdentUpperOnlyToken(Sp1, "Colour")
    val st1 = """appStr = "20";
    displayX = 0;
    displayY = 0;"""

    val et1: ParseExcEither[RArr[Token]] = st1.parseTokens    

    test("Multiple")
    { assertMatch(";;".parseTokens){ case Right(Arr2(SemicolonToken(Sp1), SemicolonToken(Sp2))) => }
      assertMatch(" ; .".parseTokens){ case Right(Arr2(SemicolonToken(Sp2), DotToken(Sp4))) => }
      assertMatch("Colour(0xFF000000)".parseTokens){ case Right(Arr4(C1, ParenthOpenToken(_), Nat0xToken(_, "FF000000"), ParenthCloseToken(_))) => }
      assertMatch(et1){case Right(_) => }
      et1.map(_.length) ==> Right(12)      
    }

    val st2 = """/* This is a comment."""
    val st3 = st1 + st2
    val et3: ParseExcEither[RArr[Token]] = st3.parseTokens    
    val st4 = "\n End of Comment. */"
    val st5 = st1 + "\n" + st2 + st4
    val et5 = st5.parseTokens

    test("Settings")
    {  assertMatch(st2.parseTokens){ case Right(Arr0()) => }
      assertMatch(et3){case Right(_) => }
      et3.map(_.length) ==> Right(12)
      assertMatch(et5){case Right(_) => }
      et5.map(_.length) ==> Right(12)      
      assert("Gh * 5".parseTokens.isRight)
    }


  }
}