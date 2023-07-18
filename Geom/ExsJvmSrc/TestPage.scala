/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

object TestPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Test page", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Test Page"), main)

  def main: HtmlDiv = HtmlDiv.classAtt("main", list)

  def list: HtmlOl = HtmlOl(RArr[XCon](p1, p2, p3))

  def p1 = HtmlP("Ok lets just start with a short paragraph.")

  def p2 = HtmlP("Ok now lets write something a bit longer. I'm really just now sure what to add, I want this to go over 150 characters So as" --
    "hopefully this will go to 2 lines. I'll just go on and on.")

  def p3 = HtmlP("Ok now lets write something even longer. I'm still not sure what else to add, I want this to go over 150 characters twice" --
    " so as to trigger a multi line response. What on earth that's class called, I just can't recall. Anyway I'll remember it in a while, but" --
    "I suppose I could jsut look it up. This will have to be changed.")
}
