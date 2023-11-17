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
  { case Level => climate.colour
    case Hilly if landUse == Forest => Chocolate.average(Forest.colour)
    case Hilly => Chocolate.average(climate.colour)
    case _ => Mountains.colour
  }
}

object Land
{
  def apply(elev: Lelev = Level, biome: Climate = Temperate, landUse: LandUse = CivMix): Land = new Land(elev, biome, landUse)
  val land: Land = Land(Level, Temperate, CivMix)
  val hills: Land = Land(Hilly, Temperate, CivMix)
  //lazy val landPairs: ArrPairStr[Land] = ArrPairStr[Land](("land", land), ("hills", hills))

  implicit lazy val showEv: Show3[Lelev, Climate, LandUse, Land] =
    Show3[Lelev, Climate, LandUse, Land]("Land", "elev", _.elev, "climate", _.climate, "use", _.landUse, Some(CivMix), Some(Temperate), Some(Level))

  lazy val unshowFullEv: Unshow3[Lelev, Climate, LandUse, Land] =  Unshow3[Lelev, Climate, LandUse, Land]("Land", "elev", "climate", "landUse",
    (elev, climate, use) => new Land(elev, climate, use), Some(CivMix), Some(Temperate), Some(Level))

  lazy val altUnshowEv: UnshowIdents[Land] = UnshowIdents[Land]("Land", ("land", land), ("hills", hills))//landPairs)

  implicit lazy val unshowEv: Unshow[Land] = unshowFullEv concat(altUnshowEv)
}