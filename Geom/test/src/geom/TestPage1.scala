/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

object TestPage1 extends HtmlPageFile
{ override def fileNameStem: String = "TestPage1"
  override def titleStr: String = "Endnotes Test page."

  override def body: HtmlBody = HtmlBody(h1, p1, p2, p3)

  val h1 = HtmlH1("This is a test page for end notes.")

  val p1: HtmlP = HtmlP.id("para1", "I'm going to link this footnote marker (1) to the footnote at the bottom of the intro text.")

  val p2: HtmlP = HtmlP.id("para2", "I'm going to link this footnote marker (2) to the footnote at the bottom of the intro text.")

  val p3: HtmlP = HtmlP.id("para3", "I'm going to link this footnote marker (3) to the footnote at the bottom of the intro text.")

  val n1 = HtmlP.id("note1", "A bit more context about paragraph 1.")
}
