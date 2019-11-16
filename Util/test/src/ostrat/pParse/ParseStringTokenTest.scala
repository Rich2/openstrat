package ostrat
package pParse
import utest._

object ParseStringTokenTest extends TestSuite
{
  val s0 = "\"\""
  val chs0 = s0.toChars
  val r0 = parseStringToken(chs0.charsOffsetter, StrPosn())(chs0)

  val s1 = "\"Hello\""
  val chs1 = s1.toChars
  val r1: EMon3[CharsOff, TextPosn, StringToken] = parseStringToken(chs1.charsOffsetter, StrPosn())(chs1)
  val s2 = s1 + " AnIdentifier"
  val chs2 = s2.toChars
  val r2 = parseStringToken(chs2.charsOffsetter, StrPosn())(chs2)

  val tests = Tests
  {
    'Test1
    {
      assertMatch(r0){case Good3(CharsOff(2), StrPosn(1, 3), StringToken(_, s0)) => }
      assertMatch(r1){case Good3(CharsOff(7), StrPosn(1, 8), StringToken(_, s1)) => }
      assertMatch(r2){case Good3(CharsOff(7), StrPosn(1, 8), StringToken(_, s1)) => }
    }
  }
}
