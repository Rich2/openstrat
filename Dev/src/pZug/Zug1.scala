package ostrat
package pZug
import pGrid._

trait ZugScen
{ implicit def grid: HexGridReg
  def terrs: Refs[ZugTerr]
  def lunits: Refs[List[Squad]]
}

object Zug1 extends ZugScen
{
  implicit val grid = HexGridReg(2, 14, 4, 48)
 // val wall1 = Coods(36 cc 14, 35 cc 13, 34 cc 12, 35 cc 11, 36 cc 10) ++ hexSidesHorrOld(9, 37, 47)
//  setSideCollection(wall1, true)
  val terrs = grid.newRefsSet[ZugTerr](Plain)
  //import terr.{setRow => gs}
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  gs(yRow = 12, cStart = 4, WheatField * 2)
  gs(10, 6, WheatField, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding)
  gs(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake)
  gs(6, 6, WheatField, Plain * 2, StoneBuilding, Plain, Lake)
  gs(4, 4, WheatField * 2)
  gs(2, 6, WheatField)
  val lunits = grid.newRefsSet[List[Squad]](Nil)
  lunits.gridPrepends(Squad(Britain), 2 rr 30, 4 rr 32, 6 rr 46, 10 rr 38, 10 rr 46, 14 rr 46)
  lunits.gridPrepends(Squad(Germany), 6 rr 18, 10 rr 18, 10 rr 6)
}
