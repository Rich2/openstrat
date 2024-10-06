/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

/** Test for top level methods. */
object OtherTest extends TestSuite
{
  val tests = Tests {
    test("MinMax0")
    { 4.min0 ==> 0
      (-4).min0 ==> -4
      0.min0 ==> 0
      4.max0 ==> 4 
      (-7).max0 ==> 0
      0.max0 ==> 0
    }
    val op1: Option[Int] = Some(5)
    val op2: Option[String] = Some("Wow")
    val op3: Option[Double] = Some(3.2)
    val op4: Option[Int] = None
    test("Option companion object")
    { Option.map2(op1, op2)(_ + "! " + _) ==> Some("5! Wow")
      Option.map3(op1, op2, op3)((s1, s2, s3) => s1 + s2.length + s3) ==> Some(11.2)
      Option.map4(op1, op2, op3, op4)((s1, s2, s3, s4) => s1 + s2.length + s3 + s4) ==> None
    }
  }
}