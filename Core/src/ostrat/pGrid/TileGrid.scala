/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

abstract class TileGridLike[TileT <: GridElem](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
{
   def xArrLen: Int
   def xToInd(x: Int): Int
   def yToInd(y: Int): Int
   def xyToInd(x: Int, y: Int) = xToInd(x) + yToInd(y)
}

/** A tileGrid is a collection of tiles, either hexs or squares. This is a fundamental class. It is a specific case of a tiled area. I
 *  have reached the conclusion that the general case of completely irregular tiling, while interesting mathematically and useful for say
 *  representing a historical game like "Risk", has insufficient utility for the representations we want today. The grid consists of tiles
 *  and tile sides. Sides have gone!!!! Ignore - I have reluctantly introduced this serious complication to the foundational model.  
 *  
 *  It is stored in an underlying array. It consists of a sequence of contiguous rows of tiles. Each row of tiles is itself contiguous,
 *  There are no breaks between the first tile of the row and the last tile of the row although a row can consist of a single tile. Every
 *  row shares at least one tile side with the row above and below. The grid includes all the sides of the tiles including the sides on
 *  the outer edges of the grid. This means to link two grids requires a Grid Bridge class. */
abstract class TileGrid[TileT <: GridElem, SideT <: GridElem](xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
   (implicit evTile: IsType[TileT], evSide: IsType[SideT]) extends TileGridLike[TileT](xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)
{
   thisGrid => 
   /** Check Think this type is needed */   
   type GridT <: TileGrid[TileT, SideT]   
   def vertCoodsOfTile(tileCood: Cood): Coods
   def sideCoodsOfTile(tileCood: Cood): Coods
   def xStep: Int
   val yRatio: Double    
   
   val yArrLen = yTileMax - yTileMin + 3//+ 1 for lowersides +1 for zeroth tile, + 1 for upper side(s)
   lazy val arr: Array[AnyRef] = new Array[AnyRef](yArrLen * xArrLen)   
   def yToInd(y: Int): Int = (y  - yTileMin + 1) * xArrLen
  
   
   def setTile(x: Int, y: Int, tile: TileT): Unit = { coodIsTile(x, y); arr(xyToInd(x, y)) = tile }
   def setTile(tc: Cood, tile: TileT): Unit = { coodIsTile(tc); arr(xyToInd(tc.x, tc.y)) = tile }
   def fSetTile(x: Int, y: Int, fTile: (Int, Int) => TileT) = { coodIsTile(x, y);  arr(xyToInd(x, y)) = fTile(x, y) }
   def fSetTile(cood: Cood, fTile: Cood => TileT) = { coodIsTile(cood); arr(xyToInd(cood.x, cood.y)) = fTile(cood) }
   
   def fSetSide[A](x: Int, y: Int, value: A)(implicit f: (Int, Int, A) => SideT) = {coodIsSide(x, y); arr(xyToInd(x, y)) = f(x, y, value)}
   def fSetSides[A](value: A, xys: Coods)(implicit f: (Int, Int, A) => SideT) = xys.foreach(p => fSetSide(p.x, p.y, value))
   
   /** Throws exception if Cood is not a valid Tile coordinate */
   final def coodIsTile(cood: Cood): Unit = coodIsTile(cood.x, cood.y)
   /** Throws exception if Cood is not a valid Tile coordinate */
   def coodIsSide(x: Int, y: Int): Unit
   /** Throws exception if Cood is not a valid Tile coordinate */
   final def coodIsSide(cood: Cood): Unit = coodIsSide(cood.x, cood.y)
   /** Throws exception if Cood is not a valid Tile coordinate */
   def coodIsTile(x: Int, y: Int): Unit   
   
   def getTile(x: Int, y: Int): TileT = { coodIsTile(x, y); evTile.asType(arr(xyToInd(x, y))) }   
   def getTile(tc: Cood): TileT = { coodIsTile(tc); evTile.asType(arr(xyToInd(tc.x, tc.y))) }
   def getSide(x: Int, y: Int): SideT = evSide.asType(arr(xyToInd(x, y))) 
   def getSide(tc: Cood): SideT = evSide.asType(arr(xyToInd(tc.x, tc.y)))
   
   def optTile(x: Int, y: Int): Option[TileT]
   final def optTile(cood: Cood): Option[TileT] = optTile(cood.x, cood.y)
   def sidesTileCoods(x: Int, y: Int): (Cood, Cood)
   def optSidesTiles(x: Int, y: Int): (Option[TileT], Option[TileT]) =
   {
      val (c1, c2) = sidesTileCoods(x, y)
      (optTile(c1), optTile(c2))
   }
   final def optSidesTiles(cood: Cood): (Option[TileT], Option[TileT]) = optSidesTiles(cood.x, cood.y)
   
   def modTiles(f: TileT => Unit, xys: (Int, Int)*): Unit = xys.foreach{ case (x, y) => f(getTile(x, y)) }
   
   //def setSide(x: Int, y: Int, side: SideT): Unit = arr(xyToInd(x, y)) = side
   //def getSide(x: Int, y: Int): SideT = evSide.asType(arr(xyToInd(x, y)))
   
   def vertCoodLineOfSide(cood: Cood): CoodLine = vertCoodLineOfSide(cood.x, cood.y)
   def vertCoodLineOfSide(x: Int, y: Int): CoodLine
   
   /** The y loop could be abstracted, but this way no worries about inlining */
   def tileRowsForeach(f: Int => Unit): Unit =
   {
      var y: Int = yTileMin
      while(y <= yTileMax) { f(y); y += 2 }
   }
   /** needs change */
   @inline final def tileCoodForeach(f: Cood => Unit): Unit = tileXYForeach((x, y) => f(Cood(x, y)))
   @inline final def sideCoodForeach(f: Cood => Unit): Unit = sideXYForeach((x, y) => f(Cood(x, y)))
      // tileRowsForeach(y => tileCoodRowForeach(y, f))
   
   @inline def tileXYForeach(f: (Int, Int) => Unit): Unit 
   @inline def sideXYForeach(f: (Int, Int) => Unit): Unit
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
   
   def gridTileDispFold(f: (GridT, Cood) => GraphicElems): GraphicElems = gridTileFold[GraphicElems](f, _ ++ _)(Nil)   
   
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
   
   /** Fundamental method for producing GraphicElems from the Grid */
   def tileAndCoodsDisplayFold(f: (TileT, Cood) => GraphicElems): GraphicElems =
      tileAndCoodsFold[GraphicElems](f, (acc, pair) => acc ++ pair)(Nil)
    def tileCoodsDisplayFold(f: Cood => GraphicElems): GraphicElems =
      tileCoodsFold[GraphicElems](f, (acc, pair) => acc ++ pair)(Nil)   
  
      
   def fTilesSetAll[A](value: A)(implicit fA: (Int, Int, A) => TileT): Unit = tileXYForeach((x, y) => fSetTile(x, y, fA(_, _, value)))   
   def tilesSetAll[A](fValue: (Int, Int) => TileT): Unit = tileXYForeach((x, y) => setTile(x, y, fValue(x, y)))
   def fSidesSetAll[A](value: A)(implicit fA: (Int, Int, A) => SideT): Unit = sideXYForeach((x, y) => fSetSide(x, y, value))
   
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
   /** Warning implementations need modification. */   
   def adjTileCoodsOfTile(tileCood: Cood): Coods
}
