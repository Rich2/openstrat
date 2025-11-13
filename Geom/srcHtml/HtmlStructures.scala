/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** HTML head element. */
case class HtmlHead(contents : RArr[XConCompound], attribs: RArr[XAtt] = RArr()) extends HtmlTagLines, HtmlUnvoid
{ override def tagName: String = "head"
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(indent + 2), "\n") + "\n" + closeTag
}  

/** Companion object for the [[HtmlHead]] case class. */
object HtmlHead
{ /** Factory apply method for creating an HTML head element from repeat parameters. Generally the title and titleCss methods will be more convenient. */
  def apply(titleStr: String, otherContents: XConCompound*): HtmlHead = new HtmlHead(otherContents.toArr)

  /** Factory method for creating an HTML head element with [[HtmlTitle]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter elements. */
  def title(titleStr: String, otherContents: XConCompound*): HtmlHead =
  { val seq = List(HtmlTitle(titleStr), HtmlUtf8, HtmlViewDevWidth) ++ otherContents
    new HtmlHead(seq.toArr)
  }

  /** Factory method for creating an HTML head element with [[HtmlTitle]], [[HtmlCssLink]], [[HtmlUtf8]], [[HtmlViewDevWidth]] plus the repeat parameter
   *  elements. */
  def titleCss(titleStr: String, cssFileStem: String): HtmlHead =
    new HtmlHead(RArr[XConCompound](HtmlTitle(titleStr), HtmlCssLink(cssFileStem), HtmlUtf8, HtmlViewDevWidth))
}

/** The HTML body element. */
class HtmlBody(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlTagLines, HtmlUnvoid
{ override def tagName: String = "body"
  override def out(indent: Int = 0, line1InputLen: Int = 0, maxLineLen: Int = 150): String =
    openTag1(indent, line1InputLen, maxLineLen) + contents.mkStr(_.out(0), "\n") + n1CloseTag
}

/** Companion object for the [[HTMLBody]] class contains factory methods.  */
object HtmlBody
{ def str(str: String): HtmlBody = new HtmlBody(RArr(str), RArr())
  def apply(inp: XCon*): HtmlBody = new HtmlBody(inp.toArr, RArr())
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlBody = new HtmlBody(contents, attribs)
}

/** An HTML Canvas element. */
case class HtmlCanvas(contents: RArr[XCon], attribs: RArr[XAtt]) extends HtmlOwnLine
{ override def tagName: String = "canvas"
}

object HtmlCanvas
{ /** Constructs an HTML canvas with an id attribute. */
  def id(idStr: String): HtmlCanvas = new HtmlCanvas(RArr(), RArr(IdAtt(idStr)))

  /** Factory apply method for an HTML Canvas. */
  def apply(): HtmlCanvas = new HtmlCanvas(RArr(), RArr())
}

/** An Html section element. */
trait HtmlSection extends HtmlTagLines
{ override def tagName: String = "section"
  override def attribs: RArr[XAtt] = RArr()
}

object HtmlSection
{ /** Factory apply method for [[HtmlSection]] passing contents and attributes. There is a apply overload convenience method for passing just contents using repeat parameters. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlSection = new HtmlSectionGen(contents, attribs)

  /** Factory apply convenience method for [[HtmlSection]] using repeat parameters. There is an apply overload method for passing contents and attributes. */
  def apply(contents: XCon*): HtmlSection = new HtmlSectionGen(contents.toArr, RArr())

  class HtmlSectionGen(val contents: RArr[XCon], override val attribs: RArr[XAtt]) extends HtmlSection
}

class SectionH2(val titleStr: String, val otherContents: RArr[XCon], override val attribs: RArr[XAtt]) extends HtmlSection
{ /** The H2 title of this HTML section. */
  def title: HtmlH2 = HtmlH2(titleStr)
  override def contents: RArr[XCon] = title %: otherContents
}

object SectionH2
{ /** Factory apply method for HTML section with an H2 header. */
  def apply(titleStr: String, otherContents: XCon*): SectionH2 = new SectionH2(titleStr, otherContents.toRArr, RArr())
}