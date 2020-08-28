/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** HTML element. */
trait HtmlElem extends XmlishElem

case class HtmlPage(body: String)
{ def out: String = "<!doctype html>\n" + HtmlHtml(body).out
}

/** A trait for HTML elements that don't indent their children. */
trait HtmlOuterElem extends HtmlElem
{ def content: String
  def out: String = "<" + tag + ">\n" + content + "\n</" + tag + ">"
}

case class HtmlHead(content: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlOuterElem
{ override def tag: String = "head"
}

/** The "html" HTML element */
case class HtmlHtml(body: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlOuterElem
{ def tag: String = "html"
  def content: String = HtmlBody(body).out
}

case class HtmlBody(content: String)
{ def out: String = "<body>\n" + content + "\n</body>"
}