/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest._

object HtmlTest extends TestSuite
{
  val tests = Tests {
    val p1 = HtmlP("Hi")
    val p2 = HtmlP("Hi again!")
    val sectStr0 = HtmlSection(p1).out(0)
    val sectStr0c = "<section>\n  <p>Hi</p>\n</section>"
    val sectStr1 = HtmlSection(p1).out(2)
    val sectStr1c = "<section>\n    <p>Hi</p>\n  </section>"
    val sectStr2 = HtmlSection(p1, p2).out(0)
    val sectStr2c = "<section>\n  <p>Hi</p>\n  <p>Hi again!</p>\n</section>"

    test("Test Html")
    { p1.out(0) ==> "<p>Hi</p>"
      sectStr0.length ==> sectStr0c.length
      sectStr0 ==> sectStr0c
      sectStr1.length ==> sectStr1c.length
      sectStr1 ==> sectStr1c
      sectStr2.length ==> sectStr2c.length
      sectStr2 ==> sectStr2c
    }

    val li0 = HtmlLi("Pot")
    val a0 = HtmlA("house.com", "House")
    val li1 = HtmlLi(a0)
    val li2 = HtmlLi(a0, "Lets talk about Houses.".xCon)
    test("Lists")
    { li0.out(0) ==> "<li>Pot</li>"
      a0.out(0) ==> "<a href='house.com'>House</a>"
      li1.out(0) ==> "<li><a href='house.com'>House</a></li>"
      li2.out(0) ==> "<li><a href='house.com'>House</a> Lets talk about Houses.</li>"
    }
  }
}