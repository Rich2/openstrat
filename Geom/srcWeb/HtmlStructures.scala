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
class HtmlBody(val contents: RArr[XCon], val attribs: RArr[XmlAtt]) extends HtmlUnvoid
{ override def tag: String = "body"
  def out(indent: Int = 0, line1Delta: Int = 0, maxLineLen: Int = 150): String = openTag1 + contents.foldStr(_.out(0), "\n") + n1CloseTag
}

/** Companion object for the [[HTMLBody]] class contains factory methods.  */
object HtmlBody
{ def str(str: String): HtmlBody = new HtmlBody(RArr(str.xCon), RArr())
  def apply(inp: XCon*): HtmlBody = new HtmlBody(inp.toArr, RArr())
  def apply(contents: RArr[XCon], attribs: RArr[XmlAtt] = RArr()): HtmlBody = new HtmlBody(contents, attribs)
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

/** An Html section element. */
trait HtmlSection extends HtmlMultiLine
{ override def tag: String = "section"
  override def attribs: RArr[XmlAtt] = RArr()
}

object HtmlSection
{ /** Factory apply method for [[HtmlSection]] passing contents and attributes. There is a apply overload convenience method for passing just contents using repeat parameters. */
  def apply(contentsIn: RArr[XCon], attribsIn: RArr[XmlAtt] = RArr()): HtmlSection = new HtmlSection
  { override def contents: RArr[XCon] = contentsIn
    override def attribs: RArr[XmlAtt] = attribsIn
  }
  /** Factory apply convenience method for [[HtmlSection]] using repeat parameters. There is an apply overload method for passing contents and attributes. */
  def apply(contents: XCon*): HtmlSection = apply(contents.toArr)
}