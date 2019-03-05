/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** A Tile grid that contains both values for the tiles and the tile boundaries or sides. Rivers, straits, walls, doors, windows ditches and
 *  fortifications are examples of values commonly assigned to tile sides. */
trait TileGridComplex[TileT <: Tile, SideT <: GridElem] extends TileGrid[TileT]
{
   thisGrid => 
   /** Check Think this type is needed */   
   type GridT <: TileGridComplex[TileT, SideT]   
   
   def evSide: IsType[SideT]
   def vertCoodsOfTile(tileCood: Cood): Coods
   def sideCoodsOfTile(tileCood: Cood): Coods     
   
   def setSide(x: Int, y: Int, side: SideT): Unit = { coodIsSide(x, y); arr(xyToInd(x, y)) = side }
   def setSide(sc: Cood, side: SideT): Unit = { coodIsSide(sc); arr(xyToInd(sc.x, sc.y)) = side }
   def fSetSide[A](x: Int, y: Int, value: A)(implicit f: (Int, Int, A) => SideT) = {coodIsSide(x, y); arr(xyToInd(x, y)) = f(x, y, value)}
   def fSetSides[A](value: A, xys: Coods)(implicit f: (Int, Int, A) => SideT) = xys.foreach(p => fSetSide(p.x, p.y, value))   
   
   /** Throws exception if Cood is not a valid Tile coordinate */
   def coodIsSide(x: Int, y: Int): Unit
   /** Throws exception if Cood is not a valid Tile coordinate */
   final def coodIsSide(cood: Cood): Unit = coodIsSide(cood.x, cood.y)   
  
   def getSide(x: Int, y: Int): SideT = evSide.asType(arr(xyToInd(x, y))) 
   def getSide(tc: Cood): SideT = evSide.asType(arr(xyToInd(tc.x, tc.y)))   
  
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
      
   def setAllTiles[A](value: A)(implicit fTile: (Int, Int, A) => TileT): Unit = tileXYForeach((x, y) => fSetTile(x, y, value)(fTile))    
   def fSidesSetAll[A](value: A)(implicit fA: (Int, Int, A) => SideT): Unit = sideXYForeach((x, y) => fSetSide(x, y, value))   
   
   /** Note set RowBack starts with the y (row) parameter */
   final def setRowBack[A](yRow: Int, xStart: Int, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
   {
      val tiles = tileMakers.toSingles      
      tiles.iForeach{(e, i) =>
         val x = xStart - i * xStep
         fSetTile(x, yRow, e)
      }
      Cood(xStart - (tiles.length - 1) * xStep, yRow)
   }
   
   final def setRowBack[A](cood: Cood, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
      setRowBack(cood.y, cood.x, tileValues: _*)(f)
   /** Warning implementations need modification. */   
   def adjTileCoodsOfTile(tileCood: Cood): Coods
}
