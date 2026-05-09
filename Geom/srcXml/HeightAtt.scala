/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName


/** XML attribute for height. */
trait HeightAtt extends  LengthAtt
{ override def name: String = "height"
  @targetName("multiply") def * (operand: Double): HeightAtt
  @targetName("divide") def / (operand: Double): HeightAtt
  
  def widthAtt(factor: Double): WidthAtt
}

/** XML attribute for height. */
trait HeightCss extends  HeightAtt, LengthCssAtt
{ @targetName("multiply") override def *(operand: Double): HeightCss
  @targetName("divide") override def /(operand: Double): HeightCss
  override def widthAtt(factor: Double): WidthCss
}

case class HeightCent(numUnits: Double) extends HeightSvg, HeightCss
{ override def valueStr: String = numUnits.str + "%"
  @targetName("multiply") override def *(operand: Double): HeightCent = HeightCent(numUnits * operand)
  @targetName("divide") override def /(operand: Double): HeightCent = HeightCent(numUnits / operand)
  override def lengthVal: LenCss = Percent(numUnits)
  override def widthAtt(factor: Double): WidthCent = WidthCent(numUnits * factor)
}

/** XML attribute for height. */
trait HeightSvg extends HeightAtt

object HeightSvg
{ def apply(inp: Double): HeightSvg = new HeightSvgGen(inp)

  case class HeightSvgGen(num: Double) extends HeightSvg
  { override def valueStr: String = num.str
    @targetName("multiply") override def *(operand: Double): HeightSvg = HeightSvgGen(num * operand)
    @targetName("divide") override def /(operand: Double): HeightSvg = HeightSvgGen(num / operand)
    override def widthAtt(factor: Double): WidthSvg = WidthSvg(num * factor)
  }
}

trait HeightPro extends HeightCss, LengthPro
{ @targetName("multiply") override def *(operand: Double): HeightPro
  @targetName("divide") override def /(operand: Double): HeightPro
  override def widthAtt(factor: Double): WidthPro
}

case class HeightPx(numUnits: Double) extends HeightPro
{ @targetName("multiply") override def *(operand: Double): HeightPx = HeightPx(numUnits * operand)
  @targetName("divide") override def /(operand: Double): HeightPx = HeightPx(numUnits / operand)
  override def lengthVal: PxCss = PxCss(numUnits)
  override def widthAtt(factor: Double): WidthPx = WidthPx(numUnits * factor)
}