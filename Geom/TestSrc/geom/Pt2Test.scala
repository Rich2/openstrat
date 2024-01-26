/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._

/** Was called LinePath. */
object Pt2Test extends TestSuite
{
  val tests = Tests {

    val ps1 = Pt2Arr.fromDbls(2,4, 5.2,0.3, 3.5,7, 3.2,7)
    val ps2 = ps1.sortBy(_.x > _.x)
    test("t1"){
      assert(ps2 === Pt2Arr.fromDbls(2,4, 3.2,7, 3.5,7, 5.2,0.3))
      4 ==> 4
    }
  }
}