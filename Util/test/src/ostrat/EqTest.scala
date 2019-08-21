package ostrat
import utest._

object EqTest extends TestSuite
{
  val tests = Tests
  {
    't1
    { assert(List(4, 5) equ List(4, 5))

    }

  }
}
