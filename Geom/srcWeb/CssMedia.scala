/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

abstract class CssMedia(val queryStr: String) extends CssRulesHolder with CssRuleLike
{
  /** Media queries can contain only rules not other media queries. */
  override def rules: RArr[CssRule]

  override def isMultiLine: Boolean = true

  /** The CSS output. */
  override def out(indent: Int = 0): String = "@media " + queryStr.enParenth --- indent.spaces + "{" + "\n  "+ rulesOut(indent + 2) --- indent.spaces + "}"
}