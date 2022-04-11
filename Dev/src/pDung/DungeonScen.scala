/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDung
import prid._, psq._

case class CharacPosn(charac: Character, y: Int, c: Int, facing: SqStep)

trait DungeonScen
{ implicit def grid: SqGrid
  def terrs: SqCenDGrid[DungTerr]
  def characs: SqCenOptDGrid[CharacPosn]
  def posn(charac: Character, y: Int, c: Int, face: SqStep): Unit = ???// characs.mutSetSome(y, c, CharacPosn(charac, y, c, face))
}

object Dungeon1 extends DungeonScen
{ implicit val grid: SqGrid = SqGrid(4, 26, 2, 46)
  val terrs: SqCenDGrid[DungTerr] = grid.newTileArr[DungTerr](Wall)
  terrs.setColumn(22, 8,  Open * 2)
  terrs.setTerrPath(6, 4, Open, SqStepRt * 11, SqStepUp * 4, SqStepLt * 5, SqStepUp * 3, SqStepRt * 7, SqStepDn * 7)
  terrs.setRect(18, 24, 16, 36, Open)

  val characs = grid.newSCenOptDGrider[CharacPosn]
//  posn(CharacA, 8, 22, SFaceUp)
//  posn(CharacB, 24, 18, SFaceRt)
//  posn(CharacY, 24, 22, SFaceUR)
//  posn(CharacZ, 12, 18, SFaceLt)
}