/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb; package osweb

val largeListStr = "LargeList"

val listLargeRule: CssClassRule = CssClassRule(largeListStr, RArr(PadLeftDec(1.em)))

val liLargeRule: CssChildRule = listLargeRule.child("li", MarginTBDec(1.em))

/** LargeList class attribute. */
object LargeListAtt extends ClassAtt(largeListStr)

/** Common trait for large ordered and unordered HTML Lists. */
trait ListLarge extends HtmlTagLines
{ /** Any other attributes in addition to LargeListAtt */
  def otherAttribs: RArr[XAtt]

  override def attribs: RArr[XAtt] = LargeListAtt %: otherAttribs
}

/** An HTML OL ordered list with the LargeList CSS class */
class OlLarge(val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends OlHtml, ListLarge

object OlLarge
{/** Factory apply method for a large HTML OL ordered list. */
  def apply(contents: XCon*): OlLarge = new OlLarge(contents.toArr, RArr())

  /** Factory apply method for a large HTML OL ordered list. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): OlLarge = new OlLarge(contents, attribs)

  /** Factory method for a large HTML OL ordered list from [[String]]s. */
  def strs(items: String*): OlLarge = new OlLarge(items.mapArr(LiHtml(_)), RArr())
}

/** An HTML UL unordered list with the LargeList CSS class */
class UlLarge(val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends OlHtml, ListLarge

object UlLarge
{/** Factory apply method for a large HTML UL unordered list. */
  def apply(contents: XCon*): UlLarge = new UlLarge(contents.toArr, RArr())

  /** Factory apply method for a large HTML UL unordered list. */
  def apply(contents: RArr[XCon], attribs: RArr[XAtt] = RArr()): UlLarge = new UlLarge(contents, attribs)

  /** Factory method for a large HTML UL unordered list from [[String]]s. */
  def strs(items: String*): UlLarge = new UlLarge(items.mapArr(LiHtml(_)), RArr())
}