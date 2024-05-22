/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb
import geom._

/** CSS declaration */
trait CssDec
{ def prop: String
  def valueStr: String
  def out: String = prop + ": " + valueStr + ";"
}

trait CssDecText extends CssDec
{ def value: CssValue
  override def valueStr: String = value.str
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

case class CssTextAlign(align: TextAlign) extends CssDec
{ override def prop: String = "text-align"
  override def valueStr: String = align.jsStr
}

object CssTextCentre extends CssTextAlign(CenAlign)

case class CssFontSize(value: CssValue) extends CssDecText
{ override def prop: String = "font-size"
}

case class CssPadLt(value: CssValue) extends CssDec
{ override def prop: String = "padding-left"
  override def valueStr: String = value.str
}

case class CssSpaces(value: CssValue) extends CssDec
{ override def prop: String = "white-space"
  override def valueStr: String = value.str
}

case class CssMargTop(value: CssValue) extends CssDec
{ override def prop: String = "margin-tpp"
  override def valueStr: String = value.str
}

case class CssMargBot(value: CssValue) extends CssDec
{ override def prop: String = "margin-bottom"
  override def valueStr: String = value.str
}