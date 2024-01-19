/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Land elevation. */
trait Lelev extends TellSimple with Coloured
{ override def typeStr: String = "Lelev"

  /** Apply method for land from this [[Lelev]]. */
  def apply(biome: Climate = Temperate, landUse: LandUse = LandFree): Land = Land(this, biome, landUse)
}

object Lelev
{ /** Implicit [[Show]] type class instance / evidence for [[Lelev]]. */
  implicit lazy val showEv: ShowTell[Lelev] = ShowTellSimple[Lelev]("Lelev")

  /** Implicit [[Unshow]] type class instance / evidence for [[Lelev]]. */
  implicit lazy val unshowEV: UnshowSingletons[Lelev] = UnshowSingletons[Lelev]("Lelev", Level, Hilly, Mountains)

  given CanEqual[Lelev, Lelev] = CanEqual.derived
}

case object WetLand extends Lelev
{ override def str = "WetLand"
  override def colour: Colour = Turquoise
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
{ override def str = "Mountains"
  override def colour = Gray
}