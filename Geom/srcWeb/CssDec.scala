/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom._

/** CSS declaration */
trait CssDec
{ def prop: String
  def valueStr: String
  def out: String = prop + ": " + valueStr + ";"
}

trait CssDecStd extends CssDec
{ def value: CssValue
  final override def valueStr: String = value.str
}

/** CSS background-color property. */
case class CssColour(colour: Colour) extends CssDec
{ override def prop: String = "color"
  override def valueStr: String = colour.webStr
}

/** CSS background-color property. */
case class CssBGColour(colour: Colour) extends CssDec
{ override def prop: String = "background-color"
  override def valueStr: String = colour.webStr
}

object CssBGColour
{

}

case class CssTextAlign(align: TextAlign) extends CssDec
{ override def prop: String = "text-align"
  override def valueStr: String = align.jsStr
}

object CssTextCentre extends CssTextAlign(CenAlign)

case class CssFontSize(value: CssValue) extends CssDecStd
{ override def prop: String = "font-size"
}

case class CssPad(value: CssValue) extends CssDecStd
{ override def prop: String = "padding"
}
case class CssPadLt(value: CssValue) extends CssDecStd
{ override def prop: String = "padding-left"
}

case class CssSpaces(value: CssValue) extends CssDecStd
{ override def prop: String = "white-space"
}

object CssSpaces
{
  def apply(str: String): CssSpaces = new CssSpaces(CssValue(str))
}

object CssNoWrap extends CssSpaces(CssValue("nowrap"))

/** Css max-width declaration. */
case class DecMaxWidth(value: CssValue) extends CssDecStd
{ override def prop: String = "max-width"
}

case class CssMarg(value: CssValue) extends CssDecStd
{ override def prop: String = "margin"
}

case class CssMargTop(value: CssValue) extends CssDecStd
{ override def prop: String = "margin-top"
}

case class CssMargBot(value: CssValue) extends CssDecStd
{ override def prop: String = "margin-bottom"
}

case class CssMargLeft(value: CssValue) extends CssDecStd
{ override def prop: String = "margin-left"
}

case class CssMargRight(value: CssValue) extends CssDecStd
{ override def prop: String = "margin-right"
}

case class CssDisplay(value: CssValue) extends CssDecStd
{ override def prop: String = "display"
}

object DispInBlock extends CssDisplay(CssInBlock)
object DispNone extends CssDisplay(CssNone)

case class DecAlign(value: CssValue) extends CssDecStd
{ override def prop: String = "text-align"
}

object DecAlignCen extends DecAlign(CssCentre)