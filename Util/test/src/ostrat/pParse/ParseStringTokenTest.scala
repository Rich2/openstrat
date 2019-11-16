package ostrat
package pParse
import utest._

object ParseStringTokenTest
{
  val s1 = """ "Hello" """
  val chs1 = s1.toChars
  val r1: EMon3[CharsOff, TextPosn, StringToken] = parseStringToken(chs1.charsOffsetter, StrPosn())(chs1)

  val tests = Tests
  {
    'Test1
    {
      5 ==> 6
        //assertMatch(h1){case Good3(CharsOff(4), StrPosn(1, 5), IntHexaToken(_, _, 68)) => }
    }
  }
}
