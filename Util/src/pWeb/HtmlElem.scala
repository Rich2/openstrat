/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML element. */
sealed trait HtmlElem extends XmlishElem

/** trait for HTML Void elements such as br img and input. */
trait HtmlVoid extends HtmlElem
{ final override def contents: Arr[XCon] = Arr()
  //def out: String = ???
}

trait HtmlNotVoid extends HtmlElem
{ def openTag: String = openAtts + ">"
  def openTag1: String = openTag + "\n"
  def openTag2: String = openTag + "\n\n"
}

case class HtmlPage(head: HtmlHead, body: HtmlBody)
{ def out: String = "<!doctype html>\n" + HtmlHtml(head, body).out(0, 0, 150)
}

object HtmlPage
{ /** A quick and crude method for creatings an HTML page object from the title String and the HTML body contents String. */
  def title(title: String, bodyContent: String): HtmlPage = HtmlPage(HtmlHead(title), HtmlBody(title.h1Str ---- bodyContent))
}

/** HTML head element. */
case class HtmlHead(contents : Arr[XCon], attribs: Arr[XmlAtt] = Arr()) extends HtmlNotVoid
{ override def tag: String = "head"
  //override def contents: Arr[XCon] = Arr[XCon](HtmlTitle(titleStr))
  def out(indent: Int, linePosn: Int, lineLen: Int): String = openTag1 + contents.toStrsFold("\n", _.out(indent + 2, 0, 150)) + "\n" + closeTag
}
  
object HtmlHead
{ def apply(titleStr: String): HtmlHead = new HtmlHead(Arr[XCon](HtmlTitle(titleStr)))
}

/** HTML title element. */
case class HtmlTitle(str: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlElem
{ override def tag = "title"
  override def contents: Arr[XCon] = Arr(str.xCon)
  override def out(indent: Int, linePosn: Int, lineLen: Int): String = indent.spaces + "<title>" + str + "</title>"
}

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: Arr[XmlAtt] = Arr()) extends HtmlNotVoid
{ def tag: String = "html"
  override def contents = Arr(head, body)
  def out(indent: Int, linePosn: Int, lineLen: Int): String = openTag2 + head.out(0, 0, 150) + "\n\n" + body.out(0, 0, 150) + n2CloseTag
}

/** The HTML body element. */
case class HtmlBody(contentStr: String) extends HtmlNotVoid
{ override def tag: String = "body"
  override def contents: Arr[XCon] = Arr(contentStr.xCon)
  def out(indent: Int, linePosn: Int, lineLen: Int): String = openTag1 + contents.toStrsFold("\n", _.out(0, 0, 150)) + n1CloseTag
  override def attribs: Arr[XmlAtt] = Arr()
  //def out: String = "<body>\n" + content + "\n</body>"
}

case class HtmlCode(contentStr: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlElem
{ override def tag: String = "code"
  override def contents: Arr[XCon] = Arr(contentStr.xCon)
  override def out(indent: Int = 0, linePosn: Int = 0, lineLen: Int = 150): String = openUnclosed + contentStr + closeTag
}

case class HtmlCssLink(fileName: String)