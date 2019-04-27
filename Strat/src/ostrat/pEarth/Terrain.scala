/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pEarth
import Colour._

trait Terrain extends WithColour
{
  def str: String  
}

object Terrain
{
  implicit object TerainIsType extends IsType[Terrain]
  {
    override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[Terrain]
    override def asType(obj: AnyRef): Terrain = obj.asInstanceOf[Terrain]      
  }
   
  implicit object TerrainPersist extends PersistSimple[Terrain]("Terrain")
  {
    def show(obj: Terrain): String = obj.str
    def fromExpr(expr: ParseExpr): EMon[Terrain] = ???
  }
  
  val plain: Terrain = Plain()   
  val hills : Terrain = Hills()
  val forr : Terrain = Plain(Forrest)   
  val desert : Terrain = Plain(Desert)
  val jungle : Terrain = Plain(Jungle)
  val taiga : Terrain = Plain(Taiga)
  val ice : Terrain = Plain(IceCap)
  val sice: Terrain = SeaIce
  val sea: Terrain = Ocean
  val mtain: Terrain = Mountains()
}

trait Water extends Terrain
{
  def colour = Blue
}

case object Ocean extends Water { def str = "Ocean" }
case object Lake extends Water { def str = "Lake" }

object TerrainNone extends Terrain
{ override def str = "No terrain"
  override def colour = Gray
}

trait Land extends Terrain
{ def biome: Biome
  override def toString = biome.toString -- str
}

case class Plain(biome: Biome = OpenTerrain) extends Land
{ override def colour = biome.colour
  override def str = "Plain"   
}

case class Hills(biome: Biome = OpenTerrain) extends Terrain
{
  override def str = "Hills"
  
  def colour = biome match
  { case Forrest => Green
    case _ => Chocolate
  }
}

case class Mountains(biome: Biome = OpenTerrain) extends Terrain
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

object SeaIce extends Terrain
{ override def str = "SeaIce" 
  override def colour = White
}

case object Taiga extends Biome
{ override def str = "Taiga"
  override def colour = DarkCyan
}
