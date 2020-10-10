package ostrat
package geom
import utest._, Colour._

object TransTest extends TestSuite
{
  val tests = Tests
  { val r1: PolygonImp = Rectangle.applyOld(4, 2)
    val r2: PolygonImp = Rectangle.applyOld(12, 6)
    val r3: PolygonImp = Rectangle.applyOld(8, 4)
    val r4: PolygonImp = Rectangle.applyOld(24, 12)
    val o1: Option[PolygonImp] = Some(r1)
    val o2 = Some(r2)
    val o4 = Some(r4)
    val v1 = Vector(r1, r2)

    "Test1" -
    { r1 ==> r1
      assert(r1.scale(3) equ (r2))
      assert(r1.scale(2) equ r3)
      assert(r2.scale(2) equ r4)
     // assert(Arr(r1, r2).scale(2) equ Arr(r3, r4))
      assert(Array(r1, r2).scale(2) equ Array(r3, r4))
      assert(List(r1, r2).scale(2) equ List(r3, r4))
      assert(v1.scale(2) equ Vector(r3, r4))
      assert(o2.scale(2) equ o4)
      assert(o1 nequ None)
    }

    val p1 = r1.fill(Red)
    val p2 = r2.fill(Green)
    val rs1: Arr[PolygonFill] = Arr(p1, p2)
    val rs1a = rs1.xSlate(2)

    "test2" -
    { rs1a(1).shape.vertNum ==> 4
      rs1a(1).shape.polyCentre ==> Vec2(2, 0)
    }
  }
}