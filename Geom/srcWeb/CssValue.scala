/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait CssValue
{
  def str: String
}

object CssValue{
  def apply(strIn: String): CssValue = new CssValue {
    override def str: String = strIn
  }
}
case class CssPx(numPx: Double) extends CssValue
{
  override def str: String = numPx.str + "px"
}

case class CssEm(numEm: Double) extends CssValue
{
  override def str: String = numEm.str + "em"
}

object CssAuto extends CssValue
{ override def str: String = "auto"
}