/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML page, contains a head and a body element */
case class HtmlPage(head: HtmlHead, body: HtmlBody)
{ def out: String = "<!doctype html>\n" + HtmlHtml(head, body).out(0, 150)
}

/** Companion object for the [[HtmlHead]] class. */
object HtmlPage
{ /** A quick and crude method for creating an HTML page object from the title String and the HTML body contents String. */
  def titleOnly(title: String, bodyContent: String): HtmlPage = HtmlPage(HtmlHead.title(title), HtmlBody.elems(HtmlH1(title), bodyContent.xCon))
}

/** HTML title element. */
case class HtmlTitle(str: String, attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ override def tag = "title"
  override def contents: RArr[XCon] = RArr(str.xCon)
  override def out(indent: Int, maxLineLen: Int): String = indent.spaces + "<title>" + str + "</title>"
}

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ def tag: String = "html"
  override def contents = RArr(head, body)
  def out(indent: Int, maxLineLen: Int): String = openTag2 + head.out() + "\n\n" + body.out(0, 150) + n2CloseTag
}