/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** A CSS value for a declaration or media query. */
trait CssVal
{ def str: String
}

object CssVal
{
  def apply(strIn: String): CssVal = new CssVal
  { override def str: String = strIn
  }
}

/** A value that can be used with most properties. */
trait CssGenVal extends CssVal

/** CSS auto value. */
object CssAuto extends CssGenVal
{ override def str: String = "auto"
}

/** CSS value in px units. Pixels are relative to the viewing device. For low-dpi devices, 1px is one device pixel (dot) of the display. For printers
 *  and high resolution screens 1px implies multiple device pixels. */
case class CssPx(numPx: Double) extends CssVal
{ override def str: String = numPx.str + "px"
}

/** CSS value in em units. Relative to the font-size of the element (2em means 2 times the size of the current font). */
case class CssEm(numEm: Double) extends CssVal
{ override def str: String = numEm.str + "em"
}

/** CSS value in vh units. Relative to 1% of the height of the viewport. */
case class CssVh(numEm: Double) extends CssVal
{ override def str: String = numEm.str + "vh"
}

/** CSS inline-block value. */
object CssInBlock extends CssVal
{ override def str: String = "inline-block"
}

/** CSS block value. */
object CssBlock extends CssVal
{ override def str: String = "block"
}

/** CSS center value. */
object CssCentre extends  CssVal
{ override def str: String = "center"
}

/** CSS none value. */
object CssNone extends  CssVal
{ override def str: String = "none"
}
/** CSS start value. */
object CssStart extends  CssVal
{ override def str: String = "start"
}
/** CSS flex value. */
object CssFlex extends  CssVal
{ override def str: String = "flex"
}