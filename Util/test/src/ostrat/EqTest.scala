package ostrat
import utest._

object EqTest extends TestSuite
{
  val tests = Tests
  {
    't1
    { assert(4.0 equ 4.0)
      assert(-5.0 notEqu 5.0)
      val d1 = 1.0 / 3
      assert(d1 * 3 equ 1.0 )
      assert(List(4, 5) equ List(4, 5))
      assert(Arr(3,4,5) notEqu( Arr(3,4,5,6)))

    }

  }
}
