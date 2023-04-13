/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 120 west. The terrain here is only very rough first approximation. */
object Terr160E120 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e120(252, 272)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(272, desert * 3, hillyDesert * 3, hills, desert, plain * 2, hills * 2, plain, hillyForest),
      TRow(270, plain * 14, sea),
      TRow(268, plain * 13, sea * 2),
      TRow(266, plain * 12, sea * 3),
      TRow(264, plain * 12, sea * 3),
      TRow(262, plain * 7, sea * 4, hills * 2, sea * 3),
      TRow(260, plain * 9, sea, hills * 3, sea * 3),
      TRow(258, plain * 9, sea * 2, hills * 2, sea * 3),
      TRow(256, plain * 8, sea * 4, hills * 2, sea, hills * 2),
      TRow(254, plain * 8, sea * 6, hills * 3),
      TRow(252, plain * 9, sea * 5, hills * 2, sea),
    )
  }
  help.run
}