/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._, reflect.ClassTag

class HexGridComplexReg[TileT <: Tile, SideT <: GridElem](xTileMin: Int, xTileMax: Int, yTileMin: Int, yTileMax: Int)(
      implicit evTile: ClassTag[TileT], evSide: ClassTag[SideT]) extends HexGridComplex[TileT, SideT](xTileMin, xTileMax, yTileMin, yTileMax) with
      HexGridRegular[TileT] with TileGridComplexReg[TileT, SideT]
{
  override def coodToVec2(cood: Cood): Vec2 = HexGridComplex.coodToVec2(cood)
  def vertMargin = 2.8
  def horrMargin = 2.2  
   
  def xRow2Start = xTileMin.incrementTill(_ % 4 == 2)
  def xRow4Start = xTileMin.incrementTill(_ % 4 == 0)
  def xRow2End = xTileMax.decrementTill(_ % 4 == 2)
  def xRow4End = xTileMax.decrementTill(_ % 4 == 0)
  /** Not sure about the following 4 values */
  def sideRow2Start = xRow2Start + 2
  def sideRow4Start = xRow4Start + 2
  def sideRow2End = xRow2End - 2
  def sideRow4End = xRow4End - 2
  def sideRowOddStart = (xRow2Start + xRow4Start) / 2
  def sideRowOddEnd = (xRow2End + xRow4End) / 2
  override def tileNum: Int = ???  
  
  val sideArr: Array[SideT] = new Array[SideT](100)
  
  /** rows 2, 6, 10 ... -2, -6, -10 ... */
  def row2sForeach(f: Int => Unit): Unit =
    for { y <- yTileMin.incrementTill(_ % 4 == 2) to yTileMax.decrementTill(_ % 4 == 2) by 4 } yield f(y)
      
  /** rows 4, 8 12 ... 0, -4, -8 ... */
  def row4sForeach(f: Int => Unit): Unit =
    for { y <- yTileMin.incrementTill(_ % 4 == 0) to yTileMax.decrementTill(_ % 4 == 0) by 4 } yield f(y)
      
  override def tileXYForeach(f: (Int, Int) => Unit): Unit = 
  { row2sForeach(y => for { x <- xRow2Start to xRow2End by 4} yield f(x, y))
    row4sForeach(y => for { x <- xRow4Start to xRow4End by 4} yield f(x, y))
  }
  
  /** Needs loking at */  
  def sideXYForeach(f: (Int, Int) => Unit): Unit = ???//{}
//  { row2sForeach(y => for { x <- sideRow2Start to sideRow2End by 4} yield f(x, y))
//    row4sForeach(y => for { x <- sideRow4Start to sideRow4End by 4} yield f(x, y))
//    for
//    { y <- (yTileMin + 1) to (yTileMax - 1) by 2
//      x <- sideRowOddStart to sideRowOddEnd by 2
//    } yield f(x, y)     
//  }
   
  def tileNeighboursCoods(cood: Cood): Coods =
    HexGridComplex.adjTileCoodsOfTile(cood).filter(c => yTileMax >= c.y & c.y >= yTileMin & xTileMax >= c.x & c.x >= xTileMin)
  def tileNeighbours(tile: TileT): List[TileT] = tileNeighboursCoods(tile.cood).lMap(getTile)
  
  def sideLines: Line2s = ???
   
  def findPath(startCood: Cood, endCood: Cood, fTerrCost: (TileT, TileT) => OptInt): Option[List[Cood]] =
  {
    var open: List[Node[TileT]] = Node(this.getTile(startCood), 0, getHCost(startCood, endCood), nullRef[Node[TileT]]) :: Nil
    var closed: List[Node[TileT]] = Nil
    var found: Option[Node[TileT]] = None
    while (open.nonEmpty & found == None)
    {
      val curr: Node[TileT] = open.minBy(_.fCost)
      //if (curr.tile.cood == endCood) found = true
      open = open.filterNot(_ == curr)
      closed ::= curr
      val neighbs: List[TileT] = this.tileNeighbours(curr.tile).filterNot(tile => closed.exists(_.tile == tile))
      neighbs.foreach { tile =>
        fTerrCost(curr.tile, tile) match
        {
          case NoInt =>
          case SomeInt(nc) if closed.exists(_.tile == tile) =>
          case SomeInt(nc) =>
          {
            val newGCost = nc + curr.gCost
            
            open.find(_.tile == tile) match
            {
              case Some(node) if newGCost < node.gCost => { node.gCost = newGCost; node.parent = Opt(curr) }
              case Some(node) =>
              case None => 
              {
                val newNode  = Node(tile, newGCost, getHCost(tile.cood, endCood), Opt(curr))
                open ::= newNode
                if (tile.cood == endCood) found = Some(newNode)
              }
            }
          }
        }
      }       
   }
     
  def loop(acc: List[Cood], curr: Node[TileT]): List[Cood] = curr.parent.fold(acc, loop(curr.tile.cood :: acc, _))
   
  found.map(endNode =>  loop(Nil, endNode))
  }
  def sideCoods: Coods = ???
}

case class Node[TileT <: Tile](val tile: TileT, var gCost: Int, var hCost: Int, var parent: Opt[Node[TileT]])
{
  def fCost = gCost + hCost
}
