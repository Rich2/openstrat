package ostrat
package geom
import utest._

object TransTest extends TestSuite
{
  val tests = Tests
  {
    val r1: Polygon = Rectangle(4, 2)
    val r2: Polygon = Rectangle(12, 6)
    't1 {
      r1 ==> r1
      assert(r1.scale(3) eq (r2))

    }

  }
}