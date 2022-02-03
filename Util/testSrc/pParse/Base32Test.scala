/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

object Base32Test extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val Sp2 = StrPosn(1, 2)
  val Sp3 = StrPosn(1, 3)

  val tests = Tests {
    "Base32" -
    { 0.base32 ==> "0"
      -0.base32 ==> "0"
      7.base32 ==> "7"
      -8.base32 ==> "-8"
      10.base32 ==> "A"
      -15.base32 ==> "-F"
      23.base32 ==> "N"
      24.base32 ==> "P"
      28.base32 ==> "T"
      31.base32 ==> "W"
      32.base32 ==> "10"
      -41.base32 ==> "-19"
      42.base32 ==> "1A"
      320.base32 ==> "A0"
      512.base32 ==> "G0"
      660.base32 ==> "KK"
      666.base32 ==> "KR"
      1024.base32 ==> "100"
    }

    val ht4 = Nat0yToken(Sp1, "9")
    val ht5 = Nat0yToken(Sp1, "A")
    val ht6 = Nat0yToken(Sp1, "M")
    val ht7 = Nat0yToken(Sp1, "P")
    val ht8 = Nat0yToken(Sp1, "W")
    val ht9 = Nat0yToken(Sp1, "80")

    "Nat0yToken" -
     { ht4.getIntStd ==> 9
       ht5.getIntStd ==> 10
       ht6.getIntStd ==> 22
       ht7.getIntStd ==> 24
       ht8.getIntStd ==> 31
       ht9.getIntStd ==> 256
     }
  }
}