/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

trait HtmlElem
{
  def tag: String
}

trait HtmlOuterElem extends HtmlElem
{
  def content: String
  def out: String = "<" + tag + ">\n" + content + "\n</" + tag + ">"
}

/** The "html" HTML element */
case class HtmlHtml(body: String) extends HtmlOuterElem
{
  def tag: String = "html"
  def content: String = HtmlBody(body).out
}

case class HtmlBody(content: String)
{
  def out: String = "<body>\n" + content + "\n</body>"
}