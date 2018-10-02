/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

object PersistGeomTest  extends TestSuite
{
  val tests = Tests
  { val vec2a = 56.1 vv -45.2
    'Vec2Test
    { assert(vec2a.toString == "Vec2(56.1; -45.2)")
      assert(vec2a.str == "Vec2(56.1; -45.2)")
      assert(vec2a.persist == "Vec2(56.1; -45.2)")
      assert(vec2a.persistTyped == "Vec2(56.1; -45.2)")
      assert(vec2a.persistSemi == "56.1; -45.2")
      assert(vec2a.persistComma == "56.1, -45.2")
    }
  
    val l1 = Line2(vec2a, Vec2Z)
    deb(l1.str)
  
    'Line2Test
    { assert(l1.persist == "Line2(56.1, -45.2; 0.0, 0.0)")
    assert(l1.str == "Line2(56.1, -45.2; 0.0, 0.0)")
    }
    
    'Vec3Test
    { assert (true == true)
    }
  }
}