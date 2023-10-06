/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Land elevation. */
trait Lelev extends TellSimple with Coloured
{
  override def typeStr: String = "Lelev"

  /** Factory apply method for land. */
  def apply(biome: Climate = Temperate, landUse: LandUse = LandFree): Land = Land(this, biome, landUse)
}

object Lelev
{
  implicit val showEv: ShowTell[Lelev] = ShowTellSimple[Lelev]("Lelev")
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