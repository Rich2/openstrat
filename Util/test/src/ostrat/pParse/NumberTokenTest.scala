package ostrat
package pParse
import utest._

/** Also tests the srcToETokens function object. */
object NumberTokenTest  extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val Sp2 = StrPosn(1, 2)
  val Sp44 = StrPosn(4, 4)
  val a1 = "0x44".toChars
  val h1: EMon3[CharsOff, TextPosn, Token] = parseNumberToken(a1.offsetter0, StrPosn())(a1)


  val tests = Tests
  {
    val it1 = DecimalToken(Sp1, "13")
    val it2 = DecimalToken(Sp44, "2147483647")
    'IntDeciToken
    {
      it1.getInt ==> 13
      it2.getInt ==> 2147483647
    }

    val ht1 = Hexa0xToken(Sp1, "A")
    val ht2 = Hexa0xToken(Sp44, "1A")
    val ht3 = Hexa0xToken(Sp2, "7FFFFFFF")

    'IntDeciToken
    { ht1.getInt ==> 10
      ht2.getInt ==> 26
      ht3.getInt ==> 2147483647
    }

    'General
    {
      /*assertMatch("4".findTokens){case GoodRefs1(IntToken(Sp1, "4")) => }
      assertMatch("45".findTokens){case GoodRefs1(IntDeciToken(Sp1, "45")) => }
      assertMatch("45".findTokens){case GoodRefs1(IntToken(Sp1, "45")) => }
      //assertMatch("4.5".findTokens){case GoodRefs1(FloatToken(Sp1, "4.5", 4.5)) => }
      //This would work under new proposed schema but that hasn't been implemented yet
      // assertMatch("4.5".findTokens){case GoodRefs3(IntToken(Sp1, "4", 4), DotToken(Sp4), IntToken(Sp1, "5", 5)) => }
      assertMatch("\"45\"".findTokens){case GoodRefs1(StringToken(Sp1, "45")) => }
      assertMatch("0x11".findTokens){case GoodRefs1(IntHexaToken(Sp1, "11")) => }
      assertMatch("0x11".findTokens){case GoodRefs1(IntToken(Sp1, "0x11")) => }*/

      assertMatch(h1){case Good3(CharsOff(4), StrPosn(1, 5), Hexa0xToken(_, _)) => }
    }
  }
}
