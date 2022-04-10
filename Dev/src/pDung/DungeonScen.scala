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
  val terrs = grid.newTileArr[DungTerr](Wall)
  //terrs.setColumn(22, 8,  Open * 2)
  //terrs.setTerrPath(6 rr 4, Open, Rt * 11, Up * 4, Lt * 5, Up * 3, Rt * 7, Dn * 7)
  //terrs.sqGridSetRect(18, 24, 16, 36, Open)

  val characs = grid.newSCenOptDGrider[CharacPosn]
//  posn(CharacA, 8, 22, SFaceUp)
//  posn(CharacB, 24, 18, SFaceRt)
//  posn(CharacY, 24, 22, SFaceUR)
//  posn(CharacZ, 12, 18, SFaceLt)
}