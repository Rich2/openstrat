/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import reflect.ClassTag

abstract class HexGridIrrAncient[TileT <: TileAncient, SideT <: TileSideAncient]
  (val rowBounds: Array[Int], xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int)
  (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends HexGridAncient[TileT, SideT](xTileMin, xTileMax, yTileMin, yTileMax, turnNum)
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
    foreachTileRowAll {y =>
      val delta = (rowTileXEnd(y) - rowTileXStart(y)) / 4 + 1
      acc += delta
    }
    acc
  }
   
  /** Needs more work. */
  final override def foreachSidesXYAll(f: (Int, Int) => Unit): Unit = 
  {   
    if (tileNum == 0) return    
    rowForeachTilesXYAll(yTileMin){(x, y) => f(x - 1, y - 1); f(x + 1, y - 1) }
    
    ((yTileMin + 1) to (yTileMax - 1) by 2).foreach{ y =>
      val xStart = rowTileXStart(y - 1).min(rowTileXStart(y + 1)) + 1
      val xEnd = rowTileXEnd(y - 1).max(rowTileXEnd(y + 1)) + 1    
      (xStart to xEnd by 2).foreach(x => f(x, y))
    }
    
    foreachTilesXYAll{ (x, y) => f(x + 2, y)}
    rowForeachTilesXYAll(yTileMax){(x, y) => f(x - 1, y + 1); f(x + 1, y + 1) }
  }  

  override def optTile(x: Int, y: Int): Option[TileT] =
    ife(y < yTileMin | y > yTileMax | x < rowTileXStart(y) | x > rowTileXEnd(y), None, Some(getTile(x, y)))
  
  final override def setTileRect[A](xFrom: Int, xTo: Int, yFrom: Int, yTo: Int, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit = ???
}
