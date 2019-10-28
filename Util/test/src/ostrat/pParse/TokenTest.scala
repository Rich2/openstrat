/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pParse
import utest._ 

object TokenTest extends TestSuite
{ 
  val tests = Tests
  { val Sp1 = StrPosn(1, 1)
    val Sp2 = StrPosn(1, 2)
    val Sp3 = StrPosn(1, 3)
    val Sp4 = StrPosn(1, 4)
    deb("-5".findTokens.asInstanceOf[Good[Refs[Token]]].value(0).srcStr)
    //val ct = "Colour(0xFF000000)".findTokens

    'Test1
    { Sp1 ==> StrPosn(1, 1)
      assertMatch("45".findTokens){case GoodRefs1(IntDecToken(Sp1, "45", 45)) => }
      assertMatch("45".findTokens){case GoodRefs1(IntToken(Sp1, "45", 45)) => }
      assertMatch("0x2D".findTokens){case GoodRefs1(IntToken(Sp1, "0x2D", 45)) => }
      assertMatch("0x11".findTokens){case GoodRefs1(IntHexToken(Sp1, "0x11", 17)) => }
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
      //assertMatch("-".findTokens){case GoodRefs1(OtherOperatorToken(Sp1, "-")) => }
      "#".findTokens.isBad ==> true
    }

    'Neg
    {
      assertMatch("-".findTokens){case GoodRefs1(OtherOperatorToken(Sp1, _)) => }
      assertMatch("- 4".findTokens){case GoodRefs2(OtherOperatorToken(Sp1, _), IntDecToken(Sp3, _, 4)) => }
    }
  }
}