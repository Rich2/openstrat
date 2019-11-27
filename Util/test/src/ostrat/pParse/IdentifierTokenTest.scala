package ostrat
package pParse
import utest._

object IdentifierTokenTest extends TestSuite
{
  val s1 = "Zog"
  val c1 = s1.toChars
  val o1 = c1.offsetter0
  val p1 = StrPosn(1, 1)
  val r1: EMon3[CharsOff, TextPosn, Token] = parseIdentifierToken(o1, p1)(c1)
  val tests = Tests
  {
    'Parse
    {
      assertMatch(r1){ case Good3(CharsOff(3), StrPosn(1, 4), IdentifierUpperOnlyToken(_, _)) => }
    }
  }
}
