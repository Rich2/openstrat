/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utest._

/** Test object for our own immutable wrapper. Types have been deliberately left unannotated to test demonstrated type inference.*/
object ArrPrimitivesTest extends TestSuite
{
  val tests = Tests {
    val ints1 = IntArr(1, 2, 3, 4)
    val ints2 = IntArr(5, 6, 7)
    val ints3 = ints1 ++ ints2
    val dbls1 = DblArr(1.5, 3, 4.5, 6)
    val dbls2 = ints1.map(_ * 1.5)
    val dbls3: DblArr = ints1.flatToIterableMap[Double, DblArr] { a => List(a + 0.1, a + 0.2) }
    val longs1 = LongArr(2, 4, 6) ++ LongArr(8, 9)
    val pre1 = 1 %: IntArr(2, 3, 4)

    test("Ints test 1")
    { ints1(3) ==> 4
      assert(ints3 === IntArr(1, 2, 3, 4, 5, 6, 7))
      dbls1(2) ==> 4.5
      assert(dbls2 === DblArr(1.5, 3, 4.5, 6))
      assert(dbls3 === DblArr(1.1, 1.2, 2.1, 2.2, 3.1, 3.2, 4.1, 4.2))
      assert(longs1 === LongArr(2, 4, 6, 8, 9))
      assert(pre1 === IntArr(1, 2, 3, 4))
    }

    val ints4 = ints1.flatMap(a => IntArr(a + 10, a + 20, a + 30))
    val longs2 = ints1.flatMap(a => LongArr(a + 100, a + 200, a + 300))

    test("Bind") -
    { assert(ints4 === IntArr(11, 21, 31, 12, 22, 32, 13, 23, 33, 14, 24, 34))
      longs2.length ==> 12
    }

    val sort1 = IntArr(4, 3, 8, 7, 2).sortBy(_ > _)
    test("sortBy"){
      assert(sort1 === IntArr(2, 3, 4, 7, 8))
    }
  }
}