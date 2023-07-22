/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML head element. */
case class HtmlHead(contents : RArr[XCon], attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ override def tag: String = "head"
  def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = 150): String = openTag1 + contents.foldStr(_.out(indent + 2), "\n") + "\n" + closeTag
}  

/** Companion object for the [[HtmlHead]] case class. */
object HtmlHead
{ def title(titleStr: String): HtmlHead = new HtmlHead(RArr[XCon](HtmlTitle(titleStr), HtmlUtf8, HtmlViewDevWidth))
  def titleCss(titleStr: String, cssFileStem: String): HtmlHead =
    new HtmlHead(RArr[XCon](HtmlTitle(titleStr), HtmlCssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth))
}

/** The HTML body element. */
case class HtmlBody(contents: RArr[XCon]) extends HtmlUnvoid
{ override def tag: String = "body"
  def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = 150): String = openTag1 + contents.foldStr(_.out(0), "\n") + n1CloseTag
  override def attribs: RArr[XmlAtt] = RArr()
}

/** Companion object for the [[HTMLBody]] class contains factory methods.  */
object HtmlBody
{ def str(str: String): HtmlBody = new HtmlBody(RArr(str.xCon))
  def apply(inp: XCon*): HtmlBody = new HtmlBody(inp.toArr)
}

/** HTML Div element. */
class HtmlDiv(val contents: RArr[XCon], val attribs: RArr[XmlAtt]) extends HtmlMultiLine
{ override def tag: String = "div"
}

/** Companion object for the [[HtmlDiv]] DIV element class, contains various factory methods. */
object HtmlDiv
{ /** Factory apply method for DIV HTML element. */
  def apply(contents: RArr[XCon], attribs: RArr[XmlAtt] = RArr()): HtmlDiv = new HtmlDiv(contents, attribs)

  /** Factory method to create Div element with an ID attribute. */
  def id(id: String, contents: XCon*): HtmlDiv = new HtmlDiv(contents.toArr, RArr(IdAtt(id)))

  /** Factory method to create Div element with a class attribute. */
  def classAtt(id: String, contents: XCon*): HtmlDiv = new HtmlDiv(contents.toArr, RArr(ClassAtt(id)))
}

/** An HTML Canvas element. */
case class HtmlCanvas(attribs: RArr[XmlAtt] = RArr()) extends HtmlEmpty
{ override def tag: String = "canvas"
}

object HtmlCanvas
{ /** Constructs an HTML canvas with an id attribute. */
  def id(idStr: String): HtmlCanvas = new HtmlCanvas(RArr(IdAtt(idStr)))
}

trait HtmlSect extends HtmlMultiLine
{ override def tag: String = "section"
}

/** Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class HtmlOlWithLH(val head: XCon, items: RArr[HtmlLi]) extends HtmlSect
{ override def contents: RArr[XCon] = RArr(head, orderedList)
  override def attribs: RArr[XmlAtt] = RArr()

  def orderedList: HtmlOl = HtmlOl(items)
}

object HtmlOlWithLH
{
  def apply(header: XCon, items: HtmlLi*): HtmlOlWithLH = new HtmlOlWithLH(header, items.toArr)
  def apply(headerStr: String, items: HtmlLi*): HtmlOlWithLH = new HtmlOlWithLH(headerStr.xCon, items.toArr)
}

/** Html UL unordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class HtmlUlWithLH(val header: XCon, items: RArr[HtmlLi]) extends HtmlSect
{ override def contents: RArr[XCon] = RArr(header, unorderedList)
  override def attribs: RArr[XmlAtt] = RArr()

  def unorderedList: HtmlUl = HtmlUl(items)
}

object HtmlUlWithLH
{ def apply(header: XCon, items: HtmlLi*): HtmlUlWithLH = new HtmlUlWithLH(header, items.toArr)
  def apply(headerStr: String, items: HtmlLi*): HtmlUlWithLH = new HtmlUlWithLH(headerStr.xCon, items.toArr)
}

case class HtmlTable(val contents: RArr[HtmlRow], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "table"
}
object HtmlTable{
  def apply(contents: HtmlRow*):  HtmlTable = new HtmlTable(contents.toArr)
}

case class HtmlRow(val contents: RArr[HtmlTd], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "tr"
}

object HtmlRow
{
  def strs2(str1: String, str2: String): HtmlRow = HtmlRow(RArr(HtmlTd(str1), HtmlTd(str2)))
}

case class HtmlTd(val contents: RArr[XCon], val attribs: RArr[XmlAtt]) extends HtmlInline
{ override def tag: String = "td"
}

object HtmlTd
{
  def apply(str: String) = new HtmlTd(RArr(str.xCon), RArr())
}