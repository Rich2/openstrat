/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

/** Html UL unordered list element. */
trait UlHtml extends HtmlTagLines
{ override def tagName: String = "ul"
}

/** Companion object for [[UlHtml]] unordered list HTML element class, contains factory apply method with repeat parameters. */
object UlHtml
{ /** Factory apply method for HTML UL unordered list. There is an apply name overload for an Unordered list without attributes that takes repeat parameter
   * contents elements. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): UlHtml = UlHtmlGen(contents, attribs)
  
  /** Factory apply method for HTML UL unordered list, with no attributes. */
  def apply(contents: XCon*): UlHtml = UlHtmlGen(contents.toArr, RArr())

  /** Factory method for HTML UL unordered list from [[String]]s. */
  def strs(items: String*): UlHtml = UlHtmlGen(items.mapArr(LiHtml(_)), RArr())

  def noStyle(contents: XCon*): UlHtml = UlHtmlGen(contents.toArr, RArr(ListStyleNoneAtt))
  
  /** Implementation class for the general case of og [[UlHtml]]. */
  case class UlHtmlGen(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends UlHtml
}

/** Html OL ordered list element. */
trait OlHtml extends HtmlTagLines
{ override def tagName: String = "ol"
}

/** Companion object for [[OlHtml]] ordered list HTML element class, contains factory apply method with repeat parameters. */
object OlHtml
{ /** Factory apply method for HTML OL ordered list. */
  def apply(contents: XCon*): OlHtml = OlHtmlGen(contents.toArr, RArr())

  /** Factory apply method for HTML OL ordered list. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): OlHtml = OlHtmlGen(contents, attribs)

  /** Factory method for HTML OL ordered list from [[String]]s. */
  def strs(items: String*): OlHtml = OlHtmlGen(items.mapArr(LiHtml(_)), RArr())

  /** Factory method for HTML OL ordered list from [[String]]s. */
  def noSpaceStrs(items: String*): OlHtml =
  { val style = StyleAtt(CssLi(Margin0Dec, Padding0Dec, BorderNoneDec))
    new OlHtmlGen(items.mapArr(LiHtml(_)), RArr(style))
  }

  case class OlHtmlGen(contents: RArr[XCon], attribs: RArr[XAtt]) extends OlHtml
}

/** Html LI, list item element. */
class LiHtml(val contents: RArr[XCon], val attribs: RArr[XAtt]) extends HtmlOwnLine
{ override def tagName: String = "li"
}

/** Companion object for HTML LI list element class, contains multiple methods fpr their construction. */
object LiHtml
{ /** Factory apply method for HTML LI list element [[LiHtml]] class. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): LiHtml = new LiHtml(contents, attribs)

  /** Factory apply method for HTML LI list element [[LiHtml]] class. */
  def apply(contents: XCon*): LiHtml = new LiHtml(contents.toArr, RArr())

  /** An HTML list item element that has a link as its sole content. */
  def a(link: FileSystemPath, label: String, attribs: XAtt*): LiHtml = new LiHtml(RArr(AHtml(link.asStr, label)), attribs.toArr)
}