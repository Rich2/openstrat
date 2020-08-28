/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb


case class HtmlPage(body: String)
{
  def out: String = "<!doctype html>\n" + HtmlHtml(body).out
}

trait HtmlElem
{ def tag: String
}

/** A trait for HTML elements that don't indent their children. */
trait HtmlOuterElem extends HtmlElem
{ def content: String
  def out: String = "<" + tag + ">\n" + content + "\n</" + tag + ">"
}

case class HtmlHead(content: String)

/** The "html" HTML element */
case class HtmlHtml(body: String) extends HtmlOuterElem
{ def tag: String = "html"
  def content: String = HtmlBody(body).out
}

case class HtmlBody(content: String)
{
  def out: String = "<body>\n" + content + "\n</body>"
}