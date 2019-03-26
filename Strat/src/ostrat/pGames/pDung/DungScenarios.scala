/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pDung
import pGrid._

object Dungeon1 extends DungeonGrid(2, 48, 2, 30) 
{
  import SquareGrid._
  allWall
  setTerrPath(Open, 4 cc 6, Rt * 11, Up * 4, Lt * 5, Up * 3, Rt * 7, Dn * 7)
  setColumn(22, 8,  Open * 2)
  setTiles(16, 36, 18, 26, Open)
  posn(CharacA, 22, 8, SFaceUp)
  posn(CharacB, 18, 24, SFaceRt)
  posn(CharacY, 22, 24, SFaceUR)
  posn(CharacZ, 18, 12, SFaceLt)
}

object Dungeon2 extends DungeonGrid(2, 10, 2, 10)
{
  allOpen
}