/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pDung
import pGrid._

object Dungeon1Old extends DungeonGridOld(2, 48, 2, 30, 0)
{
  import SquareGridOld._
  allWall
  setTerrPath(Open, 4 cc 6, Rt * 11, Up * 4, Lt * 5, Up * 3, Rt * 7, Dn * 7)
  setColumn(22, 8,  Open * 2)
  setTileRect(16, 36, 18, 26, Open)
  posn(CharacA, 22, 8, SFaceUp)
  posn(CharacB, 18, 24, SFaceRt)
  posn(CharacY, 22, 24, SFaceUR)
  posn(CharacZ, 18, 12, SFaceLt)
}

object Dungeon2Old extends DungeonGridOld(2, 10, 2, 10, 0)
{
  allOpen
}

trait DungeonScen
{ implicit def grid: TileGrid
  def terrs: Refs[DungTerr]
}

object Dungeon1 extends DungeonScen
{import SquareGrid._
  implicit val grid = SquareGrid(2, 30, 2, 48)
  val terrs = grid.newRefsSet[DungTerr](Wall)
  terrs.setColumn(22, 8,  Open * 2)
  terrs.setTerrPath(6 rr 4, Open, Rt * 11, Up * 4, Lt * 5, Up * 3, Rt * 7, Dn * 7)
  terrs.sqGridSetRect(18, 26, 16, 36, Open)
}

