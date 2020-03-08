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
    val a4 = s4.flatMapRefs(astParse(_))
    val s5: EMon[Array[Char]] = eTry(io.Source.fromResource("c1.rson").toArray)// .getLines().mkString)
    val a5: ERefsSpec[Token] = s5.baseFlatMap[Refs[Token], ERefsSpec[Token]](g => srcToETokens(g, ""))(EMonBuild.refsImplicit)
    //val s6 = "appStr =//\n\"Z0\";"
    //val a6 = s6.parseTokens

    //val t = 11

    "Test1" -
    { assertMatch(s1){ case GoodRefsSpec(Refs0()) => }
      assertMatch(s2){ case GoodRefsSpec(Refs3(_, _, _)) => }
      assertMatch(s3){ case GoodRefsSpec(Refs3(_, _, _)) => }
      assertMatch(s4){ case GoodRefsSpec(Refs3(IdentifierLowerToken(_, "x"), AsignToken(_), DecimalToken(_, _))) => }
      assertMatch(a4){ case GoodRefsSpec(Refs1(_)) => }
      assertMatch(a5){ case GoodRefsSpec(Refs4(_, _, _, _)) => }
    }
  }
}
