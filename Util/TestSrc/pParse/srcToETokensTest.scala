/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._ 

object srcToETokensTest extends TestSuite
{ 
  val tests = Tests {
    val Sp1 = StrPosn(1, 1)
    val Sp2 = StrPosn(1, 2)
    val Sp4 = StrPosn(1, 4)

    test("Single")
    { Sp1 ==> StrPosn(1, 1)

      assertMatch("\'a\'".parseTokensOld){ case Good(Arr1(CharToken(_, 'a'))) => }
      assertMatch("ZyId".parseTokensOld){ case Good(Arr1(IdentUpperToken(Sp1, "ZyId"))) => }
      assertMatch("MyId".parseTokensOld){ case Good(Arr1(IdentUpperToken(Sp1, "MyId"))) => }
      assertMatch("My3".parseTokensOld){ case Good(Arr1(IdentUpperToken(Sp1, "My3"))) => }
      assertMatch("My3Id".parseTokensOld){ case Good(Arr1(IdentUpperToken(Sp1, "My3Id"))) => }
      assertMatch("ab3_5fG".parseTokensOld){ case Good(Arr1(IdentLowerToken(Sp1, "ab3_5fG"))) => }

      assertMatch(",".parseTokensOld){ case Good(Arr1(CommaToken(Sp1))) => }
      assertMatch("{".parseTokensOld){ case Good(Arr1(CurlyOpenToken(Sp1))) => }
      assertMatch("}".parseTokensOld){ case Good(Arr1(CurlyCloseToken(Sp1))) => }
      assertMatch("(".parseTokensOld){ case Good(Arr1(ParenthOpenToken(Sp1))) => }
      assertMatch(")".parseTokensOld){ case Good(Arr1(ParenthCloseToken(Sp1))) => }
      assertMatch(" [".parseTokensOld){ case Good(Arr1(SquareOpenToken(Sp2))) => }
      assertMatch(" ]".parseTokensOld){ case Good(Arr1(SquareCloseToken(Sp2))) => }
      assertMatch(";".parseTokensOld){ case Good(Arr1(SemicolonToken(Sp1))) => }

      assertMatch("=".parseTokensOld){case Good(Arr1(AsignToken(Sp1))) => }

      "#".parseTokensOld.isBad ==> true
    }

    val C1 = IdentUpperOnlyToken(Sp1, "Colour")
    val st1 = """appStr = "20";
    displayX = 0;
    displayY = 0;"""

    val et1 = st1.parseTokensOld
    val r1: Tokens = et1.get

    test("Multiple")
    { assertMatch(";;".parseTokensOld){ case Good(Arr2(SemicolonToken(Sp1), SemicolonToken(Sp2))) => }
      assertMatch(" ; .".parseTokensOld){ case Good(Arr2(SemicolonToken(Sp2), DotToken(Sp4))) => }
      assertMatch("Colour(0xFF000000)".parseTokensOld){ case Good(Arr4(C1, ParenthOpenToken(_), Nat0xToken(_, "FF000000"), ParenthCloseToken(_))) => }
      assertMatch(et1){case Good(_) => }
      r1.length ==> 12
      assertMatch(r1){ case ArrHead4(IdentLowerToken(Sp1, "appStr"), AsignToken(_), StringToken(_, "20"), SemicolonToken(_)) => }
    }

    val st2 = """/* This is a comment."""
    val st3 = st1 + st2
    val et3 = st3.parseTokensOld
    val r3 = et3.get
    val st4 = "\n End of Comment. */"
    val st5 = st1 + "\n" + st2 + st4
    val et5 = st5.parseTokensOld
    implicit val r5: RArr[Token] = et5.get
    val ro6: ArrOff[Token] = r5.offset(4)

    test("Settings")
    { assertMatch(st2.parseTokensOld){ case Good(Arr0()) => }
      assertMatch(et3){case Good(_) => }
      r3.length ==> 12
      assertMatch(et5){case Good(_) => }
      r5.length ==> 12
      ro6.length ==> 8
      assertMatch(r5(4)){ case IdentLowerToken(_, "displayX") => }
      assert("Gh * 5".parseTokensOld.isGood)
    }


  }
}