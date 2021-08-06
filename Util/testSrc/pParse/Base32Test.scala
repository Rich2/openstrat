/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse
import utest._

object Base32Test extends TestSuite
{
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
  }
}