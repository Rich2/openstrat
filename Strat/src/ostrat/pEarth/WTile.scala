/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import pGrid._, Colour._

trait WTile extends WithColour
{
  def str: String
}

object WTile
{
  implicit object TerainIsType extends IsType[WTile]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[WTile]
    override def asType(obj: AnyRef): WTile = obj.asInstanceOf[WTile]
  }

  implicit object TerrainPersist extends PersistSimple[WTile]("Terrain")
  {
    def show(obj: WTile): String = obj.str
    def fromExpr(expr: ParseExpr): EMon[WTile] = ???
  }

  val plain: WTile = Inland(Plains)
  val hills : WTile = Inland(Hilly)
  val forr : WTile = Inland(Plains, Forrest)
  val desert : WTile = Inland(Plains, Desert)
  val jungle : WTile = Inland(Plains, Jungle)
  val taiga : WTile = Inland(Plains, Taiga)
  val ice : WTile = Inland(Plains, IceCap)
  val sice: WTile = SeaIce
  val sea: WTile = Ocean
  val mtain: WTile = Inland(Mountains)
}

trait Water extends WTile
{
  def colour = Blue
}

case object Ocean extends Water { def str = "Ocean" }
case object Lake extends Water { def str = "Lake" }


object TerrainNone extends WTile
{ override def str = "No terrain"
  override def colour = Gray
}

trait Land extends WTile
{ def terr: Terrain
  def biome: Biome
  override def toString = biome.toString -- str

  def colour: Colour = terr match
  { case Plains => biome.colour
    case Hilly if biome == Forrest => Olive
    case Hilly => Chocolate
    case Mountains => Gray
  }
}

case class Inland(terr: Terrain, biome: Biome = OpenTerrain) extends Land
{
  def str = terr match
  { case Plains => biome.str
    case _ => terr.str
  }
}

trait Terrain
{ def str: String
  def colour: Colour
}

case object Plains extends Terrain
{ override def str = "Plains"
  override def colour: Colour = White
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
}

case object OpenTerrain extends Biome
{ def colour: Colour = LightGreen
  def str = "open ground"
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

//trait CoastLike extends WTile { def vertOffs: HVertOffs}

class Coast(val terr: Terrain, val biome: Biome, val vertOffs: HVertOffs, val sideUR: Option[Unit] = None, val sideRt: Option[Unit] = None,
  val sideDR: Option[Unit]) extends
  Land with HSides[Unit]
{ def str = "Coast"
}
object Coast
{
  def apply(terr: Terrain = Plains, biome: Biome = OpenTerrain, up: TVert = HVertReg, upRt: BVert = HVertReg, dnRt: TVert = HVertReg,
    down: BVert = HVertReg, dnLt: TVert = HVertReg, upLt: BVert = HVertReg, sideUR: Option[Unit] = None, sideRt: Option[Unit] = None,
    sideDR: Option[Unit] = None): Coast = new Coast(terr, biome, HVertOffs(up, upRt, dnRt, down, dnLt, upLt), sideUR, sideRt, sideDR)
}

class Coastal(val vertOffs: HVertOffs) extends Water with HVertOffsTr { def str = "Ocean"}
object Coastal
{
  def apply(up: TVert = HVertReg, upRt: BVert = HVertReg, dnRt: TVert = HVertReg, down: BVert = HVertReg, dnLt: TVert = HVertReg,
    upLt: BVert = HVertReg): Coastal = new Coastal(HVertOffs(up, upRt, dnRt, down, dnLt, upLt))
}

//class StraitsDnLt(ltVal: Int, rtVal: Int) extends HVDnLt2(ltVal, rtVal)
//object StraitsDnLt{ def apply(ltVal: Int, rtVal: Int) = new StraitsDnLt(ltVal, rtVal) }