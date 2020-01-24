package ostrat
package pParse
import utest._

/** Also tests the srcToETokens function object. */
object IntTokenTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val Sp2 = StrPosn(1, 2)
  val Sp3 = StrPosn(1, 3)
  val Sp44 = StrPosn(4, 4)

  val tests = Tests
  {
    val it1 = DecimalToken(Sp1, "13")
    val it2 = DecimalToken(Sp44, "2147483647")

    'IntDeciToken
    { it1.getInt ==> 13
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
      assertMatch("4".parseTokens){case GoodRefs1(IntToken(Sp1, "4")) => }
      assertMatch("45".parseTokens){case GoodRefs1(DecimalToken(Sp1, "45")) => }
      assertMatch("45".parseTokens){case GoodRefs1(IntToken(Sp1, "45")) => }
      assertMatch("4.5".parseTokens){case GoodRefs3(DecimalToken(Sp1, "4"), DotToken(Sp2), DecimalToken(Sp3, "5")) => }
      assertMatch("\"45\"".parseTokens){case GoodRefs1(StringToken(Sp1, "45")) => }
      assertMatch("0x11".parseTokens){case GoodRefs1(Hexa0xToken(Sp1, "11")) => }
      assertMatch("0x11".parseTokens){case GoodRefs1(IntToken(Sp1, "0x11")) => }
    }
  }
}
