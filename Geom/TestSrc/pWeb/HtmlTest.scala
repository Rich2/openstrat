/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest._

object HtmlTest extends TestSuite
{
  val tests = Tests {
    val p1 = HtmlP("Hi")
    val sectStr0 = HtmlSection(p1).out(0)
    val sectStr0c = "<section>\n  <p>Hi</p>\n</section>"
    val sectStr1 = HtmlSection(p1).out(2)
    val sectStr1c = "  <section>\n    <p>Hi</p>\n  </section>"
    test("Test Htlml")
    {
      p1.out(0) ==> "<p>Hi</p>"
      sectStr0.length ==> sectStr0c.length
      sectStr0 ==> sectStr0c
      sectStr1.length ==> sectStr1c.length
      sectStr1 ==> sectStr1c
    }
  }
}