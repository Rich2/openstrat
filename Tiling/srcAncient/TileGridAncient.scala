/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat; package pGrid
import geom._, reflect.ClassTag, collection.mutable.ArrayBuffer, Colour._

class RowMulti[TileT <: AnyRef](val y: Int, val xStart: Int, val multis: RefsMulti[TileT])
{
  //def toPair(implicit ct: ClassTag[TileT]): (Int, Arr[TileT]) = (xStart, multis.flatSingles)
}

object RowMulti
{
  def apply[TileT <: AnyRef](y: Int, xStart: Int, multis: Multiple[TileT]*): RowMulti[TileT] = new RowMulti(y, xStart, multis.toArr)
}

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
trait TileGridAncient[TileT <: TileAncient, SideT <: TileSideAncient]
{
  def turnNum: Int
  def xTileMin: Int
  def xTileMax: Int
  def yTileMin: Int
  def yTileMax: Int
  def numTileRow: Int = (yTileMax - yTileMin + 1) / 2

  def xArrLen: Int
  def yArrLen: Int
  final def arrLen = yArrLen * xArrLen
  def evTile: ClassTag[TileT]
  val arr: Array[TileT]
  final def xToInd(x: Int): Int = (x - xTileMin) / xStep
  final def yToInd(y: Int): Int = y  - yTileMin
  def xyToInd(x: Int, y: Int): Int = xToInd(x) + yToInd(y) * xArrLen
  final def coodToInd(cood: Cood): Int = xyToInd(cood.xi, cood.yi)
  //val yRatio: Double
  val xRatio: Double
  def xStep: Int
  def rowTileXStart(y: Int): Int
  def rowTileXEnd(y: Int): Int
  def tileNum: Int

  /** For each Tile's XY in part of a row. */
  def rowForeachTilesXY(y: Int, xStart: Int, xEnd: Int, f: (Int, Int) => Unit): Unit

  /** For each Tile's XY in the whole of the row. */
  final def rowForeachTilesXYAll(y: Int)(f: (Int, Int) => Unit): Unit = rowForeachTilesXY(y, rowTileXStart(y), rowTileXEnd(y), f)

  final def rowForeachTileAll(y: Int)(f: TileT => Unit): Unit = rowForeachTilesXYAll(y)((x, y) => f(getTile(x, y)))

  final def setTiles[A](bottomLeft: Cood, topRight: Cood, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit =
    setTileRect(bottomLeft.xi, topRight.yi, bottomLeft.yi, topRight.yi, tileValue)(f)
  def setTileRect[A](xFrom: Int, xTo: Int, yFrom: Int, yTo: Int, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit
  /** Throws exception if Cood is not a valid Tile coordinate */
  def coodIsTile(x: Int, y: Int): Unit

  final def tileDestinguishColour(tileCood: Cood): Colour = tileDestinguish(tileCood, Red, Blue, Green, Orange)
  def tileDestinguish[A](cood: Cood, v1: A, v2: A, v3: A, v4: A): A

  def optTile(x: Int, y: Int): Option[TileT]
  final def optTile(cood: Cood): Option[TileT] = optTile(cood.xi, cood.yi)

  /** Throws exception if Cood is not a valid Tile coordinate */
  final def coodIsTile(cood: Cood): Unit = coodIsTile(cood.xi, cood.yi)

  def getTile(x: Int, y: Int): TileT = { coodIsTile(x, y); arr(xyToInd(x, y)) }
  def getTile(tc: Cood): TileT = { coodIsTile(tc); arr(xyToInd(tc.xi, tc.yi)) }

  def setTile(x: Int, y: Int, value: TileT): Unit = { coodIsTile(x, y); arr(xyToInd(x, y)) = value  }
  def setTile(cood: Cood, value: TileT): Unit = setTile(cood.xi, cood.yi, value)
  def copyTile(oldGrid: TileGridAncient[TileT, _], cood: Cood): Unit = setTile(cood, oldGrid.getTile(cood))

  def fSetTile[A](cood: Cood, value: A)(implicit fTile: (Int, Int, A) => TileT): Unit = fSetTile[A](cood.xi, cood.yi, value)(fTile)
  def fSetTile[A](x: Int, y: Int, value: A)(implicit fTile: (Int, Int, A) => TileT): Unit =
  { coodIsTile(x, y)
    arr(xyToInd(x, y)) = fTile(x, y, value)
  }
  final def setTilesAll[A](value: A)(implicit fTile: (Int, Int, A) => TileT): Unit = foreachTilesXYAll((x, y) => fSetTile(x, y, value)(fTile))

  /** For all Tiles call side effecting function on the Tile's XY Cood. */
  @inline final def foreachTilesXYAll(f: (Int, Int) => Unit): Unit = foreachTileRowAll(y => rowForeachTilesXYAll(y)(f))

  /** For all Tiles call side effecting function on the Tile's Cood. */
  @inline final def foreachTilesCoodAll(f: Cood => Unit): Unit = foreachTilesXYAll((x, y) => f(Cood(x, y)))

  /** For all Tiles call side effecting function on the Tile. */
  @inline final def foreachTileAll(f: TileT => Unit): Unit =  foreachTilesCoodAll{ tileCood => f(getTile(tileCood)) }

  /** For each tile row, perform side effecting method. */
  final def foreachTileRowAll(f: Int => Unit): Unit =
  { var y: Int = yTileMin
    while(y <= yTileMax) { f(y); y += 2 }
  }

  /** Map all Tiles to Array[B] with function. */
  final def tilesMapAll[B, BB <: Arr[B]](f: TileT => B)(implicit build: ArrMapBuilder[B, BB]): BB =
  { val res = build.arrUninitialised(tileNum)
    val count = 0
    while (count < tileNum)
    {
      val tile = arr(count)
      val newRes: B = f(tile)
      build.arrSet(res, count, newRes)
    }
    res
  }

  def tilesFlatMapAll[B, BB <: Arr[B]](f: TileT => BB)(implicit build: ArrMapBuilder[B, BB]): BB =
  { val acc = build.newBuff()
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: BB = f(tile)
      newRes.foreach(acc.grow)
    }
    build.buffToBB(acc)
  }
  
  /** Map all Tiles to an List with function and flatten into Single List. */
  def tilesFlatMapListAll[R: ClassTag](f: TileT => List[R]): List[R] =
  { var acc: List[R] = Nil
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

  /*@deprecated final def tilesMapOptionAllOld[A: ClassTag](f: TileT => Option[A]): ArrOld[A] =
  { var acc: Buff[A] = Buff()
    foreachTileAll(t => acc = f(t).fold(acc)(acc.+= _))
    acc.toArrOld
  }*/

  final def tilesMapOptionAll[A, AA <: Arr[A]](f: TileT => Option[A])(implicit build: ArrMapBuilder[A, AA]): AA =
  { val buff = build.newBuff()
    foreachTileAll { t =>
      f(t) match {
        case None =>
        case Some(a) => build.buffGrow(buff, a)
      }
    }
    build.buffToBB(buff)
  }

  final def tilesMapOptionListAll[A](f: TileT => Option[A]): List[A] =
  {
    var acc: List[A] = Nil
    foreachTileAll(t => acc = f(t).fold(acc)(a => a :: acc))
    acc.reverse
  }
  
  final def tileCoodsFoldAll[R](f: Cood => R, fSum: (R, R) => R)(emptyVal: R): R =
  { var acc: R = emptyVal
    foreachTilesCoodAll { tileCood => acc = fSum(acc, f(tileCood)) }
    acc
  } 
  
  /** Folds All tiles. */
  final def tilesFoldAll[R](f: TileT => R, fSum: (R, R) => R)(emptyVal: R): R =
  {
    var acc: R = emptyVal
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: R = f(tile)
      acc = fSum(acc, newRes)
    }
    acc   
  }
  
  /** Set tile row from the Cood. */
  final def setRow[A](cood: Cood, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood = setRow(cood.yi, cood.xi, tileValues: _*)(f)
  /** Note set Row starts with the y (row) parameter. */ 
  final def setRow[A](yRow: Int, xStart: Int, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
  {
    val tiles: List[A] = tileValues.toSinglesList
    tiles.iForeach{ (i, e) =>
      val x = xStart + i * xStep
      fSetTile(x, yRow, e)         
    }
    Cood(xStart + (tiles.length - 1) * xStep, yRow)   
  }
  
  /** Note set RowBack starts with the y (row) parameter */
  final def setRowBack[A](yRow: Int, xStart: Int, tileMakers: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
  {
    val tiles = tileMakers.toSinglesList
    tiles.iForeach{ (i, e) =>
      val x = xStart - i * xStep
      fSetTile(x, yRow, e)
    }
    Cood(xStart - (tiles.length - 1) * xStep, yRow)
  }
   
  final def setRowBack[A](cood: Cood, tileValues: Multiple[A]*)(implicit f: (Int, Int, A) => TileT): Cood =
    setRowBack(cood.yi, cood.xi, tileValues: _*)(f)
    
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
  
  def foreachSideCoodPDMapAll[A <: ElemValueN, M <: ValueNArr[A]](f: Cood => A)(implicit ev: ValueNSeqLikePersist[A, M]): M =
  { val acc: ArrayBuffer[ev.VT] = ev.newBuffer
    foreachSidesCoodAll(c => ev.appendtoBuffer(acc, f (c)))      
    ev.fromBuffer(acc)    
  }

  @inline final def sideCoodsAll: Coods = foreachSideCoodPDMapAll[Cood, Coods](c => c)
  final def sideCoodLinesAll: CoodLines = foreachSideCoodPDMapAll[CoodLine, CoodLines](vertCoodLineOfSide(_))
  
  /** Returns the 2 Coods for the Tile centres adjacent to the side. */
  def sidesTileCoods(x: Int, y: Int): (Cood, Cood)
  
  def vertCoodsOfTile(tileCood: Cood): Coods
  def sideCoodsOfTile(tileCood: Cood): Coods     
   
  def setSide(x: Int, y: Int, side: SideT): Unit = { coodIsSide(x, y); sideArr(xySideToInd(x, y)) = side }
  def setSide(sc: Cood, side: SideT): Unit = { coodIsSide(sc); sideArr(xySideToInd(sc.xi, sc.yi)) = side }
  def fSetSide[A](x: Int, y: Int, value: A)(implicit f: (Int, Int, A) => SideT) = {coodIsSide(x, y); sideArr(xySideToInd(x, y)) = f(x, y, value)}
  /** Sets a Coods collection of Side Cood to the same value. */
  final def setSideCollection[A](coods: Coods, value: A)(implicit f: (Int, Int, A) => SideT) = coods.foreach(p => fSetSide(p.xi, p.yi, value))
  final def setSidesAll[A](value: A)(implicit fA: (Int, Int, A) => SideT): Unit = foreachSidesXYAll((x, y) => fSetSide(x, y, value))   
  
  /** Throws exception if Cood is not a valid Tile coordinate */
  def coodIsSide(x: Int, y: Int): Unit
  /** Throws exception if Cood is not a valid Tile coordinate */
  final def coodIsSide(cood: Cood): Unit = coodIsSide(cood.xi, cood.yi)
  
  def getSide(x: Int, y: Int): SideT = sideArr(xySideToInd(x, y)) 
  def getSide(tc: Cood): SideT = sideArr(xySideToInd(tc.xi, tc.yi))
  
  def optSidesTiles(x: Int, y: Int): (Option[TileT], Option[TileT]) =
  { val (c1, c2) = sidesTileCoods(x, y)
    (optTile(c1), optTile(c2))
  }
  
  final def optSidesTiles(cood: Cood): (Option[TileT], Option[TileT]) = optSidesTiles(cood.xi, cood.yi)
   
  def modTilesAll(f: TileT => Unit, xys: (Int, Int)*): Unit = xys.foreach{ case (x, y) => f(getTile(x, y)) } 
  def vertCoodLineOfSide(cood: Cood): CoodLine = vertCoodLineOfSide(cood.xi, cood.yi)
  def vertCoodLineOfSide(x: Int, y: Int): CoodLine
  
  /** Warning implementations need modification. */   
  def adjTileCoodsOfTile(tileCood: Cood): Coods    
}