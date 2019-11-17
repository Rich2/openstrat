package ostrat
package pParse
import utest._

/** Also tests the srcToETokens function object. */
object IntTokensTest  extends TestSuite
{
  val Sp1 = StrPosn(1, 1)

  val tests = Tests
  {
    'Test1
    {
      assertMatch("4".findTokens){case GoodRefs1(IntToken(Sp1, "4")) => }
      assertMatch("45".findTokens){case GoodRefs1(IntDeciToken(Sp1, "45")) => }
      assertMatch("45".findTokens){case GoodRefs1(IntToken(Sp1, "45")) => }
      //assertMatch("4.5".findTokens){case GoodRefs1(FloatToken(Sp1, "4.5", 4.5)) => }
      //This would work under new proposed schema but that hasn't been implemented yet
      // assertMatch("4.5".findTokens){case GoodRefs3(IntToken(Sp1, "4", 4), DotToken(Sp4), IntToken(Sp1, "5", 5)) => }
      assertMatch("\"45\"".findTokens){case GoodRefs1(StringToken(Sp1, "45")) => }
      assertMatch("0x11".findTokens){case GoodRefs1(IntHexaToken(Sp1, "11")) => }
      assertMatch("0x11".findTokens){case GoodRefs1(IntToken(Sp1, "0x11")) => }
    }
  }
}
