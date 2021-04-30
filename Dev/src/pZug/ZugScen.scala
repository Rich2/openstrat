/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import prid._

trait ZugScen extends HexGridScen
{ /** tile terrain. */
  def terrs: HCenArr[ZugTerr]
}

trait ZugScenStart extends ZugScen
{ override def turn: Int = 0
}

/** ZugFuhrer scenario 1. */
object Zug1 extends ZugScenStart
{ override implicit val grid: HGrid = HGridReg(2, 14, 4, 48)
  val terrs: HCenArr[ZugTerr] = grid.newTileArr[ZugTerr](Plain)
  def tr(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  tr(yRow = 12, cStart = 4, WheatField * 2)
  tr(10, 6, WheatField, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding)
  tr(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake)
  tr(6, 6, WheatField, Plain * 2, StoneBuilding, Plain, Lake)
  tr(4, 4, WheatField * 2)
  tr(2, 6, WheatField)
  //val sTerrs: HSideBooleans = ??? //grid.newSideBooleans
  val lunits: HCenArr[List[Squad]] = grid.newTileArr[List[Squad]](Nil)
}

/** ZugFuhrer scenario 2. */
object Zug2 extends ZugScenStart
{
  override implicit val grid: HGrid = HGridReg(2, 10, 4, 38)
  val terrs = grid.newTileArr[ZugTerr](Lake)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  gs(10, 6, Plain * 3, Lake * 3, Plain * 3)
  gs(8, 4 , Plain * 4, Lake * 2, Plain * 3 )
  gs(6, 6 , Plain * 4, Lake, Plain * 4)
  gs(4, 4, Plain * 4, Lake, Hill, Plain * 3)
  gs(2, 6, Plain * 2, Lake * 2, Hill, Plain)
  //val sTerrs: SideBooleans = grid.newSideBooleans
  val lunits: HCenArr[List[Squad]] = grid.newTileArr[List[Squad]](Nil)
}

/** ZugFuhrer scenario 3. */
object Zug3  extends ZugScenStart
{
  override implicit val grid: HGrid = HGridReg(2, 10, 4, 38)
  val terrs = grid.newTileArr[ZugTerr](Plain)
  //val sTerrs: SideBooleans = grid.newSideBooleans
  //sTerrs.gridSetTrues(grid.SidesHorr(7, 5, 37))

  val lunits = grid.newTileArr[List[Squad]](Nil)
  //addUnits(Germany, 6 rr 18, 6 rr 30)
  //addUnits(France, 10 rr 14, 10 rr 22, 10 rr 30)
}