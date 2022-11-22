/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object IntsTest extends TestSuite
{
  val irb0 =IntArr(4).removeIndex(0) === IntArr()
  val irb1 =IntArr(-5, 2, 40, -89).removeIndex(2) === IntArr(-5, 2, -89)

  val a1 = IntArr(4, 3, 2, 1)
  val a1b = a1 match{
    case Arr1Tail(h, tail) => tail
    case _ => IntArr()
  }

  val tests = Tests {
    test("Remove") {
      irb0 ==> true
      irb1 ==> true
      a1b.length ==> 3
      a1b(0) ==> 3
    }
  }
}
