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
  lunits.prepends(Squad(Britain), 2 rr 30, 4 rr 32, 6 rr 46, 10 rr 38, 10 rr 46, 14 rr 46)
  lunits.prepends(Squad(Germany), 6 rr 18, 10 rr 18, 10 rr 6)

  /*val b1 = placeSquad(Britain, 30, 2)
  b1.move(26 cc 2, 22 cc 2)
  val b2 = placeSquad(Britain, 32, 4)
  b2.move(28 cc 4, 24 cc 4, 20 cc 4)
  val b3 = placeSquad(Britain, 46, 6)
  b3.move(42 cc 6)
  val b4 = placeSquad(Britain, 38, 10)
  b4.fire(18, 6)
  val b5 = placeSquad(Britain, 46, 10)
  val b6 = placeSquad(Britain, 46, 14)
  b6.move(42 cc 14, 38 cc 14, 36 cc 12)

  val g1 = placeSquad(Germany, 18, 6)
  g1.fire(32, 4)
  val g2 = placeSquad(Germany, 18, 10)
  g2.fire(32, 4)
  val g3 = placeSquad(Germany, 6, 10)
  g3.move(8 cc 8, 12 cc 8, 16 cc 8)*/
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