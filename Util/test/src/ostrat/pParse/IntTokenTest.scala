package ostrat
package pParse
import utest._

object IntTokenTest  extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val ts1 = "4".findTokens

  val tests = Tests
  {
    'Test1
    {
      assertMatch(ts1){case GoodRefs1(IntToken(Sp1, "4", 4)) => }
    }
  }
}
