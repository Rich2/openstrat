/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import Colour._

/** Not sure about this trait at all but will leave for time being. */
trait WSide extends Coloured with ShowSimple

/** World Tile, consider changing to ETile. When assigning terrain land and land terrain should take precedence over water. So in the case of world
 * 320km hex 4CG0, or 140, 512 should be a land hex belonging to continental Europe. An island must be a whole hec, except for the straits between it
 * and other land hexs.  */
trait WTile extends Coloured with ShowSimple
{ override def typeStr: String = "WTile"
  def hasLand: Boolean
  def hasWater: Boolean
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
  {
    def strT(obj: WTile): String = obj.str
    def fromExpr(expr: pParse.Expr): EMon[WTile] = ???
  }

  val plain: WTile = Land(Plains)
  val hills: WTile = Land(Hilly)
  val forest: WTile = Land(Plains, Forest)
  val forestHills = Land(Hilly, Forest)
  val desert: WTile = Land(Plains, Desert)
  val desertHills: WTile = Land(Hilly, Desert)
  val jungle: WTile = Land(Plains, Jungle)
  val jungleHills: WTile = Land(Plains, Jungle)
  val taiga: WTile = Land(Plains, Taiga)
  val taigaHills = Land(Hilly, Taiga)
  val tundra: WTile = Land(Plains, Tundra)
  val tundraHills = Land(Hilly, Tundra)
  val ice: WTile = Land(Plains, IceCap)
  val sice: WTile = SeaIce
  val sea: WTile = Sea
  val lake: WTile = Lake
  val mtain: WTile = Land(Mountains)
}

/** A common trait for water and Island hexs. */
trait WaterHas extends WTile
{ override def hasWater: Boolean = true
}

/** A common trait for Ocean and Lake. */
trait Water extends WaterHas
{ override def hasLand: Boolean = false

}

/** A common trait for sea and sea island tiles. */
trait SeaHas extends WaterHas
{ override def colour = DarkBlue
}

/** Sea. This is an object as currently has no other variables such as depth, current or climate. */
case object Sea extends Water with SeaHas with WSide
{ override def str = "Ocean"
}

/** A common trait for sea and sea island tiles. */
trait LakeHas extends WaterHas
{ override def colour = Blue
}

case object Lake extends Water with LakeHas with WSide
{ override def str = "Lake"
}

object TerrainNone extends WTile
{ override def str = "No terrain"
  override def colour = Gray
  override def hasLand: Boolean = false
  override def hasWater: Boolean = false
}

/** Common trait for land and Islands. */
trait LandHas extends WTile
{ def terr: Terrain
  def biome: Biome

  def landColour: Colour = terr match {
    case Plains => biome.colour

    case Hilly => biome match {
      case Tundra => Chocolate.average(Tundra.colour)
      case Taiga => Chocolate.average(Taiga.colour)
      case Forest => Chocolate.average(Forest.colour)
      case Desert => Chocolate.average(Desert.colour)
      case _ => Chocolate
    }
    case Mountains => Gray
  }
}

class Land(val terr: Terrain, val biome: Biome) extends LandHas
{ override def toString: String = "Land" + str.enParenth

  override def str = terr match
  { case Plains => biome.toString
    case t => t.str
  }

  override def colour: Colour = landColour
  override def hasLand: Boolean = true
  override def hasWater: Boolean = false
}

object Land
{ /** Factory apply method for land. */
  def apply(terr: Terrain = Plains, biome: Biome = OpenTerrain): Land = new Land(terr, biome)
}

trait Island extends WaterHas with LandHas
{ override def hasLand: Boolean = true
  override def hasWater: Boolean = true
}

object Island
{
  def unapply(inp: Any): Option[(Terrain, Biome)] = inp match
  { case isl: Island => Some(isl.terr, isl.biome)
    case _ => None
  }
}

case class SeaIsland(terr: Terrain, biome: Biome) extends Island with SeaHas
{ override def str: String = "SeaIsland"
}

class LakeIsland(val terr: Terrain, val biome: Biome) extends Island with LakeHas
{ override def str: String = "SeaIsland"
}

trait Terrain
{ def str: String
  def colour: Colour
}

case object Plains extends Terrain
{ override def str = "Plains"
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

trait Biome
{ def colour: Colour
  def str: String
  override def toString: String = str
}

case object OpenTerrain extends Biome
{ def colour: Colour = LightGreen
  def str = "Open Ground"
}

case object Forest extends Biome
{ override def str = "Forest"
  override def colour = ForestGreen
}

case object Desert extends Biome
{ override def str = "Desert"
  override def colour = LemonChiffon
}

object Jungle extends Biome
{ override def str = "Jungle"
  override def colour = DarkGreen
}

object IceCap extends Biome
{ override def str = "IceCap"
  override def colour = White
}

object SeaIce extends WTile
{ override def str = "SeaIce"
  override def colour = White
  override def hasLand: Boolean = false
  override def hasWater: Boolean = false
}

case object Taiga extends Biome
{ override def str = "Taiga"
  override def colour = DarkCyan
}

case object Tundra extends Biome
{ override def str = "Tundra"
  override def colour = LightCyan
}

/*class Coastal(val vertOffs: HVertOffs) extends Water with HVertOffsTr { def str = "Ocean"}
object Coastal
{
  def apply(up: TVert = HVertReg, upRt: BVert = HVertReg, dnRt: TVert = HVertReg, down: BVert = HVertReg, dnLt: TVert = HVertReg,
    upLt: BVert = HVertReg): Coastal = new Coastal(HVertOffs(up, upRt, dnRt, down, dnLt, upLt))
}*/

//class StraitsDnLt(ltVal: Int, rtVal: Int) extends HVDnLt2(ltVal, rtVal)
//object StraitsDnLt{ def apply(ltVal: Int, rtVal: Int) = new StraitsDnLt(ltVal, rtVal) }