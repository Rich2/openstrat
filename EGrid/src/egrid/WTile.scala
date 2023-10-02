/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

trait WTileHelper

/** World Tile, consider changing to ETile. When assigning terrain land and land terrain should take precedence over water. So in the case of world
 * 320km hex 4CG0, or 140, 512 should be a land hex belonging to continental Europe. An island must be a whole hec, except for the straits between it
 * and other land hexs.  */
trait WTile extends WTileHelper with Coloured with Tell//Simple //with Descrip
{ override def typeStr: String = "WTile"
  def isLand: Boolean
  def isWater: Boolean = !isLand
}

/** Companion object for WTile, contains short factory methods. */
object WTile
{
  implicit object TerainIsType extends IsType[WTile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[WTile]
    override def asType(obj: AnyRef): WTile = obj.asInstanceOf[WTile]
  }

  /** This is not correct, but put in as temporary measure. */
  implicit val eqImplicit: EqT[WTile] = (a1, a2) => a1 == a2

  implicit val showTEv: Showeding[WTile] = Showeding[WTile]("Terrain")

  val plain: Land = Land(Level, Temperate, CivMix)
  val hills: Land = Land(Hilly, Temperate, CivMix)
  val forest: Land = Land(Level, Temperate, Forest)
  val hillyForest: Land = Land(Hilly, Temperate, Forest)
  val desert: Land = Land(Level, Desert, LandFree)
  val hillyDesert: Land = Land(Hilly, Desert, LandFree)
  val jungle: Land = Land(Level, Tropical, Forest)
  val hillyJungle: Land = Land(Hilly, Tropical, Forest)
  val taiga: Land = Land(Level, Taiga, Forest)
  val hillyTaiga: Land = Land(Hilly, Taiga, LandFree)
  val tundra: Land = Land(Level, Tundra, LandFree)
  val hillyTundra: Land = Land(Hilly, Tundra, LandFree)
  val savannah: Land = Land(Level, Savannah, CivMix)
  val hillySavannah: Land = Land(Hilly, Savannah, CivMix)
  val sahel: Land = Land(Level, Sahel, CivMix)
  val hillySahel: Land = Land(Hilly, Sahel, CivMix)
  val ice: Land = Land(Level, IceCap, LandFree)
  val sice: WTile = SeaIce
  val sea: Water = Sea
  val lake: Water = Lake
  val mtain: Land = Land(Mountains, Taiga, Forest)
}

/** A common trait for Ocean and Lake. */
trait Water extends WTile with WSideSome
{ override def isLand: Boolean = false
}

/** Sea. This is an object as currently has no other variables such as depth, current or climate. */
case object Sea extends Water with  ShowSimpled
{ override def str = "Sea"
  override def colour: Colour = DarkBlue
  //override def shortDescrip: String = "Sea"
}

case object Lake extends Water with ShowSimpled
{ override def str = "Lake"
  override def colour: Colour = Blue
  //override def shortDescrip: String = "Lake"
}

object TerrainNone extends WTile with ShowSimpled
{ override def str = "NoTerrain"
  override def colour = Gray
  override def isLand: Boolean = false
  //override def shortDescrip: String = "No terrain"
}

/** Land tile. Describes topology, climate-biome and land use. */
class Land(val elev: Lelev, val climate: Climate, val landUse: LandUse) extends WTile with Tell3[Lelev, Climate, LandUse]
{ override def name1: String = "elev"
  override def name2: String = "climate"
  override def name3: String = "landUse"
  override def show1: Lelev = elev
  override def show2: Climate = climate
  override def show3: LandUse = landUse
  override def persist1: Show[Lelev] = Lelev.showEv
  override def persist2: Show[Climate] = Climate.showEv
  override def persist3: Show[LandUse] = LandUse.showEv
  override def syntaxDepth: Int = 2

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
  def apply(elev: Lelev, biome: Climate = Temperate, landUse: LandUse = CivMix): Land = new Land(elev, biome, landUse)
}



/** Winter sea ice. */
object WSeaIce extends Water with ShowSimpled
{ override def str = "WSeaIce"
  override def colour = LightSkyBlue.average(White).average(White)
  override def isLand: Boolean = false
  //override def shortDescrip: String = "WTile"
}
