/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, geom.pglobe._, pEarth._, prid._, phex._, WTile._

object Terr0
{
  def apply(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Main = EGrid320Km.l0(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(152, 460 + 60, taiga)
    gs(150, 460 + 58, taiga)
    gs(148, 460 + 56, taiga * 2)
    gs(146, 460 + 50, hills, sea * 2, plain)
    gs(144, 460 + 48, plain, sea * 2, plain)
    gs(142, 460 + 46, plain, plain, sea, plain * 2)
    gs(140, 516, plain * 3)
    gs(138, 460 + 50, plain * 5)
    terrs
  }

  def regGrid: HGridReg = HGridReg(138, 148, 504, 520)
  def regTerrs: HCenDGrid[WTile] = regGrid.newHCenDGrid[WTile](sea)
}
