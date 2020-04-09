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
  posn(CharacOldA, 22, 8, SFaceUp)
  posn(CharacOldB, 18, 24, SFaceRt)
  posn(CharacOldY, 22, 24, SFaceUR)
  posn(CharacOldZ, 18, 12, SFaceLt)
}

object Dungeon2Old extends DungeonGridOld(2, 10, 2, 10, 0)
{
  allOpen
}

case class CharacPosn(charac: Character, y: Int, c: Int, facing: SqFace)

trait DungeonScen
{ implicit def grid: TileGrid
  def terrs: Refs[DungTerr]
  def characs: OptRefs[CharacPosn]
  def posn(charac: Character, y: Int, c: Int, face: SqFace): Unit = characs.unsafeSetSome(grid.index(y, c), CharacPosn(charac, y, c, face))
}

object Dungeon1 extends DungeonScen
{import SquareGrid._
  implicit val grid = SquareGrid(4, 26, 2, 46)
  val terrs = grid.newRefsSet[DungTerr](Wall)
  terrs.setColumn(22, 8,  Open * 2)
  terrs.setTerrPath(6 rr 4, Open, Rt * 11, Up * 4, Lt * 5, Up * 3, Rt * 7, Dn * 7)
  terrs.sqGridSetRect(18, 24, 16, 36, Open)

  val characs = grid.newOptRefsOld[CharacPosn]
  posn(CharacA, 8, 22, SFaceUp)
  posn(CharacB, 24, 18, SFaceRt)
  posn(CharacY, 24, 22, SFaceUR)
  posn(CharacZ, 12, 18, SFaceLt)
}

