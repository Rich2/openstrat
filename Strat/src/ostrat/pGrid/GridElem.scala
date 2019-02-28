/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** In a simple Grid the only GridElems are Tiles. In a complex grid, there are values for tiles and tile sides. For programmatic purposes it is
 *  vital that GridElem contains its grid coordinates, although at a logical level a tile does not need to know its position within the tile grid.
 *  It needs this information so it can communicate with its grid. */
trait GridElem
{
   def x: Int
   def y: Int
   def cood: Cood = Cood(x, y)
}

trait Tile extends GridElem
{ def canEqual(a: Any) = a.isInstanceOf[Tile]
  override def hashCode: Int = (x, y).##
  override def equals(that: Any): Boolean = that match
  { case that: Tile => that.canEqual(this) & cood == that.cood
    case _ => false
  }
}

trait ColouredTile extends Tile with WithColour

case class TileBare(x: Int, y: Int) extends Tile
case class SideBare(x: Int, y: Int) extends GridElem
object SideBare
{
   implicit object SideBareIsType extends IsType[SideBare]
   {
      override def isType(obj: AnyRef): Boolean = obj.isInstanceOf[SideBare]
      override def asType(obj: AnyRef): SideBare = obj.asInstanceOf[SideBare]   
   }
}

