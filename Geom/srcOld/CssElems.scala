/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pWeb

trait CssCon extends IndentCon

object CssDec
{
  def apply(propIn: String, valStrIn: String): CssDec = new CssDec
  { val prop = propIn
    val valStr = valStrIn
  }
}

trait CssDec extends CssCon
{ def prop: String
  def valStr: String
  def out(indent: Int): String = prop :- valStr +";"
  //def multiLine: Boolean = false
}

trait CssPercent extends CssDec
{ def percents: Int
  def valStr: String = percents.toString + "%"
}

trait CssEm extends CssDec
{ def ems: Double
  def valStr: String = ems.toString + "em"
}

case class CssFill(valStr: String) extends CssDec { def prop = "fill" }
case class CssFontFamily(valStr: String) extends CssDec { def prop = "font-family" }
trait CssFontSize extends CssDec { def prop = "font-size" }
case class CssFontEm(ems: Double) extends CssFontSize with CssEm// { def valStr = ems.toString - "em" }
case class CssFontCent(percents: Int) extends CssFontSize with CssPercent //{ def valStr = cents.toString - "%" }

case class CssColour(colour: Colour) extends CssDec
{ def prop = "color"
  override val valStr: String = colour.hexStr 
}

case class CssStroke(valStr: String) extends CssDec { def prop = "stroke" }

case class CssStrokeWidth(value: Int) extends CssDec
{ def prop = "stroke"
  def valStr = value.toString   
}

object CssMaxWidth
{ def apply(valStrIn: String): CssMaxWidth = new CssMaxWidth { val valStr = valStrIn }
}

trait CssMaxWidth extends CssDec { def prop = "max-width" }
case class CssMaxWidthEm(ems: Double) extends CssDec with CssEm { def prop = "max-width" }
case class CssMaxHeight(valStr: String) extends CssDec { def prop = "max-height" }

case class CssMaxHeightVH(valInt: Integer) extends CssDec
{ def prop = "max-height"
  def valStr: String = valInt.toString + "vh"
}

case class CssFlex (valStr: String) extends CssDec { def prop = "flex" }
case class CssWidth(valStr: String) extends CssDec{ def prop = "width" }

case class CssWidthCent(valInt: Int)extends CssDec
{ def prop = "width"
  def valStr = valInt.toString + "%"
}

object CssMargin
{
   def apply(valStrIn: String): CssMargin = new CssMargin { val valStr = valStrIn }
}
trait CssMargin extends CssDec { def prop = "margin" }
object CssMarginAuto extends CssMargin { def valStr = "auto" }
case class CssTopMarginEm(ems: Double) extends CssEm { def prop = "margin-top" }