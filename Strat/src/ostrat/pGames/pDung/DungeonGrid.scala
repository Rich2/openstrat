/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pDung
import pGrid._

/** Tile is 0.5m square. */
class DungeonGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int) extends SquareGrid[DTile, SideBare](xTileMin, xTileMax,
    yTileMin, yTileMax, turnNum)
{
   def posn(charac: Character, x: Int, y: Int, facing: SFace): Unit =
   { getTile(x, y).charac = OptRef(charac)
     charac.setCood(x, y)
     charac.facing = facing
   }
   
   def copy: DungeonGrid =
   {
     val newGrid = new DungeonGrid(xTileMin, xTileMax, yTileMin, yTileMax, turnNum)
     newGrid
   }
   
   def resolveTurn(actSeqs: List[ActionSeq]): DungeonGrid =
   {
     
     this
   }
   
   def allWall() = setTilesAll(Wall)
   def allOpen() = setTilesAll(Open)
}

