/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Land elevation. */
trait Lelev extends TellSimple with Coloured
{ override def typeStr: String = "Lelev"

  /** Apply method for land from this [[Lelev]]. */
  def apply(biome: Climate = Oceanic, landUse: LandUse = LandFree): Land = Land(this, biome, landUse)
}

object Lelev
{ /** Implicit [[Show]] type class instance / evidence for [[Lelev]]. */
  implicit lazy val showEv: ShowTell[Lelev] = ShowTellSimple[Lelev]("Lelev")

  /** Implicit [[Unshow]] type class instance / evidence for [[Lelev]]. */
  implicit lazy val unshowEV: UnshowSingletons[Lelev] = UnshowSingletons[Lelev]("Lelev", Plain, Hilly, Mountains)

  given CanEqual[Lelev, Lelev] = CanEqual.derived
}

trait PlainLike extends Lelev

/** Plain / flatland with lakes. */
case object PlainLakes extends PlainLike
{ override def str = "LandLakes"
  override def colour: Colour = Turquoise
}

/** Plain or flatland. */
case object Plain extends PlainLike
{ override def str = "Plain"
  override def colour: Colour = White
}

trait HillyLike extends Lelev
{ override def colour: Colour = Brown
}

/** Hilly terrain. */
case object Hilly extends HillyLike
{ override def str = "Hilly"
}

trait MountainsLike extends Lelev

/** Mountainous terrain. */
case object Mountains extends MountainsLike
{ override def str = "Mountains"
  override def colour = Gray
}

/** Hilly ground with lakes / swamps. */
case object HillyLakes extends HillyLike
{ override def str = "HillyLakes"
}

/** Mountainous terrain with lakes. */
case object MountLakes extends MountainsLike
{ override def str = "MountLakes"
  override def colour = Mountains.colour//.average(Lake.colour)
}