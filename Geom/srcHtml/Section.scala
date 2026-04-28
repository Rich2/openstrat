/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** An HTML section element. */
trait Section extends HtmlTagLines
{ override def tagName: String = "section"
  override def attribs: RArr[XAtt] = RArr()
}

object Section
{ /** Factory apply method for [[Section]] passing contents and attributes. There is an apply overload convenience method for passing just contents using
   * repeat parameters. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): Section = new SectionGen(contents, attribs)

  /** Factory apply convenience method for [[Section]] using repeat parameters. There is an apply overload method for passing contents and attributes. */
  def apply(contents: XCon*): Section = new SectionGen(contents.toArr, RArr())

  /** General implementation class for HTML section element. */
  class SectionGen(val contents: RArr[XCon], override val attribs: RArr[XAtt]) extends Section
}

/** HTML OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class OlSection(val header: RArr[XCon], items: RArr[LiHtml]) extends Section
{ override def contents: RArr[XCon] = header +% orderedList
  override def attribs: RArr[XAtt] = RArr()

  def orderedList: OlHtml = OlHtml(items)
}

object OlSection
{ /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
 * section. */
  def apply(header: XCon, items: LiHtml*): OlSection = new OlSection(RArr(header), items.toArr)

  /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   *  section. */
  def apply(header: RArr[XCon], items: LiHtml*): OlSection = new OlSection(header, items.toArr)

  /** Factory method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   * section. */
  def strs(header: XCon, items: String*): OlSection = new OlSection(RArr(header), items.mapArr(LiHtml(_)))

  /** Factory method for an HTML OL ordered list, with an H2 header. */
  def h2(headerStr: String, items: LiHtml*): OlSection = new OlSection(RArr(H2Html(headerStr)), items.toArr)
}

/** HTML UL unordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class UlSection(val header: RArr[XCon], items: RArr[LiHtml]) extends Section
{ override def contents: RArr[XCon] = header +% unorderedList
  override def attribs: RArr[XAtt] = RArr()

  /** the HTML unordered list element. */
  def unorderedList: UlHtml = UlHtml(items)
}

object UlSection
{ /** Factory apply method to construct unordered list with a header, as an [[Section]]. */
  def apply(header: XCon, items: LiHtml*): UlSection = new UlSection(RArr(header), items.toArr)

  /** Factory method to construct unordered list with a header, as an [[Section]], where the header is a single [[XCon]] element and the list items are just
   * [[String]]s. */
  def strs(header: XCon, items: String*): UlSection = new UlSection(RArr(header), items.mapArr(str => LiHtml(str)))

  /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   *  section. */
  def apply(header: RArr[XCon], items: LiHtml*): UlSection = new UlSection(header, items.toArr)
}