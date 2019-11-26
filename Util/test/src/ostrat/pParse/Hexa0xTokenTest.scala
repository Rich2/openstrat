package ostrat
package pParse
import utest._

object Hexa0xTokenTest extends TestSuite
{
  val c1 = "0x1234".toChars
  val o1 = c1.offsetter2
  val p1 = StrPosn(1, 3)
  val r1: EMon3[CharsOff, TextPosn, Token] = parseHexa0xToken(o1, p1)(c1)
  val tests = Tests
  {
    'Parse
    {
      //r1 ==> Good3(_, _, _)
    }
  }
}
