/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

/** A CSS length value, px, rem, em, % etc. */
sealed trait LengthVal extends OutElem
{ /** Multiplies this length value */
  @targetName("multiply") def *(operand: Double): LengthVal

  /** Divides this length value by the operand */
  @targetName("divide") def /(operand: Double): LengthVal
}

/** A CSS length value that excludes percentage, because percentage can not be used to derive height form width or wdth from height. */
sealed trait LengthRotateable extends LengthVal

/** A length measured in pixels. */
case class PixelLen(num: Double) extends LengthRotateable
{ @targetName("multiply") override def *(operand: Double): PixelLen = PixelLen(num * operand)
  @targetName("divide") override def /(operand: Double): PixelLen = PixelLen(num / operand)
  override def out: String = num.str + "px"
}

/** A length measured in pixels. */
case class RemLen(num: Double) extends LengthRotateable
{ @targetName("multiply") override def *(operand: Double): RemLen = RemLen(num * operand)
  @targetName("divide") override def /(operand: Double): RemLen = RemLen(num / operand)
  override def out: String = num.str + "rem"
}

case class Percent(num: Double) extends LengthVal
{ @targetName("multiply") override def *(operand: Double): Percent = Percent(num * operand)
  @targetName("divide") override def /(operand: Double): Percent = Percent(num / operand)
  override def out: String = num.str + "%"
}