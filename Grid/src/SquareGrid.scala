/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pGrid
import geom._

/** Currently all SquareGrids are regular. */
class SquareGrid(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int) extends SquareGridSimple(yTileMin, yTileMax, cTileMin, cTileMax) with
  TileGrid
{

}

object SquareGrid
{
  def apply(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int): SquareGrid = new SquareGrid(yTileMin, yTileMax, cTileMin, cTileMax)
  val vertRoordsOfTile00: Roords = Roords(1 rr 1,  -1 rr 1,  -1 rr -1, 1 rr -1)
  def vertRoordsOfTile(y: Int, c: Int): Roords = vertRoordsOfTile(y rr c)
  def vertRoordsOfTile(inp: Roord): Roords = vertRoordsOfTile00.pMap(inp + _)
  val adjTileRoordsOfTile00: Roords = Roords(2 rr 0, 2 rr 2, 0 rr 2, -2 rr 2, -2 rr 0, -2 rr -2, 0 rr -2)
  def adjTileRoordsOfTile(tileRoord: Roord): Roords = adjTileRoordsOfTile00.pMap(tileRoord + _)
  def sideRoordsOfTile(inp: Roord): Roords = Roords(inp.addY(1), inp.addC(1), inp.subY(1), inp.subC(1))

  sealed trait PathDirn
  object Rt extends PathDirn
  object Lt extends PathDirn
  object Up extends PathDirn
  object Dn extends PathDirn
  def sideRoordToLineRel(sideRoord: Roord, scale: Double, relPosn: Vec2): Line2 =
    sideRoordToRoordLine(sideRoord).toLine2(r => (Vec2(r.c, r.y) -relPosn) * scale)

  def sideRoordToRoordLine(sideRoord: Roord): RoordLine = sideRoordToRoordLine(sideRoord.y, sideRoord.c)

  def sideRoordToRoordLine(y: Int, c: Int): RoordLine = (y %% 2, c %% 2) match
  { case (1, 0) => RoordLine(y, c - 1, y, c + 1)
    case (0, 1)=> RoordLine(y - 1, c, y + 1, c)
    case _ => excep("Invalid Square Roord for a side")
  }
}


