/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse
import utest._ 

object srcToETokensTest extends TestSuite
{ 
  val tests = Tests
  { val Sp1 = StrPosn(1, 1)
    val Sp2 = StrPosn(1, 2)
    val Sp3 = StrPosn(1, 3)
    val Sp4 = StrPosn(1, 4)

    'Single
    { Sp1 ==> StrPosn(1, 1)

      assertMatch("\'a\'".findTokens){ case GoodRefs1(CharToken(_, 'a')) => }
      assertMatch("MyId".findTokens){ case GoodRefs1(IdentifierUpperOnlyToken(Sp1, "MyId")) => }
      assertMatch("My3".findTokens){ case GoodRefs1(IdentifierUpperOnlyToken(Sp1, "My3")) => }
      assertMatch("My3Id".findTokens){ case GoodRefs1(IdentifierUpperOnlyToken(Sp1, "My3Id")) => }
      assertMatch("ab3_5fG".findTokens){ case GoodRefs1(IdentifierLowerOnlyToken(Sp1, "ab3_5fG")) => }

      assertMatch(",".findTokens){ case GoodRefs1(CommaToken(Sp1)) => }
      assertMatch("{".findTokens){ case GoodRefs1(CurlyOpen(Sp1)) => }
      assertMatch("}".findTokens){ case GoodRefs1(CurlyClose(Sp1)) => }
      assertMatch("(".findTokens){ case GoodRefs1(ParenthOpen(Sp1)) => }
      assertMatch(")".findTokens){ case GoodRefs1(ParenthClose(Sp1)) => }
      assertMatch(" [".findTokens){ case GoodRefs1(SquareOpen(Sp2)) => }
      assertMatch(" ]".findTokens){ case GoodRefs1(SquareClose(Sp2)) => }
      assertMatch(";".findTokens){ case GoodRefs1(SemicolonToken(Sp1)) => }

      assertMatch("=".findTokens){case GoodRefs1(AsignToken(Sp1)) => }

      "#".findTokens.isBad ==> true
    }

    val C1 = IdentifierUpperOnlyToken(Sp1, "Colour")
    val st1 = """appStr = "20";
    displayX = 0;
    displayY = 0;"""

    val et1 = st1.findTokens
    val r1: Tokens = et1.get

    'Multiple
    {
      assertMatch(";;".findTokens){ case GoodRefs2(SemicolonToken(Sp1), SemicolonToken(Sp2)) => }
      assertMatch(" ; .".findTokens){ case GoodRefs2(SemicolonToken(Sp2), DotToken(Sp4)) => }
      assertMatch("Colour(0xFF000000)".findTokens){ case GoodRefs4(C1, ParenthOpen(_), Hexa0xToken(_, "FF000000"), ParenthClose(_)) => }
      assertMatch(et1){case Good(_) => }
      r1.length ==> 12
      assertMatch(r1){case RefsHead3(IdentifierLowerToken(Sp1, "appStr"), AsignToken(_), StringToken(_, "20")) => }
    }

    val st2 = """/* This is a comment."""
    val st3 = st1 + st2
    val et3 = st3.findTokens
    val r3 = et3.get
    val st4 = "\n End of Comment. */"
    val st5 = st1 + "\n" + st2 + st4
    val et5 = st5.findTokens
    val r5 = et5.get

    'Settings
    {

      assertMatch(st2.findTokens){ case GoodRefs0() => }
      assertMatch(et3){case Good(_) => }
      r3.length ==> 12
      assertMatch(et5){case Good(_) => }
      r5.length ==> 12
    }

    Symbol("Neg")
    {
      assertMatch("-".findTokens){case GoodRefs1(PlusInToken(_, _)) => }
      //assertMatch("- 4".findTokens){case GoodRefs2(PlusInToken(Sp1, "-"), IntDeciToken(Sp3, "4")) => }
      
    }
  }
}
