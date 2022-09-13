/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E0 extends Warm320Terrs
{
  override implicit val grid: EGrid320Warm = EGrid320.e0(138)

  override val terrs: HCenLayer[WTile] =
  {
    val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
    gs(152, 460 + 60, taiga)
    gs(150, 460 + 58, taiga)
    gs(148, 460 + 56, taiga * 2)
    gs(146, 460 + 50, hills, sea * 2, plain)
    gs(144, 460 + 48, plain, sea * 2, plain)
    gs(142, 460 + 46, plain, plain, sea, plain * 2)
    gs(140, 516, plain * 3)
    gs(138, 460 + 50, plain * 5)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts((142, 508), (143, 507), (144, 522), (145, 521))
    res
  }

  def regGrid: HGridReg = HGridReg(138, 148, 504, 520)

  def regTerrs: HCenLayer[WTile] = regGrid.newHCenSubLayer(EGrid320.e0(138), terrs)

  def regScen: EScenFlat = new EScenFlat {
    override implicit val gridSys: HGridSys = regGrid
    override val terrs: HCenLayer[WTile] = regTerrs
    override val sTerrs: HSideBoolLayer = gridSys.newSideBools
    sTerrs.setTruesInts((142, 508), (143, 507))
  }
}