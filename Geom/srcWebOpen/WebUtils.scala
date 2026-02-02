 /* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package osweb

val largeListStr = "LargeList"

val listLargeRule: CssClassRule = CssClassRule(largeListStr, RArr(PadLeftDec(1.em)))

val liLargeRule: CssChildRule = listLargeRule.child("li", MarginTBDec(1.em))

val utilRules: RArr[CssRule] = RArr(listLargeRule, liLargeRule)

object LargeListAtt extends ClassAtt(largeListStr)

trait HtmlLargeList

/** An HTML OL ordered list with the LargeList CSS class */
class HtmlLargeOl(val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends HtmlOl
{ override def attribs: RArr[XAtt] = LargeListAtt %: otherAttribs
}

object HtmlLargeOl
{/** Factory apply method for a large HTML OL ordered list. */
  def apply(contents: XCon*): HtmlLargeOl = new HtmlLargeOl(contents.toArr, RArr())

  /** Factory apply method for a large HTML OL ordered list. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlLargeOl = new HtmlLargeOl(contents, attribs)

  /** Factory method for a large HTML OL ordered list from [[String]]s. */
  def strs(items: String*): HtmlLargeOl = new HtmlLargeOl(items.mapArr(HtmlLi(_)), RArr())
}

/** An HTML UL unordered list with the LargeList CSS class */
class HtmlLargeUl(val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends HtmlOl
{ override def attribs: RArr[XAtt] = LargeListAtt %: otherAttribs
}

object HtmlLargeUl
{/** Factory apply method for a large HTML UL unordered list. */
  def apply(contents: XCon*): HtmlLargeUl = new HtmlLargeUl(contents.toArr, RArr())

  /** Factory apply method for a large HTML UL unordered list. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): HtmlLargeUl = new HtmlLargeUl(contents, attribs)

  /** Factory method for a large HTML UL unordered list from [[String]]s. */
  def strs(items: String*): HtmlLargeUl = new HtmlLargeUl(items.mapArr(HtmlLi(_)), RArr())
}