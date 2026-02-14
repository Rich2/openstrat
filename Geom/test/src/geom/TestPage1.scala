/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package geom
import pWeb.*

object TestPage1 extends HtmlPageFile
{ override def fileNameStem: String = "TestPage1"
  override def titleStr: String = "Endnotes Test page."

  override def body: HtmlBody = HtmlBody(p1)

  val p1 = HtmlP("This is a test page for end notes.")
}
