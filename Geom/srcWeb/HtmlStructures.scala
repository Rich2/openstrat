/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML head element. */
case class HtmlHead(contents : RArr[XConElem], attribs: RArr[XAtt] = RArr()) extends HtmlUnvoid
{ override def tag: String = "head"
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(indent + 2), "\n") + "\n" + closeTag
}  

/** Companion object for the [[HtmlHead]] case class. */
object HtmlHead
{ /** Factory apply method for creating an HTML head element from repeat parameters. Generally the title and titleCss methods will be more convenient. */
  def apply(titleStr: String, otherContents: XConElem*): HtmlHead = new HtmlHead(otherContents.toArr)

  /** Factory method for creating an HTML head element with [[HtmlTitle]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter elements. */
  def title(titleStr: String, otherContents: XConElem*): HtmlHead =
  { val seq = List(HtmlTitle(titleStr), HtmlUtf8, HtmlViewDevWidth) ++ otherContents
    new HtmlHead(seq.toArr)
  }

  /** Factory method for creating an HTML head element with [[HtmlTitle]], [[HtmlCssLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter
   *  elements. */
  def titleCss(titleStr: String, cssFileStem: String): HtmlHead =
    new HtmlHead(RArr[XConElem](HtmlTitle(titleStr), HtmlCssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth))
}

/** The HTML body element. */
class HtmlBody(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlUnvoid
{ override def tag: String = "body"
  def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(0), "\n") + n1CloseTag
}

/** Companion object for the [[HTMLBody]] class contains factory methods.  */
object HtmlBody
{ def str(str: String): HtmlBody = new HtmlBody(RArr(str), RArr())
  def apply(inp: XCon*): HtmlBody = new HtmlBody(inp.toArr, RArr())
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlBody = new HtmlBody(contents, attribs)
}

/** HTML Div element.  */
trait HtmlDiv extends HtmlUnvoid
{ override def tag: String = "div"
}

/** Companion object for the [[HtmlDiv]] DIV element class, contains various factory methods. */
object HtmlDiv
{ /** Factory apply method for div HTML element. There is an apply overload that takes an [[RArr]] of [[XConInline]] and an [[RArr]] of [[XAtt]], with a default of no
   * [[XAtt]]s. */
  def apply(input: XCon*): HtmlDiv = new HtmlDivGen(input.toRArr, RArr())

  /** Factory apply method for div HTML element. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlDiv = new HtmlDivGen(contents, attribs)

  /** Factory method to create Div element with an ID attribute. */
  def id(id: String, contents: XCon*): HtmlDiv = new HtmlDivGen(contents.toArr, RArr(IdAtt(id)))

  /** Factory method to create Div element with a class attribute. */
  def classAtt(id: String, contents: XCon*): HtmlDiv = new HtmlDivGen(contents.toArr, RArr(ClassAtt(id)))

  class HtmlDivGen(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlDiv, HtmlOwnLine
}

class HtmlDivOneLine(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlDiv
{
  override def out(indent: Int, line1InputLen: Int, maxLineLen: Int): String = ???
}

object HtmlDivOneLine

/** An HTML Canvas element. */
case class HtmlCanvas(contents: RArr[XCon], attribs: RArr[XAtt]) extends HtmlOwnLine
{ override def tag: String = "canvas"
}

object HtmlCanvas
{ /** Constructs an HTML canvas with an id attribute. */
  def id(idStr: String): HtmlCanvas = new HtmlCanvas(RArr(), RArr(IdAtt(idStr)))

  def apply(): HtmlCanvas = new HtmlCanvas(RArr(), RArr())
}

/** An Html section element. */
trait HtmlSection extends HtmlTagLines
{ override def tag: String = "section"
  override def attribs: RArr[XAtt] = RArr()
}

object HtmlSection
{ /** Factory apply method for [[HtmlSection]] passing contents and attributes. There is a apply overload convenience method for passing just contents using repeat parameters. */
  def apply(contentsIn: RArr[XConElem], attribsIn: RArr[XAtt] = RArr()): HtmlSection = new HtmlSection
  { override def contents: RArr[XConElem] = contentsIn
    override def attribs: RArr[XAtt] = attribsIn
  }
  /** Factory apply convenience method for [[HtmlSection]] using repeat parameters. There is an apply overload method for passing contents and attributes. */
  def apply(contents: XConElem*): HtmlSection = apply(contents.toArr)
}