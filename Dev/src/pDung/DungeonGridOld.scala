/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDung
import pGrid._

/** Tile is 0.5m square. */
class DungeonGridOld(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int, turnNum: Int) extends SquareGridOld[DTileOld, SideOldBare](xTileMin, xTileMax,
    yTileMin, yTileMax, turnNum)
{
   def posn(charac: Character, x: Int, y: Int, facing: SFace): Unit =
   { getTile(x, y).charac = OptRef(charac)
     charac.setCood(x, y)
     charac.facing = facing
   }
   
   def copy: DungeonGridOld =
   {
     val newGrid = new DungeonGridOld(xTileMin, xTileMax, yTileMin, yTileMax, turnNum)
     newGrid
   }
   
   def resolveTurn(actSeqs: List[ActionSeq]): DungeonGridOld =
   {
     
     this
   }
   
   def allWall() = setTilesAll(Wall)
   def allOpen() = setTilesAll(Open)
}

