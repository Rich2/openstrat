/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

object HexaTokenTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val Sp2 = StrPosn(1, 2)
  val Sp3 = StrPosn(1, 3)
  val Sp44 = StrPosn(4, 4)

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

  val r3 = srcToETokens.str("0xCG3")

  val tests = Tests {
    "Parse" -
    { assertMatch(r1){ case GoodArr1(Ht1) => }
      Ht1.asHexaInt ==> 0x123
      Ht1.getIntStd ==> rr1
      Ht1.asHexaInt ==> rr1
      assertMatch(r2){ case GoodArr1(Ht2) => }
      Ht2.getIntStd ==> rr2
      assertMatch(r3) { case Bad(_) => }
    }

    val ht1 = Nat0xToken(Sp1, "A")
    val ht2 = Nat0xToken(Sp44, "1A")
    val ht3 = Nat0xToken(Sp2, "7FFFFFFF")

    "Nat0xToken" -
    { ht1.getIntStd ==> 10
      ht1.getNatStd ==> 10
      ht2.getIntStd ==> 26
      ht3.getIntStd ==> 2147483647
      ht3.getNatStd ==> 2147483647
    }
  }
}