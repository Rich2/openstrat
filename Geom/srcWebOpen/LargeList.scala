 /* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb; package osweb

object LargeList
{
  val classStr = "LargeList"
  
  val classRule = CssClassRule(classStr, RArr(PadLeftDec (1.em)))
  
  val liRule = classRule.child("li", MarginTBDec(2.em))

  val rules = RArr(classRule, CssRule("ol li", MarginTBDec(2.em)), CssRule("ul li", MarginDec(0.25.em)),
    CssRule("ol > li", MarginTBDec(1.em)))
}