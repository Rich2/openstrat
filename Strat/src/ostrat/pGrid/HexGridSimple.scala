/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, reflect.ClassTag

/** A regular HexGrid containing only values for the tiles not for the boundaries between the tiles. */
abstract class HexGridSimple[TileT <: Tile](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
   (implicit val evTile: ClassTag[TileT]) extends HexGridRegular[TileT]
{
  override def coodToVec2(cood: Cood): Vec2 = HexGridSimple.coodToVec2(cood)
  //Y coordinate multiplied by sqrt(3) to get Vec2. And the upper and lower hex vertices have a greater than 1 cood.y delta. 
  def vertMargin = 1.2
  def horrMargin = 0.6

  override def xStep: Int = 2
  override def xArrLen: Int = xTileMax / 2 - xTileMin / 2 + 2 //+1 for zeroth tile, +1 for right side
  override val yArrLen: Int = yTileMax - yTileMin + 1//for zeroth tile, + 1 for upper side(s)
  override val arr: Array[TileT] = new Array[TileT](arrLen)
  override def xToInd(x: Int): Int = x / 2 - xTileMin / 2  
  
  override def rowTileXStart(y: Int): Int = ife(y.isEven, xRow2Start, xRow1Start)
  override def rowTileXEnd(y: Int): Int = ife(y.isEven, xRow2End, xRow1End)
  override def rowForeachTileXY(y: Int, f: (Int, Int) => Unit): Unit = (rowTileXStart(y) to rowTileXEnd(y) by 2).foreach(x => f(x, y))
  
  override def tileNum: Int = xRow1Length * yRow1Length + xRow2Length * yRow2Length
  def xRow1Start: Int = xTileMin.incrementTill(_.isOdd)
  def xRow2Start: Int = xTileMin.incrementTill(_.isEven)
  def xRow1End: Int = xTileMax.decrementTill(_.isOdd)
  def xRow2End: Int = xTileMax.decrementTill(_.isEven)
  
  def xRow1Length: Int = (xRow1End - xRow1Start) match
  { case l if l < 0 => 0
    case l => l.incrementTill(_.isOdd) /2
  }
  
  def xRow2Length: Int = (xRow2End - xRow2Start) match
  { case l if l < 0 => 0
    case l => l.incrementTill(_.isOdd) /2
  }
  
  def yRow1Start: Int = yTileMin.incrementTill(_.isOdd)
  def yRow2Start: Int = yTileMin.incrementTill(_.isEven)
  def yRow1End: Int = yTileMax.decrementTill(_.isOdd)
  def yRow2End: Int = yTileMax.decrementTill(_.isEven)
  def yRow1Length: Int = (yRow1End - yRow1Start).min(0)
  def yRow2Length: Int = (yRow2End - yRow2Start).min(0)
  
  /** rows 1, 3, 5 ... -1, -3, -5 ... */
  def row1sForeach(f: Int => Unit): Unit = for { y <- yRow1Start to yRow1End by 2 } yield f(y)      
  /** rows 2, 4, 6 ... 0, -2, -4, -6 ... */
  def row2sForeach(f: Int => Unit): Unit =  for { y <- yRow2Start to yRow2End by 2 } yield f(y)
  
  final override def forallTilesXY(f: (Int, Int) => Unit): Unit = 
  { row1sForeach(y => for { x <- xRow1Start to xRow1End by 2} yield f(x, y))
    row2sForeach(y => for { x <- xRow2Start to xRow2End by 2} yield f(x, y))
  }
  //override def tileCoods: Coods = ???  
  def sidePseudoCoods: Coods = ???  
  override def allSideLines: Line2s =
  {

  } ???
  final override def setTilesRectangle[A](bottomLeft: Cood, topRight: Cood, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit = ???
}

object HexGridSimple
{
  def coodToVec2(cood: Cood): Vec2 = coodToVec2(cood.x, cood.y)
  def coodToVec2(x: Int, y: Int): Vec2 =
    if(x.isEven & y.isEven | x.isOdd & y.isOdd) Vec2(x, y * HexGrid.yRatio)
    else throw new Exception("This is an invalid HexCood")
}

