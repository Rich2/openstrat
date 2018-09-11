/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._
import Double.{NegativeInfinity => NegInf, PositiveInfinity => PosInf}
object CurveTest extends TestSuite
{
   val tests = Tests{
   val ls1 = LineSeg(4 vv 56)
   't1 { assert (ls1 == CurveSeg(NegInf, 0, 0, 0, 4, 56)) }
   val as = ArcSeg(4 vv 4, 6 vv 4)
   't2 { assert(as == CurveSeg(PosInf, 0, 4, 4, 6, 4)) }
   val as2 = as.scale(2)
   val as3 = ArcSeg(8 vv 8, 12 vv 8)
   't2 { assert(as2 == as3) }
   val as4 = as3.slate(5, 10)
   't3 { assert(as4 == ArcSeg(13 vv 18, 17 vv 18)) }
   val ls2 = LineSeg(-5 vv -8)
   't4 { assert(ls2 == CurveSeg(NegInf, 0, 0, 0, -5, -8)) }
   val ls3 = ls2.slate(50, -50)
  't5 { assert(ls3 == CurveSeg(NegInf, 0, 0, 0, 45, -58)) }
   val css = CurveSegs(ls1, ls2, ls3)
   't6 { assert(css(0) == ls1) }
   }
}