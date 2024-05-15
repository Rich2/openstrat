/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom._

/** CSS declaration */
trait CssDec
{ def prop: String
  def valueStr: String
  def out: String = prop + ": " + valueStr + ";"
}

/** CSS background-color property. */
case class CssBGColour(colour: Colour) extends CssDec
{ override def prop: String = "background-color"
  override def valueStr: String = colour.webStr
}

case class CssTextAlign(align: TextAlign) extends CssDec
{ override def prop: String = "text-align"
  override def valueStr: String = align.jsStr
}

object CssTextCentre extends CssTextAlign(CenAlign)

case class CssFontSize(value: CssValue) extends CssDec
{ override def prop: String = "font-size"
  override def valueStr: String = value.str
}

case class CssPadLt(value: CssValue) extends CssDec
{ override def prop: String = "padding-left"
  override def valueStr: String = value.str
}