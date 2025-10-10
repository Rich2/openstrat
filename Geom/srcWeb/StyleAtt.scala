/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** The Style attribute for inline CSS. CSS only recognises a single style attribute. In this API an object can inherit multiple style attributes and will
 * combine them into a single style attribute for the HTML code output. */
class StyleAtt(decs: RArr[CssDec]) extends XAttSimple
{ override def name: String = "style"

  override def valueStr: String = decs.mkStr(_.out, " ")
}

object StyleAtt
{ /** Factory apply method to create a CSS style attribute. There is an apply name overload that takes [[CssDec]] repeat parameters. */
  def apply(decs: RArr[CssDec]): StyleAtt = new StyleAtt(decs)

  /** Factory apply method to create a CSS style attribute. There is an apply name overload that takes an [[RArr]] of [[CssDec]] declarations. */
  def apply(decs: CssDec*): StyleAtt = new StyleAtt(decs.toArr)
}

/** Css Display declaration set to inline-block. */
object DispInBlock extends DecDisplay(CssInBlock)

/** Css Display declaration set to block. */
object DispBlock extends DecDisplay(CssBlock)

object DispBlockAtt extends StyleAtt(RArr(DispBlock))

/** Css Display declaration set to none. */
object DispNone extends DecDisplay(CssNone)

/** Css Display declaration set to flex. */
object DispFlex extends DecDisplay(CssFlex)