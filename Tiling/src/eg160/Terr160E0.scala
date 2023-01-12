/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr160E0 extends LongTerrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.e0(276)

  override val terrs: HCenLayer[WTile] =
  {
    val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
//    gs(152, 460 + 60, taiga)
//    gs(150, 460 + 58, taiga)
//    gs(148, 460 + 56, taiga * 2)
//    gs(146, 460 + 50, hills, sea * 2, plain)
//    gs(144, 460 + 48, plain, sea * 2, plain)
//    gs(142, 460 + 46, plain, plain, sea, plain * 2)
    gs(294, 506, hills, sea * 3, hills, sea, taiga)
    gs(292, 504, hills * 2, sea * 5, plain)
    gs(290, 506, hills, sea * 4, plain, sea, plain)
    gs(288, 500, plain, hills, plain, sea * 4, plain, sea)
    gs(286, 498, plain, sea * 2, plain, sea * 3, plain * 3)
    gs(284, 496, plain * 2, hills, plain * 2, sea * 2, plain * 4)
    gs(282, 506, hills, plain * 8)
    gs(280, 504, hills, sea * 2, plain * 6)
    gs(278, 506, plain * 5, hills, plain * 2, hills)
    gs(276, 508, plain * 4, hills * 2, mtain * 3)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507), (144, 522), (145, 521))
    res
  }

  override val corners: CornerLayer = grid.newHVertOffsetLayer

  def britTerrs: HCenLayer[WTile] = EGrid160.britGrid.hCenLayerSpawn(grid, terrs)
  def britSTerrs: HSideBoolLayer = EGrid160.britGrid.sideBoolLayerSpawn(grid, sTerrs)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: HCenLayer[WTile] = britTerrs
    override val sTerrs: HSideBoolLayer = britSTerrs
    override val corners: CornerLayer = gridSys.newHVertOffsetLayer
  }
}