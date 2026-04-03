/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Html UL unordered list element. */
trait HtmlUl extends HtmlTagLines
{ override def tagName: String = "ul"
}

/** Companion object for [[HtmlUl]] unordered list HTML element class, contains factory apply method with repeat parameters. */
object HtmlUl
{ /** Factory apply method for HTML UL unordered list. There is an apply name overload for an Unordered list without attributes that takes repeat parameter
   * contents elements. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlUl = HtmlUlGen(contents, attribs)
  
  /** Factory apply method for HTML UL unordered list, with no attributes. */
  def apply(contents: XCon*): HtmlUl = HtmlUlGen(contents.toArr, RArr())

  /** Factory method for HTML UL unordered list from [[String]]s. */
  def strs(items: String*): HtmlUl = HtmlUlGen(items.mapArr(HtmlLi(_)), RArr())

  def noStyle(contents: XCon*): HtmlUl = HtmlUlGen(contents.toArr, RArr(ListStyleNoneAtt))
  
  /** Implementation class for the general case of og [[HtmlUl]]. */
  case class HtmlUlGen(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlUl
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

  /** Factory method for HTML OL ordered list from [[String]]s. */
  def strs(items: String*): HtmlOl = HtmlOlGen(items.mapArr(HtmlLi(_)), RArr())

  /** Factory method for HTML OL ordered list from [[String]]s. */
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

  /** An HTML list item element that has a link as its sole content. */
  def a(link: AllDirFilePathBase, label: String, attribs: XAtt*): HtmlLi = new HtmlLi(RArr(HtmlA(link.asStr, label)), attribs.toArr)

  /** An HTML list item element that has a link, followed by some text as its sole contents. */
  def linkAndText(link: String, label: String, otherText: String, attribs: XAtt*): HtmlLi =
    new HtmlLi(RArr(new HtmlA(link, RArr(label)), otherText), attribs.toArr)
}