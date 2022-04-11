/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDung
import prid._, psq._

//case class CharacPosn(charac: Character, y: Int, c: Int, facing: SqStep)

trait DungeonScen
{ implicit def grid: SqGrid
  def terrs: SqCenDGrid[DungTerr]
  def characs: SqCenOptDGrid[Character]
  def posn(charac: Character, y: Int, c: Int, face: SqDirn): Unit = ???// characs.mutSetSome(y, c, CharacPosn(charac, y, c, face))
}

object Dungeon1 extends DungeonScen
{ implicit val grid: SqGrid = SqGrid(4, 26, 2, 46)
  val terrs: SqCenDGrid[DungTerr] = grid.newSqCenDGrid[DungTerr](Wall)
  terrs.setColumn(22, 8,  Open * 2)
  terrs.setTerrPath(6, 4, Open, SqRt * 11, SqUp * 4, SqLt * 5, SqUp * 3, SqRt * 7, SqDn * 7)
  terrs.setRect(18, 24, 16, 36, Open)

  val characs = grid.newSCenOptDGrider[Character]
//  posn(CharacA, 8, 22, SFaceUp)
//  posn(CharacB, 24, 18, SFaceRt)
//  posn(CharacY, 24, 22, SFaceUR)
//  posn(CharacZ, 12, 18, SFaceLt)
}