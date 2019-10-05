package ostrat
import utest._

object EqTest extends TestSuite
{
  val tests = Tests
  {
    val d1 = 1.0 / 3
    't1
    { assert(4.0 equ 4.0)
      assert(-5.0 nequ 5.0)
      assert(d1 * 3 equ 1.0 )
      assert(List(4, 5) equ List(4, 5))
      assert(List(6, -10, 2) equ List(6, -10, 2))
      assert(Arr(3,4,5) nequ( Arr(3,4,5,6)))
      assert(Array(-2, -68, 45) equ (Array(-2, -68, 45)))

    }
    val o1: Option[Int] = Some(-56)
    val o2: Option[Int] = Some(-56)

    'OptionTest
    {
      assert(None equ None)
      assert(Some(7) equ Some(7))
      assert(o1 equ o2)
      assert(o1 equ Some(-56))
      assert(None equ None)
      assert(o2 nequ None)
    }
  }
}
