/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A regular tile grid, covering a flat Euclidean 2s surface as opposed to an earth based tile grid or other grid that is not mapping a flat 2d
 *  surface. */
trait TileGridReg[TileT <: Tile] extends TileGrid[TileT]
{
  override def optTile(x: Int, y: Int): Option[TileT] = if (x >= xTileMin & x <= xTileMax & y >= yTileMin & y <= yTileMax )
    Some(getTile(x, y)) else None
  
  def coodToVec2(cood: Cood): Vec2
  def left: Double
  def right: Double 
  def bottom: Double 
  def top: Double
  def dimensionsStr(f: Double => String = _.str2): String =
    List("left" -> left, "right" -> right, "bottom" -> bottom, "top" -> top).map(p => p._1 :- f(p._2)).mkString("; ")
   
  def width = right -left
  def height = top - bottom
  def diagLength = math.sqrt(width * width + height * height)
  def xCen = (left + right) / 2
  def yCen = (top + bottom) / 2
  def cen: Vec2 = Vec2(xCen, yCen)
}

trait TileGridComplexReg[TileT <: Tile, SideT <: GridElem] extends TileGridComplex[TileT, SideT] with TileGridReg[TileT]
