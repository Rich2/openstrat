/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid.phex._, Colour._

trait WSide extends Coloured// with ShowSimple
{
  def nonEmpty: Boolean
}

object WSide
{
  implicit val noneImplicit: NoneTC[WSide] = new NoneTC[WSide]
  {
    override def noneValue: WSide = WSideNone
  }
}

case object WSideNone extends WSide //with HSideNone
{ override val colour: Colour = Black
  override def nonEmpty: Boolean = false
}

/*trait WSideSome extends WSide with HSideSome
{ def terr: WSTerr
  override def colour: Colour = terr.colour
  override def nonEmpty: Boolean = true
}*/

/** A Side between 2 lands. */
//case class WSideMid(terr: WSTerr = Sea) extends WSideSome with HSideMid

/** A Side between 2 islands. */
//case class WSideBoth(terr: WSTerr = Sea) extends WSideSome

/** A Side on the left tile. The main purpose of this class is that water hexs are lined off from the side terrain. */
//case class WSideMid(terr: WSTerr = Sea) extends WSideSome

/** A Side on the right tile. The main purpose of this class is that water hexs are lined off from the side terrain. */
//case class WSideMid(terr: WSideSome = Sea) extends WSideSome

trait WSideSome extends WSide with HSideSome// Coloured
{
  override def nonEmpty: Boolean = true
}