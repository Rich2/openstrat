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
  def apply(attribs: RArr[XAtt], contents: RArr[XCon]) = UlHtmlGen(attribs, contents)

  def apply(contents: RArr[XCon]) = UlHtmlGen(RArr(), contents)
  
  /** Factory apply method for HTML UL unordered list, with no attributes. */
  def apply(contents: XCon*): UlHtml = UlHtmlGen(RArr(), contents.toArr)

  /** Factory method for HTML UL unordered list from [[String]]s. */
  def strs(items: String*): UlHtml = UlHtmlGen(RArr(), items.mapArr(LiHtml(_)))

  def noStyle(contents: XCon*): UlHtml = UlHtmlGen(RArr(ListStyleNoneAtt), contents.toArr)
  
  /** Implementation class for the general case of og [[UlHtml]]. */
  case class UlHtmlGen(attribs: RArr[HAtt], contents: RArr[XCon]) extends UlHtml
}

/** Html OL ordered list element. */
trait OlHtml extends HtmlTagLines
{ override def tagName: String = "ol"
}

/** Companion object for [[OlHtml]] ordered list HTML element class, contains factory apply method with repeat parameters. */
object OlHtml
{ /** Factory apply method for HTML OL ordered list. */
  def apply(contents: XCon*): OlHtml = OlHtmlGen(RArr(), contents.toArr)

  /** Factory apply method for HTML OL ordered list. */
  def apply(attribs: RArr[HAtt], contents: RArr[XCon]) = OlHtmlGen(attribs, contents)

  /** Factory apply method for HTML OL ordered list. */
  def apply(contents: RArr[XCon]) = OlHtmlGen(RArr(), contents)

  /** Factory method for HTML OL ordered list from [[String]]s. */
  def strs(items: String*): OlHtml = OlHtmlGen(RArr(), items.mapArr(LiHtml(_)))

  /** Factory method for HTML OL ordered list from [[String]]s. */
  def noSpaceStrs(items: String*): OlHtml =
  { val style = StyleAtt(CssLi(Margin0Dec, Padding0Dec, BorderNoneDec))
    new OlHtmlGen(RArr(style), items.mapArr(LiHtml(_)))
  }

  case class OlHtmlGen(attribs: RArr[HAtt], contents: RArr[XCon]) extends OlHtml
}

/** Html LI, list item element. */
class LiHtml(val contents: RArr[XCon], val attribs: RArr[HAtt]) extends HtmlOwnLine
{ override def tagName: String = "li"
}

/** Companion object for HTML LI list element class, contains multiple methods fpr their construction. */
object LiHtml extends HtmlXConCompanion[LiHtml]
{ /** Factory apply method for HTML LI list element [[LiHtml]] class. */
  override def apply(attribs: RArr[HAtt], contents: RArr[XCon]): LiHtml = new LiHtml(contents, attribs)

  /** An HTML list item element that has a link as its sole content. */
  def a(link: FileSystemPath, label: String, attribs: HAtt*): LiHtml = new LiHtml(RArr(AHtml(link.asStr, label)), attribs.toArr)
}