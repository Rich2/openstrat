/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

/** Test object for our own immutable wrapper. Types have been deliberatly left unannotated to test demonstrated type inference.*/
object ArrOtherTest extends TestSuite
{
  val tests = Tests {
    val ints1 = Ints(1, 2, 3, 4)
    val ints2 = Ints(5, 6, 7)
    val ints3 = ints1 ++ ints2
    val dbls1 = Dbls(1.5, 3, 4.5, 6)
    val dbls2 = ints1.map(_ * 1.5)
    //val dbls3: Dbls = ints1.iterFlatMap { a => List(a + 0.1, a + 0.2)}
    val longs1 = Longs(2, 4, 6) ++ Longs(8, 9)
    val pre1 = Ints(2, 3, 4).prepend(1)

    "test1" -
    { ints1(3) ==> 4
      ints3.dataLength ==> 7
      ints3(6) ==> 7
      dbls1(2) ==> 4.5
      dbls2(0) ==> 1.5
      dbls2.dataLength ==> 4
     // dbls3(0) ==> 1.1
     // dbls3.length ==> 8
      longs1(4) == 9L
      pre1(0) ==> 1
      pre1(3) ==> 4
    }

    val ints4 = ints1.flatMap(a => Ints(a + 10, a + 20, a + 30))

    val longs2 = ints1.flatMap(a => Longs(a + 100, a + 200, a + 300))
    //val dbls4 = ints2.flatMap(i => Dbls(i, i * 0.5))

    "Bind" -
    { ints4(1) ==> 21
      ints4(5) ==> 32
      ints4.dataLength ==> 12
    //  dbls4(1) ==> 2.5
      longs2.dataLength ==> 12
    }
  }
}