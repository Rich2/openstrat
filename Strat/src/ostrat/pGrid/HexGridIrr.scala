/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import reflect.ClassTag

abstract class HexGridIrr[TileT <: Tile, SideT <: GridElem](val rowBounds: Array[Int], xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
   (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends HexGrid[TileT, SideT](xTileMin, xTileMax, yTileMin, yTileMax)          
{ 
  def rowStartInd(y: Int) = (y - yTileMin)  * 2
  def rowTileXStart(y: Int) = rowBounds(rowStartInd(y))
  def setRowStart(y: Int, value: Int): Unit = rowBounds(rowStartInd(y)) = value
  def rowEndInd(y: Int) = rowStartInd(y) + 1
  def rowTileXEnd(y: Int): Int = rowBounds(rowEndInd(y))
  def setRowEnd(y: Int, value: Int): Unit = rowBounds(rowEndInd(y)) = value 
  override def sideNum: Int = ???
  
  val sideArr: Array[SideT] = new Array[SideT](sideArrLen)
   
  override def tileNum: Int =
  {
    var acc: Int = 0
    forallTileRows {y =>
      val delta = (rowTileXEnd(y) - rowTileXStart(y)) / 4 + 1
      acc += delta
    }
    acc
  }
   
  /** Needs more work. */
  final override def forallSidesXY(f: (Int, Int) => Unit): Unit = 
  {   
    if (tileNum == 0) return    
    rowForeachTileXY(yTileMin, (x, y) => { f(x - 1, y - 1); f(x + 1, y - 1) })    
    
    ((yTileMin + 1) to (yTileMax - 1) by 2).foreach{ y =>
      val xStart = rowTileXStart(y - 1).min(rowTileXStart(y + 1)) + 1
      val xEnd = rowTileXEnd(y - 1).max(rowTileXEnd(y + 1)) + 1    
      (xStart to xEnd by 2).foreach(x => f(x, y))
    }
    
    forallTilesXY{ (x, y) => f(x + 2, y)}
    rowForeachTileXY(yTileMax, (x, y) => { f(x - 1, y + 1); f(x + 1, y + 1) })    
  }
  

  override def optTile(x: Int, y: Int): Option[TileT] = Unit match
  {
    case _ if y < yTileMin => None
    case _ if y > yTileMax => None
    case _ if x < rowTileXStart(y) => None
    case _ if x > rowTileXEnd(y) => None   
    case _ => Some(getTile(x, y))
  } 
  
  final override def setTilesRectangle[A](bottomLeft: Cood, topRight: Cood, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit = ???
}
