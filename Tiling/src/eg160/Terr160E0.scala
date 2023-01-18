/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr160E0 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.e0(276)

  /** Terrain for 160km 30East. Zealand has been moved north. 94GG has been left as Sea. */
  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(300, sea * 6, mtain, hillTaiga * 2)
    wr(298, sea * 6, mtain * 2, hillTaiga * 2)
    wr(296, sea * 6, hillTaiga * 2, taiga * 2)
    wr(294, sea * 3, hills, sea * 3, hills, sea, taiga)
    wr(292, sea * 3, hills * 2, sea * 5, plain)
    wr(290, sea * 2, hills * 2, sea * 4, plain * 3)
    wr(288, sea * 2, plain, hills, plain, sea * 4, plain, sea)
    wr(286, sea * 2, plain, sea , plain* 2, sea * 3, plain * 3)
    wr(284, sea, plain * 2, hills, plain * 2, sea * 2, plain * 4)
    wr(282, sea * 4, hills, plain * 8)
    wr(280, sea * 4, hills, sea * 2, plain * 6)
    wr(278, sea * 4, plain * 5, hills, plain * 2, hills)
    wr(276, sea * 5, plain * 4, hills * 2, mtain * 3)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] = {
    val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setSomeInts(Sea, 279,505,  281,515,  282,516,  284,502,  288,502,  289,501,  289,529,  290,528,  290,532,  291,531)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer


}

object Brit160
{ def britTerrs: HCenLayer[WTile] = EGrid160.britGrid.hCenLayerSpawn(Terr160E0.grid, Terr160E0.terrs)

  def britSTerrs: HSideOptLayer[WSide] = EGrid160.britGrid.sideOptLayerSpawn(Terr160E0.grid, Terr160E0.sTerrs)

  //def britSTerrsDepr: HSideBoolLayer = EGrid160.britGrid.sideBoolLayerSpawn(Terr160E0.grid, Terr160E0.sTerrsDepr)

  def britScen: EScenBasic = new EScenBasic {
    override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: HCenLayer[WTile] = britTerrs

    override def sTerrs: HSideOptLayer[WSide] = britSTerrs

    //override val sTerrsDepr: HSideBoolLayer = britSTerrsDepr
    override val corners: HCornerLayer = gridSys.newHVertOffsetLayer
  }
}