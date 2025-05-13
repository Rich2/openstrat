/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDung
import prid.psq.*, MultExt.*

/** Dungeon game scenario. */
trait DungeonScen
{ implicit def gSys: SqGridSys
  def terrs: SqCenLayer[DungTerr]
  def characs: SqCenOptLayer[CharacState]
  def posn(charac: Character, r: Int, c: Int, face: SqStep): Unit = characs.unsafeSetSome(r, c, CharacState(charac, face))
}

/** Scenario 1 example for Dungeon game. */
object Dungeon1 extends DungeonScen
{ implicit val gSys: SqGrid = SqGrid(4, 26, 2, 46)
  val terrs: SqCenLayer[DungTerr] = gSys.newSqCenDGrid[DungTerr](Wall)
  terrs.setColumn(22, 8,  Open * 2)
  terrs.setTerrPath(6, 4, Open, SqRt * 11, SqUp * 4, SqLt * 5, SqUp * 3, SqRt * 7, SqDn * 7)
  terrs.setRect(18, 24, 16, 36, Open)

  val characs: SqCenOptLayer[CharacState] = gSys.newSCenOptDGrider[CharacState]
  posn(CharacA, 8, 22, SqUp)
  posn(CharacB, 24, 18, SqRt)
  posn(CharacY, 24, 22, SqUR)
  posn(CharacZ, 12, 18, SqLt)
}