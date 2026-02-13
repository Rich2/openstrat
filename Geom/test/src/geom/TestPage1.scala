/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

class TestPage1 extends HtmlPage
{ override def head: HtmlHead = HtmlHead.title("Endnotes Test page.")

  override def body: HtmlBody = HtmlBody(p1)

  val p1 = HtmlP("This is a test page for end notes.")
}
