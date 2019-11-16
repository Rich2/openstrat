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
    //val ct = "Colour(0xFF000000)".findTokens

    Symbol("Test1")
    { Sp1 ==> StrPosn(1, 1)
      assertMatch("45".findTokens){case GoodRefs1(IntDeciToken(Sp1, 45)) => }
      assertMatch("45".findTokens){case GoodRefs1(IntToken(Sp1, "45", 45)) => }
//w0d      assertMatch("45".findTokens){case GoodRefs1(FloatToken(Sp1, "45", 45)) => }
      assertMatch("\"45\"".findTokens){case GoodRefs1(StringToken(Sp1, "45")) => }
//w0d     assertMatch("a".findTokens){case GoodRefs1(CharToken(Sp1)) => }

//w0d      assertMatch("Ab3_5fG".findTokens){case GoodRefs1(AlphaToken(Sp1, "Ab3_5fG")) => }
      assertMatch(",".findTokens){case GoodRefs1(CommaToken(Sp1)) => }
      assertMatch("0x11".findTokens){case GoodRefs1(IntHexaToken(Sp1, "0x11", 17)) => }
      assertMatch("0x11".findTokens){case GoodRefs1(IntToken(Sp1, "0x11", 17)) => }
      assertMatch("{".findTokens){case GoodRefs1(CurlyOpen(Sp1)) => }
      assertMatch("}".findTokens){case GoodRefs1(CurlyClose(Sp1)) => }
      assertMatch("(".findTokens){case GoodRefs1(ParenthOpen(Sp1)) => }
      assertMatch(")".findTokens){case GoodRefs1(ParenthClose(Sp1)) => }
      assertMatch(" [".findTokens){case GoodRefs1(SquareOpen(Sp2)) => }
      assertMatch(" ]".findTokens){case GoodRefs1(SquareClose(Sp2)) => }
      assertMatch(";".findTokens){case GoodRefs1(SemicolonToken(Sp1)) => }
      assertMatch(";;".findTokens){case GoodRefs2(SemicolonToken(Sp1), SemicolonToken(Sp2)) => }
      assertMatch(" ; .".findTokens){case GoodRefs2(SemicolonToken(Sp2), DotToken(Sp4)) => }
//w0d      assertMatch("-".findTokens){case GoodRefs1(OtherOperatorToken(Sp1, "-")) => }
      assertMatch("=".findTokens){case GoodRefs1(AsignToken(Sp1)) => }
      //assertMatch("-".findTokens){case GoodRefs1(PrefixToken(Sp1, "-")) => }
      "#".findTokens.isBad ==> true
    }

    Symbol("Neg")
    {
      assertMatch("-".findTokens){case GoodRefs1(PlusInToken(_, _)) => }
      assertMatch("- 4".findTokens){case GoodRefs2(PlusInToken(Sp1, "-"), IntDeciToken(Sp3, 4)) => }
      
      
    }
  }
}
