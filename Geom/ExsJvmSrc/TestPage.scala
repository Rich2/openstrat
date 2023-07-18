/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import pWeb._

object TestPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("Test page", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("Test Page"), main)

  def main: HtmlDiv = HtmlDiv.classAtt("main", p1, p2, p3, p4)

  def p1: HtmlP = HtmlP("Ok lets just start with a short paragraph.")

  def p2: HtmlP = HtmlP("Ok now lets write something a bit longer. I'm really just not sure what to add, I want this to go over 150 characters So as" --
    "hopefully this will go to two lines. I'll just go on and on.")

  def p3: HtmlP = HtmlP("Ok now lets write something even longer. I'm still not sure what else to add, I want this to go over 150 characters twice" --
    " so as to trigger a multi line response. What on earth that's class called, I just can't recall. Anyway I'll remember it in a while, but" --
    "I suppose I could just look it up. This will have to be changed.")

  def p4: HtmlP = HtmlP("Now lets try for a few lines. Tiling of the whole world in Hex grids, defining the changes over the course of history." --
    "This will be a data orientated module. It will also include terrain types to model terrain, both real and imagined for local maps and higher" --
    "scales right up to 0.5 metres per tile However it won't generally include the data for these. The data for the real world will be organised" --
    "according to a number of levels, which are likely to change over increasingly shorter historical time frames.")
}