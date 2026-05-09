/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

trait WidthAtt extends LengthAtt
{ override def name: String = "width"
  @targetName("multiply") override def * (operand: Double): WidthAtt
  @targetName("divide") override def / (operand: Double): WidthAtt

  def heightAtt(factor: Double): HeightAtt
}

trait WidthCss extends WidthAtt, LengthCssAtt
{ @targetName("multiply") override def *(operand: Double): WidthCss
  @targetName("divide") override def / (operand: Double): WidthCss  
  override def heightAtt(factor: Double): HeightCss
}

/** CSS width defined as a percentage. */
case class WidthCent(numUnits: Double) extends WidthSvg, WidthCss
{ @targetName("multiply") override def *(operand: Double): WidthCent = WidthCent(numUnits * operand)
  @targetName("divide") override def /(operand: Double): WidthCent = WidthCent(numUnits / operand)
  override def lengthVal: LenCss = Percent(numUnits)
  override def heightAtt(factor: Double): HeightCent = HeightCent(numUnits * factor) 
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
    override def heightAtt(factor: Double): HeightSvg = HeightSvg(num * factor)
  }
}

trait WidthPro extends WidthCss, LengthPro
{ @targetName("multiply") override def *(operand: Double): WidthPro
  @targetName("divide") override def /(operand: Double): WidthPro
  override def lengthVal: LengthRotateable
}

case class WidthPx(numUnits: Double) extends WidthPro
{ @targetName("multiply") override def *(operand: Double): WidthPx = WidthPx(numUnits * operand)
  @targetName("divide") override def /(operand: Double): WidthPx = WidthPx(numUnits / operand)
  override def lengthVal: PxCss = PxCss(numUnits)
  override def heightAtt(factor: Double): HeightPx = HeightPx(numUnits * factor)
}