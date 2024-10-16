/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

object ErrBiBuildTest  extends TestSuite
{
  val tests = Tests {
    val a1: ExcMon[Int] = Succ(5)
    val a2 = a1.map(_ * 11)
    val a3 = a2.map("Succ " + _.toString)
    val b = NoInt
    val s1: ExcMon[String] = Succ("Hello")

    test("Test1")
    { a2 ==> Succ(55)
      a3 ==> Succ("Succ 55")
      b.map(_ * 2) ==> NoInt
      s1.map(_.length) ==> Succ(5)
    }
  }

}
