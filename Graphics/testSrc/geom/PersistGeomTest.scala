/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package geom
import utest._

object PersistGeomTest  extends TestSuite
{
  val tests = Tests
  { val vec2a: Pt2 = 56.1 pp -45.2
    val vec2aSemi = "56.1; -45.2"
    val vec2aComma = "56.1, -45.2"
    val vec2aStd = "Vec2" + vec2aSemi.enParenth
    val vec2b: Pt2 = -0.01 pp 0.02
    val vec2bSemi = "-0.01; 0.02"
    val vec2bComma = "-0.01, 0.02"
    val vec2bStd: String = "Vec2" + vec2bSemi.enParenth
    deb(Pt3(3.1, -4, 5).str)

    "Vec2Test" -
    { assert(vec2a.toString == vec2aStd)
      assert(vec2a.str == vec2aStd)
      assert(vec2a.str == vec2aStd)
      assert(vec2a.strTyped == vec2aStd)
      assert(vec2a.strSemi == vec2aSemi)
      assert(vec2b.str == vec2bStd)      
      assert(vec2b.strSemi == vec2bSemi)
      assert(vec2b.strComma == vec2bComma)
    }  
    
    val l1 = LineSeg(vec2a, vec2b)
    val lineStd: String = "Line2(" + vec2aComma + "; " + vec2bComma + ")"
    
    "Line2Test" -
    { assert(l1.toString == lineStd)      
      assert(l1.str == lineStd)
      assert(l1.str == lineStd)
      assert(l1.strTyped == lineStd)
      assert(l1.strSemi == vec2aComma + "; " + vec2bComma)
      assert(l1.strComma == vec2aStd + ", " + vec2bStd)
    }
    
    "OtherTest" -
    { assert(Pt3(3.1, -4, 5).str == "Vec3(3.1; -4; 5)")
    }
  }
}