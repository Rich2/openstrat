/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

abstract class CssMedia(val queryStr: String) extends CssRulesHolder with CssRuleLike
{
  /** Media queries can contain only rules not other media queries. */
  override def rules: RArr[CssRule]

  override def isMultiLine: Boolean = true

  /** The CSS output. */
  override def out: String = "@media " + queryStr.enParenth + rulesOut.enCurly
}