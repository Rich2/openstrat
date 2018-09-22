/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import pGrid._, pSq._

class DungGrid extends SquareGrid[DTile, SideBare](2, 48, 2, 30)
{
   def posn(charac: Character, x: Int, y: Int, facing: Facing): Unit =
   { getTile(x, y).charac = Some(charac)
     charac.facing = facing
   }
}

object Dungeon1 extends DungGrid 
{
   fTilesSetAll(Wall)
   import SquareGrid._
   setTerrPath(Open, 4 cc 6, Rt * 11, Up * 4, Lt * 5, Up * 3, Rt * 7, Dn * 7)
   setColumn(22, 8,  Open * 2)
   setRectangle(16 cc 18, 36 cc 26, Open)
   posn(CharacA, 22, 8, FaceUp)
   posn(CharacB, 18, 24, FaceRt)
   posn(CharacY, 22, 24, FaceUR)
   posn(CharacZ, 18, 12, FaceLt)
}