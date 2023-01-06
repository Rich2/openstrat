/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E0 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e0(124)

  override val terrs: HCenLayer[WTile] =
  {
    val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    gs(152, 520, taiga)
    gs(150, 518, taiga)
    gs(148, 516, taiga * 2)
    gs(146, 510, hills, sea * 2, plain)
    gs(144, 508, plain, sea * 2, plain)
    gs(142, 506, plain, plain, sea, plain * 2)
    gs(140, 516, plain * 3)
    gs(138, 460 + 50, plain * 5)
    gs(136, 512, plain, hills, mtain, plain)
    gs(134, 506, hills * 3, sea * 2, hills)
    gs(132, 504, hills, plain * 2, sea * 3)
    gs(130, 506, plain, hills, sea * 3, hills)
    gs(128, 508, hills *5, sea)
    wr(126, sea * 2, mtain, desert * 5)
    wr(124, sea * 2, hills, desert * 6)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(129,507,  129,509,  129,511,  129,525,  130,528,  131,527,  134,528,  135,527,  136,526,  142,508,  143,507,  144,522,  145,521)
    res
  }

  def regGrid: EGrid320Long = EGrid320Long.reg(138, 148, 0, 504, 520)

  def regTerrs: HCenLayer[WTile] = regGrid.newHCenSubLayer(grid, terrs)
  def regSTerrs: HSideBoolLayer = regGrid.newHSideBoolSubLayer(grid, sTerrs)

  def regScen: EScenBasic = new EScenBasic
  { override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid320Long = regGrid
    override val terrs: HCenLayer[WTile] = regTerrs
    override val sTerrs: HSideBoolLayer = regSTerrs
  }
}