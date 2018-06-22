/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A tileGrid is a collection of tiles, either hexs or squares. This is a fundamental class. It is a specific case of a tiled area. I
 *  have reached the conclusion that the general case of completely irregular tiling, while interesting mathematically and useful for say
 *  representing a historical game like "Risk", has insufficient utility for the representations we want today. The grid consists of tiles
 *  and tile sides. Sides have gone!!!! Ignore - I have reluctantly introduced this serious complication to the foundational model.  
 *  
 *  It is stored in an underlying array. It consists of a sequence of contiguous rows of *  tiles. Each row of tiles is itself contiguous,
 *  There are no breaks between the first tile of the row and the last tile of the row although a row can consist of a single tile. Every
 *  row shares at least one tile side with the row above and below. The grid includes all the sides of the tiles including the sides on
 *  the outer edges of the grid. This means to link two grids requires a Grid Bridge class. */
abstract class TileGrid[TileT <: Tile](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
   (implicit evTile: IsType[TileT])
{
   thisGrid => 
   /** Check Think this type is needed */   
   type GridT <: TileGrid[TileT]
   /** Not sure if this type is needed */
   //type TileOFGridT = TileOfGrid[TileT]
   
   def tileVertCoods(cenCood: Cood): Coods
   //final def tileVertCoods(cenCood: Cood): List[VecCood]
//   def xSideMin: Int 
//   def xSideMax: Int 
//   val ySideMin: Int = yTileMin + 1
//   val ySideMax: Int = yTileMax + 1
   def xStep: Int
   val yRatio: Double
   
   def left: Double
   def right: Double 
   def bottom: Double 
   def top: Double
   def dimensionsStr(f: Double => String = _.str2): String =
      List("left" -> left, "right" -> right, "bottom" -> bottom, "top" -> top).map(p => p._1 :- f(p._2)).mkString("; ")
   def width = right -left
   def height = top - bottom
   lazy val diagLength = math.sqrt(width * width + height * height)
   def xCen = (left + right) / 2
   def yCen = (top + bottom) / 2
   def cen: Vec2 = Vec2(xCen, yCen)
   def coodToVec2(cood: Cood): Vec2
   
   def xArrLen: Int// = xSideMax / 2 - xSideMin / 2 + 1
   val yArrLen = yTileMax - yTileMin + 2
   lazy val arr: Array[AnyRef] = new Array[AnyRef](yArrLen * xArrLen)
   def xToInd(x: Int): Int// = x / 2 - xSideMin /2   
   def yToInd(y: Int) = (y  - yTileMin) * xArrLen
   def xyToInd(x: Int, y: Int) = xToInd(x) + yToInd(y)
   
   def setTile(x: Int, y: Int, tile: TileT): Unit = arr(xyToInd(x, y)) = tile
   def setTile(tc: Cood, tile: TileT): Unit = arr(xyToInd(tc.x, tc.y)) = tile
   def fSetTile(x: Int, y: Int, fTile: (Int, Int) => TileT) = arr(xyToInd(x, y)) = fTile(x, y)
   def fSetTile(cood: Cood, fTile: Cood => TileT) = arr(xyToInd(cood.x, cood.y)) = fTile(cood)
   
   def getTile(x: Int, y: Int): TileT = evTile. asType(arr(xyToInd(x, y)))   
   def getTile(tc: Cood): TileT = evTile. asType(arr(xyToInd(tc.x, tc.y)))
   def modTiles(f: TileT => Unit, xys: (Int, Int)*): Unit = xys.foreach{ case (x, y) => f(getTile(x, y)) }
   
   //def setSide(x: Int, y: Int, side: SideT): Unit = arr(xyToInd(x, y)) = side
   //def getSide(x: Int, y: Int): SideT = evSide.asType(arr(xyToInd(x, y)))
   
   def sideCoodVertCoods(cood: Cood): CoodLine = sideXYVertCoods(cood.x, cood.y)
   def sideXYVertCoods(x: Int, y: Int): CoodLine
   
   /** The y loop could be abstracted, but this way no worries about inlining */
   def tileRowsForeach(f: Int => Unit): Unit =
   {
      var y: Int = yTileMin
      while(y <= yTileMax) { f(y); y += 2 }
   }
   /** needs change */
   @inline final def tileCoodForeach(f: Cood => Unit): Unit = tileXYForeach((x, y) => f(Cood(x, y)))
      // tileRowsForeach(y => tileCoodRowForeach(y, f))
   
   @inline def tileXYForeach(f: (Int, Int) => Unit): Unit 
   //def sideXYForeach(f: (Int, Int) => Unit): Unit
   //def setSides(fValue: (Int, Int) => SideT): Unit = sideXYForeach{(x, y) => setSide(x, y, fValue(x, y))}
//   def sidesMap[R](f: SideT => R): List[R] =
//   { var acc: List[R] = Nil
//      sideXYForeach{ (x, y) =>
//         val s = getSide(x, y)
//         acc ::= f(s)
//      }
//      acc.reverse
//   }
   
   /** Not sure how useful this method is. */
   final def tileAndCoodsFold[R](f: (TileT, Cood) => R, fSum: (R, R) => R)(emptyVal: R): R =
   {
      var acc: R = emptyVal
      tileCoodForeach{ tileCood =>
         val tile = getTile(tileCood)
         val newRes: R = f(tile, tileCood)
         acc = fSum(acc, newRes)
      }
      acc
   }
   
   def tileCoodsFold[R](f: Cood => R, fSum: (R, R) => R)(emptyVal: R): R =
   {
      var acc: R = emptyVal
      tileCoodForeach { tileCood => acc = fSum(acc, f(tileCood)) }
      acc
   }  
   
   def gridTileFold[R](f: (GridT, Cood) => R, fSum: (R, R) => R)(emptyVal: R): R =
   {
      var acc: R = emptyVal
      tileCoodForeach{ tileCood =>         
         val newRes: R = f(this.asInstanceOf[GridT], tileCood)
         acc = fSum(acc, newRes)
      }
      acc
   }
   
   def gridTileDispFold(f: (GridT, Cood) => Disp2): Disp2 = gridTileFold[Disp2](f, _ ++ _)(Disp2.empty)
   //def gridTileCoodDispFold(f: Cood => Disp2) = ???
   
   
   final def tilesMap[R](f: TileT => R): List[R] =
   {
      var acc: List[R] = Nil
      tileCoodForeach{ tileCood =>
         val tile = getTile(tileCood)
         val newRes: R = f(tile)
         acc = newRes :: acc
      }
      acc.reverse
   }
   
   final def tilesForeach[R](f: TileT => Unit): Unit =  tileCoodForeach{ tileCood => f(getTile(tileCood)) }  
      
   def tilesFlatMap[R](f: TileT => Seq[R]): List[R] = tilesMap(f).flatten
   
   /** Fundamental method for producing Disp2 from the Grid */
   def tileAndCoodsDisplayFold(f: (TileT, Cood) => Disp2): Disp2 =
      tileAndCoodsFold[Disp2](f, (acc, pair) => acc ++ pair)(Disp2.empty)
    def tileCoodsDisplayFold(f: Cood => Disp2): Disp2 =
      tileCoodsFold[Disp2](f, (acc, pair) => acc ++ pair)(Disp2.empty)   
  
      
   def fTilesSetAll[A](value: A)(implicit fA: (Int, Int, A) => TileT): Unit = tileXYForeach((x, y) => fSetTile(x, y, fA(_, _, value)))
   
   def tilesSetAll[A](fValue: (Int, Int) => TileT): Unit = tileXYForeach((x, y) => setTile(x, y, fValue(x, y)))
   
   /** Not set Row starts with the y (row) parameter */
   final def setRow[A](yRow: Int, xStart: Int, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
   {
      val tiles = tileMakers.flatMap(_.toSeq)      
      tiles.iForeach{(e, i) =>
         
            val x = xStart + i * xStep
            setTile(x, yRow, f(x, yRow, e))
         }
      Cood(xStart + (tiles.length - 1) * xStep, yRow)
   }
   final def setRow[A](cood: Cood, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood = setRow(cood.y, cood.x, tileMakers: _*)(f)
   
   /** Note set RowBack starts with the y (row) parameter */
   final def setRowBack[A](yRow: Int, xStart: Int, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
   {
      val tiles = tileMakers.flatMap(_.toSeq)      
      tiles.iForeach{(e, i) =>
         val x = xStart - i * xStep
         setTile(x, yRow, f(x, yRow, e))
      }
      Cood(xStart - (tiles.length - 1) * xStep, yRow)
   }
   final def setRowBack[A](cood: Cood, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
      setRowBack(cood.y, cood.x, tileMakers: _*)(f)
}
