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
  @targetName("multiply") def *(operand: Double): LengthAtt

  /** Divides the length of this length attribute by a scalar. */
  @targetName("divide") def /(operand: Double): LengthAtt
}

/** An SVG length attribute. */
trait LengthSvgAtt extends LengthAtt
{  type UnitsT <: Double | Percent  
  override def valueStr: String = numUnits.str
  @targetName("multiply") override def *(operand: Double): LengthCssAtt
  @targetName("divide") override def /(operand: Double): LengthCssAtt
}

/** A CSS length attribute. */
trait LengthCssAtt extends LengthAtt
{  type UnitsT <: LenCss
  override def valueStr: String = numUnits.str + lengthVal.extStr
  @targetName("multiply") override def *(operand: Double): LengthCssAtt
  @targetName("divide") override def /(operand: Double): LengthCssAtt
}

/** A Length XML / SVG attribute in scalar units. */
trait LengthScalarAtt extends LengthAtt
{ type UnitsT = Double
  @targetName("multiply") override def *(operand: Double): LengthScalarAtt
  @targetName("divide") override def /(operand: Double): LengthScalarAtt
}

/** A SVG / CSS Length attribute in percentage units. */
trait LengthCentAtt extends LengthSvgAtt, LengthCssAtt
{ type UnitsT = Percent
  @targetName("multiply") override def *(operand: Double): LengthCentAtt
  @targetName("divide") override def /(operand: Double): LengthCentAtt
}

/** A length attribute in pixel units. */
trait LengthPxAtt extends LengthCssAtt
{ type UnitsT = PxCss
  @targetName("multiply") override def *(operand: Double): LengthPxAtt
  @targetName("divide") override def /(operand: Double): LengthCssAtt
}

/** A length attribute in CSS rem units. */
trait LengthRemAtt extends LengthCssAtt
{ type UnitsT = RemCss
  @targetName("multiply") override def *(operand: Double): LengthRemAtt
  @targetName("divide") override def /(operand: Double): LengthRemAtt
}

/** A length attribute in CSS em units. */
trait LengthEmAtt extends LengthCssAtt
{ type UnitsT = EmCss
  @targetName("multiply") override def *(operand: Double): LengthEmAtt
  @targetName("divide") override def /(operand: Double): LengthEmAtt
}