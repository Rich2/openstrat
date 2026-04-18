/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** The HTML body element. */
class BodyHtml(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlTagLines, HtmlUnvoid
{ override def tagName: String = "body"
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(0), "\n") + n1CloseTag
}

/** Companion object for the [[HTMLBody]] element class. Contains factory methods. */
object BodyHtml
{ /** Factory apply method to create an HTML body element, with no attributes. There is an apply name overload that takes [[RArr]]s of the contents and
   * attributes as parameters. */
  def apply(contents: XCon*): BodyHtml = new BodyHtml(contents.toArr, RArr())

  /** Factory apply method to create an HTML body element. There is an apply name overload that takes the contents as [[RArr]] parameters. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): BodyHtml = new BodyHtml(contents, attribs)
}