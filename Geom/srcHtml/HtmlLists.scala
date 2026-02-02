/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Html UL unordered list element. */
class HtmlUl(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlTagLines
{ override def tagName: String = "ul"
}

/** Companion object for [[HtmlUl]] unordered list HTML element class, contains factory apply method with repeat parameters. */
object HtmlUl
{ /** Factory apply method for HTML UL unordered list. There is an apply name overload for an Unordered list without attributes that takes repeat parameter
   * contents elements. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlUl = new HtmlUl(contents, attribs)
  
  /** Factory apply method for HTML UL unordered list, with no attributes. */
  def apply(contents: XCon*): HtmlUl = new HtmlUl(contents.toArr, RArr())

  /** Factory method for HTML UL unordered list frpm [[String]]s. */
  def strs(items: String*): HtmlUl = new HtmlUl(items.mapArr(HtmlLi(_)), RArr())

  def noStyle(contents: XCon*): HtmlUl = new HtmlUl(contents.toArr, RArr(ListStyleNoneAtt))
}

/** Html OL ordered list element. */
trait HtmlOl extends HtmlTagLines
{ override def tagName: String = "ol"
}

/** Companion object for [[HtmlOl]] ordered list HTML element class, contains factory apply method with repeat parameters. */
object HtmlOl
{ /** Factory apply method for HTML OL ordered list. */
  def apply(contents: XCon*): HtmlOl = HtmlOlGen(contents.toArr, RArr())

  /** Factory apply method for HTML OL ordered list. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlOl = HtmlOlGen(contents, attribs)

  /** Factory method for HTML OL ordered list frpm [[String]]s. */
  def strs(items: String*): HtmlOl = HtmlOlGen(items.mapArr(HtmlLi(_)), RArr())

  /** Factory method for HTML OL ordered list frpm [[String]]s. */
  def noSpaceStrs(items: String*): HtmlOl =
  { val style = StyleAtt(CssLi(Margin0Dec, Padding0Dec, BorderNoneDec))
    new HtmlOlGen(items.mapArr(HtmlLi(_)), RArr(style))
  }

  case class HtmlOlGen(contents: RArr[XCon], attribs: RArr[XAtt]) extends HtmlOl
}

/** Html LI, list item element. */
case class HtmlLi(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()) extends HtmlOwnLine
{ override def tagName: String = "li"
}

/** Companion object for HTML LI list element class, contains multiple methods fpr their construction. */
object HtmlLi
{ /** Factory apply method for HTML LI list element [[HtmlLi]] class. */
  def apply(contents: XCon*): HtmlLi = new HtmlLi(contents.toArr)

  /** An HTML list item element that has a link as its sole content. */
  def a(link: String, label: String, attribs: XAtt*): HtmlLi = new HtmlLi(RArr(HtmlA(link, label)), attribs.toArr)

  /** An HTML list item element that has a link, followed by some text as its sole contents. */
  def linkAndText(link: String, label: String, otherText: String, attribs: XAtt*): HtmlLi =
    new HtmlLi(RArr(new HtmlA(link, RArr(label)), otherText), attribs.toArr)
}

/** Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class HtmlOlWithLH(val header: RArr[XCon], items: RArr[HtmlLi]) extends HtmlSection
{ override def contents: RArr[XCon] = header +% orderedList
  override def attribs: RArr[XAtt] = RArr()

  def orderedList: HtmlOl = HtmlOl(items)
}

object HtmlOlWithLH
{ /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   * section. */
  def apply(header: XCon, items: HtmlLi*): HtmlOlWithLH = new HtmlOlWithLH(RArr(header), items.toArr)

  /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   *  section. */
  def apply(header: RArr[XCon], items: HtmlLi*): HtmlOlWithLH = new HtmlOlWithLH(header, items.toArr)

  /** Factory method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   * section. */
  def strs(header: XCon, items: String*): HtmlOlWithLH = new HtmlOlWithLH(RArr(header), items.mapArr(HtmlLi(_)))

  /** Factory method for an HTML OL ordered list, with an H2 header. */
  def h2(headerStr: String, items: HtmlLi*): HtmlOlWithLH = new HtmlOlWithLH(RArr(HtmlH2(headerStr)), items.toArr)
}

/** Html UL unordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class HtmlUlWithLH(val header: RArr[XCon], items: RArr[HtmlLi]) extends HtmlSection
{ override def contents: RArr[XCon] = header +% unorderedList
  override def attribs: RArr[XAtt] = RArr()

  /** the HTML unordered list element. */
  def unorderedList: HtmlUl = HtmlUl(items)
}

object HtmlUlWithLH
{ /** Factory apply method to construct unordered list with a header, as an [[HtmlSection]]. */
  def apply(header: XCon, items: HtmlLi*): HtmlUlWithLH = new HtmlUlWithLH(RArr(header), items.toArr)

  /** Factory method to construct unordered list with a header, as an [[HtmlSection]], where the header is a single [[XCon]] element and the list items are just
   * [[String]]s. */
  def strs(header: XCon, items: String*): HtmlUlWithLH = new HtmlUlWithLH(RArr(header), items.mapArr(str => HtmlLi(str)))

  /** Factory apply method for Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a
   *  section. */
  def apply(header: RArr[XCon], items: HtmlLi*): HtmlUlWithLH = new HtmlUlWithLH(header, items.toArr)
}