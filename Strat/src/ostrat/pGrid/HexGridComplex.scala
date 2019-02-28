/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid

/** A Hex tile own the right sides, upRight, Right and DownRight. It owns the Up, UpRight and DownRight Vertices numbers 0, 1 and 2. */
abstract class HexGridComplex[TileT <: Tile, SideT <: GridElem](val xTileMin: Int, val xTileMax: Int, val yTileMin: Int, val yTileMax: Int)
(implicit val evTile: IsType[TileT], val evSide: IsType[SideT]) extends TileGridComplex[TileT, SideT] with HexGrid[TileT]   
{
  override def xArrLen: Int = xTileMax / 2 - xTileMin / 2 + 2 //+1 for zeroth tile, +1 for right side
  override val yArrLen: Int = yTileMax - yTileMin + 3//+ 1 for lowersides +1 for zeroth tile, + 1 for upper side(s)
  override val arr: Array[AnyRef] = new Array[AnyRef](arrLen)
  override def vertCoodsOfTile(tileCood: Cood): Coods = HexGrid.vertCoodsOfTile(tileCood)
  override def sideCoodsOfTile(tileCood: Cood): Coods = HexGrid.sideCoodsOfTile(tileCood)   
  override def xStep: Int = 4   
  override def xToInd(x: Int): Int = x / 2 - xTileMin / 2
  override def yToInd(y: Int): Int = (y  - yTileMin + 1)
     
  //def fTiles[D](f: (TileT, D) => Unit, data: (Int, Int, D)*) = data.foreach(tr => f(getTile(tr._1, tr._2), tr._3))      
   
  def isTile(x: Int, y: Int): Boolean = getTile(x, y) != null   
   
  override def vertCoodLineOfSide(x: Int, y: Int): CoodLine = HexGrid.vertCoodsOfSide(x, y)
  
  override def coodIsTile(x: Int, y: Int): Unit = Unit match
  { case _ if x %% 4 == 0 & y %% 4 == 0 =>
    case _ if x %% 4 == 2 & y %% 4 == 2 =>
    case _ => excep(x.toString.commaAppend(y.toString) -- "is an invalid Hex tile coordinate")   
  }
  
  override def coodIsSide(x: Int, y: Int): Unit = Unit match
  { case _ if x %% 4 == 0 & y %% 4 == 2 =>
    case _ if x %% 4 == 2 & y %% 4 == 0 =>
    case _ if x.isOdd & y.isOdd =>   
    case _ => excep(x.toString.commaAppend(y.toString) -- "is an invalid Hexside tile coordinate")   
  }
  
  override def sidesTileCoods(x: Int, y: Int): (Cood, Cood) = Unit match
  { case _ if (x %% 4 == 0 & y %% 4 == 2) | (x %% 4 == 2 & y %% 4 == 0)  => (Cood(x -2, y), Cood(x + 2, y))
    case _ if (x %% 4 == 1 & y %% 4 == 1) | (x %% 4 == 3 & y %% 4 == 3) =>  (Cood(x - 1, y - 1), Cood(x + 1, y + 1))
    case _ if (x %% 4 == 1 & y %% 4 == 3) | (x %% 4 == 3 & y %% 4 == 1) => (Cood(x - 1, y + 1), Cood(x + 1, y - 1))
    case _ => excep("Invalid Hex Side Coordinate".commaAppend(x.toString, y.toString))
  }
   
  /** Warning needs modification. */
  override def adjTileCoodsOfTile(tileCood: Cood): Coods = HexGrid.adjTileCoodsOfTile(tileCood)
   
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of 
   *  departure and the tile of arrival. */
  def getHCost(startCood: Cood, endCood: Cood): Int =
  { val diff = endCood - startCood
    val x: Int = diff.x.abs
    val y: Int = diff.y.abs
     
    y - x match
    { case 0 => x 
      case n if n > 0 => y 
      case n if n %% 4 == 0 => y - n / 2 //Subtract because n is negative, y being greater than x
      case n => y - n / 2 + 2
    }
  }
}
