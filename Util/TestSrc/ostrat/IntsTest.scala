/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object IntsTest extends TestSuite
{
  val irb0 =IntArr(4).removeIndex(0) === IntArr()
  val irb1 =IntArr(-5, 2, 40, -89).removeIndex(2) === IntArr(-5, 2, -89)

  val tests = Tests {
    test("Remove") {
      irb0 ==> true
      irb1 ==> true
    }
  }
}
