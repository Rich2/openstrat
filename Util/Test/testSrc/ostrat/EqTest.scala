/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object EqTest extends TestSuite
{
  val tests = Tests {
    val d1 = 1.0 / 3
    "Test1" -
    { assert(4.0 === 4.0)
      assert(-5.0 !== 5.0)
      assert(d1 * 3 === 1.0 )
      assert(List(4, 5) === List(4, 5))
      assert(List(6, -10, 2) === List(6, -10, 2))
      assert(IntArr(3,4,5) !==( IntArr(3,4,5,6)))
      assert(Array(-2, -68, 45) === Array(-2, -68, 45))
    }

    val d2 = 4.567
    val s1: Option[Double] = Some(d2)

    "ApproxTest" -
    {
      4.567 =~(4.56, 0.001) ==> false
      4.567 =~(4.56, 0.01) ==> true
      4.567 =~(4.56) ==> false
      s1 =~ (Some(4.56), 0.01) ==> true
    }

    val o1: Option[Int] = Some(-56)
    val o2: Option[Int] = Some(-56)

    "OptionTest" -
    {
      assert(None === None)
      assert(Some(7) === Some(7))
      assert(o1 === o2)
      assert(o1 === Some(-56))
      assert(None === None)
      assert(o2 !== None)
    }
  }
}