/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pGrid
import geom._

trait HexGridSimple extends TileGridSimple
{
  /** Return the Side Row start for the given Row y value. */
  def cSideRowMin(y: Int): Int

  /** Array of indexs for Side data Arrs giving the index value for the start of each side row. */
  def sideRowIndexArray: Array[Int]

  /** The c delta between tiles. */
  def cStep: Int = 4

  override def roordToVec2(roord: Roord): Vec2 = HexGrid.roordToVec2(roord)
  def cCen: Double = (cTileMin + cTileMax) / 2.0
  def roordCen = Vec2(cCen, yCen)
  def xRatio: Double = HexGrid.xRatio
  override def xCen: Double = (cTileMin + cTileMax) / 2.0 * xRatio
  //override def sideRoordToLineRel(sideRoord: Roord, scale: Double, relPosn: Vec2): Line2 = HexGrid.sideRoordToLineRel(sideRoord, scale, relPosn)
  override def sideRoordsOfTile(tileRoord: Roord): Roords = HexGrid.sideRoordsOfTile(tileRoord)
  override def xLeft: Double = (cTileMin - 2) * xRatio
  override def xRight: Double = (cTileMax + 2) * xRatio
  def top: Double = yTileMax + HexGrid.yDist2
  def bottom: Double = yTileMin - HexGrid.yDist2
  override def sideRoordToRoordLine(sideRoord: Roord): RoordLine = HexGrid.sideRoordToRoordLine(sideRoord)
  override def tileVertRoords(roord: Roord): Roords = HexGrid.vertRoordsOfTile(roord)
  def isTileRoord(r: Roord): Boolean = r.y.div4Rem2 & r.c.div4Rem2 | r.y.div4Rem0 & r.c.div4Rem0

  /* Override methods */
  final override def tileExists(y: Int, c: Int): Boolean =  ???

  /** The active tiles without any PaintElems. */
  override def activeTiles: Arr[PolygonActive] = map{ roord =>
    val vcs = tileVertRoords(roord)
    val vvs = vcs.map(r => roordToVec2(r))
    vvs.toPolygon.active(roord.toHexTile)
  }

  def roordOffToVec2(rd: RoordOff): Vec2 = rd.toVec2(roordToVec2)

  /** Gives a Coods Seq of Cood along a horisonatal line */
  def SidesHorr(y: Int, xStart: Int, xEnd : Int): Roords =
  { val xs = if (xStart > xEnd) xStart.roundDownToOdd to xEnd.roundUpToOdd by -2 else xStart.roundUpToOdd to xEnd.roundDownToOdd by 2
    xs.pMap(c => Roord(y, c))
  }
  def findPath(startRoord: Roord, endRoord: Roord)(fTerrCost: (Roord, Roord) => OptInt): Option[List[Roord]] =
  {
    var open: List[Node] = Node(startRoord, 0, getHCost(startRoord, endRoord), NoRef) :: Nil
    var closed: List[Node] = Nil
    var found: Option[Node] = None

    while (open.nonEmpty & found == None)
    {
      val curr: Node = open.minBy(_.fCost)
      //if (curr.tile.Roord == endRoord) found = true
      open = open.filterNot(_ == curr)
      closed ::= curr
      val neighbs: Roords = HexGrid.adjTilesOfTile(curr.tile).filterNot(tile => closed.exists(_.tile == tile))
      neighbs.foreach { tile =>
        fTerrCost(curr.tile, tile) match {
          case NoInt =>
          case SomeInt(nc) if closed.exists(_.tile == tile) =>
          case SomeInt(nc) => {
            val newGCost = nc + curr.gCost

            open.find(_.tile == tile) match {
              case Some(node) if newGCost < node.gCost => node.gCost = newGCost; node.parent = OptRef(curr)
              case Some(node) =>
              case None =>
              { val newNode = Node(tile, newGCost, getHCost(tile, endRoord), OptRef(curr))
                open ::= newNode
                if (tile == endRoord) found = Some(newNode)
              }
            }
          }
        }
      }
    }
    def loop(acc: List[Roord], curr: Node): List[Roord] = curr.parent.fld(acc, loop(curr.tile :: acc, _))

    found.map(endNode =>  loop(Nil, endNode))
  }

  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of tile of
   *  departure and the tile of arrival. */
  def getHCost(startRoord: Roord, endRoord: Roord): Int =
  { val diff = endRoord - startRoord
    val c: Int = diff.c.abs
    val y: Int = diff.y.abs

    y - c match
    { case 0 => c
    case n if n > 0 => y
    case n if n %% 4 == 0 => y - n / 2 //Subtract because n is negative, y being greater than x
    case n => y - n / 2 + 2
    }
  }

  def sidePolygon(sr: Roord): PolygonImp =
  { //val (topEnd, bottomEnd) = HexGrid.sideRoordToLineEndRoords(sr)
    //val vTop: Vec2 = roordToVec2(topEnd)
    //val vBottom: Vec2 = roordToVec2(bottomEnd)
    val (o1, o2, o3, o4) = HexGrid.sideRoordToRoordOffs(sr)
    Arr(o1, o2, o4, o3).map(_.toVec2(roordToVec2)).toPolygon
  }
}