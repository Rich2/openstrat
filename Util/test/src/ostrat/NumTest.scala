/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object NumTest  extends TestSuite
{
  val tests = Tests {
    test("Base 32")
    { 10.base32 ==> "A"
      16.base32 ==> "G"
      17.base32 ==> "H"
      18.base32 ==> "I"
      20.base32 ==> "K"
      23.base32 ==> "N"
      24.base32 ==> "P"
      31.base32 ==> "W"
      32.base32 ==> "10"
    }
  }
}