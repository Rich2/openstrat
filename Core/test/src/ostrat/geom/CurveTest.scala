/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

object CurveTest extends TestSuite
{
   val tests = Tests{
   val ls = LineSeg(4, 56)
   't1 { assert (ls == CurveSeg(Double.NegativeInfinity, 0, 0, 0, 4, 56)) } 
   }
}