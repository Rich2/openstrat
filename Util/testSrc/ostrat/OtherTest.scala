/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

/** Test for top level methods. */
object OtherTest extends TestSuite
{
  val tests = Tests {
    "MinMax0" - {
      4.min0 ==> 0
      (-4).min0 ==> -4
      0.min0 ==> 0
      4.max0 ==> 4
      (-7).max0 ==> 0
      0.max0 ==> 0
    }
  }
}
