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

/** CSS inline-block value. */
object InlineBlockVal extends CssVal
{ override def str: String = "inline-block"
}

/** CSS block value. */
object BlockVal extends CssVal
{ override def str: String = "block"
}

/** Css Display declaration. */
case class DisplayDec(value: CssVal) extends CssDecStd
{ override def property: String = "display"
}

/** Css Display declaration set to inline-block. */
object InlineBlockDec extends DisplayDec(InlineBlockVal)

/** Css Display declaration set to block. */
object BlockDec extends DisplayDec(BlockVal)

/** Style attribute with single display set to block declaration. */
object BlockStyle extends StyleAtt(RArr(BlockDec))

/** Style attribute with single display set to inline-block declaration. */
object InlineBlockStyle extends StyleAtt(RArr(InlineBlockDec))

/** Css Display declaration set to none. */
object DispNoneDec extends DisplayDec(CssNone)

/** Css Display declaration set to flex. */
object DispFlexDec extends DisplayDec(CssFlex)