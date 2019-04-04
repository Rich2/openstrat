/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, reflect.ClassTag, collection.mutable.ArrayBuffer, Colour._

/** A Tile grid that contains both values for the tiles and the tile boundaries or sides. Rivers, straits, walls, doors, windows ditches and
 *  fortifications are examples of values commonly assigned to tile sides.
 * 
 *  A tileGrid is a collection of tiles, either hexs or squares. This is a fundamental trait. It is a specific case of a tiled area. I
 *  have reached the conclusion that the general case of completely irregular tiling, while interesting mathematically and useful for say
 *  representing a historical game like "Risk", has insufficient utility for the representations we want today. Tile rids can however be fully regular
 *  or partly irregular such as the grids for covering the Earth's surface. Grids can be simple just consisting of values for the tiles or complex
 *  containing values for the tiles and the tile sides. Rivers, straits, walls, ditches are examples of values commonly assigned to tile sides.  
 *  
 *  It is stored in an underlying array. It consists of a sequence of contiguous rows of tiles. Each row of tiles is itself contiguous,
 *  There are no breaks between the first tile of the row and the last tile of the row although a row can consist of a single tile. Every
 *  row shares at least one tile side with the row above and below. The grid includes all the sides of the tiles including the sides on
 *  the outer edges of the grid. This means to link two grids requires a Grid Bridge class. */
trait TileGrid[TileT <: Tile, SideT <: TileSide]
{  
  val arr: Array[TileT]
  def evTile: ClassTag[TileT]
  def xTileMin: Int
  def xTileMax: Int
  def yTileMin: Int
  def yTileMax: Int  
  def xArrLen: Int
  def yArrLen: Int
  def xToInd(x: Int): Int
  val yRatio: Double
  def xStep: Int 
  def tileNum: Int
  def rowForeachTilesXY(y: Int, xStart: Int, xEnd: Int, f: (Int, Int) => Unit): Unit
  final def rowForeachTilesXYAll(y: Int, f: (Int, Int) => Unit): Unit = rowForeachTilesXY(y, xTileMin, xTileMax, f)
  final def setTiles[A](bottomLeft: Cood, topRight: Cood, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit =
    setTiles(bottomLeft.x, topRight.y, bottomLeft.y, topRight.y, tileValue)(f)
  def setTiles[A](xFrom: Int, xTo: Int, yFrom: Int, yTo: Int, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit
  /** Throws exception if Cood is not a valid Tile coordinate */
  def coodIsTile(x: Int, y: Int): Unit
  
  final def tileDestinguishColour(tileCood: Cood): Colour = tileDestinguish(tileCood, Red, Blue, Green, Orange)
  def tileDestinguish[A](cood: Cood, v1: A, v2: A, v3: A, v4: A): A
  
  final def arrLen = yArrLen * xArrLen  
  final def yToInd(y: Int): Int = y  - yTileMin
  def xyToInd(x: Int, y: Int) = xToInd(x) + yToInd(y) * xArrLen
  
  def optTile(x: Int, y: Int): Option[TileT]
  final def optTile(cood: Cood): Option[TileT] = optTile(cood.x, cood.y)
    
  /** Throws exception if Cood is not a valid Tile coordinate */
  final def coodIsTile(cood: Cood): Unit = coodIsTile(cood.x, cood.y)
  
  def getTile(x: Int, y: Int): TileT = { coodIsTile(x, y); arr(xyToInd(x, y)) }   
  def getTile(tc: Cood): TileT = { coodIsTile(tc); arr(xyToInd(tc.x, tc.y)) } 
 
  def setTile(x: Int, y: Int, value: TileT): Unit = { coodIsTile(x, y); arr(xyToInd(x, y)) = value  }  
  def setTile(cood: Cood, value: TileT): Unit = setTile(cood.x, cood.y, value)
  def copyTile(oldGrid: TileGrid[TileT, _], cood: Cood): Unit = setTile(cood, oldGrid.getTile(cood))
  
  def fSetTile[A](cood: Cood, value: A)(implicit fTile: (Int, Int, A) => TileT): Unit = fSetTile[A](cood.x, cood.y, value)(fTile)
  def fSetTile[A](x: Int, y: Int, value: A)(implicit fTile: (Int, Int, A) => TileT): Unit =
  { coodIsTile(x, y)
    arr(xyToInd(x, y)) = fTile(x, y, value)
  }
  final def setTilesAll[A](value: A)(implicit fTile: (Int, Int, A) => TileT): Unit = foreachTilesXYAll((x, y) => fSetTile(x, y, value)(fTile))
  
  /** For all Tiles call side effecting function on the Tile's XY Cood. */
  @inline final def foreachTilesXYAll(f: (Int, Int) => Unit): Unit = foreachTileRowAll(y => rowForeachTilesXYAll(y, f))  
  
  /** For all Tiles call side effecting function on the Tile's Cood. */
  @inline final def foreachTilesCoodAll(f: Cood => Unit): Unit = foreachTilesXYAll((x, y) => f(Cood(x, y)))
  
  /** For all Tiles call side effecting function on the Tile. */
  @inline final def foreachTileAll(f: TileT => Unit): Unit =  foreachTilesCoodAll{ tileCood => f(getTile(tileCood)) }  
  
  final def foreachTileRowAll(f: Int => Unit): Unit =
  { var y: Int = yTileMin
    while(y <= yTileMax) { f(y); y += 2 }
  }
  
  /** Map all Tiles to Array[B] with function. */
  final def tilesMapAll[B: ClassTag](f: TileT => B): Array[B] =
  {
    val acc: ArrayBuffer[B] = new ArrayBuffer(0)
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: B = f(tile)
      acc += newRes
    }
    acc.toArray
  }
  
  /** Map all Tiles to an Array with function and flatten into Single Array. */
  def tilesFlatMapAll[R: ClassTag](f: TileT => Array[R]): Array[R] =
  {
    val acc: ArrayBuffer[R] = new ArrayBuffer(0)
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: Array[R] = f(tile)
      acc ++= newRes
    }
    acc.toArray
  }
  
  /** Map all Tiles to an List with function and flatten into Single List. */
  def tilesFlatMapListAll[R: ClassTag](f: TileT => List[R]): List[R] =
  {
    var acc: List[R] = Nil
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: List[R] = f(tile)
      acc :::= newRes
    }
    acc.reverse
  }
  
  
  /** Map all tiles' Cood to a List[B]. */
  final def tilesCoodMapListAll[B](f: Cood => B): List[B] =
  { var acc: List[B] = Nil
    foreachTilesCoodAll{c => acc ::= f(c) }
    acc.reverse    
  }
  
  /** FlatMap all tiles' Cood to a List[B]. */
  final def tilesCoodFlatMapListAll[B](f: Cood => List[B]): List[B] =
  { var acc: List[B] = Nil
    foreachTilesCoodAll{c => acc = f(c).reverse ::: acc }
    acc.reverse    
  }
  
  /** Map all tiles' XY Cood to List. */
  final def tilesXYMapListAll[B](f: (Int, Int) => B): List[B] =
  { var acc: List[B] = Nil
    foreachTilesXYAll{(x, y) => acc ::= f(x, y) }
    acc.reverse    
  }
  
  final def tilesMapListAll[A](f: TileT => A): List[A] =
  {
    var acc: List[A] = Nil
    foreachTileAll(acc ::= f(_))
    acc.reverse
  }
  
  final def tileCoodsFoldAll[R](f: Cood => R, fSum: (R, R) => R)(emptyVal: R): R =
  { var acc: R = emptyVal
    foreachTilesCoodAll { tileCood => acc = fSum(acc, f(tileCood)) }
    acc
  } 
  
  /** Not sure how useful this method is. */
  final def tileAndCoodsFoldAll[R](f: (TileT, Cood) => R, fSum: (R, R) => R)(emptyVal: R): R =
  {
    var acc: R = emptyVal
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: R = f(tile, tileCood)
      acc = fSum(acc, newRes)
    }
    acc   
  }
  
  final def tilesOptionDispAll(f: TileT => Option[GraphicElem[_]]): GraphicElems = 
  {
    var acc: GraphicElems = Nil
    foreachTileAll(t => f(t) match
      {
      case None => acc
      case Some(g) => acc ::= g
      })    
    acc.reverse
  }
  
  final def tilesOptionFlattenDispAll(f: TileT => Option[GraphicElems]): GraphicElems = 
  {
    var acc: GraphicElems = Nil
    foreachTileAll(t => f(t) match
      {
      case None => acc
      case Some(newList) => acc :::= newList
      })    
    acc.reverse
  }
  
  /** Set tile row from the Cood. */
  final def setRow[A](cood: Cood, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood = setRow(cood.y, cood.x, tileValues: _*)(f)
  /** Note set Row starts with the y (row) parameter. */ 
  final def setRow[A](yRow: Int, xStart: Int, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
  {
    val tiles: List[A] = tileValues.toSingles      
    tiles.iForeach{(e, i) =>
      val x = xStart + i * xStep
      fSetTile(x, yRow, e)         
    }
    Cood(xStart + (tiles.length - 1) * xStep, yRow)   
  }
  
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
    
  /* *********************************************** Side stuff *************************************************************/
  
  def evSide: ClassTag[SideT]
  val sideArr: Array[SideT]
  //final override def yStep: Int = 2
  final def sideArrLen = ySideArrLen * xSideArrLen
  final def xSideToInd(x: Int): Int = (x - xSideMin) / 2
  final def ySideToInd(y: Int): Int = y - ySideMin  
  def xySideToInd(x: Int, y: Int) = xSideToInd(x) + ySideToInd(y) * xSideArrLen
  def xSideMin: Int
  def xSideMax: Int
  def sideNum: Int
  final def ySideMin: Int = yTileMin - 1
  final def ySideMax: Int = yTileMax + 1
  final def xSideArrLen: Int = (xSideMax - xSideMin) / 2 + 1
  final def ySideArrLen: Int = ySideMax - ySideMin + 1
  
  /** For all Sides call side effecting function on the Tile side's XY Cood. */
  @inline def foreachSidesXYAll(f: (Int, Int) => Unit): Unit
  /** For all Sides call side effecting function on the Tile side's Cood. */
  @inline final def foreachSidesCoodAll(f: Cood => Unit): Unit = foreachSidesXYAll((x, y) => f(Cood(x, y)))
  
  def foreachSideCoodPDMapAll[A, M <: ProductVals[A]](f: Cood => A)(implicit ev: ProductValsBuilder[A, M]): M =
  {
    val acc: ArrayBuffer[ev.VT] = ev.newBuffer
    foreachSidesCoodAll(c => ev.appendtoBuffer(acc, f (c)))      
    ev.fromBuffer(acc)    
  }
  
  //final def SidesCoodPMap
  @inline final def sideCoodsAll: Coods = foreachSideCoodPDMapAll[Cood, Coods](c => c)
  final def sideCoodLinesAll: CoodLines = foreachSideCoodPDMapAll[CoodLine, CoodLines](vertCoodLineOfSide(_))
  
  /** Returns the 2 Coods for the Tile centres adjacent to the side. */
  def sidesTileCoods(x: Int, y: Int): (Cood, Cood)
  
  def vertCoodsOfTile(tileCood: Cood): Coods
  def sideCoodsOfTile(tileCood: Cood): Coods     
   
  def setSide(x: Int, y: Int, side: SideT): Unit = { coodIsSide(x, y); sideArr(xySideToInd(x, y)) = side }
  def setSide(sc: Cood, side: SideT): Unit = { coodIsSide(sc); sideArr(xySideToInd(sc.x, sc.y)) = side }
  def fSetSide[A](x: Int, y: Int, value: A)(implicit f: (Int, Int, A) => SideT) = {coodIsSide(x, y); sideArr(xySideToInd(x, y)) = f(x, y, value)}
  /** Sets a Coods collection of Side Cood to the same value. */
  final def setSideCollection[A](coods: Coods, value: A)(implicit f: (Int, Int, A) => SideT) = coods.foreach(p => fSetSide(p.x, p.y, value))   
  final def setSidesAll[A](value: A)(implicit fA: (Int, Int, A) => SideT): Unit = foreachSidesXYAll((x, y) => fSetSide(x, y, value))   
  
  /** Throws exception if Cood is not a valid Tile coordinate */
  def coodIsSide(x: Int, y: Int): Unit
  /** Throws exception if Cood is not a valid Tile coordinate */
  final def coodIsSide(cood: Cood): Unit = coodIsSide(cood.x, cood.y)   
  
  def getSide(x: Int, y: Int): SideT = sideArr(xySideToInd(x, y)) 
  def getSide(tc: Cood): SideT = sideArr(xySideToInd(tc.x, tc.y))  
  
  def optSidesTiles(x: Int, y: Int): (Option[TileT], Option[TileT]) =
  { val (c1, c2) = sidesTileCoods(x, y)
    (optTile(c1), optTile(c2))
  }
  
  final def optSidesTiles(cood: Cood): (Option[TileT], Option[TileT]) = optSidesTiles(cood.x, cood.y)
   
  def modTilesAll(f: TileT => Unit, xys: (Int, Int)*): Unit = xys.foreach{ case (x, y) => f(getTile(x, y)) } 
  def vertCoodLineOfSide(cood: Cood): CoodLine = vertCoodLineOfSide(cood.x, cood.y)
  def vertCoodLineOfSide(x: Int, y: Int): CoodLine  
     
  /** Fundamental method for producing GraphicElems from the Grid */
  def tileAndCoodsDisplayFoldAll(f: (TileT, Cood) => GraphicElems): GraphicElems = tileAndCoodsFoldAll[GraphicElems](f, (acc, pair) => acc ++ pair)(Nil)
  def tileCoodsDisplayFoldAll(f: Cood => GraphicElems): GraphicElems = tileCoodsFoldAll[GraphicElems](f, (acc, pair) => acc ++ pair)(Nil)   
  
  /** Warning implementations need modification. */   
  def adjTileCoodsOfTile(tileCood: Cood): Coods    
}
