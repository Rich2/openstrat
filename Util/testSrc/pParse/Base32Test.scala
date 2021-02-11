/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pParse
import utest._

object Base32Test extends TestSuite
{
  val tests = Tests
  {
    "Base32" -
    { 0.base32Str ==> "0"
      -0.base32Str ==> "0"
      7.base32Str ==> "7"
      -8.base32Str ==> "-8"
      10.base32Str ==> "A"
      -15.base32Str ==> "-F"
      23.base32Str ==> "N"
      24.base32Str ==> "P"
      28.base32Str ==> "T"
      31.base32Str ==> "W"
      32.base32Str ==> "10"
      -41.base32Str ==> "-19"
      42.base32Str ==> "1A"
      320.base32Str ==> "A0"
      512.base32Str ==> "G0"
      660.base32Str ==> "KK"
      666.base32Str ==> "KR"
      1024.base32Str ==> "100"
    }

  }
}
