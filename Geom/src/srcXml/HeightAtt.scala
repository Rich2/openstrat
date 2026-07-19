/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

/** XML attribute for height. */
trait HeightAtt extends  LengthAtt
{ override def name: String = "height"
  @targetName("multiply") def *(operand: Double = 1): HeightAtt
  @targetName("divide") def /(operand: Double = 1): HeightAtt
  
  /** Constructs a width attribute in proportion to this height atttribute in the same units. */
  def widthAtt(factor: Double): WidthAtt
}

/** XML attribute for height. */
trait HeightCss extends  HeightAtt, LengthCssAtt
{ @targetName("multiply") override def *(operand: Double = 1): HeightCss
  @targetName("divide") override def /(operand: Double = 1): HeightCss
  override def widthAtt(factor: Double): WidthCss
}

case class HeightCent(numUnits: Double) extends HeightSvg, HeightCss, LengthCentAtt
{ override def valueStr: String = numUnits.str + "%"
  @targetName("multiply") override def *(operand: Double = 1): HeightCent = HeightCent(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): HeightCent = HeightCent(numUnits / operand)
  override def lengthVal: Percent = Percent(numUnits)
  override def widthAtt(factor: Double): WidthCent = WidthCent(numUnits * factor)
}

/** XML attribute for height. */
trait HeightSvg extends HeightAtt

object HeightSvg
{ /** Factory apply method to create SVG height attribute aith scalar units. */
  def apply(inp: Double): HeightScalar = new HeightScalar(inp)  
}

/** An SVG height attribute in sclar units. */
case class HeightScalar(numUnits: Double) extends HeightSvg, LengthScalarAtt
{ override def valueStr: String = numUnits.str
  @targetName("multiply") override def *(operand: Double = 1): HeightScalar = HeightScalar(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): HeightScalar = HeightScalar(numUnits / operand)
  inline override def lengthVal: Double = numUnits
  override def widthAtt(factor: Double): WidthScalar = WidthScalar(numUnits * factor)
}

/** A CSS height attribute specified in pixels. */
case class HeightPx(numUnits: Double) extends HeightCss, LengthPxAtt
{ @targetName("multiply") override def *(operand: Double = 1): HeightPx = HeightPx(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): HeightPx = HeightPx(numUnits / operand)
  override def lengthVal: PxCss = PxCss(numUnits)
  override def widthAtt(factor: Double): WidthPx = WidthPx(numUnits * factor)
}

/** A CSS height attribute specified in CSS rem units. */
case class HeightRem(numUnits: Double) extends HeightCss, LengthRemAtt
{ @targetName("multiply") override def *(operand: Double = 1): HeightRem = HeightRem(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): HeightRem = HeightRem(numUnits / operand)
  override def lengthVal: RemCss = RemCss(numUnits)
  override def widthAtt(factor: Double): WidthRem = WidthRem(numUnits * factor)
}

/** A CSS height attribute specified in CSS em units. */
case class HeightEm(numUnits: Double) extends HeightCss, LengthEmAtt
{ @targetName("multiply") override def *(operand: Double = 1): HeightEm = HeightEm(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): HeightEm = HeightEm(numUnits / operand)
  override def lengthVal: EmCss = EmCss(numUnits)
  override def widthAtt(factor: Double): WidthEm = WidthEm(numUnits * factor)
}

/** A CSS height attribute specified in CSS vmin units. */
case class HeightVmin(numUnits: Double) extends HeightCss, LengthVminAtt
{ @targetName("multiply") override def *(operand: Double = 1): HeightVmin = HeightVmin(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): HeightVmin = HeightVmin(numUnits / operand)
  override def lengthVal: VminCss = VminCss(numUnits)
  override def widthAtt(factor: Double): WidthVmin = WidthVmin(numUnits * factor)
}

/** A CSS height attribute specified in CSS vmax units. */
case class HeightVmax(numUnits: Double) extends HeightCss, LengthVmaxAtt
{ @targetName("multiply") override def *(operand: Double = 1): HeightVmax = HeightVmax(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): HeightVmax = HeightVmax(numUnits / operand)
  override def lengthVal: VmaxCss = VmaxCss(numUnits)
  override def widthAtt(factor: Double): WidthVmax = WidthVmax(numUnits * factor)
}