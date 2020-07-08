package ostrat
import utest._

object ExtensionsTest extends TestSuite
{
  val tests = Tests
  {
    test("test1")
    { -1 %% 2 ==> 1
      -1 %% 3 ==> 2
      -7 %% 4 ==> 1
      9 %% 5 ==> 4
      11.divRoundUp(10) ==> 2
    }
  }

}
