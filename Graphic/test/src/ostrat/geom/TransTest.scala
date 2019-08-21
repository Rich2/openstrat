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
      assert(r1.scale(3) eq (r2))

     // assert(List(r1, r2).scale(2) eq List(r3, r4))
    }

  }
}