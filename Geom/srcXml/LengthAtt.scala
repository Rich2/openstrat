/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

/** A CSS length value, px, rem, em, % etc. */
trait LengthVal extends OutElem
{ /** Multiplies this length value */
  @targetName("multiply") def *(operand: Double): LengthVal

  /** Divides this length value by the operand */
  @targetName("divide") def /(operand: Double): LengthVal
}

/** A CSS length value that excludes percentage. */
trait LengthRotateable extends LengthVal


case class PixelLen(num: Double) extends LengthRotateable
{ @targetName("multiply") override def *(operand: Double): PixelLen = PixelLen(num * operand)
  @targetName("divide") override def /(operand: Double): PixelLen = PixelLen(num / operand)
  override def out: String = num.str + "px"
}

case class Percent(num: Double) extends LengthVal
{ @targetName("multiply") override def *(operand: Double): Percent = Percent(num * operand)
  @targetName("divide") override def /(operand: Double): Percent = Percent(num / operand)
  override def out: String = num.str + "%"
}

trait LengthAtt extends XAttShort

trait WidthAtt extends XAttShort
{ override def name: String = "width"
  @targetName("multiply") def * (operand: Double): WidthAtt
}

case class WidthCent(num: Double) extends WidthSvg, WidthCss
{ override def valueStr: String = num.str + "%"
  @targetName("multiply") override def *(operand: Double): WidthCent = ???
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

trait WidthCss extends WidthAtt
{
  @targetName("multiply") override def *(operand: Double): WidthCss
}

/** XML attribute for height. */
case class HeightAtt(valueStr: String) extends  XAttShort
{ override def name: String = "height"
}

object HeightAtt
{ def apply(inp: Double): HeightAtt = new HeightAtt(inp.toString)
}