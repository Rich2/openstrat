/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import reflect.ClassTag

/** A Hex tile own the right sides, upRight, Right and DownRight. It owns the Up, UpRight and DownRight Vertices numbers 0, 1 and 2. */
abstract class HexGridOld[TileT <: TileOld, SideT <: TileSideOld](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int, val turnNum: Int)
                                                                 (implicit val evTile: ClassTag[TileT], val evSide: ClassTag[SideT]) extends TileGridOld[TileT, SideT]// with HexGrid[TileT]
{
  //override val yRatio: Double = HexGrid.yRatio
  override val xRatio: Double = HexGrid.xRatio
  override def xArrLen: Int = (xTileMax - xTileMin) / 4 + 1 //+1 for zeroth tile
  override val yArrLen: Int = yTileMax - yTileMin + 3//+ 1 for lowersides +1 for zeroth tile, + 1 for upper side(s)
  override val arr: Array[TileT] = new Array[TileT](arrLen)
  override def vertCoodsOfTile(tileCood: Cood): Coods = HexGrid.vertCoodsOfTile(tileCood)
  override def sideCoodsOfTile(tileCood: Cood): Coods = HexGrid.sideCoodsOfTile(tileCood)
  override def xStep: Int = 4
  override def xSideMin: Int = xTileMin - 2
  override def xSideMax: Int = xTileMax + 2

  /** For each Tile's XY in part of a row. */
  final override def rowForeachTilesXY(y: Int, xStart: Int, xEnd: Int, f: (Int, Int) => Unit): Unit =
  {
    val xPt: Int = ife(y %% 4 == 0, 0, 2)
    val xFinalStart = rowTileXStart(y).max(xStart).roundUpTo(_ %% 4 == xPt)
    val xFinalEnd = rowTileXEnd(y).min(xEnd).roundDownTo(_ %% 4 == xPt)
    (xFinalStart to xFinalEnd by 4).foreach(x => f(x, y))
  }

  def isTile(x: Int, y: Int): Boolean = getTile(x, y) != null   
   
  override def vertCoodLineOfSide(x: Int, y: Int): CoodLine = HexGrid.sideCoodToCoodLine(x, y)
  
  override def coodIsTile(x: Int, y: Int): Unit = ifNotExcep(
    x %% 4 == 0 & y %% 4 == 0 | x %% 4 == 2 & y %% 4 == 2,
    x.commaInts(y) -- "is an invalid Hex tile coordinate")
  
  override def coodIsSide(x: Int, y: Int): Unit = ifNotExcep(
    (x %% 4 == 0 & y %% 4 == 2) | (x %% 4 == 2 & y %% 4 == 0) | (x.isOdd & y.isOdd),
    x.commaInts(y) -- "is an invalid Hexside tile coordinate")
  
  override def sidesTileCoods(x: Int, y: Int) = ife3(
    (x %% 4 == 0 & y %% 4 == 2) | (x %% 4 == 2 & y %% 4 == 0), (Cood(x -2, y), Cood(x + 2, y)),
    (x %% 4 == 1 & y %% 4 == 1) | (x %% 4 == 3 & y %% 4 == 3), (Cood(x - 1, y - 1), Cood(x + 1, y + 1)),
    (x %% 4 == 1 & y %% 4 == 3) | (x %% 4 == 3 & y %% 4 == 1), (Cood(x - 1, y + 1), Cood(x + 1, y - 1)),
    excep("Invalid Hex Side Coordinate".commaInts(x, y)))
    
  override def tileDestinguish[A](cood: Cood, v1: A, v2: A, v3: A, v4: A): A =  cood match
  { case Cood(x, y) if x %% 8 == 0 & y %% 4 == 0 => v1
    case Cood(x, y) if x %% 8 == 4 & y %% 4 == 0 => v2
    case Cood(x, y) if x %% 8 == 2 => v3
    case Cood(x, y) if x %% 8 == 6 => v4    
  }
   
  /** Warning needs modification. */
  override def adjTileCoodsOfTile(tileCood: Cood): Coods = HexGrid.adjTileCoodsOfTile(tileCood)
   
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of 
   *  departure and the tile of arrival. */
  def getHCost(startCood: Cood, endCood: Cood): Int =
  { val diff = endCood - startCood
    val x: Int = diff.c.abs
    val y: Int = diff.y.abs
     
    y - x match
    { case 0 => x 
      case n if n > 0 => y 
      case n if n %% 4 == 0 => y - n / 2 //Subtract because n is negative, y being greater than x
      case n => y - n / 2 + 2
    }
  }
}