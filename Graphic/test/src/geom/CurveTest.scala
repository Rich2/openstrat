/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

object CurveTest extends TestSuite
{
  val tests = Tests
  {
    val ls1 = LineSeg(4 vv 56)
    val as1 = ArcSeg(4 vv 4, 6 vv 4)
    val as2 = as1.scale(2)
    val as3 = ArcSeg(8 vv 8, 12 vv 8)
    val as4 = as3.slate(5, 10)
    val ls2 = LineSeg(-5 vv -8)
    val ls3 = ls2.slate(50, -50)
    val css = PolyCurve(ls1, ls2, ls3)

    "Test1" -
    { ls1 ==> CurveTail(10, 0, 0, 0, 0, 4, 56)
      as1 ==> CurveTail(11, 0, 0, 4, 4, 6, 4)
      as2 ==> as3
      as4 ==> ArcSeg(13 vv 18, 17 vv 18)
      ls2 ==> CurveTail(10, 0, 0, 0, 0, -5, -8)
      ls3 ==> CurveTail(10, 0, 0, 0, 0, 45, -58)
      css(0) ==> ls1
    }
  }
}