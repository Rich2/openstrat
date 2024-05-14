/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML page, contains a head and a body element */
trait HtmlPage
{
  def head: HtmlHead
  def body: HtmlBody
  def htmlElem: HtmlHtml = HtmlHtml(head, body)
  def out: String = "<!doctype html>\n" + htmlElem.out(0, 150)
  def zioOut: String = "\n" + htmlElem.out(0, 150)
}

/** Companion object for the [[HtmlHead]] class. */
object HtmlPage
{ /** Factory apply method for [[HtmlPage]]. */
  def apply(headIn: HtmlHead, bodyIn: HtmlBody): HtmlPage = new HtmlPage
  { override val head: HtmlHead = headIn
    override val body: HtmlBody = bodyIn
  }

  /** A quick and crude method for creating an HTML page object from the title String and the HTML body contents String. */
  def titleOnly(title: String, bodyContent: String): HtmlPage = new HtmlPage{
    override val head: HtmlHead = HtmlHead.title(title)
    override val body: HtmlBody = HtmlBody(HtmlH1(title), bodyContent.xCon)
  }
}

/** HTML title element. */
case class HtmlTitle(str: String, attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ override def tag = "title"
  override def contents: RArr[XCon] = RArr(str.xCon)
  override def out(indent: Int, line1Delta: Int = 0, maxLineLen: Int = 150): String = indent.spaces + "<title>" + str + "</title>"
}

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ def tag: String = "html"
  override def contents: RArr[HtmlUnvoid] = RArr(head, body)
  def out(indent: Int, line1Delta: Int = 0, maxLineLen: Int = 150): String = openTag2 + head.out() + "\n\n" + body.out(0) + n2CloseTag
}