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

/** Companion object for the [[HTMLBody]] class contains factory methods.  */
object HtmlBody
{ def str(str: String): HtmlBody = new HtmlBody(RArr(str), RArr())
  def apply(inp: XCon*): HtmlBody = new HtmlBody(inp.toArr, RArr())
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlBody = new HtmlBody(contents, attribs)
}