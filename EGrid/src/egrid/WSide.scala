/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid.phex._, Colour._

trait WSide extends Coloured// with ShowSimple

case object WSideNone extends WSide //with HSideNone
{ override val colour: Colour = Black
}

trait WSideSome extends WSide with HSideSome
{ def terr: WSTerr
  override def colour: Colour = terr.colour
}

/** A Side between 2 lands. */
case class WSideMid(terr: WSTerr = Sea) extends WSideSome

/** A Side between 2 islands. */
trait WSideIslands extends WSide

/** A Side on the left tile. */
trait WSideLeft extends WSide

/** A Side on the right tile. */
trait WSideRight extends WSide

trait SideSea extends WSide
{ override def colour = DarkBlue
}

trait SideLake extends WSide
{ override def colour = Blue
}

object SLSea extends WSideLeft with SideSea
object SRSea extends WSideRight with SideSea
object SISea extends WSideIslands with SideSea

trait WSTerr extends Coloured