package ostrat
package pParse
import utest._

object HexadecimalTest extends TestSuite
{
  val a1 = "0x44".toChars
  val h1: EMon[(CharsOff, TextPosn,IntLikeHexaToken)] = Hexadecimal(a1.charsOffsetter, StrPosn())(a1)
  deb(h1.toString)
  val tests = Tests
  {
    'Test1
    {
      h1 ==> Good[(CharsOff, TextPosn, IntLikeHexaToken)]((_, _, _))
    }
  }
}