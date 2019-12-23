package ostrat
package pParse
import utest._

object CommentTest extends TestSuite
{
  val tests = Tests
  {
    val s1 = """\n"""

    'Test1
    {
      s1 ==> "\n"
      assertMatch("""//df5""".findTokens){ case Good(_) => }
    }
  }
}
