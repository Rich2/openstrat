/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
    val it1 = NatBase10Token(Sp1, "13")
    val it2 = NatBase10Token(Sp44, "2147483647")

    test("IntDeciToken")
    { it1.getIntStd ==> 13
      it2.getIntStd ==> 2147483647
    }

    test("General")
    { assertMatch("4".parseTokens){ case Good(Arr1(NatBase10Token(Sp1, "4"))) => }
      assertMatch("45".parseTokens){ case Good(Arr1(NatBase10Token(Sp1, "45"))) => }
      assertMatch("4A".parseTokens){ case Good(Arr1(ValidRawHexaIntToken(74))) => }
      assertMatch("4F5".parseTokens){ case Good(Arr1(ValidRawHexaIntToken(1269))) => }
      assertMatch("\"45\"".parseTokens){ case Good(Arr1(StringToken(Sp1, "45"))) => }
      assertMatch("0x11".parseTokens){ case Good(Arr1(Nat0xToken(Sp1, "11"))) => }
      assertMatch("0y11".parseTokens){ case Good(Arr1(Nat0yToken(Sp1, "11"))) => }
    }

    val st1 = "true; 17; false"

    "Find / as Int" -
    { "17".findType[Int] ==> Good(17)
      "17".asNat ==> Good(17)
      st1.intAtStsIndex(1) ==> Good(17)
      st1.intAtStsIndex(1) ==> Good(17)
      "true".asBool ==> Good(true)
      st1.findType[Boolean].isGood ==> false
      "17; -17".findType[Int].isBad ==> true
      "17; -17".asNat.isBad ==> true
      "25".asInt ==> Good(25)
      "25;".asInt.isBad ==> true
    }

    test("Negative")
    { assertMatch("-4".parseTokens){ case Good(Arr1(NegBase10Token(Sp1, "4"))) => }
      "-4".asInt ==> Good(-4)
      "-4".asNat.isBad ==> true
      "-257".asInt ==> Good(-257)
      "-257".asNat.isBad ==> true
    }
  }
}