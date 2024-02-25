/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** Terrain for 160km 120 west. The terrain here is only very rough first approximation. */
object Terr160E120 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e120(252, 272)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(272, desert * 3, hillyDesert * 3, hilly, desert, plain * 2, hilly * 2, plain, hillyForest),
      TRow(270, plain * 14, sea),
      TRow(268, plain * 13, sea * 2),
      TRow(266, plain * 12, sea * 3),
      TRow(264, plain * 12, sea * 3),
      TRow(262, plain * 7, sea * 4, hilly * 2, sea * 3),
      TRow(260, plain * 9, sea, hilly * 3, sea * 3),
      TRow(258, plain * 9, sea * 2, hilly * 2, sea * 3),
      TRow(256, plain * 8, sea * 4, hilly * 2, sea, hilly * 2),
      TRow(254, plain * 8, sea * 6, hilly * 3),
      TRow(252, plain * 9, sea * 5, hilly * 2, sea),
    )
  }
  help.run
}