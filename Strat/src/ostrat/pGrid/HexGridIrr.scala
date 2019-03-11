/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import reflect.ClassTag

abstract class HexGridIrr[TileT <: Tile, SideT <: GridElem](val rowBounds: Array[Int], xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
   (implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends HexGridComplex[TileT, SideT](xTileMin, xTileMax, yTileMin, yTileMax)          
{ 
  def rowStartInd(y: Int) = (y - yTileMin)  * 2
  def rowTileXStart(y: Int) = rowBounds(rowStartInd(y))
  def setRowStart(y: Int, value: Int): Unit = rowBounds(rowStartInd(y)) = value
  def rowEndInd(y: Int) = rowStartInd(y) + 1
  def rowTileXEnd(y: Int): Int = rowBounds(rowEndInd(y))
  def setRowEnd(y: Int, value: Int): Unit = rowBounds(rowEndInd(y)) = value 
  
  val sideArr: Array[SideT] = new Array[SideT](sideArrLen)
   
  override def tileNum: Int =
  {
    var acc: Int = 0
    tileRowsForeach{y =>
      val delta = (rowTileXEnd(y) - rowTileXStart(y)) / 4 + 1
      acc += delta
    }
    acc
  }  
   
  override def tileRowsForeach(f: Int => Unit): Unit = 
  { var y: Int = yTileMin
    while(y <= yTileMax) { f(y); y += 2 }
  }
   
  @inline override def foreachTileXY(f: (Int, Int) => Unit): Unit = tileRowsForeach{ y => rowForeachTileXY(y, f) }
  
 // def tileRowMap[R](y: Int, f: (TileT, Cood) => R): Seq[R] = (rowTileXStart(y) to rowTileXEnd(y) by 4).map(x => f(getTile(x, y), Cood(x, y)))

  def tileRowFold[R](y: Int, f: (TileT, Cood) => R, fSum: (R, R) => R)(emptyVal: R): R =
    (rowTileXStart(y) to rowTileXEnd(y) by 4).foldLeft(emptyVal){(acc, x) =>
      val res = f(getTile(x, y), Cood(x, y))
      fSum(acc, res)     
   }     
   
  override def optTile(x: Int, y: Int): Option[TileT] = Unit match
  {
    case _ if y < yTileMin => None
    case _ if y > yTileMax => None
    case _ if x < rowTileXStart(y) => None
    case _ if x > rowTileXEnd(y) => None   
    case _ => Some(getTile(x, y))
  } 
}
