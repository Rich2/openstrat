/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import prid.phex._, Colour._

/** World Tile, consider changing to ETile. When assigning terrain land and land terrain should take precedence over water. So in the case of world
 * 320km hex 4CG0, or 140, 512 should be a land hex belonging to continental Europe. An island must be a whole hec, except for the straits between it
 * and other land hexs.  */
trait WTile extends Coloured with ShowSimple
{ override def typeStr: String = "WTile"
  def hasLand: Boolean
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

  val plain: WTile = Land(Level)
  val hills: WTile = Land(Hilly)
  val forest: WTile = Land(Level, Forest)
  val forestHills = Land(Hilly, Forest)
  val desert: WTile = Land(Level, Desert)
  val desertHills: WTile = Land(Hilly, Desert)
  val jungle: WTile = Land(Level, Jungle)
  val jungleHills: WTile = Land(Level, Jungle)
  val taiga: WTile = Land(Level, Taiga)
  val taigaHills = Land(Hilly, Taiga)
  val tundra: WTile = Land(Level, Tundra)
  val tundraHills = Land(Hilly, Tundra)
  val ice: WTile = Land(Level, IceCap)
  val sice: WTile = SeaIce
  val sea: WTile = Sea
  val lake: WTile = Lake
  val mtain: WTile = Land(Mountains)
}

/** A common trait for Ocean and Lake. */
trait Water extends WTile with WSideSome
{ override def hasLand: Boolean = false
}

/** Sea. This is an object as currently has no other variables such as depth, current or climate. */
case object Sea extends Water with  ShowSimple//WSide
{ override def str = "Ocean"
  override def colour: Colour = DarkBlue
}

case object Lake extends Water with ShowSimple// WSide
{ override def str = "Lake"
  override def colour: Colour = Blue
}

object TerrainNone extends WTile
{ override def str = "No terrain"
  override def colour = Gray
  override def hasLand: Boolean = false
}

/** Common trait for land and Islands. */
trait LandLike extends WTile
{ def terr: Terrain
  def biome: Biome

  def colour: Colour = terr match
  { case Level => biome.colour

    case Hilly => biome match
    { case Tundra => Chocolate.average(Tundra.colour)
      case Taiga => Chocolate.average(Taiga.colour)
      case Forest => Chocolate.average(Forest.colour)
      case Desert => Chocolate.average(Desert.colour)
      case IceCap => Chocolate.average(IceCap.colour).average(IceCap.colour)
      case _ => Chocolate
    }
    case Mountains => Gray
  }
}

class Land(val terr: Terrain, val biome: Biome) extends LandLike
{ override def toString: String = "Land" + str.enParenth

  override def str = terr match
  { case Level => biome.toString
    case t => t.str
  }

  override def hasLand: Boolean = true
}

object Land
{ /** Factory apply method for land. */
  def apply(terr: Terrain = Level, biome: Biome = OpenTerrain): Land = new Land(terr, biome)
}

trait Coastal extends LandLike with HIndentN
{ override def hasLand: Boolean = true
  def sideTerrs: Water
  override def str: String = "Coastal"
}

case class Island(terr: Terrain = Level, biome: Biome = OpenTerrain, sideTerrs: Water = Sea) extends Coastal with HIndent6

object Island
{
  def unapply(inp: Any): Option[(Terrain, Biome)] = inp match
  { case isl: Island => Some(isl.terr, isl.biome)
    case _ => None
  }
}

/** Headland surrounded by [[Water]] on 1 to 4 sides. */
case class Headland(numIndentedVerts: Int, indentStartIndex: Int, terr: Terrain = Level, biome: Biome = OpenTerrain, sideTerrs: Water = Sea) extends Coastal with
  HIndent5Minus

trait Terrain
{ def str: String
  def colour: Colour
}

case object Level extends Terrain
{ override def str = "Level"
  override def colour: Colour = MintCream
}

case object Hilly extends Terrain
{ override def str = "Hilly"
  override def colour = Chocolate
}

case object Mountains extends Terrain
{ override def str = "Mountain"
  override def colour = Gray
}