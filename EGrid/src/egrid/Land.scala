/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

/** Land tile. Describes topology, climate-biome and land use. */
case class Land(elev: Lelev, climate: Climate, landUse: LandUse) extends WTile with Tell3[Lelev, Climate, LandUse]
{ override def typeStr: String = "Land"
  override def name1: String = "elev"
  override def name2: String = "climate"
  override def name3: String = "landUse"
  override def tell1: Lelev = elev
  override def tell2: Climate = climate
  override def tell3: LandUse = landUse
  override def show1: Show[Lelev] = Lelev.showEv
  override def show2: Show[Climate] = Climate.showEv
  override def show3: Show[LandUse] = LandUse.showEv
  override def opt1: Option[Lelev] = Some(Plain)
  override def opt2: Option[Climate] = Some(Temperate)
  override def opt3: Option[LandUse] = Some(CivMix)
  override def tellDepth: Int = 2

  override def isLand: Boolean = true

  override def colour: Colour = elev match
  { case Mountains => Mountains.colour
    case MountLakes => MountLakes.colour
    case Hilly if climate == Boreal => Boreal.colour.average(Hilly.colour)
    case Hilly if landUse == Forest => Chocolate.average(Forest.colour)
    case Hilly => Chocolate.average(climate.colour)
    case HillyLakes if landUse == Forest => Chocolate.average(Forest.colour)
    case HillyLakes => HillyLakes.colour
    case PlainLakes if climate == Tundra => PlainLakes.colour.average(Tundra.colour)
    case PlainLakes if climate == Boreal => PlainLakes.colour.average(Boreal.colour)
    case PlainLakes => PlainLakes.colour
    case Plain if climate == Tropical && landUse == Forest => DarkGreen
    case Plain if climate == Boreal => Boreal.colour
    case Plain if landUse == Forest => Forest.colour
    case _ => climate.colour

  }
}

/** Companion object for [[Land]], contains factory apply method and type class instance for [[Show]], [[Unshow]] and [[EqT]], */
object Land
{ /** Factory apply method for [[Land]] objects. */
  def apply(elev: Lelev = Plain, biome: Climate = Temperate, landUse: LandUse = CivMix): Land = new Land(elev, biome, landUse)

  /** Implicit [[Show]] and [[Unshow]] type class instances / evidence for [[Land]]. */
  implicit lazy val persistEv: Persist3Both[Lelev, Climate, LandUse, Land] =
    Persist3Both.shorts[Lelev, Climate, LandUse, Land]("Land", "elev", _.elev, "climate", _.climate, "use", _.landUse, WTiles.landWords, apply,
    Some(CivMix),  Some(Temperate), Some(Plain))

  /** Implicit [[EqT]] type class instance / evidence for [[Land]]. */
  implicit val eqEv: EqT[Land] = (a1, a2) => a1.elev == a2.elev && a1.climate == a1.climate && a1.landUse == a2.landUse
}