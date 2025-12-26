/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** A CSS value for a declaration or media query. */
trait CssVal
{ def str: String
}

object CssVal
{ /** Factory apply method for creating CSS value. */
  def apply(strIn: String): CssVal = new CssVal
  { override def str: String = strIn
  }
}

/** A CSS value that can be used with most properties. */
trait CssValGen extends CssVal

/** CSS auto value. */
object AutoCss extends CssValGen
{ override def str: String = "auto"
}

/** CSS initial value. */
object InitialCss extends CssValGen
{ override def str: String = "initial"
}

/** CSS inherit value. */
object InheritCss extends CssValGen
{ override def str: String = "inherit"
}

/** CSS value in px units. Pixels are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display. For printers and high
 * resolution screens 1px implies multiple device pixels. */
case class PxCss(numPx: Double) extends CssVal
{ override def str: String = numPx.str + "px"
}

/** CSS value in em units. Relative to the font-size of the element (2em means 2 times the size of the current font). */
case class EmCss(numEm: Double) extends CssVal
{ override def str: String = numEm.str + "em"
}

/** CSS value in vw units. Relative to 1% of the width of the viewport. */
case class VwCss(numVw: Double) extends CssVal
{ override def str: String = numVw.str + "vw"
}

/** CSS value in vh units. Relative to 1% of the height of the viewport. */
case class VhCss(numVh: Double) extends CssVal
{ override def str: String = numVh.str + "vh"
}

/** Legal CSS value for text-align property */
trait TextAlignCss extends CssVal

/** CSS left value. */
object LeftCss extends TextAlignCss
{ override def str: String = "left"
}

/** CSS right value. */
object RightCss extends TextAlignCss
{ override def str: String = "right"
}

/** CSS center value. */
object CentreCss extends TextAlignCss
{ override def str: String = "center"
}

/** CSS justify value. */
object JustifyCss extends TextAlignCss
{ override def str: String = "justify"
}

/** CSS none value. */
object NoneCss extends ListStyleVal
{ override def str: String = "none"
}

/** CSS start value. */
object StartCss extends CssVal
{ override def str: String = "start"
}

/** CSS flex value. */
object FlexCss extends CssVal
{ override def str: String = "flex"
}

/** CSS column value. */
object ColummnCss extends CssVal
{ override def str: String = "column"
}

/** CSS hidden value. */
object HiddenCss extends CssVal
{ override def str: String = "hidden"
}

/** CSS bold value. */
object BoldVal extends CssVal
{ override def str: String = "bold"
}