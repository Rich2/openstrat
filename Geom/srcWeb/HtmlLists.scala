/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** Html UL unordered list element. */
case class HtmlUl(val contents: RArr[XCon], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "ul"
}

/** Companion object for [[HtmlUl]] unordered list HTML element class, contains factory apply method with repeat parameters. */
object HtmlUl
{ /** Factory apply method for HTML UL unordered list. */
  def apply(contents: XCon*): HtmlUl = new HtmlUl(contents.toArr)
}
/** Html OL ordered list element. */
case class HtmlOl(val contents: RArr[XCon], val attribs: RArr[XmlAtt] = RArr()) extends HtmlMultiLine
{ override def tag: String = "ol"
}

/** Companion object for [[HtmlOl]] ordered list HTML element class, contains factory apply method with repeat parameters. */
object HtmlOl
{ /** Factory apply method for HTML OL ordered list. */
  def apply(contents: XCon*): HtmlOl = new HtmlOl(contents.toArr)
}

/** Html LI, list item element. */
case class HtmlLi(contents: RArr[XCon], attribs: RArr[XmlAtt] = RArr()) extends HtmlInline
{ override def tag: String = "li"
}

/** Companion object for HTML LI list element class, contains multiple methods fpr their construction. */
object HtmlLi
{ /** Factory apply method for HTML LI list element [[HtmlLi]] class. */
  def apply(contents: XCon*): HtmlLi = new HtmlLi(contents.toArr)
  def apply(text: String): HtmlLi = HtmlLi(RArr(text.xCon))
  /** An HTML list item element that has a link as its sole content. */
  def a(link: String, label: String, attribs: XmlAtt*): HtmlLi = new HtmlLi(RArr( new HtmlA(link, RArr(label.xCon))), attribs.toArr)

  /** An HTML list item element that has a link, followed by some text as its sole contents. */
  def linkAndText(link: String, label: String,otherText: String, attribs: XmlAtt*): HtmlLi =
    new HtmlLi(RArr(new HtmlA(link, RArr(label.xCon)), otherText.xCon), attribs.toArr)

  def boldStart(str1: String, str2: String): HtmlLi = HtmlLi(str1.htmlB)

  def sbtAndText(sbtStr: String, str2: String): HtmlLi = new HtmlLi(RArr(HtmlSbtInline(sbtStr), str2.xCon))
}

/** Html OL ordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class HtmlOlWithLH(val head: XCon, items: RArr[HtmlLi]) extends HtmlSection
{ override def contents: RArr[XCon] = RArr(head, orderedList)
  override def attribs: RArr[XmlAtt] = RArr()

  def orderedList: HtmlOl = HtmlOl(items)
}

object HtmlOlWithLH
{
  def apply(header: XCon, items: HtmlLi*): HtmlOlWithLH = new HtmlOlWithLH(header, items.toArr)
  def apply(headerStr: String, items: HtmlLi*): HtmlOlWithLH = new HtmlOlWithLH(headerStr.xCon, items.toArr)
}

/** Html UL unordered list, with an effective LH list header. As the LH never made it into the W3C standard this is implemented as a section. */
class HtmlUlWithLH(val header: XCon, items: RArr[HtmlLi]) extends HtmlSection
{ override def contents: RArr[XCon] = RArr(header, unorderedList)
  override def attribs: RArr[XmlAtt] = RArr()

  /** the HTML unordered list element. */
  def unorderedList: HtmlUl = HtmlUl(items)
}

object HtmlUlWithLH
{ def apply(header: XCon, items: HtmlLi*): HtmlUlWithLH = new HtmlUlWithLH(header, items.toArr)
  def apply(headerStr: String, items: HtmlLi*): HtmlUlWithLH = new HtmlUlWithLH(headerStr.xCon, items.toArr)
  def strs(headerStr: String, items: String*): HtmlUlWithLH = new HtmlUlWithLH(headerStr.xCon, items.mapArr(str => HtmlLi(str)))
}