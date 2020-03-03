/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, reflect.ClassTag, collection.mutable.ArrayBuffer, Colour._

/*trait TGrid[TileT]
{ type GridT[A] <: TGrid[A]
  @inline final def yStep: Int = 2
  def coodToVec2(cood: Cood): Vec2
  def tArr: Array[TileT]
  def numTile: Int = tArr.length
  /** The index Arr first value is the bottom row number followed by 2 values for each row. The index for the start of the row in the tile Arr
   * followed by the xCood for the start of the row. Hence its length is 2 * numRows + 1. */
  def indArr: Array[Int]
  def vertCoodsOfTile(x: Int, y: Int): Coods = vertCoodsOfTile(x cc y)
  def vertCoodsOfTile(tileCood: Cood): Coods

  /** The number of rows. */
  def numRows: Int = indArr.length / 2
  @inline def yMin: Int = indArr(0)
  @inline def yLen: Int = yMin + indArr.length / 2 - 1
  @inline def yMax: Int = yMin + indArr.length - 3

  @inline def xMin: Int =
  { var acc: Int = 10000000
    rowsForAll(y => acc = acc.min(xRowStart(y)))
    acc
  }

  @inline def xMax: Int =
  { var acc: Int = -10000000
    rowsForAll(y => acc = acc.max(xRowEnd(y)))
    acc
  }

  def cen: Vec2
  def yInd(y: Int): Int = (y - indArr.last) / 2
  def rowIndex(y: Int): Int = indArr(y - yMin + 1)
  def xRowStart(y: Int): Int = indArr(y - yMin + 2)
  def xRowEnd(y: Int): Int = xRowStart(y) + xRowLen(y)
  def xRowLen(y: Int): Int = (rowTileNum(y) - 1 ) * 4
  def rowTileNum(y: Int): Int =  ife(y == yMax, numTile - rowIndex(y), rowIndex(y + yStep) - rowIndex(y))
  @inline def xStep: Int

  def xyToInd(x: Int, y: Int): Int = (x - xRowStart(y)) / xStep + rowIndex(y)
  def getTile(x: Int, y: Int): TileT = tArr(xyToInd(x, y))
  def tilePolygon(x: Int, y: Int): Polygon = vertCoodsOfTile(x, y).pMap(coodToVec2)
  def fTrans(scale: Double, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z): Vec2 => Vec2 =
    v => (v - mapOffset) * scale - displayOffset

  def tileDisplayPolygon(x: Int, y: Int, scale: Double, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z): Polygon =
    tilePolygon(x, y).fTrans(v => (v - mapOffset) * scale - displayOffset)

  def tileFill(x: Int, y: Int, scale: Double, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z)(f: TileT => Colour): PolyFill =
    tileDisplayPolygon(x, y, scale, mapOffset, displayOffset).fill(f(getTile(x, y)))

  def tilesFillAll(scale: Double, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z)(f: TileT => Colour): ArrOld[PolyFill] =
    xyTilesMapAll((x, y, tile) => tileFill(x, y, scale, mapOffset, displayOffset)(f))

  /** Throws exception if Cood is not a valid Tile coordinate */
  def coodIsTile(x: Int, y: Int): Unit
  def sideCoodLine(cood: Cood): CoodLine = sideCoodLine(cood.x, cood.y)
  def sideCoodLine(x: Int, y: Int): CoodLine

  def map[B](newGrid: Array[Int] => GridT[B], f: TileT => B): GridT[B] =
  { val res = newGrid(indArr)
    tArr.iForeach((t, i) => res.tArr(i) = f(t))
    res
  }

  def xyTilesForAll(f: (Int, Int, TileT) => Unit): Unit = rowsForAll(y => rowXYTilesForAll(y)(f))

  def xyTilesMapAll[B](f: (Int, Int, TileT) => B)(implicit ct: ClassTag[B]): ArrOld[B] =
  { val array: Array[B] = new Array(numTile)
    var count: Int = 0
    tilesXYForAll{(x, y) => array(count) = f(x, y, getTile(x, y)); count += 1}
    array.toArrOld
  }

  def xyTilesAccAll[B](f: (Int, Int, TileT, Buff[B]) => Unit)(implicit ct: ClassTag[B]): ArrOld[B] =
  { val buff: Buff[B] = Buff(numTile)
    tilesXYForAll((x, y) => f(x, y, getTile(x, y), buff))
    buff.toArr
  }

  def xyTilesDispAll(f: (Int, Int, TileT, Buff[PaintElem]) => Unit): ArrOld[PaintElem] = xyTilesAccAll(f)
  def rowsForAll(f: Int => Unit): Unit = iToForeach(yMin, yMax, yStep)(f)
  def tilesXYForAll(f: (Int, Int) => Unit): Unit = rowsForAll(y => rowTilesXYForAll(y)(f))
  def tilesCoodForAll(f: Cood => Unit): Unit = rowsForAll(y => rowTilesCoodForAll(y)(f))

  def yToRowI(y: Int): Int = (y - yMin) / 2

  def rowsMapAll[B](f: Int => B)(implicit ct: ClassTag[B]): ArrOld[B] =
  { val array: Array[B] = new Array(numRows)
    rowsForAll(y => array(yToRowI(y)) = f(y))
    array.toArrOld
  }

  def rowTilesForAll(y: Int)(f: TileT => Unit): Unit = iToForeach(xRowStart(y), xRowEnd(y), xStep)(x => f(getTile(x, y)))
  def rowXYTilesForAll(y: Int)(f: (Int, Int, TileT) => Unit): Unit = iToForeach(xRowStart(y), xRowEnd(y), xStep)(x => f(x, y, getTile(x, y)))
  def rowTilesXYForAll(y: Int)(f: (Int, Int) => Unit): Unit = iToForeach(xRowStart(y), xRowEnd(y), xStep)(x => f(x, y))
  def rowTilesCoodForAll(y: Int)(f: Cood => Unit): Unit = iToForeach(xRowStart(y), xRowEnd(y), xStep)(x => f(x cc y))

  def rowTilesIForAll(y: Int)(f: (TileT, Int) => Unit): Unit =
  { var x = xRowStart(y)
    val xe = xRowEnd(y)
    var i = 0
    while(x <= xe)
    { f(getTile(x, y), i)
      x += xStep
      i += 1
    }
  }

  def rowTileArr(y: Int)(implicit ct: ClassTag[TileT]): ArrOld[TileT] =
  { val array: Array[TileT] = new Array(rowTileNum(y))
    rowTilesIForAll(y)((t, i) => array(i) = t)
    array.toArrOld
  }

  def fRow[B](y: Int, f: (Int, Int, ArrOld[TileT]) => B): B = f(y, xRowStart(y), ???)

  def rowsStr(len: Int = 3)(implicit ct: ClassTag[TileT]): String =
  {
    val xm = xMin
    val strs: ArrOld[String] = rowsMapAll{ y =>
      xRowStart(y).toString + ((xRowStart(y) - xm) * 2).spaces + rowTileArr(y).toStrsFold((len - 3).min(0).spaces, _.toString.lengthFix(len))
    }
    "TGrid\n" + strs.encurly
  }

  def sideCoodsAll: Coods =
  { val acc: CoodsBuff = Coods.buff(numTile * 2)
    tilesCoodForAll(tc =>  acc.grows(HexGrid.sideCoodsOfTile(tc)))
    acc.toArr
  }

  def sideCoodLinesAll: CoodLines = sideCoodsAll.pMap(sideCoodLine)

  def sideLinesAll(scale: Double, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z): Line2s =
     sideCoodLinesAll.toLine2s(cood => (coodToVec2(cood) - mapOffset) * scale - displayOffset)

  def sideDrawsAll(scale: Double, mapOffset: Vec2 = cen, displayOffset: Vec2 = Vec2Z)(lineWidth: Double, colour: Colour = Black): ArrOld[LineDraw] = {
    val res1 = sideLinesAll(scale, mapOffset, displayOffset)
    res1.mapArrSeq(_.draw(lineWidth, colour))
  }
}

object TGrid
{
  def rowMulti[TileT <: AnyRef, GridT <: TGrid[TileT]](yMin: Int, fac:(Array[TileT], Array[Int]) => GridT, inp: RowMulti[TileT]*)(
    implicit ct: ClassTag[TileT]): GridT = rowMultis[TileT, GridT](inp.toRefs, yMin: Int, fac)(ct)

  def rowMultis[TileT <: AnyRef, GridT <: TGrid[TileT]](inp: Refs[RowMulti[TileT]], yMin: Int, fac:(Array[TileT], Array[Int]) => GridT)(
    implicit ct: ClassTag[TileT]): GridT =
  {
    val len = inp.sumBy(_.multis.singlesLen)
    val tiles = new Array[TileT](len)
    /* The index Arr first value is the bottom row number followed by 2 values for each row. The index for the start of the row in the tile Arr
     * followed by the xCood for the start of the row. Hence its length is 2 * numRows + 1. */
    val indArr = new Array[Int](inp.length * 2 + 1)
    indArr(0) = yMin
    var count = 0
    var rowCount = 1
    inp.foreachReverse { rm =>
      indArr(rowCount) = count
      indArr(rowCount + 1) = rm.xStart
      rowCount += 2
      rm.multis.foreach(_.foreach{t => tiles(count) = t; count += 1 })
    }
    fac(tiles, indArr)
  }
}*/

class RowMulti[TileT <: AnyRef](val y: Int, val xStart: Int, val multis: RefsMulti[TileT])
{
  //def toPair(implicit ct: ClassTag[TileT]): (Int, Arr[TileT]) = (xStart, multis.flatSingles)
}

object RowMulti
{
  def apply[TileT <: AnyRef](y: Int, xStart: Int, multis: Multiple[TileT]*): RowMulti[TileT] = new RowMulti(y, xStart, multis.toRefs)
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
trait TileGrid[TileT <: TileOld, SideT <: TileSideOld]
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
  final def coodToInd(cood: Cood): Int = xyToInd(cood.x, cood.y)
  val yRatio: Double
  def xStep: Int
  def rowTileXStart(y: Int): Int
  def rowTileXEnd(y: Int): Int
  def tileNum: Int

  /** For each Tile's XY in part of a row. */
  def rowForeachTilesXY(y: Int, xStart: Int, xEnd: Int, f: (Int, Int) => Unit): Unit
  /** For each Tile's XY in the whole of the row. */
  final def rowForeachTilesXYAll(y: Int)(f: (Int, Int) => Unit): Unit = rowForeachTilesXY(y, rowTileXStart(y), rowTileXEnd(y), f)
  final def rowForeachTileAll(y: Int)(f: TileT => Unit): Unit = rowForeachTilesXYAll(y)((x, y) => f(getTile(x, y)))

  def tilesToMultiAll: ArrOld[TileRow[TileT#FromT]] = tileRowMapAll(tileRowClass)

  def tileRowClass(y: Int): TileRow[TileT#FromT] = TileRow(y, rowTileXStart(y), rowTileXEnd(y), tileRowToMulti(y))

  def tileRowToMulti(y: Int): ArrOld[Multiple[TileT#FromT]] =
   {
     val acc: Buff[Multiple[TileT#FromT]] = Buff()
     var subAcc: Int = 0
     var oValue: Option[TileT#FromT] = None
     rowForeachTileAll(y){ tile =>
       val newT = tile.fromT
       oValue match
       {
         case None => { subAcc = 1; oValue = Some(newT) }
         case Some(sv) if sv == newT => subAcc += 1
         case Some(sv) => { acc += Multiple(sv, subAcc); subAcc = 1; oValue = Some(newT) }
       }
     }
     oValue match
     {
       case Some(sv) => acc += Multiple (sv, subAcc)
       case None =>
     }
     acc.toArr
   }

  final def setTiles[A](bottomLeft: Cood, topRight: Cood, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit =
    setTiles(bottomLeft.x, topRight.y, bottomLeft.y, topRight.y, tileValue)(f)
  def setTiles[A](xFrom: Int, xTo: Int, yFrom: Int, yTo: Int, tileValue: A)(implicit f: (Int, Int, A) => TileT): Unit
  /** Throws exception if Cood is not a valid Tile coordinate */
  def coodIsTile(x: Int, y: Int): Unit
  
  final def tileDestinguishColour(tileCood: Cood): Colour = tileDestinguish(tileCood, Red, Blue, Green, Orange)
  def tileDestinguish[A](cood: Cood, v1: A, v2: A, v3: A, v4: A): A  
  
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

  /** Maps each tile row to an Arr[A] */
  def tileRowMapAll[A](f: Int => A)(implicit ct: ClassTag[A]): ArrOld[A] =
  { val array: Array[A] = new Array(arrLen)
    var count = 0
    foreachTileRowAll{y => array(count) = f(y); count += 1 }
    array.toArrOld
  }
  
  /** Map all Tiles to Array[B] with function. */
  final def tilesMapAll[B: ClassTag](f: TileT => B): ArrOld[B] =
  {
    val acc: ArrayBuffer[B] = new ArrayBuffer(0)
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: B = f(tile)
      acc += newRes
    }
    acc.toArr
  }
  
  /** Map all Tiles to an Array with function and flatten into Single Array. */
  def tilesFlatMapAllOld[R: ClassTag](f: TileT => ArrOld[R]): ArrOld[R] =
  {0
    val acc: ArrayBuffer[R] = new ArrayBuffer(0)
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: ArrOld[R] = f(tile)
      acc ++= newRes
    }
    acc.toArr
  }

  def tilesFlatMapAll[B, BB <: ArrImut[B]](f: TileT => BB)(implicit build: ArrBuild[B, BB]): BB =
  {
    val acc = build.buffNew()
    foreachTilesCoodAll{ tileCood =>
      val tile = getTile(tileCood)
      val newRes: BB = f(tile)
      build.buffGrowArr(acc, newRes)
    }
    build.buffToArr(acc)
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

  final def tilesMapOptionAll[A: ClassTag](f: TileT => Option[A]): ArrOld[A] =
  { var acc: Buff[A] = Buff()
    foreachTileAll(t => acc = f(t).fold(acc)(acc.+= _))
    acc.toArr
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
  
  final def tilesOptionDispAll(f: TileT => Option[GraphicElem]): GraphicElemsOld =
  {
    val acc: Buff[GraphicElem] = Buff()
    foreachTileAll(t => f(t) match
      {
      case None =>
      case Some(g) => acc += g
      })
    acc.toArr
  }
  
  final def tilesOptionFlattenDispAll[A](f1: TileT => Option[A])(f2: (TileT, A) => GraphicElemsOld): GraphicElemsOld =
  {
    val acc: Buff[GraphicElem] = Buff()
    foreachTileAll(t => f1(t) match
      {
      case None =>
      case Some(a) => acc ++= f2(t, a)
      })    
    acc.toArr
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
  
  def foreachSideCoodPDMapAll[A, M <: ArrProdHomo[A]](f: Cood => A)(implicit ev: ArrProdHomoPersist[A, M]): M =
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
  def tilesDisplayFoldAll(f: TileT => GraphicElemsOld): GraphicElemsOld = tilesFoldAll[GraphicElemsOld](f, (acc, pair) => acc ++ pair)(ArrOld())
  def tileCoodsDisplayFoldAll(f: Cood => GraphicElemsOld): GraphicElemsOld = tileCoodsFoldAll[GraphicElemsOld](f, (acc, pair) => acc ++ pair)(ArrOld())
  
  /** Warning implementations need modification. */   
  def adjTileCoodsOfTile(tileCood: Cood): Coods    
}
