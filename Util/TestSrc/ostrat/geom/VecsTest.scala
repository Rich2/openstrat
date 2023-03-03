/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._

/** Was called LinePath. */
object VecsTest extends TestSuite
{
  val tests = Tests {
    val v1s: LinePath = LinePath(2.1 pp 0, 5.4 pp 0)

    test("Test0")
    {
      4 ==> 4 // assert(v1s.str == "LinePath(2.1, 0; 5.4, 0)")
    }

    val v21 = 4 pp 4
    val v22 = -2.2 pp -6.7
    val v23 = -2.2 pp 0
    val vs0 = Pt2Arr(v21, v22, v23)
    val v31 = -2.4 pp 54.6 pp 34.7
    val v32 = 4 pp -5 pp 0
   // val v2s: LinePath = v1s :+ v21
    val vs3 = IntArr(1, 2, 3).map(i => i pp i)
    val cf = vs3.toPolygon.fill(Colour.Red)

    test("Test1")
    { v21 ==> Pt2(4, 4)
      vs0(2) ==> v23
      v31 ==> Pt3(-2.4, 54.6, 34.7)
      v32 ==> Pt3(4, -5, 0)
      //v2s.str ==> "LinePath(2.1, 0; 5.4, 0; 4, 4)"
      vs3(1) ==> Pt2(2, 2)
      //cf.shape.vert(2) ==> Pt2(3, 3)
    }    
  }
}