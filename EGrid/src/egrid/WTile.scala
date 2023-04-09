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

  val plain: WTile = Level()
  val hills: WTile = Hilly()
  val forest: WTile = Level(Forest)
  val forestHills = Hilly(Forest)
  val desert: WTile = Level(Desert)
  val desertHills: WTile = Hilly(Desert)
  val jungle: WTile = Level(Jungle)
  val jungleHills: WTile = Level(Jungle)
  val taiga: WTile = Level(Taiga)
  val taigaHills = Hilly(Taiga)
  val tundra: WTile = Level(Tundra)
  val tundraHills = Hilly(Tundra)
  val ice: WTile = Level(IceCap)
  val sice: WTile = SeaIce
  val sea: WTile = Sea
  val lake: WTile = Lake
  val mtain: WTile = Mountains()
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

trait Land extends WTile
{
  def biome: Biome
  override def toString: String = "Land" + str.enParenth

  override def str = this match
  { case _: Level => biome.toString
    case _ => "Other"
  }

  override def isLand: Boolean = true
}

object Land
{ /** Factory apply method for land. */
  def apply(biome: Biome = OpenTerrain): Land = Level(biome)
}

case class Level(biome: Biome = OpenTerrain) extends Land
{ override def str = "Level"
  override def colour: Colour = biome.colour
}

case class Hilly(biome: Biome = OpenTerrain) extends Land
{ override def str = "Hilly"
  override def colour = biome match {
    case Tundra => Chocolate.average(Tundra.colour)
    case Taiga => Chocolate.average(Taiga.colour)
    case Forest => Chocolate.average(Forest.colour)
    case Desert => Chocolate.average(Desert.colour)
    case IceCap => Chocolate.average(IceCap.colour).average(IceCap.colour)
    case _ => Chocolate
  }
}

case class Mountains(biome: Biome = OpenTerrain) extends Land
{ override def str = "Mountain"
  override def colour = Gray
}