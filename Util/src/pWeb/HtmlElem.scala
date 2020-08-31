/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

/** HTML element. */
trait HtmlElem extends XmlishElem
{ override def openVoid: String = openAtts + "/>"
}

case class HtmlPage(head: HtmlHead, body: HtmlBody)
{ def out: String = "<!doctype html>\n" + HtmlHtml(head, body).out(0, 0, 150)
}

object HtmlPage
{ /** Creates an HTML page object form the title String and the HTML body contents String. */
  def title(title: String, bodyContent: String): HtmlPage = HtmlPage(HtmlHead(title), HtmlBody(bodyContent))
}

/** HTML head element. */
case class HtmlHead(titleStr: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlElem
{ override def tag: String = "head"
  override def contents: Arr[XCon] = Arr[XCon](HtmlTitle(titleStr))
  def out(indent: Int, linePosn: Int, lineLen: Int): String = openTag1 + contents.toStrsFold("\n", _.out(indent + 2, 0, 150)) + "\n" + closeTag
}

/** HTML title element. */
case class HtmlTitle(str: String, attribs: Arr[XmlAtt] = Arr()) extends HtmlElem
{ override def tag = "title"
  override def contents: Arr[XCon] = Arr(str.xCon)
  override def out(indent: Int, linePosn: Int, lineLen: Int): String = indent.spaces + "<title>" + str + "</title>"
}

/** The "html" HTML element */
case class HtmlHtml(head: HtmlHead, body: HtmlBody, attribs: Arr[XmlAtt] = Arr()) extends HtmlElem
{ def tag: String = "html"
  override def contents = Arr(head, body)
  def out(indent: Int, linePosn: Int, lineLen: Int): String = openTag2 + head.out(0, 0, 150) + "\n\n" + body.out(0, 0, 150) + n2CloseTag
}

/** The HTML body element. */
case class HtmlBody(contentStr: String) extends HtmlElem
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