/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** A CSS value for a declaration or media query. */
trait CssValue
{ def str: String
}

object CssValue
{
  def apply(strIn: String): CssValue = new CssValue {
    override def str: String = strIn
  }
}

/** CSS value in px units. */
case class CssPx(numPx: Double) extends CssValue
{ override def str: String = numPx.str + "px"
}

/** CSS value in em units. */
case class CssEm(numEm: Double) extends CssValue
{ override def str: String = numEm.str + "em"
}

/** CSS auto value. */
object CssAuto extends CssValue
{ override def str: String = "auto"
}

/** CSS inline-block value. */
object CssInBlock extends CssValue
{ override def str: String = "inline-block"
}