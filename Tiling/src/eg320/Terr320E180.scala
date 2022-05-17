/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320E180
{
  def apply(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Warm = EGrid320.e180(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(154, 6650, tundra * 3, sea)
    gs (152, 6652, tundra, sea, tundra, taiga)
    gs(150, 6650, tundra * 2, sea * 2)
    gs(148, 6648, tundra, sea * 4)
    terrs
  }
}
