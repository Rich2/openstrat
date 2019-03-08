/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A regular HexGrid containing only values for the tiles not for the boundaries between the tiles. */
abstract class HexGridSimple[TileT <: Tile](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
   (implicit val evTile: IsType[TileT]) extends TileGridReg[TileT] with HexGrid[TileT]
{
  override def coodToVec2(cood: Cood): Vec2 = HexGridSimple.coodToVec2(cood)
  def vertMargin = 0.6
  def horrMargin = 0.6
  override def left: Double = xTileMin - horrMargin
  override def right: Double = xTileMax + horrMargin
  def bottom: Double = (yTileMin) * yRatio - vertMargin
  def top: Double = (yTileMax) * yRatio + vertMargin
  override def xStep: Int = 2
  override def xArrLen: Int = xTileMax / 2 - xTileMin / 2 + 2 //+1 for zeroth tile, +1 for right side
  override val yArrLen: Int = yTileMax - yTileMin + 1//for zeroth tile, + 1 for upper side(s)
  override val arr: Array[AnyRef] = new Array[AnyRef](arrLen)
  override def xToInd(x: Int): Int = x / 2 - xTileMin / 2
  override def yToInd(y: Int): Int = (y  - yTileMin + 1)
  
  val row2Start = xTileMin.incrementTill(_.isOdd)
  val row4Start = xTileMin.incrementTill(_.isEven)
  val row2End = xTileMax.decrementTill(_.isOdd)
  val row4End = xTileMax.decrementTill(_.isEven)
  /** rows 1, 3, 5 ... -1, -3, -5 ... */
  def row1sForeach(f: Int => Unit): Unit =
    for { y <- yTileMin.incrementTill(_.isOdd) to yTileMax.decrementTill(_.isOdd) by 2 } yield f(y)
      
  /** rows 2, 4, 6 ... 0, -2, -4, -6 ... */
  def row2sForeach(f: Int => Unit): Unit =
    for { y <- yTileMin.incrementTill(_.isEven) to yTileMax.decrementTill(_.isEven) by 2 } yield f(y)
  override def tileXYForeach(f: (Int, Int) => Unit): Unit = 
  { row1sForeach(y => for { x <- row2Start to row2End by 2} yield f(x, y))
    row2sForeach(y => for { x <- row4Start to row4End by 2} yield f(x, y))
  }
  def sideCoods: Coods = ???// tilesMap( 
}

object HexGridSimple
{
  def coodToVec2(cood: Cood): Vec2 = coodToVec2(cood.x, cood.y)
  def coodToVec2(x: Int, y: Int): Vec2 =
    if(x.isEven & y.isEven | x.isOdd & y.isOdd) Vec2(x, y * HexGrid.yRatio)
    else throw new Exception("This is an invalid HexCood")        
  
}

