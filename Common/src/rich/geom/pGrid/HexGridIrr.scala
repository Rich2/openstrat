/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package geom
package pGrid

abstract class HexGridIrr[TileT <: Tile](val rowBounds: Array[Int], xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
   (implicit evTile: IsType[TileT]) extends HexGrid[TileT](xTileMin, xTileMax, yTileMin, yTileMax)          
{   
   //lazy val rowBounds: Array[Int] = bounds// new Array[Int](yArrLen * 2)
   def rowStartInd(y: Int) = (y - yTileMin)  * 2
   def rowStart(y: Int) = rowBounds(rowStartInd(y))
   def setRowStart(y: Int, value: Int): Unit = rowBounds(rowStartInd(y)) = value
   def rowEndInd(y: Int) = rowStartInd(y) + 1
   def rowEnd(y: Int): Int = rowBounds(rowEndInd(y))
   def setRowEnd(y: Int, value: Int): Unit = rowBounds(rowEndInd(y)) = value
   
  // tileCoodRowsForeach{y => setRowStart(y, 4); setRowEnd(y, 0) }
   
   override def tileRowsForeach(f: Int => Unit): Unit = 
   {
      var y: Int = yTileMin
      while(y <= yTileMax) { f(y); y += 2 }
   }
   
   def sideXYForeach(f: (Int, Int) => Unit): Unit = ???
   
   @inline override def tileXYForeach(f: (Int, Int) => Unit): Unit = tileRowsForeach(y => tileXYRowForeach(y, f))
   
   /** Needs removal */
   def tileXYRowForeach(y: Int, f: (Int, Int) => Unit): Unit =
   {
      var x: Int = rowStart(y)
      val xEnd = rowEnd(y)
      while(x <= xEnd){ f(x, y); x += 4 }
   }   
   
   def tileRowMap[R](y: Int, f: (TileT, Cood) => R): Seq[R] = (rowStart(y) to rowEnd(y) by 4).map(x => f(getTile(x, y), Cood(x, y)))      
   
   
  // def sideRowMap[R](y: Int, f: (Cood, SideT) => R): Seq[R] = {deb("stop") ; ??? } 
//   {
//      val len = rowLen(y)
//      val xLow = rowStart(y)
//      y % 4 match
//      {
//         case _ if len == 0 => Seq()
//         case 0 => (xLow.incrementTill(_ % 4 == 2) to (xLow + len).decrementTill(_ % 4 == 2) by 4).map(x => f(HexCood(x, y), getSide(x, y)))
//         case 2 => (xLow.incrementTill(_ % 4 == 0) to (xLow + len).decrementTill(_ % 4 == 0) by 4).map(x => f(HexCood(x, y), getSide(x, y)))   
//         case _ => (xLow.incrementTill(_.isOdd) to (xLow + len).decrementTill(_.isOdd) by 2).map(x => f(HexCood(x, y), getSide(x, y)))        
//      }
//   }   
   
   //override def tilesMap[R](f: (TileT, TileCood) => R): Seq[R] = (yTileMin to yTileMax by 2).flatMap(y => tileRowMap(y, f))
   
//   def tilesFold[R](f: (TileT, TileCood) => R, fSum: (R, R) => R)(emptyVal: R): R =
//      (yTileMin to yTileMax by 2).foldLeft(emptyVal){(acc, y) =>
//         val rowRes: R = tileRowFold(y, f, fSum)(emptyVal)
//         fSum(acc, rowRes)
//         }
   
   def tileRowFold[R](y: Int, f: (TileT, Cood) => R, fSum: (R, R) => R)(emptyVal: R): R = 
   {
      
     (rowStart(y) to rowEnd(y) by 4).foldLeft(emptyVal){(acc, x) =>
        val res = f(getTile(x, y), Cood(x, y))
        fSum(acc, res)
     }
      
   }
   
  // def sidesMap[R](f: HexCood => R): Seq[R] = cenRows.flatMap(
   
   
   def fSetRow[A](f: (Int, Int, A) => TileT)(y: Int, xStart: Int, tileMakers: Multiple[A]*): Unit =
   {
      val tiles = tileMakers.flatMap(_.toSeq)
    //  setRowEnd(y, xStart + (tiles.length - 1) * 4)      
   //   setRowStart(y, xStart)
      tiles.iForeach{(e, i) =>
         val x = xStart + i * 4
         setTile(x, y, f(x, y, e))
         
      }
   }
   
  // def fSetAll[A](f: (Int, Int, A) => TileT, tValue: A): Unit = this.tilesSetAll(f(_, _, tValue))
   /** sets the inner sides of the grid */
 //  def setDefaultSides(defaultSide: SideT): Unit = ???
//   {
//      //sets the even rows
//      for
//      { y <- evenRows
//         x <- (rowStart(y) + 2) to (rowStart(y) + rowLen(y) -2)         
//      } setSide(x, y, defaultSide)
//      (yTileMin + 1 to yTileMax - 1).foreach(y =>
//         {
//            
//         })      
//   }
}
