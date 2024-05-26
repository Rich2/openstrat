/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
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

trait CssGenVal extends CssVal

/** CSS auto value. */
object CssAuto extends CssGenVal
{ override def str: String = "auto"
}

/** CSS value in px units. */
case class CssPx(numPx: Double) extends CssVal
{ override def str: String = numPx.str + "px"
}

/** CSS value in em units. */
case class CssEm(numEm: Double) extends CssVal
{ override def str: String = numEm.str + "em"
}

/** CSS inline-block value. */
object CssInBlock extends CssVal
{ override def str: String = "inline-block"
}

/** CSS block value. */
object CssBlock extends CssVal
{ override def str: String = "block"
}

object CssCentre extends  CssVal
{ override def str: String = "centre"
}

object CssNone extends  CssVal
{ override def str: String = "none"
}

object CssStart extends  CssVal
{ override def str: String = "start"
}