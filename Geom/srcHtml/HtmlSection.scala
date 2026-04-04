/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An HTML section element. */
trait HtmlSection extends HtmlTagLines
{ override def tagName: String = "section"
  override def attribs: RArr[XAtt] = RArr()
}

object HtmlSection
{ /** Factory apply method for [[HtmlSection]] passing contents and attributes. There is a apply overload convenience method for passing just contents using repeat parameters. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlSection = new HtmlSectionGen(contents, attribs)

  /** Factory apply convenience method for [[HtmlSection]] using repeat parameters. There is an apply overload method for passing contents and attributes. */
  def apply(contents: XCon*): HtmlSection = new HtmlSectionGen(contents.toArr, RArr())

  /** General implementation class for HTML section element. */
  class HtmlSectionGen(val contents: RArr[XCon], override val attribs: RArr[XAtt]) extends HtmlSection
}

/** HTML section element with an h2 element as its 1st content. */
class SectionH2(val titleStr: String, val otherContents: RArr[XCon], override val attribs: RArr[XAtt]) extends HtmlSection
{ /** The H2 title of this HTML section. */
  def title: HtmlH2 = HtmlH2(titleStr)
  override def contents: RArr[XCon] = title %: otherContents
}

object SectionH2
{ /** Factory apply method for HTML section with an H2 header. */
  def apply(titleStr: String, otherContents: XCon*): SectionH2 = new SectionH2(titleStr, otherContents.toRArr, RArr())
}

/** HTML OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class OlSection(val header: RArr[XCon], items: RArr[HtmlLi]) extends HtmlSection
{ override def contents: RArr[XCon] = header +% orderedList
  override def attribs: RArr[XAtt] = RArr()

  def orderedList: HtmlOl = HtmlOl(items)
}

object OlSection
{ /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
 * section. */
  def apply(header: XCon, items: HtmlLi*): OlSection = new OlSection(RArr(header), items.toArr)

  /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   *  section. */
  def apply(header: RArr[XCon], items: HtmlLi*): OlSection = new OlSection(header, items.toArr)

  /** Factory method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   * section. */
  def strs(header: XCon, items: String*): OlSection = new OlSection(RArr(header), items.mapArr(HtmlLi(_)))

  /** Factory method for an HTML OL ordered list, with an H2 header. */
  def h2(headerStr: String, items: HtmlLi*): OlSection = new OlSection(RArr(HtmlH2(headerStr)), items.toArr)
}

/** HTML UL unordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class UlSection(val header: RArr[XCon], items: RArr[HtmlLi]) extends HtmlSection
{ override def contents: RArr[XCon] = header +% unorderedList
  override def attribs: RArr[XAtt] = RArr()

  /** the HTML unordered list element. */
  def unorderedList: HtmlUl = HtmlUl(items)
}

object UlSection
{ /** Factory apply method to construct unordered list with a header, as an [[HtmlSection]]. */
  def apply(header: XCon, items: HtmlLi*): UlSection = new UlSection(RArr(header), items.toArr)

  /** Factory method to construct unordered list with a header, as an [[HtmlSection]], where the header is a single [[XCon]] element and the list items are just
   * [[String]]s. */
  def strs(header: XCon, items: String*): UlSection = new UlSection(RArr(header), items.mapArr(str => HtmlLi(str)))

  /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   *  section. */
  def apply(header: RArr[XCon], items: HtmlLi*): UlSection = new UlSection(header, items.toArr)
}