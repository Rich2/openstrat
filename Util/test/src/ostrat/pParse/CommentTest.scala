package ostrat
package pParse
import utest._

object CommentTest extends TestSuite
{
  val tests = Tests
  {
    val s1 = "//".parseTokens
    val s2 = "x = 5//".parseTokens
    val s3 = "x = 5//Some blurb".parseTokens
    val s4 = "x = //\n5".parseTokens
    val a4 = s4.flatMap(astParse(_))
    val s5: EMon[String] = eTry(io.Source.fromResource("c1.rson").toString)// .getLines().mkString)
    val a5 = s5.flatMap(_.parseTokens)
    deb(s5.get.toString)
    val s6 = "appStr =//\n\"Z0\";"
    val a6 = s6.parseTokens
    deb(s6)
    deb(s6.toString)
    val t = 11

    'Test1
    { assertMatch(s1){ case Good(Refs0()) => }
      assertMatch(s2){ case Good(Refs3(_, _, _)) => }
      assertMatch(s3){ case Good(Refs3(_, _, _)) => }
      assertMatch(s4){ case Good(Refs3(IdentifierLowerToken(_, "x"), AsignToken(_), DecimalToken(_, _))) => }
      assertMatch(a4){ case GoodRefs1(_) => }
      assertMatch(a5) { case Good(_) => }
      s5.get.take(t) ==> s6.take(t)
    }
  }
}
