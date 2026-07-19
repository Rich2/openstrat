/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

trait WidthAtt extends LengthAtt
{ override def name: String = "width"
  @targetName("multiply") override def *(operand: Double = 1): WidthAtt
  @targetName("divide") override def /(operand: Double = 1): WidthAtt

  /** Constructs a height attribute in proportion to this width attribute. */
  def heightAtt(factor: Double = 1): HeightAtt
}

trait WidthCss extends WidthAtt, LengthCssAtt
{ @targetName("multiply") override def *(operand: Double = 1): WidthCss
  @targetName("divide") override def /(operand: Double = 1): WidthCss  
  override def heightAtt(factor: Double = 1): HeightCss
}

/** SVG attribute for width. */
trait WidthSvg extends WidthAtt
{ @targetName("multiply") override def *(operand: Double = 1): WidthSvg
  @targetName("divide") override def /(operand: Double = 1): WidthSvg
  override def heightAtt(factor: Double = 1): HeightSvg
}

object WidthSvg
{ /** Factory apply method to creat width attribute for SVG wth scalar units. */
  def apply(inp: Double): WidthScalar = WidthScalar(inp)
}

/** An SVG / XML width attribute with scalar units. */
case class WidthScalar(numUnits: Double) extends WidthSvg, LengthScalarAtt
{ override def valueStr: String = numUnits.str
  @targetName("multiply") override def *(operand: Double = 1): WidthScalar = WidthScalar(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): WidthScalar = WidthScalar(numUnits / operand)
  override def lengthVal: Double = numUnits
  override def heightAtt(factor: Double): HeightScalar = HeightScalar(numUnits * factor)
}

/** CSS width defined as a percentage. */
case class WidthCent(numUnits: Double) extends WidthSvg, WidthCss, LengthCentAtt
{ @targetName("multiply") override def *(operand: Double = 1): WidthCent = WidthCent(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): WidthCent = WidthCent(numUnits / operand)
  override def lengthVal: Percent = Percent(numUnits)
  override def heightAtt(factor: Double): HeightCent = HeightCent(numUnits * factor) 
}

/** A CSS width attribute specified in pixels. */
case class WidthPx(numUnits: Double) extends WidthCss, LengthPxAtt
{ @targetName("multiply") override def *(operand: Double = 1): WidthPx = WidthPx(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): WidthPx = WidthPx(numUnits / operand)
  override def lengthVal: PxCss = PxCss(numUnits)
  override def heightAtt(factor: Double): HeightPx = HeightPx(numUnits * factor)
}

/** A CSS width attribute specified in CSS rem units. */
case class WidthRem(numUnits: Double) extends WidthCss, LengthRemAtt
{ @targetName("multiply") override def *(operand: Double = 1): WidthRem = WidthRem(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): WidthRem = WidthRem(numUnits / operand)
  override def lengthVal: RemCss = RemCss(numUnits)
  override def heightAtt(factor: Double): HeightRem = HeightRem(numUnits * factor)
}

/** A CSS width attribute specified in CSS em units. */
case class WidthEm(numUnits: Double) extends WidthCss, LengthEmAtt
{ @targetName("multiply") override def *(operand: Double = 1): WidthEm = WidthEm(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): WidthEm = WidthEm(numUnits / operand)
  override def lengthVal: EmCss = EmCss(numUnits)
  override def heightAtt(factor: Double): HeightPx = HeightPx(numUnits * factor)
}
/** A CSS width attribute specified in CSS vmin units. */
case class WidthVmin(numUnits: Double) extends WidthCss, LengthVminAtt
{ @targetName("multiply") override def *(operand: Double = 1): WidthVmin = WidthVmin(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): WidthVmin = WidthVmin(numUnits / operand)
  override def lengthVal: VminCss = VminCss(numUnits)
  override def heightAtt(factor: Double): HeightVmin = HeightVmin(numUnits * factor)
}
/** A CSS width attribute specified in CSS vmax units. */
case class WidthVmax(numUnits: Double) extends WidthCss, LengthVmaxAtt
{ @targetName("multiply") override def *(operand: Double = 1): WidthVmax = WidthVmax(numUnits * operand)
  @targetName("divide") override def /(operand: Double = 1): WidthVmax = WidthVmax(numUnits / operand)
  override def lengthVal: VmaxCss = VmaxCss(numUnits)
  override def heightAtt(factor: Double): HeightVmax = HeightVmax(numUnits * factor)
}