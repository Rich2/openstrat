/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._

case class Node(cood: Cood, gCost: Int, hCost: Int, parent: Cood)
{
  def fCost = gCost + hCost
}

class ZugGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[ZugTile, ZugSide](xTileMin, xTileMax, yTileMin, yTileMax)
{   
   def placeSquads(triples: (Polity, Int, Int) *): Unit = triples.foreach {tr =>
     val x = tr._2
     val y = tr._3
     val sd = Squad(tr._1, x, y)     
     val tile = getTile(x, y)
     tile.lunits ::=  sd //:: tile.lunits
   }
   
   def getHCost(startCood: Cood, endCood: Cood): Int =
   { val diff = endCood - startCood
     val x: Int = diff.x.abs
     val y: Int = diff.y.abs
     y - x match
     {
       case 0 => x / 2
       case n if n > 0 => y / 2
       case n if n %% 4 == 0 => y / 2 - n / 4
       case n => y / 2 - n /4 + 1
     }
   }
   def findPath(startCood: Cood, endCood: Cood): Option[List[Cood]] =
   {
     
     var open: List[Node] = Node(startCood, 0, getHCost(startCood,endCood), startCood) :: Nil
     var closed: List[Node] = Nil
     None
   }
}

object Zug1 extends ZugGrid(4, 48, 2, 14)
{ fTilesSetAll(Plain)
  fSidesSetAll(false)
  fSetSides(true, 35 -> 11, 36 -> 10, 37 -> 9)
  import Zug1.{setRow => gs}
  gs(yRow = 12, xStart = 4, WheatField * 2)
  gs(10, 6, WheatField, Plain * 7, WoodBuilding)
  gs(8, 4, WheatField * 2, StoneBuilding * 2, WheatField * 2, Lake)
  gs(6, 6, WheatField, Plain * 4, Lake)
  gs(4, 4, WheatField * 2)
  gs(2, 6, WheatField)
  //fSetSide(30, 11, true)
  placeSquads((Germany,18, 6), (Germany, 30, 6), (Britain, 22, 10), (Britain, 30, 10))
}