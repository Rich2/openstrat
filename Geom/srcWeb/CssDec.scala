/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom.*

/** base trait for [[CssDec]] and [[CssDecMulti]], certain scala declarations that translate to multiple CSS Declarations. */
trait CssDecs
{ /** The declaration sequence, for [[CssDec]] this will have a single member. */
  def decs: RArr[CssDec]
}

/** CSS declaration. This consists of a key-value pair. */
trait CssDec extends CssDecs
{ /** The CSS property */
  def prop: String

  /** The CSS value output. */
  def valueStr: String

  /** The CSS code output. */
  def out: String = prop + ": " + valueStr + ";"

  override def decs: RArr[CssDec] = RArr(this)
}

/** Standard CSS declaration which takes a [[CssVal]] to produce the CSS value output. */
trait CssDecStd extends CssDec
{ /** The CSS declaration value */
  def value: CssVal

  final override def valueStr: String = value.str
}

/** CSS color declaration. */
case class ColourDec(colour: Colour) extends CssDec
{ override def prop: String = "color"
  override def valueStr: String = colour.webStr
}

/** CSS background-color property. */
case class BGColourDec(colour: Colour) extends CssDec
{ override def prop: String = "background-color"
  override def valueStr: String = colour.webStr
}

/** CSS text-align declaration. */
case class TextAlignDec(align: TextAlign) extends CssDec
{ override def prop: String = "text-align"
  override def valueStr: String = align.jsStr
}

/** CSS text-align declaration set to center. */
object TextCentreDec extends TextAlignDec(CenAlign)

/** CSS font-size declaration. */
case class FontSizeDec(value: CssVal) extends CssDecStd
{ override def prop: String = "font-size"
}

/** CSS white-space declaration. */
case class WhiteSpacesDec(value: CssVal) extends CssDecStd
{ override def prop: String = "white-space"
}

object WhiteSpacesDec
{ /** Factory apply method for creating CSS white-space declaration. */
  def apply(str: String): WhiteSpacesDec = new WhiteSpacesDec(CssVal(str))
}

object NoWrapVal extends CssVal
{ override val str: String = "nowrap"
}

/** CSS white-space declaration set to nowrap. */
object NoWrapDec extends WhiteSpacesDec(NoWrapVal)

/** Css max-width declaration. */
case class MaxWidthDec(value: CssVal) extends CssDecStd
{ override def prop: String = "max-width"
}

/** CSS declaration for font width. */
case class FontWidthDec(value: CssVal) extends CssDecStd
{ override def prop: String = "font-weight"
}

/** CSS declaration for font width set to bold. */
object DecBold extends FontWidthDec(BoldVal)

/** Css text-align declaration. */
case class DecAlign(value: CssTextAlignVal | CssGenVal) extends CssDecStd
{ override def prop: String = "text-align"
}

/** Css text-align declaration set to left. */
object DecAlignLeft extends DecAlign(CssLeft)

/** Css text-align declaration set to right. */
object DecAlignRight extends DecAlign(CssRight)

/** Css text-align declaration set to center. */
object DecAlignCen extends DecAlign(CssCentre)

/** Css text-align declaration set to center. */
object DecAlignJus extends DecAlign(CssJustify)

/** CSS width declaration. */
case class DecWidth(value: CssVal) extends CssDecStd
{ override def prop: String = "width"
}

/** CSS height declaration. */
case class DecHeight(value: CssVal) extends CssDecStd
{ override def prop: String = "height"
}

/** CSS width and height declarations set to same value. */
case class DecWidthHeight(value: CssVal) extends CssDecs
{ override def decs: RArr[CssDec] = RArr(DecWidth(value), DecHeight(value))
}

/** CSS min=height declaration. */
case class DecMinHeight(value: CssVal) extends CssDecStd
{ override def prop: String = "min-height"
}

/** Css flex-direction declaration. */
case class DecFlexDirn(value: CssVal) extends CssDecStd
{ override def prop: String = "flex-direction"
}

/** Css text-align declaration set to start. */
object DecFlexDirnCol extends DecFlexDirn(CssCol)

/** Css Overflow declaration. */
case class DecOverflow(value: CssVal) extends CssDecStd
{ override def prop: String = "overflow"
}

/** Css Overflow declaration set to inline-block. */
object DecOverflowHidden extends DecOverflow(CssHidden)