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
{ def value: CssVal
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

case class CssFontSize(value: CssVal) extends CssDecStd
{ override def prop: String = "font-size"
}


case class CssSpaces(value: CssVal) extends CssDecStd
{ override def prop: String = "white-space"
}

object CssSpaces
{
  def apply(str: String): CssSpaces = new CssSpaces(CssVal(str))
}

object CssNoWrap extends CssSpaces(CssVal("nowrap"))

/** Css max-width declaration. */
case class DecMaxWidth(value: CssVal) extends CssDecStd
{ override def prop: String = "max-width"
}

/** Css Display declaration. */
case class DecDisplay(value: CssVal) extends CssDecStd
{ override def prop: String = "display"
}

object DispInBlock extends DecDisplay(CssInBlock)
object DispBlock extends DecDisplay(CssBlock)
object DispNone extends DecDisplay(CssNone)

case class DecAlign(value: CssVal) extends CssDecStd
{ override def prop: String = "text-align"
}

object DecAlignCen extends DecAlign(CssCentre)
object DecAlignStart extends DecAlign(CssStart)