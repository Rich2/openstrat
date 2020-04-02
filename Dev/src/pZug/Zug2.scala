package ostrat
package pZug
import pGrid._

object Zug2 extends ZugScen
{
  override implicit def grid: HexGridReg = HexGridReg(2, 10, 4, 38)
  val terrs = grid.newRefsSet[ZugTerr](Lake)
  def gs(yRow: Int, cStart: Int, tileValues: Multiple[ZugTerr]*) = terrs.setRow(yRow, cStart, tileValues :_*)(grid)
  gs(10, 6, Plain * 3, Lake * 3, Plain * 3)
  gs(8, 4 , Plain * 4, Lake * 2, Plain * 3 )
  gs(6, 6 , Plain * 4, Lake, Plain * 4)
  gs(4, 4, Plain * 4, Lake, Hill, Plain * 3)
  gs(2, 6, Plain * 2, Lake * 2, Hill, Plain)
  val lunits = grid.newRefsSet[List[Squad]](Nil)
}