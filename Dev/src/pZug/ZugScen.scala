package ostrat
package pZug
import pGrid._

trait ZugScen
{ implicit def grid: HexGridReg
  def terrs: TilesRef[ZugTerr]
  def sTerrs: SideBooleans
  def lunits: TilesRef[List[Squad]]
}

object Zug1 extends ZugScen
{
  implicit val grid = HexGridReg(2, 14, 4, 48)

  val terrs = grid.newTilesRefInit[ZugTerr](Plain)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  gs(yRow = 12, cStart = 4, WheatField * 2)
  gs(10, 6, WheatField, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding)
  gs(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake)
  gs(6, 6, WheatField, Plain * 2, StoneBuilding, Plain, Lake)
  gs(4, 4, WheatField * 2)
  gs(2, 6, WheatField)

  val sTerrs: SideBooleans = grid.newSidesBoolean
  val wall1 = Roords(14 rr 36, 13 rr 35, 12 rr 34, 11 rr 35, 10 rr 36) ++ grid.SidesHorr(9, 37, 47)
  sTerrs.gridSetTrues(wall1)

  val lunits: TilesRef[List[Squad]] = grid.newTilesRefInit[List[Squad]](Nil)
  lunits.prepend(2, 30, Squad(Britain, Move(2 rr 26, 2 rr 22)))
  lunits.prepend(10, 38, Squad(Britain, Fire(6 rr 18)))
  lunits.prepend(4, 32, Squad(Britain, Move(4 rr 28, 4 rr 24, 4 rr 20)))
  lunits.prepend(6, 46, Squad(Britain, Move(6 rr 42)))
  lunits.prepend(14 rr 46, Squad(Britain, Move(14 rr 42, 14 rr 38, 12 rr 36)))
  lunits.prepends(Squad(Britain), 10 rr 46)
  lunits.prepend(6, 18, Squad(Germany, Fire(4 rr 32)))
  lunits.prepend(10, 18, Squad(Germany, Fire(4 rr 32)))
  lunits.prepend(6, 10, Squad(Germany, Move(8 rr 8, 8 rr 12, 8 rr 16)))
}

object Zug2 extends ZugScen
{
  override implicit def grid: HexGridReg = HexGridReg(2, 10, 4, 38)
  val terrs = grid.newTilesRefInit[ZugTerr](Lake)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  gs(10, 6, Plain * 3, Lake * 3, Plain * 3)
  gs(8, 4 , Plain * 4, Lake * 2, Plain * 3 )
  gs(6, 6 , Plain * 4, Lake, Plain * 4)
  gs(4, 4, Plain * 4, Lake, Hill, Plain * 3)
  gs(2, 6, Plain * 2, Lake * 2, Hill, Plain)
  val sTerrs: SideBooleans = grid.newSidesBoolean
  val lunits = grid.newTilesRefInit[List[Squad]](Nil)
}

object Zug3  extends ZugScen
{
  override implicit def grid: HexGridReg = HexGridReg(2, 10, 4, 38)
  val terrs = grid.newTilesRefInit[ZugTerr](Plain)
  val sTerrs: SideBooleans = grid.newSidesBoolean
  sTerrs.gridSetTrues(grid.SidesHorr(7, 5, 37))

 val lunits = grid.newTilesRefInit[List[Squad]](Nil)
  lunits.prepends(Squad(Germany), 6 rr 18, 6 rr 30)
  lunits.prepends(Squad( France), 10 rr 14, 10 rr 22, 10 rr 30)
}