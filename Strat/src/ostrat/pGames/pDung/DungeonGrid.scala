/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pDung
import pGrid._

class DungeonGrid extends SquareGridComplex[DTile, SideBare](2, 48, 2, 30)
{
   def posn(charac: Character, x: Int, y: Int, facing: SFace): Unit =
   { getTile(x, y).charac = Opt(charac)
     charac.setCood(x, y)
     charac.facing = facing
   }
}

object Dungeon1 extends DungeonGrid 
{
   fTilesSetAll(Wall)
   import SquareGridComplex._
   setTerrPath(Open, 4 cc 6, Rt * 11, Up * 4, Lt * 5, Up * 3, Rt * 7, Dn * 7)
   setColumn(22, 8,  Open * 2)
   setRectangle(16 cc 18, 36 cc 26, Open)
   posn(CharacA, 22, 8, SFaceUp)
   posn(CharacB, 18, 24, SFaceRt)
   posn(CharacY, 22, 24, SFaceUR)
   posn(CharacZ, 18, 12, SFaceLt)
}