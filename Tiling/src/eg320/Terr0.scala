/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._
import prid._
import phex._
import WTile._
import ostrat.egrid.EScenFlat

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

  def regTerrs: HCenDGrid[WTile] =
  { val newTerrs = regGrid.newHCenDGrid[WTile](sea)
    iToForeach(regGrid.bottomCenR, regGrid.topCenR, 2) { r =>
      iToForeach(regGrid.rowLeftCenC(r), regGrid.rowRightCenC(r), 4) { c =>
        val value = apply().rc(r, c)(EGrid320Km.l0(138))
        newTerrs.set(r, c, value)(regGrid)
      }
    }
    newTerrs
  }

  def regScen: EScenFlat = new EScenFlat {
    override implicit val gridSys: HGridSys = regGrid
    override val terrs: HCenDGrid[WTile] = regTerrs
    override val sTerrs: HSideBoolDGrid = gridSys.newSideBools
    sTerrs.setTrues(HSide(142, 508))
  }
}
