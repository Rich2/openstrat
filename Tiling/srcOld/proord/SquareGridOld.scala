/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pGrid
import proord._

/** Currently all SquareGrids are regular. */
class SquareGridOld(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int) extends SquareGridSimpleOld(yTileMin, yTileMax, cTileMin, cTileMax) with
  TileGridOld
{

}

object SquareGridOld
{
  def apply(yTileMin: Int, yTileMax: Int, cTileMin: Int, cTileMax: Int): SquareGridOld = new SquareGridOld(yTileMin, yTileMax, cTileMin, cTileMax)
  val vertRoordsOfTile00: Roords = Roords(1 rr 1,  -1 rr 1,  -1 rr -1, 1 rr -1)
  def vertRoordsOfTile(y: Int, c: Int): Roords = vertRoordsOfTile(y rr c)
  def vertRoordsOfTile(inp: Roord): Roords = vertRoordsOfTile00.dataMap(inp + _)
  val adjTileRoordsOfTile00: Roords = Roords(2 rr 0, 2 rr 2, 0 rr 2, -2 rr 2, -2 rr 0, -2 rr -2, 0 rr -2)
  def adjTileRoordsOfTile(tileRoord: Roord): Roords = adjTileRoordsOfTile00.dataMap(tileRoord + _)
  def sideRoordsOfTile(inp: Roord): Roords = Roords(inp.addY(1), inp.addC(1), inp.subY(1), inp.subC(1))

  sealed trait PathDirn
  object Rt extends PathDirn
  object Lt extends PathDirn
  object Up extends PathDirn
  object Dn extends PathDirn

  def sideRoordToRoordLine(sideRoord: Roord): RoordLine = sideRoordToRoordLine(sideRoord.y, sideRoord.c)

  def sideRoordToRoordLine(y: Int, c: Int): RoordLine = (y %% 2, c %% 2) match
  { case (1, 0) => RoordLine(y, c - 1, y, c + 1)
    case (0, 1)=> RoordLine(y - 1, c, y + 1, c)
    case _ => excep("Invalid Square Roord for a side")
  }
}


