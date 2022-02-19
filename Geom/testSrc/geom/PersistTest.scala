/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import utest._

object PersistTest extends TestSuite
{
  val tests = Tests {
    val p1 = Pt2(2.5, -4)
    test("Test 1") {
      p1.str ==> "Pt2(2.5; -4)"
      p1.strSemi ==> "2.5; -4"
      p1.strComma ==> "2.5, -4"
      p1.str.asType[Pt2] ==> Good(p1)
      p1.strSemi.asType[Pt2] ==> Good(p1)
      p1.strComma.asType[Pt2] ==> Good(p1)
    }
  }
}
