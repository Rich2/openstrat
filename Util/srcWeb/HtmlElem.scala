/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML element. */
trait HtmlElem extends XmlElemLike
{ thisHElem: HtmlUnvoid | HtmlVoid =>
}

/** An HTML element that is not void. */
trait HtmlUnvoid extends HtmlElem
{ def openTag: String = openAtts + ">"
  def openTag1: String = openTag + "\n"
  def openTag2: String = openTag + "\n\n"
}

/** An HTML element that is not void, but has no content. */
trait HtmlEmpty extends HtmlUnvoid
{ override def out(indent: Int, maxLineLen: Int = 150): String = openUnclosed + closeTag
  override def contents: RArr[XCon] = RArr()
}

trait HtmlInline extends HtmlUnvoid
{ def str: String
  override def contents: RArr[XCon] = RArr(str.xCon)
  override def out(indent: Int, maxLineLen: Int): String = openTag + str + closeTag
}

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
  def out(indent: Int, maxLineLen: Int): String = openTag2 + head.out(0, 150) + "\n\n" + body.out(0, 150) + n2CloseTag
}

/** The HTML body element. */
case class HtmlBody(contents: RArr[XCon]) extends HtmlUnvoid
{ override def tag: String = "body"
  //override def  = RArr(contentStr.xCon)
  def out(indent: Int, maxLineLen: Int): String = openTag1 + contents.foldStr(_.out(0, 150), "\n") + n1CloseTag
  override def attribs: RArr[XmlAtt] = RArr()
}

object HtmlBody{
  def apply(str: String): HtmlBody = new HtmlBody(RArr(str.xCon))
  def elems(inp: XCon*): HtmlBody = new HtmlBody(inp.toArr)
}

/** An HTML code element. */
case class HtmlCode(contentStr: String, attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ override def tag: String = "code"
  override def contents: RArr[XCon] = RArr(contentStr.xCon)
  override def out(indent: Int = 0, maxLineLen: Int = 150): String = openUnclosed + contentStr + closeTag
}

case class HtmlCanvas(attribs: RArr[XmlAtt] = RArr()) extends HtmlEmpty
{ override def tag: String = "canvas"
}

object HtmlCanvas
{ def id(idStr: String): HtmlCanvas = new HtmlCanvas(RArr(IdAtt(idStr)))
}

case class HtlmA(link: String, label: String = "") extends HtmlInline
{ override def tag: String = "a"
  override def attribs: RArr[XmlAtt] = RArr(HrefAtt(link))
  override def contents: RArr[XCon] = RArr(label.xCon)
  inline override def str: String = label
}

case class HtmlH1(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag = "h1"
}

case class HtmlH2(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ def tag = "h2"
}

case class HtmlH3(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ def tag = "h3"
}