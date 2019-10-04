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

    't1
    { r1 ==> r1
      assert(r1.scale(3) equ (r2))
      assert(r1.scale(2) equ r3)
      assert(r2.scale(2) equ r4)
      assert(List(6, -10, 2) equ List(6, -10, 2))
      assert(List(r1, r2).scale(2) equ List(r3, r4))
    }

  }
}