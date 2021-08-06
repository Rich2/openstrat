/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object EqTest extends TestSuite
{
  val tests = Tests {
    val d1 = 1.0 / 3
    "Test1" -
    { assert(4.0 equ 4.0)
      assert(-5.0 nequ 5.0)
      assert(d1 * 3 equ 1.0 )
      assert(List(4, 5) equ List(4, 5))
      assert(List(6, -10, 2) equ List(6, -10, 2))
      assert(Ints(3,4,5) nequ( Ints(3,4,5,6)))
      assert(Array(-2, -68, 45) equ Array(-2, -68, 45))
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
      assert(None equ None)
      assert(Some(7) equ Some(7))
      assert(o1 equ o2)
      assert(o1 equ Some(-56))
      assert(None equ None)
      assert(o2 nequ None)
    }
  }
}