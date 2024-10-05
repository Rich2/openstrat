/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pParse; package plex
import utest._

object CommentTest extends TestSuite
{
  val tests = Tests {
    val s1 = "//".parseTokens
    val s2 = "x = 5//".parseTokens
    val s3 = "x = 5//Some blurb".parseTokens
    val s4 = "x = //\n5".parseTokens
    val a4 = s4.flatMap(tokensToStatements(_))
    val s5: EMon[Array[Char]] = eTry(io.Source.fromResource("c1.rson").toArray)
    val a5: EArr[Token] = s5.flatMap(g => lexSrc(g, "").toEMon)
    //val s6 = "appStr =//\n\"Z0\";"
    //val a6 = s6.parseTokens

    //val t = 11

    test("Test1")
    { assertMatch(s1){ case Succ(Arr0()) => }
      assertMatch(s2){ case Succ(Arr3(_, _, _)) => }
      assertMatch(s3){ case Succ(Arr3(_, _, _)) => }
      assertMatch(s4){ case Succ(Arr3(IdentLowerToken(_, "x"), AsignToken(_), NatBase10Token(_, _))) => }
      assertMatch(a4){ case Succ(Arr1(_)) => }
      assertMatch(a5){ case Good(Arr4(_, _, _, _)) => }
    }
  }
}