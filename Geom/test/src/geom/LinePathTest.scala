/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._

object LinePathTest extends TestSuite
{
  val tests = Tests {
    val lp1 = LinePath.dbls(1,1, 2,2, 3,3)
    val lp2 = LinePath.dbls(6,6, 7,7, 8,8)
    
    test("LinePath append")
    { assert(lp1 ++ lp2 === LinePath.dbls(1,1, 2,2, 3,3, 6,6, 7,7, 8,8))
      assert(lp1 ++< lp2 === LinePath.dbls(1,1, 2,2, 3,3, 8,8, 7,7, 6,6))
      assert(lp1 +-+ lp2 === LinePath.dbls(1,1, 2,2, 3,3, 7,7, 8,8))
      assert((lp1 +<+ lp2) === LinePath.dbls(3,3, 2,2, 1,1, 6,6, 7,7, 8,8))
      assert((lp1 +<+< lp2) === LinePath.dbls(3,3, 2,2, 1,1, 8,8, 7,7, 6,6))
    }
    test("Append to polygon")
    { assert((lp1 |-++-| lp2) === Polygon.dbls(1,1, 2,2, 6,6, 7,7))
      assert((lp1 |+<+| lp2) === Polygon.dbls(3,3, 2,2, 1,1, 6,6, 7,7, 8,8))
    }
  }
}