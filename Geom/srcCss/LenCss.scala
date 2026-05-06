/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pweb

trait LenCss extends CssVal
{ /** The number of px, rem, em etc. */
  def numUnits: Double

  /** The extension [[String]] "px", "rem", "em" etc. */
  def extStr: String

  override def str: String = numUnits.str + extStr
}

/** CSS value in px units. Pixels are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display. For printers and high
 * resolution screens 1px implies multiple device pixels. */
case class PxCss(numUnits: Double) extends LenCss
{ override def extStr: String = "px"
}

/** CSS value in em units. Relative to the font-size of the root element (2em means 2 times the size of the current font). */
case class RemCss(numUnits: Double) extends LenCss
{ override def extStr: String = "rem"
}
/** CSS value in em units. Relative to the font-size of the parent element (2em means 2 times the size of the current font). */
case class EmCss(numUnits: Double) extends LenCss
{ override def extStr: String = "em"
}

/** CSS value in vw units. Relative to 1% of the width of the viewport. */
case class VwCss(numUnits: Double) extends LenCss
{ override def extStr: String = "vw"
}

/** CSS value in vh units. Relative to 1% of the height of the viewport. */
case class VhCss(numUnits: Double) extends LenCss
{ override def extStr: String = "vh"
}