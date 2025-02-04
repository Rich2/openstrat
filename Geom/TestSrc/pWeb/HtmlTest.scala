/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest._

object HtmlTest extends TestSuite
{
  val tests = Tests {
    val p1 = HtmlP("Hi")
    val sectStr = HtmlSection(p1).out(0)
    val sectStr2 = "<section>\n  <p>Hi</p>\n</section>"
    test("Test Htlml")
    {
      p1.out(0) ==> "<p>Hi</p>"
      sectStr ==> sectStr2
    }
  }
}