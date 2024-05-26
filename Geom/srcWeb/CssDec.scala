/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom._

/** CSS declaration */
trait CssDec
{ /** The CSS property */
  def prop: String

  /** The CSS value output. */
  def valueStr: String

  /** The CSS code output. */
  def out: String = prop + ": " + valueStr + ";"
}

/** Standard CSS deccalaration which takes a [[CssVal]] to produce the CSS value output. */
trait CssDecStd extends CssDec
{ /** The CSS declaration value */
  def value: CssVal

  final override def valueStr: String = value.str
}

/** CSS color declaration. */
case class DecColour(colour: Colour) extends CssDec
{ override def prop: String = "color"
  override def valueStr: String = colour.webStr
}

/** CSS background-color property. */
case class CssBGColour(colour: Colour) extends CssDec
{ override def prop: String = "background-color"
  override def valueStr: String = colour.webStr
}

/** CSS text-align declaration. */
case class DecTextAlign(align: TextAlign) extends CssDec
{ override def prop: String = "text-align"
  override def valueStr: String = align.jsStr
}

/** CSS text-align declaration set to center. */
object DecTextCentre extends DecTextAlign(CenAlign)

/** CSS font-size declaration. */
case class DecFontSize(value: CssVal) extends CssDecStd
{ override def prop: String = "font-size"
}

/** CSS white-space declaration. */
case class DecSpaces(value: CssVal) extends CssDecStd
{ override def prop: String = "white-space"
}

object DecSpaces
{ def apply(str: String): DecSpaces = new DecSpaces(CssVal(str))
}

/** CSS white-space declaration set to nowrap. */
object DecNoWrap extends DecSpaces(CssVal("nowrap"))

/** Css max-width declaration. */
case class DecMaxWidth(value: CssVal) extends CssDecStd
{ override def prop: String = "max-width"
}

/** Css Display declaration. */
case class DecDisplay(value: CssVal) extends CssDecStd
{ override def prop: String = "display"
}

/** Css Display declaration set to inline-block. */
object DispInBlock extends DecDisplay(CssInBlock)

/** Css Display declaration set to block. */
object DispBlock extends DecDisplay(CssBlock)

/** Css Display declaration set to none. */
object DispNone extends DecDisplay(CssNone)

/** Css text-align declaration. */
case class DecAlign(value: CssVal) extends CssDecStd
{ override def prop: String = "text-align"
}

/** Css text-align declaration set to center. */
object DecAlignCen extends DecAlign(CssCentre)

/** Css text-align declaration set to start. */
object DecAlignStart extends DecAlign(CssStart)