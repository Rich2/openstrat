/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

trait WidthAtt extends LengthAtt
{ override def name: String = "width"
  @targetName("multiply") override def * (operand: Double): WidthAtt
  @targetName("divide") override def / (operand: Double): WidthAtt
}

trait WidthCss extends WidthAtt, LengthCssAtt
{ @targetName("multiply") override def *(operand: Double): WidthCss
  @targetName("divide") override def / (operand: Double): WidthCss
  override def valueStr: String = numUnits.str + lengthVal.extStr
}

/** CSS width defined as a percentage. */
case class WidthCent(numUnits: Double) extends WidthSvg, WidthCss
{ @targetName("multiply") override def *(operand: Double): WidthCent = WidthCent(numUnits * operand)
  @targetName("divide") override def /(operand: Double): WidthCent = WidthCent(numUnits / operand)
  override def lengthVal: LenCss = Percent(numUnits)
}

trait WidthSvg extends WidthAtt
{ @targetName("multiply") override def *(operand: Double): WidthSvg
  @targetName("divide") override def /(operand: Double): WidthSvg
}

object WidthSvg
{ def apply(inp: Double): WidthSvg = WidthSvgGen(inp)

  case class WidthSvgGen(num: Double) extends WidthSvg
  { override def valueStr: String = num.str
    @targetName("multiply") override def *(operand: Double): WidthSvg = WidthSvgGen(num * operand)
    @targetName("divide") override def /(operand: Double): WidthSvg = WidthSvgGen(num / operand)
  }
}

trait WidthRotateableCss extends WidthCss, LengthRotateableCss
{ @targetName("multiply") override def *(operand: Double): WidthRotateableCss
  @targetName("divide") override def /(operand: Double): WidthRotateableCss
  override def lengthVal: LengthRotateable
}

case class WidthPx(numUnits: Double) extends WidthRotateableCss
{ @targetName("multiply") override def *(operand: Double): WidthPx = WidthPx(numUnits * operand)
  @targetName("divide") override def /(operand: Double): WidthPx = WidthPx(numUnits / operand)
  override def lengthVal: PxCss = PxCss(numUnits)
}