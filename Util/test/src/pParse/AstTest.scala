package ostrat
package pParse
import utest._

object AstTest extends TestSuite
{
  val Sp1 = StrPosn(1, 1)
  //val s1 = "x = y;"
  //val t1 = srcToETokens(s1, "String")
 // val w1 = stringToStatements(s1)
  val t3= Arr(IdentifierLowerOnlyToken(Sp1, "appStr"), AsignToken(Sp1), StringToken(Sp1, "20"), SemicolonToken(Sp1))
  val a1 = astParse(t3)
  deb(a1.get.lenStr)
 // deb(w1.toString)

  val tests = Tests
  {
    "Test1" -
    {
      assertMatch(a1){case Good(Arr1(_)) => }
    }
  }
}
