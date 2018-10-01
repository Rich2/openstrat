/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

object PersistGeomTest  extends TestSuite
{ val v1 = 56.1 vv -45.2
  val tests = Tests {
    'Vec2Test {
      assert(v1.str == "Vec2(56.1; -45.2)")
      assert(v1.persist == "Vec2(56.1; -45.2)")
      assert(v1.persistTyped == "Vec2(56.1; -45.2)")
      assert(v1.persistSemi == "56.1; -45.2")
      assert(v1.persistComma == "56.1, -45.2")
    }
  }
}