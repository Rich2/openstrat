/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pEarth
import Colour._

/** World Tile, consider changing to ETile. */
trait WTile extends Coloured
{ def str: String
}

object WTile
{
  implicit object TerainIsType extends IsType[WTile]
  { override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[WTile]
    override def asType(obj: AnyRef): WTile = obj.asInstanceOf[WTile]
  }

  /** This is not correct, but put in as temporary measure. */
  implicit val eqImplicit: EqT[WTile] = (a1, a2) => a1 == a2

  implicit val persistImplicit: PersistPrec[WTile] = new PersistSimplePrecision[WTile]("Terrain")
  {
    def strT(obj: WTile): String = obj.str
    def fromExpr(expr: pParse.Expr): EMon[WTile] = ???
  }

  val plain: WTile = Land(Plains)
  val hills : WTile = Land(Hilly)
  val forr : WTile = Land(Plains, Forrest)
  val desert : WTile = Land(Plains, Desert)
  val jungle : WTile = Land(Plains, Jungle)
  val taiga : WTile = Land(Plains, Taiga)
  val ice : WTile = Land(Plains, IceCap)
  val sice: WTile = SeaIce
  val sea: WTile = Ocean
  val lake: WTile = Lake
  val mtain: WTile = Land(Mountains)
}

/** Currently a common trait for Ocean and Lake. */
trait Water extends WTile

case object Ocean extends Water
{ def str = "Ocean"
  def colour = DarkBlue
}

case object Lake extends Water
{ def str = "Lake"
  def colour = Blue
}

object TerrainNone extends WTile
{ override def str = "No terrain"
  override def colour = Gray
}

class Land(val terr: Terrain, val biome: Biome) extends WTile
{ override def toString: String = "Land" + str.enParenth

  override def str = terr match
  { case Plains => biome.toString //-- str
    case t => t.str
  }

  def colour: Colour = terr match
  { case Plains => biome.colour
    case Hilly if biome == Forrest => Olive
    case Hilly => Chocolate
    case Mountains => Gray
  }
}

object Land
{
  def apply(terr: Terrain = Plains, biome: Biome = OpenTerrain/*, up: TVert = HVertReg, upRt: BVert = HVertReg, dnRt: TVert = HVertReg,
            down: BVert = HVertReg, dnLt: TVert = HVertReg, upLt: BVert = HVertReg, sideUR: Option[Unit] = None, sideRt: Option[Unit] = None,
            sideDR: Option[Unit] = None*/): Land = new Land(terr, biome/*, HVertOffs(up, upRt, dnRt, down, dnLt, upLt), sideUR, sideRt, sideDR*/)
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

case object Forrest extends Biome
{ override def str = "Forrest"
  override def colour = Green
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
}

case object Taiga extends Biome
{ override def str = "Taiga"
  override def colour = DarkCyan
}

/*class Coastal(val vertOffs: HVertOffs) extends Water with HVertOffsTr { def str = "Ocean"}
object Coastal
{
  def apply(up: TVert = HVertReg, upRt: BVert = HVertReg, dnRt: TVert = HVertReg, down: BVert = HVertReg, dnLt: TVert = HVertReg,
    upLt: BVert = HVertReg): Coastal = new Coastal(HVertOffs(up, upRt, dnRt, down, dnLt, upLt))
}*/

//class StraitsDnLt(ltVal: Int, rtVal: Int) extends HVDnLt2(ltVal, rtVal)
//object StraitsDnLt{ def apply(ltVal: Int, rtVal: Int) = new StraitsDnLt(ltVal, rtVal) }