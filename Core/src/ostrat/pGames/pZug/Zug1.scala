/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames.pZug
import pGrid._

object Zug1 extends ZugGrid(4, 48, 2, 14)
{ 
  val wall1 = Coods(36 cc 14, 35 cc 13, 34 cc 12, 35 cc 11, 36 cc 10) ++ hSidesHorr(9, 37, 47)
  fSetSides(true, wall1)
  import Zug1.{setRow => gs}
  gs(yRow = 12, xStart = 4, WheatField * 2)
  gs(10, 6, WheatField, Plain * 7, WoodBuilding)
  gs(8, 4, WheatField * 2, StoneBuilding * 2, WheatField * 2, Lake)
  gs(6, 6, WheatField, Plain * 4, Lake)
  gs(4, 4, WheatField * 2)
  gs(2, 6, WheatField)  
  placeSquads((Germany, 18, 6), (Germany, 30, 6), (Britain, 22, 10), (Britain, 30, 10))
  val g1 = placeSquad(Germany, 38, 6)
  g1.move = List(40 cc 8, 42 cc 10) 
}