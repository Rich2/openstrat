/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E0 extends WarmTerrs
{
  implicit val grid: EGrid320Warm = EGrid320.e0(138)

  def terrs: HCenDGrid[WTile] =
  {
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

  def apply(): HCenDGrid[WTile] = terrs

  def sTerrs(): HSideBoolDGrid =
  { val sTerrs = grid.newSideBools
    sTerrs.setTruesInts((142, 508), (143, 507))
    sTerrs
  }

  def regGrid: HGridReg = HGridReg(138, 148, 504, 520)

  def regTerrs: HCenDGrid[WTile] = regGrid.newHCenDSubGrid(EGrid320.e0(138), apply())

  def regScen: EScenFlat = new EScenFlat {
    override implicit val gridSys: HGridSys = regGrid
    override val terrs: HCenDGrid[WTile] = regTerrs
    override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
    sTerrs.setTruesInts((142, 508), (143, 507))
  }
}
