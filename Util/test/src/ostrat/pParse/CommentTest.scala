package ostrat
package pParse
import utest._

object CommentTest extends TestSuite
{
  val tests = Tests
  {
    val s1 = "//".findTokens
    val s2 = "x = 5//".findTokens
    val s3 = "x = 5//Some blurb".findTokens
    val s4 = "x = //\n5".findTokens
    val a4 = s4.flatMap(astParse(_))
    val s5: EMon[String] = eTry(io.Source.fromResource("c1.rson").getLines().mkString)
    val a5 = s5.flatMap(_.findTokens)
    deb(a5.toString)

    'Test1
    { assertMatch(s1){ case Good(Refs0()) => }
      assertMatch(s2){ case Good(Refs3(_, _, _)) => }
      assertMatch(s3){ case Good(Refs3(_, _, _)) => }
      assertMatch(s4){ case Good(Refs3(IdentifierLowerToken(_, "x"), AsignToken(_), DecimalToken(_, _))) => }
      assertMatch(a4){ case GoodRefs1(_) => }
    }
  }
}
