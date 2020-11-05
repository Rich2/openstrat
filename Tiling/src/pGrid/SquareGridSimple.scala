/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
class SquareGridSimple(val yTileMin: Int, val yTileMax: Int, val cTileMin: Int, val cTileMax: Int) extends TileGrid
{
  final override def xCen: Double = (cTileMin + cTileMax) / 2.0
  override def roordToVec2(roord: Roord): Pt2 = Pt2(roord.c, roord.y)
  override def sideRoordToRoordLine(sideRoord: Roord): RoordLine = SquareGrid.sideRoordToRoordLine(sideRoord)
  def tileRowLen: Int = ((cTileMax.roundDownToEven - cTileMin.roundUpToEven + 2) / 2).atMost0
  def numOfTileRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).atMost0
  def numOfTiles: Int = numOfTileRows * tileRowLen
  def cStep: Int = 2
  @inline override def arrIndex(y: Int, c: Int): Int = (y - yTileMin) / 2 * tileRowLen + (c - cTileMin) / 2

  @inline override def sideRoordsOfTile(tileRoord: Roord): Roords = SquareGrid.sideRoordsOfTile(tileRoord)

  override def xLeft: Double = cTileMin - 1
  override def xRight: Double = cTileMax + 1
  override def top: Double = yTileMax + 1
  override def bottom: Double = yTileMin - 1
  override def tileVertRoords(roord : Roord): Roords = SquareGrid.vertRoordsOfTile(roord)
  override def isTileRoord(r: Roord): Boolean = r.y.isEven & r.c.isEven

  /** The active tiles without any PaintElems. */
  override def activeTiles: Arr[PolygonActive] = map{ roord =>
    val vcs = tileVertRoords(roord)
    val vvs = vcs.map(r => roordToVec2(r))
    vvs.toPolygon.active(roord.toHexTile)
  }

  override def sideArrIndex(y: Int, c: Int): Int =
  { val oddRows = (y - ySideMin + 1).atMost0 / 2 * tileRowLen
    val evenRows = (y - yTileMin + 1).atMost0 / 2 * (tileRowLen + 1)
    oddRows + evenRows + ife(y.isOdd, (c - cTileMin + 2) / 2, (c - cTileMin + 1) / 2)
  }

  override def rowForeachTile(y: Int)(f: Roord => Unit): Unit = iToForeach(cTileMin, cTileMax, 2)(c => f(Roord(y, c)))
  override def rowForeachSide(y: Int)(f: Roord => Unit): Unit = if(y.isOdd) iToForeach(cTileMin, cTileMax, 2){c => f(Roord(y, c)) }

  override def rowForeachVert(y: Int)(f: Roord => Unit): Unit = iToForeach(cTileMin - 1, cTileMax + 1, 2)(c => f(Roord(y, c)))

  override def tileExists(y: Int, c: Int): Boolean = y match
  { case y if y.isEven & c.isEven & c >= cTileMin & c <= cTileMax => true
    case y if y.isEven & c.isEven => false
    case _ => excep("tile exists: invalide Tile Roord.")
  }

}

object SquareGridSimple
{
  def apply(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int): SquareGridSimple =
    new SquareGridSimple(yTileMin, yTileMax, cTileMin, cTileMax)
}