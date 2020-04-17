/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
trait SquareGridSimple extends TileGrid
{
  //(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int)
  final override def xCen: Double = (cTileMin + cTileMax) / 2.0
  override def roordToVec2(roord: Roord): Vec2 = Vec2(roord.c, roord.y)
  override def sideRoordToRoordLine(sideRoord: Roord): RoordLine = SquareGrid.sideRoordToRoordLine(sideRoord)
  def rowTileLen: Int = ((cTileMax.roundDownToEven - cTileMin.roundUpToEven + 2) / 2).max0
  def numOfRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).max0
  def numOfTiles: Int = numOfRows * rowTileLen
  def cStep: Int = 2
  @inline override def index(y: Int, c: Int): Int = (y - yTileMin) / 2 * rowTileLen + (c - cTileMin) / 2

  @inline override def sideRoordsOfTile(tileRoord: Roord): Roords = SquareGrid.sideRoordsOfTile(tileRoord)

  override def xLeft: Double = cTileMin - 1
  override def xRight: Double = cTileMax + 1
  override def top: Double = yTileMax + 1
  override def bottom: Double = yTileMin - 1
  override def tileVertRoords(roord : Roord): Roords = SquareGrid.vertRoordsOfTile(roord)
  override def isTileRoord(r: Roord): Boolean = r.y.isEven & r.c.isEven

  /** The active tiles without any PaintElems. */
  override def activeTiles: Arr[PolyActiveOnly] = map{ roord =>
    val vcs = tileVertRoords(roord)
    val vvs = vcs.map(r => roordToVec2(r))
    vvs.toPolygon.active(roord.toHexTile)
  }

  override def rowForeachTile(y: Int)(f: Roord => Unit): Unit = iToForeach(cTileMin, cTileMax, 2)(c => f(Roord(y, c)))
  override def rowForeachSide(y: Int)(f: Roord => Unit): Unit = if(y.isOdd) iToForeach(cTileMin, cTileMax, 2){c => f(Roord(y, c)) }

  override def rowForeachVert(y: Int)(f: Roord => Unit): Unit = iToForeach(cTileMin - 1, cTileMax + 1, 2)(c => f(Roord(y, c)))
}