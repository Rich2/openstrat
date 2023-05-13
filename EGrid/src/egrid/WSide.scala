/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid.phex._, Colour._

trait WSide extends Coloured// with ShowSimple
{
  def nonEmpty: Boolean
}

object WSide
{
  implicit val defaultValueEv: DefaultValue[WSide] = new DefaultValue[WSide]
  {
    override def default: WSide = WSideNone
  }
}

case object WSideNone extends WSide
{ override val colour: Colour = Black
  override def nonEmpty: Boolean = false
}

trait WSideSome extends WSide with HSideSome
{ override def nonEmpty: Boolean = true
}