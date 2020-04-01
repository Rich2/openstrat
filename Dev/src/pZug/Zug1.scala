package ostrat
package pZug
import pGrid._

object Zug1 //extends ZugGridOld(4, 48, 2, 14, 0)
{
  val grid = HexGridReg(2, 14, 4, 48)
  val wall1 = Coods(36 cc 14, 35 cc 13, 34 cc 12, 35 cc 11, 36 cc 10) ++ hexSidesHorrOld(9, 37, 47)
//  setSideCollection(wall1, true)
  import Zug1Old.{setRow => gs}
//  gs(yRow = 12, xStart = 4, WheatField * 2)
//  gs(10, 6, WheatField, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding)
//  gs(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake)
//  gs(6, 6, WheatField, Plain * 2, StoneBuilding, Plain, Lake)
//  gs(4, 4, WheatField * 2)
//  gs(2, 6, WheatField)
//  val b1 = placeSquad(Britain, 30, 2)
//  b1.move(26 cc 2, 22 cc 2)
//  val b2 = placeSquad(Britain, 32, 4)
//  b2.move(28 cc 4, 24 cc 4, 20 cc 4)
//  val b3 = placeSquad(Britain, 46, 6)
//  b3.move(42 cc 6)
//  val b4 = placeSquad(Britain, 38, 10)
//  b4.fire(18, 6)
//  val b5 = placeSquad(Britain, 46, 10)
//  val b6 = placeSquad(Britain, 46, 14)
//  b6.move(42 cc 14, 38 cc 14, 36 cc 12)
//
//  val g1 = placeSquad(Germany, 18, 6)
//  g1.fire(32, 4)
//  val g2 = placeSquad(Germany, 18, 10)
//  g2.fire(32, 4)
//  val g3 = placeSquad(Germany, 6, 10)
//  g3.move(8 cc 8, 12 cc 8, 16 cc 8)
}
