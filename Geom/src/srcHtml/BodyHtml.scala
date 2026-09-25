/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** The HTML body element. */
class BodyHtml(val attribs: RArr[HAtt], val contents: RArr[XCon]) extends HtmlTagLines, HtmlUnvoid
{ override def tagName: String = "body"
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(0), "\n") + n1CloseTag
}

/** Companion object for the [[BodyHtml]] element class. Contains factory methods. */
object BodyHtml
{ /** Factory apply method to create an HTML body element, with no attributes. There is an apply name overload that takes [[RArr]]s of the contents and
   * attributes as parameters. */
  def apply(contents: XCon*): BodyHtml = new BodyHtml(RArr(), contents.toArr)

  /** Factory apply method to create an HTML body element. There is an apply name overload that takes the contents as [[RArr]] parameters. */
  def apply(contents: RArr[XCon]) = new BodyHtml(RArr(), contents)

  /** Factory apply method to create an HTML body element. There is an apply name overload that takes the contents as [[RArr]] parameters. */
  def apply(attribs: RArr[XAtt], contents: RArr[XCon]) = new BodyHtml(attribs, contents)
}