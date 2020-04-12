/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pZug
import pGrid._

object Zug1Old extends ZugGridOld(4, 48, 2, 14, 0)
{
  val wall1 = Coods(36 cc 14, 35 cc 13, 34 cc 12, 35 cc 11, 36 cc 10) ++ hexSidesHorrOld(9, 37, 47)
  setSideCollection(wall1, true)
  import Zug1Old.{setRow => gs}
  gs(yRow = 12, xStart = 4, WheatField * 2)
  gs(10, 6, WheatField, Plain * 2, StoneBuilding, Plain * 4, WoodBuilding)
  gs(8, 4, WheatField * 3, StoneBuilding * 1, WheatField * 2, Lake)
  gs(6, 6, WheatField, Plain * 2, StoneBuilding, Plain, Lake)
  gs(4, 4, WheatField * 2)
  gs(2, 6, WheatField)
}