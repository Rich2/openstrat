/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import Colour._

trait WTileHelper

/** World Tile, consider changing to ETile. When assigning terrain land and land terrain should take precedence over water. So in the case of world
 * 320km hex 4CG0, or 140, 512 should be a land hex belonging to continental Europe. An island must be a whole hec, except for the straits between it
 * and other land hexs.  */
trait WTile extends WTileHelper with Coloured with ShowSimple
{ override def typeStr: String = "WTile"
  def isLand: Boolean
  def isWater: Boolean = !isLand
}

object WTile
{
  implicit object TerainIsType extends IsType[WTile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[WTile]
    override def asType(obj: AnyRef): WTile = obj.asInstanceOf[WTile]
  }

  /** This is not correct, but put in as temporary measure. */
  implicit val eqImplicit: EqT[WTile] = (a1, a2) => a1 == a2

  implicit val persistImplicit: Persist[WTile] = new PersistSimple[WTile]("Terrain")
  { def strT(obj: WTile): String = obj.str
    def fromExpr(expr: pParse.Expr): EMon[WTile] = ???
  }

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
case object Sea extends Water with  ShowSimple
{ override def str = "Sea"
  override def colour: Colour = DarkBlue
}

case object Lake extends Water with ShowSimple
{ override def str = "Lake"
  override def colour: Colour = Blue
}

object TerrainNone extends WTile
{ override def str = "No terrain"
  override def colour = Gray
  override def isLand: Boolean = false
}

/** Land tile. Describes topology, climate-biome and land use. */
class Land(val elev: Lelev, val biome: Climate, val landUse: LandUse) extends WTile
{
  override def toString: String = "Land" + str.enParenth

  override def str = elev match
  { case Level => biome.toString
    case _ => "Other"
  }

  override def isLand: Boolean = true

  override def colour: Colour = elev match
  { case Level => biome.colour
    case Hilly if landUse == Forest => Chocolate.average(Forest.colour)
    case Hilly => Chocolate.average(biome.colour)
    case _ => Mountains.colour
  }
}

object Land
{
  def apply(elev: Lelev, biome: Climate = Temperate, landUse: LandUse = CivMix): Land = new Land(elev, biome, landUse)
}

/** Land elevation. */
trait Lelev
{
  /** Factory apply method for land. */
  def apply(biome: Climate = Temperate): Land = Land(this, biome)

  def str: String

  def colour: Colour
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