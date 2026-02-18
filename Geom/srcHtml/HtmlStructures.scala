/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

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

/** HTML P paragraph element. */
trait HtmlP extends HtmlOwnLine
{ def tagName = "p"
  override def closeTagLine: Boolean = true
  def text(indent: Int, line1InputLen: Int, maxLineLen: Int = MaxLineLen) = contents.foldLeft("")(_ + _.out(indent, line1InputLen, maxLineLen))
  def textLen: String = text(0, 0)
  override def toString: String = s"HtmlP $textLen characters, $attribsLen attributes"
}

object HtmlP
{ /** Factory apply method for creating HTML paragraphs. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt]): HtmlP = HtmlPGen(contents, attribs)

  /** Factory apply method for creating HTML paragraphs. */
  def apply(contents: XCon*) : HtmlP = HtmlPGen(contents.toRArr, RArr())

  /** Factory method for creating HTML paragraphs with an id attribute. There is a name overload that takes the content as an [[RArr]]. */
  def id(idStr: String, contents: XCon*): HtmlP = HtmlPGen(contents.toRArr, RArr(IdAtt(idStr)))

  /** Factory method for creating HTML paragraphs with an id attribute. There is a name overload that takes the content as repeat parameters. */
  def id(idStr: String, contents: RArr[XCon]): HtmlP = HtmlPGen(contents, RArr(IdAtt(idStr)))

  /** implementation  class for the general case of HTML P paragraph element. */
  case class HtmlPGen(contents: RArr[XCon], attribs: RArr[XAtt]) extends HtmlP
}