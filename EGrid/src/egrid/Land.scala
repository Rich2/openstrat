/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
  override def opt1: Option[Lelev] = Some(Level)
  override def opt2: Option[Climate] = Some(Temperate)
  override def opt3: Option[LandUse] = Some(CivMix)
  override def tellDepth: Int = 2

  override def isLand: Boolean = true

  override def colour: Colour = elev match
  { case WetLand if climate == Tundra => WetLand.colour.average(Tundra.colour)
    case WetLand if climate == Taiga => WetLand.colour.average(Taiga.colour)
    case WetLand => WetLand.colour
    case Level => climate.colour
    case Hilly if landUse == Forest => Chocolate.average(Forest.colour)
    case Hilly => Chocolate.average(climate.colour)
    case Level if climate == Tropical && LandUse == Forest => DarkGreen
    case Level if LandUse == Forest => Forest.colour
    case Level => climate.colour
    case _ => Mountains.colour
  }
}

/** Companion object for [[Land]], contains factory apply method and type class instance for [[Show]], [[Unshow]] and [[EqT]], */
object Land
{ /** Factory apply method for [[Land]] objects. */
  def apply(elev: Lelev = Level, biome: Climate = Temperate, landUse: LandUse = CivMix): Land = new Land(elev, biome, landUse)

  /** Implicit [[Show]] and [[Unshow]] type class instances / evidence for [[Land]]. */
  implicit lazy val persistEv: Persist3Both[Lelev, Climate, LandUse, Land] =
    Persist3Both.shorts[Lelev, Climate, LandUse, Land]("Land", "elev", _.elev, "climate", _.climate, "use", _.landUse, WTiles.landWords, apply,
    Some(CivMix),  Some(Temperate), Some(Level))

  /** Implicit [[EqT]] type class instance / evidence for [[Land]]. */
  implicit val eqEv: EqT[Land] = (a1, a2) => a1.elev == a2.elev && a1.climate == a1.climate && a1.landUse == a2.landUse
}