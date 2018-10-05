/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGames
package pZug
import pGrid._



class ZugGrid(xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int) extends HexGridReg[ZugTile, ZugSide](xTileMin, xTileMax, yTileMin, yTileMax)
{
  def placeSquads(triples: (Polity, Int, Int) *): Unit = triples.foreach {tr =>
    val x = tr._2
    val y = tr._3
    val sd = Squad(tr._1, x, y)     
    val tile = getTile(x, y)
    tile.lunits ::=  sd //:: tile.lunits
  }
   
  case class Node(val tile: ZugTile, var gCost: Int, var hCost: Int, var parent: Node)
  { def fCost = gCost + hCost
  }
  
  //object Node {def apply(tile: ZugTile, gCost: Int, hCost: Int, parent: Cood): Node  = new Node(tile, gCost, hCost, parent) }
   
   def findPath(startCood: Cood, endCood: Cood): Option[List[Cood]] =
   {     
     var open: List[Node] = Node(this.getTile(startCood), 0, getHCost(startCood, endCood), null) :: Nil
     var closed: List[Node] = Nil
     var found: Option[Node] = None
     while (open.nonEmpty & found == None)
     {
       val curr: Node = open.minBy(_.fCost)
       //if (curr.tile.cood == endCood) found = true
       open = open.filterNot(_ == curr)
       closed ::= curr
       val neighbs = this.tileNeighbours(curr.tile).filterNot(tile => closed.exists(_.tile == tile))
       neighbs.foreach
       {
         case ZugTile(_, _, Lake) =>
         case zt if closed.exists(_.tile == zt) =>
         case zt =>
         { val newGCost = 1 + 1 + curr.gCost
           
           open.find(_.tile == zt) match
           {
             case Some(node) if newGCost < node.gCost => { node.gCost = newGCost; node.parent = curr }
             case Some(node) =>
             case None => 
             {
               val newNode  = Node(zt, newGCost, getHCost(zt.cood, endCood), curr)
               open ::= newNode
               if (zt.cood == endCood) found = Some(newNode)
             }
           }
         }
       }       
     }
     found.map{endNode =>
       var acc: List[Cood] = Nil
       var curr: Node = endNode
       while (curr.parent != null) { acc ::= curr.tile.cood; curr = curr.parent  }
       acc
     }
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