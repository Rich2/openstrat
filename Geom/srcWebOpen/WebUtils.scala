 /* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package osweb

object WebUtils
{
  val largeListStr = "LargeList"

  val listLargeRule: CssClassRule = CssClassRule(largeListStr, RArr(PadLeftDec(1.em)))

  val liLargeRule: CssChildRule = listLargeRule.child("li", MarginTBDec(2.em))

  val rules: RArr[CssRule] = RArr(listLargeRule, liLargeRule)//, CssRule("ul li", MarginDec(0.25.em)))
}

object LargeListAtt extends ClassAtt(WebUtils.largeListStr)

trait HtmlLargeList

class HtmlLargeOl(val contents: RArr[XCon], val otherAttribs: RArr[XAtt]) extends HtmlOl
{ override def attribs: RArr[XAtt] = LargeListAtt %: otherAttribs
}