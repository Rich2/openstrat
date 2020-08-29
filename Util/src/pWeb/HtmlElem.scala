/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** HTML element. */
trait HtmlElem extends XmlishElem

case class HtmlPage(head: HtmlHead, body: HtmlBody)
{ def out: String = "<!doctype html>\n" + HtmlHtml(head, body).out
}

object HtmlPage
{ def apply(title: String, body: String): HtmlPage = HtmlPage(HtmlHead(title), HtmlBody(body))
}

case class HtmlHead(titleStr: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlElem
{ override def tag: String = "head"
  override def content: Arr[XCon] = Arr[XCon](HtmlTitle(titleStr))
  def out: String = openTag1 + content.toStrsFold("\n", _.out) + "\n" + closeTag
}

case class HtmlTitle(str: String) extends HtmlElem
{
  def tag = "title"

  override def attribs: Arr[XmlAtt] = Arr()

  override def content: Arr[XCon] = ???
  override def out = "<title>" + str + "</title>"
}

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: Arr[XmlAtt] = Arr()) extends HtmlElem
{ def tag: String = "html"
  override def content = Arr(head, body)
  def out: String = openTag2 + head.out + "\n\n" + body.out + n2CloseTag
}

case class HtmlBody(contentStr: String) extends HtmlElem
{ override def tag: String = "body"
  override def content: Arr[XCon] = Arr(contentStr.xCon)
  def out: String = openTag1 + content.toStrsFold("\n", _.out) + n1CloseTag
  override def attribs: Arr[XmlAtt] = Arr()
  //def out: String = "<body>\n" + content + "\n</body>"
}