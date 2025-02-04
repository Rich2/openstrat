/* Copyright 2025 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import utest._

object HtmlTest extends TestSuite
{
  val tests = Tests {
    test("Test Htlml")
    {
      HtmlP("Hi").out(0) ==> "<p>Hi</p>"
    }
  }
}