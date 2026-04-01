/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: RArr[XAtt] = RArr()) extends HtmlTagLines, HtmlUnvoid
{ def tagName: String = "html"
  override def contents: RArr[HtmlUnvoid] = RArr(head, body)

  override def out(indent: Int, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + head.out() + "\n\n" + body.out(0) + n1CloseTag
}

/** The HTML body element. */
class HtmlBody(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlTagLines, HtmlUnvoid
{ override def tagName: String = "body"
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(0), "\n") + n1CloseTag
}

/** Companion object for the [[HTMLBody]] element class. Contains factory methods. */
object HtmlBody
{ /** Factory apply method to create an HTML body element, with no attributes. There is an apply name overload that takes [[RArr]]s of the contents and
   * attibutes as parameters. */
  def apply(contents: XCon*): HtmlBody = new HtmlBody(contents.toArr, RArr())

  /** Factory apply method to create an HTML body element. There is an apply name overload that takes the contents as re[et parameterd. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlBody = new HtmlBody(contents, attribs)

  /** Factory method to create an HTML body element, with no attributes, whose first contents element is an HTNL H1 header. */
  def h1(headerStr: String, otherContents: XCon*): HtmlBody = new HtmlBody(HtmlH1(headerStr) %: otherContents.toArr, RArr())
}