/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

trait WSide extends Coloured// with ShowSimple

/** A Side between 2 lands. */
trait WSideLands extends WSide

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

object SCSea extends WSideLands with SideSea
object SLSea extends WSideLeft with SideSea
object SRSea extends WSideRight with SideSea
object SISea extends WSideIslands with SideSea
object SCLake extends WSideLands with SideLake