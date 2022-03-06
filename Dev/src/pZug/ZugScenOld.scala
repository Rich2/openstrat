/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pZug
import pGrid._, proord._

/** Old ZugScen using pGrid. */
trait ZugScenOld
{ implicit def grid: HexGridRegOld
  def terrs: TilesArr[ZugTerr]
  def sTerrs: SideBooleans
  def lunits: TilesArr[List[SquadOld]]
  def addUnit(polity: Polity, roord: Roord, action: ActionOld = NoActionOld): Unit = lunits.prepend(roord, SquadOld(polity, roord, action))
  def addYC(polity: Polity, y: Int, c: Int, action: ActionOld = NoActionOld): Unit = lunits.prepend(y rr c, SquadOld(polity, y rr c, action))
  def addUnits(polity: Polity, roords: Roord*): Unit = roords.foreach{r => lunits.prepend(r, SquadOld(polity, r))}
}

object Zug1Old extends ZugScenOld
{
  implicit val grid = HexGridRegOld(2, 14, 4, 48)

  val terrs = grid.newTileArr[ZugTerr](Plain)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  gs(yRow = 12, cStart = 4, WheatField * 2)
  gs(10, 6, WheatField, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding)
  gs(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake)
  gs(6, 6, WheatField, Plain * 2, StoneBuilding, Plain, Lake)
  gs(4, 4, WheatField * 2)
  gs(2, 6, WheatField)

  val sTerrs: SideBooleans = grid.newSideBooleans
  val wall1: Roords = Roords(14 rr 36, 13 rr 35, 12 rr 34, 11 rr 35, 10 rr 36) ++ grid.SidesHorr(9, 37, 47)
  sTerrs.gridSetTrues(wall1)

  val lunits: TilesArr[List[SquadOld]] = grid.newTileArr[List[SquadOld]](Nil)
  lunits.prepend(2, 30, SquadOld(Britain, 2 rr 30, MoveOld(2 rr 26, 2 rr 22)))
  lunits.prepend(10, 38, SquadOld(Britain, 10 rr 38, FireOld(6 rr 18)))
  lunits.prepend(4, 32, SquadOld(Britain, 4 rr 32, MoveOld(4 rr 28, 4 rr 24, 4 rr 20)))
  lunits.prepend(6, 46, SquadOld(Britain, 6 rr 46, MoveOld(6 rr 42)))
  lunits.prepend(14 rr 46, SquadOld(Britain, 14 rr 46, MoveOld(14 rr 42, 14 rr 38, 12 rr 36)))
  lunits.prepends(SquadOld(Britain, 10 rr 46), 10 rr 46)
  lunits.prepend(6, 18, SquadOld(Germany, 6 rr 18, FireOld(4 rr 32)))
  lunits.prepend(10, 18, SquadOld(Germany, 10 rr 18, FireOld(4 rr 32)))
  lunits.prepend(6, 10, SquadOld(Germany, 6 rr 10, MoveOld(8 rr 8, 8 rr 12, 8 rr 16)))
}

object Zug2Old extends ZugScenOld
{
  override implicit def grid: HexGridRegOld = HexGridRegOld(2, 10, 4, 38)
  val terrs = grid.newTileArr[ZugTerr](Lake)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  gs(10, 6, Plain * 3, Lake * 3, Plain * 3)
  gs(8, 4 , Plain * 4, Lake * 2, Plain * 3 )
  gs(6, 6 , Plain * 4, Lake, Plain * 4)
  gs(4, 4, Plain * 4, Lake, Hill, Plain * 3)
  gs(2, 6, Plain * 2, Lake * 2, Hill, Plain)
  val sTerrs: SideBooleans = grid.newSideBooleans
  val lunits = grid.newTileArr[List[SquadOld]](Nil)
}

object Zug3Old  extends ZugScenOld
{
  override implicit def grid: HexGridRegOld = HexGridRegOld(2, 10, 4, 38)
  val terrs = grid.newTileArr[ZugTerr](Plain)
  val sTerrs: SideBooleans = grid.newSideBooleans
  sTerrs.gridSetTrues(grid.SidesHorr(7, 5, 37))

 val lunits = grid.newTileArr[List[SquadOld]](Nil)
  addUnits(Germany, 6 rr 18, 6 rr 30)
  addUnits(France, 10 rr 14, 10 rr 22, 10 rr 30)
}