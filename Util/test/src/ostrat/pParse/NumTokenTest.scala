package ostrat
package pParse
import utest._

object NumTokenTest  extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  val ts1 = "0x2D".findTokens

  val tests = Tests
  {
    'Test1
    {
      assertMatch(ts1){case GoodRefs1(IntToken(Sp1, "0x2D", 45)) => }
    }
  }
}
