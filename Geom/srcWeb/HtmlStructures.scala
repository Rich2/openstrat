/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML head element. */
case class HtmlHead(contents : RArr[XCon], attribs: RArr[XmlAtt] = RArr()) extends HtmlUnvoid
{ override def tag: String = "head"
  //override def contents: Arr[XCon] = Arr[XCon](HtmlTitle(titleStr))
  def out(indent: Int = 0, maxLineLen: Int = 150): String = openTag1 + contents.foldStr(_.out(indent + 2, 150), "\n") + "\n" + closeTag
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
  def out(indent: Int = 0, maxLineLen: Int = 150): String = openTag1 + contents.foldStr(_.out(0, 150), "\n") + n1CloseTag
  override def attribs: RArr[XmlAtt] = RArr()
}

/** Companion object for the [[HTMLBody]] class contains factory methods.  */
object HtmlBody
{ def str(str: String): HtmlBody = new HtmlBody(RArr(str.xCon))
  def apply(inp: XCon*): HtmlBody = new HtmlBody(inp.toArr)
}

class HtmlDiv(val contents: RArr[XCon], val attribs: RArr[XmlAtt]) extends HtmlMultiLine
{ override def tag: String = "div"
}

object HtmlDiv
{
  def id(id: String, contents: XCon*): HtmlDiv = new HtmlDiv(contents.toArr, RArr(IdAtt(id)))
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

/** Html OL ordered list, with an effectiive LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class HtmlOlWithLH(val head: XCon, items: RArr[HtmlLi]) extends HtmlSect
{ override def contents: RArr[XCon] = RArr(head, orderedList)
  override def attribs: RArr[XmlAtt] = RArr()

  def orderedList: HtmlOl = HtmlOl(items)
}

object HtmlOlWithLH{
  def apply(head: XCon, items: HtmlLi*): HtmlOlWithLH = new HtmlOlWithLH(head, items.toArr)
}