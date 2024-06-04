/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS media query. */
abstract class CssMedia(val queryStr: String) extends CssRulesHolder with CssRuleLike
{ override def rules: RArr[CssRule]
  override def isMultiLine: Boolean = true

  override def out(indent: Int = 0): String =
    "@media " + queryStr.enParenth --- indent.spaces + "{" + "\n" +  rulesOut(indent + 2) --- indent.spaces + "}"
}

/** CSS min-width media query. */
abstract class MediaMinWidth(val qVal: CssVal) extends CssMedia("min-width:" -- qVal.str)