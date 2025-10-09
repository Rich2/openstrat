/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** CSS media query. */
abstract class CssMedia(val queryStr: String) extends CssRulesHolder, CssRuleLike
{ override def rules: RArr[CssRule]
  override def isMultiLine: Boolean = true

  override def out(indent: Int = 0, line1InputLen: Int, maxLineLen: Int): String =
    "@media " + queryStr.enParenth --- indent.spaces + "{" + "\n" +  rulesOut(indent + 2) --- indent.spaces + "}"
}

/** CSS min-width media query. */
abstract class MediaMinWidth(val qVal: CssVal) extends CssMedia("min-width:" -- qVal.str)