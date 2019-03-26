/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pDung
import pGrid._

/** Tile is 0.5m square. */
class DungeonGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends SquareGrid[DTile, SideBare](xTileMin, xTileMax,
    yTileMin, yTileMax)
{
   def posn(charac: Character, x: Int, y: Int, facing: SFace): Unit =
   { getTile(x, y).charac = Opt(charac)
     charac.setCood(x, y)
     charac.facing = facing
   }
   
   def copy: DungeonGrid =
   {
     val newGrid = new DungeonGrid(xTileMin, xTileMax, yTileMin, yTileMax)
     newGrid
   }
   
   def resolveTurn(actSeqs: List[ActionSeq]): DungeonGrid =
   {
     
     this
   }
   
   def allWall() = setTilesAll(Wall)
   def allOpen() = setTilesAll(Open)
}

