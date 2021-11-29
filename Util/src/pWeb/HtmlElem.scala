/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML element. */
trait HtmlElem extends XmlishElem
{ thisHElem: HtmlUnvoid | HtmlVoid =>
}

/** An HTML element that is not void. */
trait HtmlUnvoid extends HtmlElem
{ def openTag: String = openAtts + ">"
  def openTag1: String = openTag + "\n"
  def openTag2: String = openTag + "\n\n"
}

/** An HTML page, contains a head and a body element */
case class HtmlPage(head: HtmlHead, body: HtmlBody)
{ def out: String = "<!doctype html>\n" + HtmlHtml(head, body).out(0, 0, 150)
}

/** Companion object for the [[HtmlHead]] class. */
object HtmlPage
{ /** A quick and crude method for creating an HTML page object from the title String and the HTML body contents String. */
  def titleOnly(title: String, bodyContent: String): HtmlPage = HtmlPage(HtmlHead.title(title), HtmlBody(title.h1Str ---- bodyContent))
}

/** HTML title element. */
case class HtmlTitle(str: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlUnvoid
{ override def tag = "title"
  override def contents: Arr[XCon] = Arr(str.xCon)
  override def out(indent: Int, linePosn: Int, lineLen: Int): String = indent.spaces + "<title>" + str + "</title>"
}

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: Arr[XmlAtt] = Arr()) extends HtmlUnvoid
{ def tag: String = "html"
  override def contents = Arr(head, body)
  def out(indent: Int, linePosn: Int, lineLen: Int): String = openTag2 + head.out(0, 0, 150) + "\n\n" + body.out(0, 0, 150) + n2CloseTag
}

/** The HTML body element. */
case class HtmlBody(contentStr: String) extends HtmlUnvoid
{ override def tag: String = "body"
  override def contents: Arr[XCon] = Arr(contentStr.xCon)
  def out(indent: Int, linePosn: Int, lineLen: Int): String = openTag1 + contents.toStrsFold("\n", _.out(0, 0, 150)) + n1CloseTag
  override def attribs: Arr[XmlAtt] = Arr()
}

/** An HTML code element. */
case class HtmlCode(contentStr: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlUnvoid
{ override def tag: String = "code"
  override def contents: Arr[XCon] = Arr(contentStr.xCon)
  override def out(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = openUnclosed + contentStr + closeTag
}

case class HtmlCssLink(fileName: String)

//case class HtmlH1(strIn: String) extends Html