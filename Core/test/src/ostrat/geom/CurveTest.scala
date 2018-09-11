/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._
import Double.{NegativeInfinity => NegInf, PositiveInfinity => PosInf}
object CurveTest extends TestSuite
{
   val tests = Tests{
   val ls = LineSeg(4, 56)
   't1 { assert (ls == CurveSeg(NegInf, 0, 0, 0, 4, 56)) }
   val as = ArcSeg(4, 4, 6, 4)
   't2 { assert(as == CurveSeg(PosInf, 0, 4, 4, 6, 4)) }
   val as2 = as.scale(2)
   't2 { assert(as2 == ArcSeg(8, 8, 12, 8)) }
   }
}