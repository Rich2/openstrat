/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid.phex._, Colour._

trait WSide extends Coloured// with ShowSimple
{
  def nonEmpty: Boolean
}
case object WSideNone extends WSide //with HSideNone
{ override val colour: Colour = Black
  override def nonEmpty: Boolean = false
}

trait WSideSome extends WSide with HSideSome
{ def terr: WSTerr
  override def colour: Colour = terr.colour
  override def nonEmpty: Boolean = true
}

/** A Side between 2 lands. */
case class WSideMid(terr: WSTerr = Sea) extends WSideSome

/** A Side between 2 islands. */
case class WSideBoth(terr: WSTerr = Sea) extends WSideSome

/** A Side on the left tile. */
case class WSideLt(terr: WSTerr = Sea) extends WSideSome

/** A Side on the right tile. */
case class WSideRt(terr: WSTerr = Sea) extends WSideSome

trait WSTerr extends Coloured