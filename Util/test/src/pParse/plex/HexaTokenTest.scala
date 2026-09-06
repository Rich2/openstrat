/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex
import utest.*

object HexaTokenTest extends TestSuite
{
  val Sp1: TextPosn = StrPosn(1, 1)
  val Sp2: TextPosn = StrPosn(1, 2)
  val Sp3: TextPosn = StrPosn(1, 3)
  val Sp44: TextPosn = StrPosn(4, 4)

  val s1 = "0x123"
  val p1: TextPosn = StrPosn(1, 1)
  val r1: Either[LexarException, RArr[Token]] = lexSrc.str(s1)
  val Ht1: Nat0xToken = Nat0xToken(p1, "123")
  val rr1: Int = 0x123

  val s2: String = "0xC32AB34"
  val c2: CharArr = s2.toChars
  val o2: CharsOff = c2.offsetter0
  val r2: Either[LexarException, RArr[Token]] = lexSrc.str(s2)
  val Ht2: Nat0xToken = Nat0xToken(p1, "C32AB34")
  val rr2: Int = 0xC32AB34

  val r3 = lexSrc.str("0xCG3")

  val tests = Tests {
    test("Parse")
    { assert(r1 === Right(RArr(Ht1)))
      Ht1.asHexaInt ==> 0x123
      Ht1.getIntStd ==> rr1
      Ht1.asHexaInt ==> rr1
      assert(r2 === Right(RArr(Ht2)))
      Ht2.getIntStd ==> rr2
      assertMatch(r3) { case Left(_) => }
    }

    val ht1 = Nat0xToken(Sp1, "A")
    val ht2 = Nat0xToken(Sp44, "1A")
    val ht3 = Nat0xToken(Sp2, "7FFFFFFF")

    test("Nat0xToken")
    { ht1.getIntStd ==> 10
      ht1.getNatStd ==> 10
      ht2.getIntStd ==> 26
      ht3.getIntStd ==> 2147483647
      ht3.getNatStd ==> 2147483647
    }

    test("Raw Test")
    { "10".asHexaInt ==> Right(16)
      "1A".asHexaNat ==> Right(26)
      "-5A".asHexaInt ==> Right(-90)
      "-5A".asHexaNat.isLeft ==> true
      "C0".asHexaInt ==> Right(192)
      "C0".asHexaNat ==> Right(192)
      "-C0".asHexaInt ==> Right(-192)
      "-C0".asHexaNat.isLeft ==> true
    }
  }
}