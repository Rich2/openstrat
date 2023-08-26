/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Land elevation. */
trait Lelev extends ShowSimple with Coloured
{
  override def typeStr: String = "Lelev"

  /** Factory apply method for land. */
  def apply(biome: Climate = Temperate): Land = Land(this, biome)
}

object Lelev
{
  implicit val showEv: ShowShowT[Lelev] = ShowShowSimpleT[Lelev]("Lelev")
}

case object Level extends Lelev
{ override def str = "Level"
  override def colour: Colour = White
}

object Hilly extends Lelev
{ override def str = "Hilly"
  override def colour: Colour = Brown
}

object Mountains extends Lelev
{ override def str = "Mountain"
  override def colour = Gray
}