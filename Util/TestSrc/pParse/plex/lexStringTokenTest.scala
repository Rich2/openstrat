/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex
import utest._

object lexStringTokenTest extends TestSuite
{
  val s0 = "\"\""
  val chs0 = s0.toChars
  val r0 = lexStringToken(chs0.offsetter0, StrPosn())(chs0)

  val p1 = StrPosn(1, 1)
  val s1 = "\"Hello\""
  val chs1 = s1.toChars
  val r1: ErrBi3[ExcLexar, CharsOff, TextPosn, StringToken] = lexStringToken(chs1.offsetter0, StrPosn())(chs1)
  val s2 = s1 + " AnIdentifier"
  val chs2 = s2.toChars
  val r2 = lexStringToken(chs2.offsetter0, StrPosn())(chs2)
  val s3 = "1234\"abc\nABC\""
  val chs3 = s3.toChars
  val r3 =  lexStringToken(chs0.offsetter(4), StrPosn(1, 5))(chs3)

  val tests = Tests {
    test("Test1")
    { assertMatch(r0){case Succ3(CharsOff(2), StrPosn(1, 3), StringToken(p1, s0)) => }
      assertMatch(r1){case Succ3(CharsOff(7), StrPosn(1, 8), StringToken(p1, s1)) => }
      assertMatch(r2){case Succ3(CharsOff(7), StrPosn(1, 8), StringToken(p1, s1)) => }
      assertMatch(r3){case Succ3(CharsOff(13), StrPosn(1, 14), StringToken(StrPosn(1, 5), s3)) => }
    }

    //val s4 = "\"Hello"
    //val chs4 = s4.toChars
    //val r4 = parseStringToken(chs4.offsetter0, StrPosn())(chs4)

    test("Test2")
    {
     // assertMatch(r4){case Bad1("String 1, 1: Unclosed String") => }
    }
  }
}