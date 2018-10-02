/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package geom
import utest._

object PersistGeomTest  extends TestSuite
{
  val tests = Tests
  { val vec2a: Vec2 = 56.1 vv -45.2
    val vec2aSemi = "56.1; -45.2"
    val vec2aComma = "56.1, -45.2"
    val vec2aStd = "Vec2(" + vec2aSemi + ")"
    val vec2b: Vec2 = -0.01 vv 0.02
    val vec2bSemi = "-0.01; 0.02"
    val vec2bComma = "-0.01, 0.02"
    val vec2bStd: String = "Vec2(" + vec2bSemi + ")"
    
    'Vec2Test
    { assert(vec2a.toString == vec2aStd)
      assert(vec2a.str == vec2aStd)
      assert(vec2a.persist == vec2aStd)
      assert(vec2a.persistTyped == vec2aStd)
      assert(vec2a.persistSemi == vec2aSemi)
      assert(vec2b.persist == vec2bStd)      
      assert(vec2b.persistSemi == vec2bSemi)
      assert(vec2b.persistComma == vec2bComma)
    }  
    
    val l1 = Line2(vec2a, vec2b)
    val lineStd: String = "Line2(" + vec2aComma + "; " + vec2bComma + ")"
    
    'Line2Test
    { assert(l1.toString == lineStd)      
      assert(l1.str == lineStd)
      assert(l1.persist == lineStd)
      assert(l1.persistTyped == lineStd)
      assert(l1.persistSemi == vec2aComma + "; " + vec2bComma)
      assert(l1.persistComma == vec2aStd + ", " + vec2bStd)
    }
    
    'OtherTest
    { assert (Vec3(3.1, -4, 5).persist == "Vec3(3.1; -4.0; 5.0)")
    }
  }
}