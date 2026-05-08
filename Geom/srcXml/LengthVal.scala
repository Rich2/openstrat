/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb
import annotation.targetName

/** A CSS length value, px, rem, em, % etc. */
sealed trait LenCss extends CssVal, OutElem
{ /** The number of px, rem, em etc. */
  def numUnits: Double

  /** The extension [[String]] "px", "rem", "em" etc. */
  def extStr: String

  /** Multiplies this length value */
  @targetName("multiply") def *(operand: Double): LenCss

  /** Divides this length value by the operand */
  @targetName("divide") def /(operand: Double): LenCss

  override def str: String = numUnits.str + extStr
  override def out: String = numUnits.str + extStr
}

/** A CSS length value that excludes percentage, because percentage can not be used to derive height from width or width from height. */
sealed trait LengthRotateable extends LenCss

/** CSS value in px units. Pixels are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display. For printers and high
 * resolution screens 1px implies multiple device pixels. */
case class PxCss(numUnits: Double) extends LengthRotateable
{ override def extStr: String = "px"
  @targetName("multiply") override def *(operand: Double): PxCss = PxCss(numUnits * operand)
  @targetName("divide") override def /(operand: Double): PxCss = PxCss(numUnits / operand)
}

/** CSS value in em units. Relative to the font-size of the root element (2em means 2 times the size of the current font). */
case class RemCss(numUnits: Double) extends LenCss
{ override def extStr: String = "rem"
  @targetName("multiply") override def *(operand: Double): RemCss = RemCss(numUnits * operand)
  @targetName("divide") override def /(operand: Double): RemCss = RemCss(numUnits / operand)
}

/** CSS value in em units. Relative to the font-size of the parent element (2em means 2 times the size of the current font). */
case class EmCss(numUnits: Double) extends LenCss
{ override def extStr: String = "em"
  @targetName("multiply") override def *(operand: Double): EmCss = EmCss(numUnits * operand)
  @targetName("divide") override def /(operand: Double): EmCss = EmCss(numUnits / operand)
}

/** CSS value in vw units. Relative to 1% of the width of the viewport. */
case class VwCss(numUnits: Double) extends LenCss
{ override def extStr: String = "vw"
  @targetName("multiply") override def *(operand: Double): VwCss = VwCss(numUnits * operand)
  @targetName("divide") override def /(operand: Double): VwCss = VwCss(numUnits / operand)
}

/** CSS value in vh units. Relative to 1% of the height of the viewport. */
case class VhCss(numUnits: Double) extends LenCss
{ override def extStr: String = "vh"
  @targetName("multiply") override def *(operand: Double): VhCss = VhCss(numUnits * operand)
  @targetName("divide") override def /(operand: Double): VhCss = VhCss(numUnits / operand)
}

case class Percent(numUnits: Double) extends LenCss
{ override def extStr: String = "%"
  @targetName("multiply") override def *(operand: Double): Percent = Percent(numUnits * operand)
  @targetName("divide") override def /(operand: Double): Percent = Percent(numUnits / operand)
}