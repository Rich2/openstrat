/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

/** Also tests the srcToETokens function object. */
object IntTokenTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val Sp2 = StrPosn(1, 2)
  val Sp3 = StrPosn(1, 3)
  val Sp44 = StrPosn(4, 4)

  val tests = Tests {
    val it1 = NatDeciToken(Sp1, "13")
    val it2 = NatDeciToken(Sp44, "2147483647")

    "IntDeciToken" -
    { it1.getInt ==> 13
      it2.getInt ==> 2147483647
    }

    val ht1 = Nat0xToken(Sp1, "A")
    val ht2 = Nat0xToken(Sp44, "1A")
    val ht3 = Nat0xToken(Sp2, "7FFFFFFF")

    "IntDeciToken" -
    { ht1.getInt ==> 10
      ht2.getInt ==> 26
      ht3.getInt ==> 2147483647
    }

    "General" -
    { assertMatch("4".parseTokens){ case Good(Arr1(NatDeciToken(Sp1, "4"))) => }
      assertMatch("45".parseTokens){ case Good(Arr1(NatDeciToken(Sp1, "45"))) => }
      assertMatch("4A".parseTokens){ case Good(Arr1(NatRawHexaToken(Sp1, "4A"))) => }
      assertMatch("4F5".parseTokens){ case Good(Arr1(NatRawHexaToken(Sp1, "4F5"))) => }
      assertMatch("4.5".parseTokens){ case Good(Arr3(NatDeciToken(Sp1, "4"), DotToken(Sp2), NatDeciToken(Sp3, "5"))) => }
      assertMatch("\"45\"".parseTokens){ case Good(Arr1(StringToken(Sp1, "45"))) => }
      assertMatch("0x11".parseTokens){ case Good(Arr1(Nat0xToken(Sp1, "11"))) => }
      assertMatch("0y11".parseTokens){ case Good(Arr1(Nat0yToken(Sp1, "11"))) => }
    }

    "Negative" -
    {
      assertMatch("-4".parseTokens){ case Good(Arr1(IntNegToken(Sp1, "4"))) => }
      "-4".findInt ==> Good(-4)
    }
  }
}