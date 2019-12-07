package ostrat
package pParse
import utest._

object AstTest extends TestSuite
{
  val s1 = "x = y;"
  val t1 = srcToETokens(s1, "String")
  val w1 = stringToStatements(s1)
  deb(w1.toString)

  val tests = Tests
  {
    'Test1
    {
      5 ==> 5
    }
  }
}
