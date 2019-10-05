package ostrat
package geom
import utest._

object TransTest extends TestSuite
{
  val tests = Tests
  { val r1: Polygon = Rectangle(4, 2)
    val r2: Polygon = Rectangle(12, 6)
    val r3: Polygon = Rectangle(8, 4)
    val r4: Polygon = Rectangle(24, 12)
    val o1: Option[Polygon] = Some(r1)
    val o2 = Some(r2)
    val o4 = Some(r4)

    't1
    { r1 ==> r1
      assert(r1.scale(3) equ (r2))
      assert(r1.scale(2) equ r3)
      assert(r2.scale(2) equ r4)
      assert(Arr(r1, r2).scale(2) equ Arr(r3, r4))
      assert(List(r1, r2).scale(2) equ List(r3, r4))
      assert(o2.scale(2) equ o4)
      //assert(o2 notEqu None)
    }

  }
}