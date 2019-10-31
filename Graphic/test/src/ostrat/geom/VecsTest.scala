/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

/** Was called LinePath. */
object VecsTest extends TestSuite
{
  val tests = Tests
  {
    val v1s: LinePath = LinePath(2.1 vv 0, 5.4 vv 0)
    't1 { assert(v1s.str == "LinePath(2.1, 0.0; 5.4, 0.0)") }
    val v44 = 4 vv 4
    val v31 = -2.4 vv 54.6 vv 34.7
    val v32 = 4 vv -5 vv 0
    val v2s: LinePath = v1s :+ v44
    val vs3 = Ints(1, 2, 3).map(i => i vv i)
    val cf = vs3.toPolygon.fill(Colour.Red)
    't2
    {
      v44 ==> Vec2(4, 4)
      v31 ==> Vec3(-2.4, 54.6, 34.7)
      v32 ==> Vec3(4, -5, 0)
      v2s.str ==> "LinePath(2.1, 0.0; 5.4, 0.0; 4.0, 4.0)"
      vs3(1) ==> Vec2(2, 2)
      cf.poly(2) ==> Vec2(3, 3)
    }    
  }
}