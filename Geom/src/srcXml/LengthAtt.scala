/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

/** A length attribute for CSS and SVG. */
trait LengthAtt extends XAttShort
{ type UnitsT <: Double | LenCss

  /** The number of units in this CSS / SVG length. */
  def numUnits: Double

  /** The length value that can be moved between width and height. */
  def lengthVal: UnitsT
  
  /** Multiplies the length of this length attribute by a scalar. */
  @targetName("multiply") def *(operand: Double = 1): LengthAtt

  /** Divides the length of this length attribute by a scalar. */
  @targetName("divide") def /(operand: Double = 1): LengthAtt
}

/** An SVG length attribute. */
trait LengthSvgAtt extends LengthAtt
{  type UnitsT <: Double | Percent  
  override def valueStr: String = numUnits.str
  @targetName("multiply") override def *(operand: Double = 1): LengthCssAtt
  @targetName("divide") override def /(operand: Double = 1): LengthCssAtt
}

/** A CSS length attribute. */
trait LengthCssAtt extends LengthAtt
{  type UnitsT <: LenCss
  override def valueStr: String = numUnits.str + lengthVal.extStr
  @targetName("multiply") override def *(operand: Double = 1): LengthCssAtt
  @targetName("divide") override def /(operand: Double = 1): LengthCssAtt
}

/** A Length XML / SVG attribute in scalar units. */
trait LengthScalarAtt extends LengthAtt
{ type UnitsT = Double
  @targetName("multiply") override def *(operand: Double = 1): LengthScalarAtt
  @targetName("divide") override def /(operand: Double = 1): LengthScalarAtt
}

/** A SVG / CSS Length attribute in percentage units. */
trait LengthCentAtt extends LengthSvgAtt, LengthCssAtt
{ type UnitsT = Percent
  @targetName("multiply") override def *(operand: Double = 1): LengthCentAtt
  @targetName("divide") override def /(operand: Double = 1): LengthCentAtt
}

/** A length attribute in pixel units. */
trait LengthPxAtt extends LengthCssAtt
{ type UnitsT = PxCss
  @targetName("multiply") override def *(operand: Double = 1): LengthPxAtt
  @targetName("divide") override def /(operand: Double = 1): LengthCssAtt
}

/** A length attribute in CSS rem units. */
trait LengthRemAtt extends LengthCssAtt
{ type UnitsT = RemCss
  @targetName("multiply") override def *(operand: Double = 1): LengthRemAtt
  @targetName("divide") override def /(operand: Double = 1): LengthRemAtt
}

/** A length attribute in CSS em units. */
trait LengthEmAtt extends LengthCssAtt
{ type UnitsT = EmCss
  @targetName("multiply") override def *(operand: Double = 1): LengthEmAtt
  @targetName("divide") override def /(operand: Double = 1): LengthEmAtt
}

/** A length attribute in CSS vmin units. */
trait LengthVminAtt extends LengthCssAtt
{ type UnitsT = VminCss
  @targetName("multiply") override def *(operand: Double = 1): LengthVminAtt
  @targetName("divide") override def /(operand: Double = 1): LengthVminAtt
}

/** A length attribute in CSS vmax units. */
trait LengthVmaxAtt extends LengthCssAtt
{ type UnitsT = VmaxCss
  @targetName("multiply") override def *(operand: Double = 1): LengthVmaxAtt
  @targetName("divide") override def /(operand: Double = 1): LengthVmaxAtt
}