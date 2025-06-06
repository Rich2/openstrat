/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._

object CurveTest extends TestSuite
{
  val tests = Tests {
    val ls1 = LineTail(4 pp 56)
    val as1 = ArcTail(4 pp 4, 6 pp 4)
    val as2 = as1.scale(2)
    val as3 = ArcTail(8 pp 8, 12 pp 8)
    val as4 = as3.slate(5, 10)
    val ls2 = LineTail(-5 pp -8)
    val ls3 = ls2.slate(50, -50)

    test("Test1")
    { ls1 ==> CurveTailOld(10, 0, 0, 0, 0, 4, 56)
      as1 ==> CurveTailOld(11, 0, 0, 4, 4, 6, 4)
      as2 ==> as3
      as4 ==> ArcTail(13 pp 18, 17 pp 18)
      ls2 ==> CurveTailOld(10, 0, 0, 0, 0, -5, -8)
      ls3 ==> CurveTailOld(10, 0, 0, 0, 0, 45, -58)
    }
  }
}