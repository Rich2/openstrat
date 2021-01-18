package ostrat
package pParse
import utest._

object Hexa0xTokenTest extends TestSuite
{
  val s1 = "0x123"
  val p1 = StrPosn(1, 1)
  val r1 = srcToETokens.str(s1)
  val Ht1 = Nat0xToken(p1, "123")
  val rr1 = 0x123

  val s2 = "0xC32AB34"
  val c2 = s2.toChars
  val o2 = c2.offsetter0
  val r2 = srcToETokens.str(s2)
  val Ht2 = Nat0xToken(p1, "C32AB34")
  val rr2 = 0xC32AB34

  val tests = Tests
  {
    "Parse" -
    {
      assertMatch(r1){ case GoodArr1(Ht1) => }
      Ht1.asHexaInt ==> 0x123
      Ht1.getInt ==> rr1
      Ht1.asHexaInt ==> rr1
      assertMatch(r2){ case GoodArr1(Ht2) => }
      Ht2.getInt ==> rr2
    }
  }
}
