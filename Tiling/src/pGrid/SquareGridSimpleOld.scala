/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
class SquareGridSimpleOld(val yTileMin: Int, val yTileMax: Int, val cTileMin: Int, val cTileMax: Int) extends TileGridOld
{
  final override def xCen: Double = (cTileMin + cTileMax) / 2.0
  override def roordToPt2(roord: Roord): Pt2 = Pt2(roord.c, roord.y)
  override def sideRoordToRoordLine(sideRoord: Roord): RoordLine = SquareGridOld.sideRoordToRoordLine(sideRoord)
  def tileRowLen: Int = ((cTileMax.roundDownToEven - cTileMin.roundUpToEven + 2) / 2).min0
  def numOfTileRows: Int = ((yTileMax.roundDownToEven - yTileMin + 2) / 2).min0
  def numOfTiles: Int = numOfTileRows * tileRowLen
  def cStep: Int = 2
  @inline override def arrIndex(y: Int, c: Int): Int = (y - yTileMin) / 2 * tileRowLen + (c - cTileMin) / 2

  @inline override def sideRoordsOfTile(tileRoord: Roord): Roords = SquareGridOld.sideRoordsOfTile(tileRoord)

  override def xLeft: Double = cTileMin - 1
  override def xRight: Double = cTileMax + 1
  override def top: Double = yTileMax + 1
  override def bottom: Double = yTileMin - 1
  override def tileVertRoords(roord : Roord): Roords = SquareGridOld.vertRoordsOfTile(roord)
  override def isTileRoord(r: Roord): Boolean = r.y.isEven & r.c.isEven

  /** The active tiles without any PaintElems. */
  override def activeTiles: Arr[PolygonActive] = map{ roord =>
    val vcs = tileVertRoords(roord)
    val vvs = vcs.map(r => roordToPt2(r))
    vvs.toPolygon.active(roord.toHexTile)
  }

  override def sideArrIndex(y: Int, c: Int): Int =
  { val oddRows = (y - ySideMin + 1).min0 / 2 * tileRowLen
    val evenRows = (y - yTileMin + 1).min0 / 2 * (tileRowLen + 1)
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

object SquareGridSimpleOld
{
  def apply(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int): SquareGridSimpleOld =
    new SquareGridSimpleOld(yTileMin, yTileMax, cTileMin, cTileMax)
}