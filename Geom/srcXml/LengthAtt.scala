/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

/** A length attribute for CSS and SVG. */
trait LengthAtt extends XAttShort

trait WidthAtt extends XAttShort
{ override def name: String = "width"
  @targetName("multiply") def * (operand: Double): WidthAtt
}

trait LengthCssAtt extends LengthAtt
{
  def lengthVal: LengthVal
}

case class WidthCent(num: Double) extends WidthSvg, WidthCss
{ override def valueStr: String = num.str + "%"
  @targetName("multiply") override def *(operand: Double): WidthCent = WidthCent(num * operand)
  override def lengthVal: LengthVal = Percent(num)
}

trait WidthSvg extends WidthAtt
{ @targetName("multiply") override def *(operand: Double): WidthSvg
}

object WidthSvg
{ def apply(inp: Double): WidthSvg = new WidthSvgGen(inp)

  case class WidthSvgGen(num: Double) extends WidthSvg
  { override def valueStr: String = num.str
    @targetName("multiply") override def *(operand: Double): WidthSvg = WidthSvgGen(num * operand)
  }
}

trait WidthCss extends WidthAtt, LengthCssAtt
{
  @targetName("multiply") override def *(operand: Double): WidthCss
}

/** XML attribute for height. */
trait HeightAtt extends  XAttShort
{ override def name: String = "height"
  @targetName("multiply") def * (operand: Double): HeightAtt
}

/** XML attribute for height. */
trait HeightCss extends  HeightAtt
{ @targetName("multiply") override def *(operand: Double): HeightCss
}

case class HeightCent(num: Double) extends HeightSvg, HeightCss
{ override def valueStr: String = num.str + "%"
  @targetName("multiply") override def *(operand: Double): HeightCent = ???
}

/** XML attribute for height. */
trait HeightSvg extends HeightAtt

object HeightSvg
{ def apply(inp: Double): HeightSvg = new HeightSvgGen(inp)

  case class HeightSvgGen(num: Double) extends HeightSvg
  { override def valueStr: String = num.str
    @targetName("multiply") override def *(operand: Double): HeightSvg = HeightSvgGen(num * operand)
  }
}