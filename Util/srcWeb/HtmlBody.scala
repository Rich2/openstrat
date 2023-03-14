/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** The HTML body element. */
case class HtmlBody(contents: RArr[XCon]) extends HtmlUnvoid
{ override def tag: String = "body"
  def out(indent: Int = 0, maxLineLen: Int = 150): String = openTag1 + contents.foldStr(_.out(0, 150), "\n") + n1CloseTag
  override def attribs: RArr[XmlAtt] = RArr()
}

object HtmlBody
{ def apply(str: String): HtmlBody = new HtmlBody(RArr(str.xCon))
  def elems(inp: XCon*): HtmlBody = new HtmlBody(inp.toArr)
}

/** An HTML code element. */
case class HtmlCode(contentStr: String, attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ override def tag: String = "code"
  override def contents: RArr[XCon] = RArr(contentStr.xCon)
  override def out(indent: Int = 0, maxLineLen: Int = 150): String = openUnclosed + contentStr + closeTag
}

/** An HTML Canvas element. */
case class HtmlCanvas(attribs: RArr[XmlAtt] = RArr()) extends HtmlEmpty
{ override def tag: String = "canvas"
}

object HtmlCanvas
{ def id(idStr: String): HtmlCanvas = new HtmlCanvas(RArr(IdAtt(idStr)))
}

/** HTML anchor. */
case class HtmlA(link: String, contents: RArr[XCon]) extends HtmlInline
{ override def tag: String = "a"
  override def attribs: RArr[XmlAtt] = RArr(HrefAtt(link))
}

object HtmlA
{ def apply(link: String, label: String): HtmlA = new HtmlA(link, RArr(label.xCon))
}

case class HtmlLi(contents: RArr[XCon], attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "li"
}

object HtmlLi
{
  def a(link: String, label: String): HtmlLi = new HtmlLi(RArr( new HtmlA(link, RArr(label.xCon))))
}

class HtmlUl(val contents: RArr[XCon], val attribs: RArr[XmlAtt] = RArr()) extends HtmlOutline
{ override def tag: String = "ul"
}

case class HtmlH1(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ override def tag = "h1"
}

case class HtmlH2(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h2"
}

case class HtmlH3(str : String, attribs: RArr[XmlAtt] = RArr()) extends HtmlStr
{ def tag = "h3"
}