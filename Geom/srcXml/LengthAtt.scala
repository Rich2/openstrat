/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

/** A length attribute for CSS and SVG. */
trait LengthAtt extends XAttShort
{ /** Multiplies the length of this length attribute by a scalar. */
  @targetName("multiply") def *(operand: Double): LengthAtt

  /** Divides the length of this length attribute by a scalar. */
  @targetName("divide") def /(operand: Double): LengthAtt
}

trait LengthCssAtt extends LengthAtt
{ /** The length value that can be moved between width and height. */
  def lengthVal: LenCss

  /** The number of units in this CSS / SVG length. */
  def numUnits: Double

  override def valueStr: String = numUnits.str + lengthVal.extStr
}

/** A CSS length value that excludes percentage, because percentage can not be used to derive height from width or width from height. */
trait LengthPro extends LengthCssAtt
{ override def lengthVal: LengthRotateable

  @targetName("multiply") override def *(operand: Double): LengthPro
  @targetName("divide") override def /(operand: Double): LengthPro
}