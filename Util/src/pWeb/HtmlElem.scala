/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** HTML element. */
trait HtmlElem extends XmlishElem

case class HtmlPage(head: HtmlHead, body: HtmlBody)
{ def out: String = "<!doctype html>\n" + HtmlHtml(head, body).out
}

object HtmlPage
{
  def apply(title: String, body: String): HtmlPage = HtmlPage(HtmlHead(title), HtmlBody(body))
}

/** A trait for HTML elements that don't indent their children. */
trait HtmlOuterElem extends HtmlElem
{ def out: String = "<" + tag + ">\n" + content + "\n</" + tag + ">"
}

case class HtmlHead(titleStr: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlOuterElem
{ override def tag: String = "head"
  override def content: Arr[XCon] = Arr[XCon](titleStr.xCon)
}

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: Arr[XmlAtt] = Arr()) extends HtmlOuterElem
{ def tag: String = "html"
  override def content = Arr(head, body)
}

case class HtmlBody(contentStr: String) extends HtmlOuterElem
{ override def tag: String = "body"
  override def content: Arr[XCon] = ???

  override def attribs: Arr[XmlAtt] = ???
  //def out: String = "<body>\n" + content + "\n</body>"
}