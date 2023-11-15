/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object EMonBuildTest  extends TestSuite
{
  val tests = Tests {
    val a1: EMon[Int] = Good(5)
    val a2 = a1.map(_ * 11)
    val a3 = a2.map("Good " + _.toString)
    val b = NoInt
    val s1: EMon[String] = Good("Hello")

    test("Test1")
    { a2 ==> Good(55)
      a3 ==> Good("Good 55")
      b.map(_ * 2) ==> NoInt
      s1.map(_.length) ==> Good(5)
    }
  }

}
