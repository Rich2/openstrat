/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 90 west. */
object Terr160W90 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w90(314)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(320, sea * 2, hillyTundra * 2, sea),
      TRow(318, hillyTundra * 4, sea),
      TRow(316, hillyTundra * 2, sea, hillyTundra * 3),
      TRow(314, sea, hillyTundra, sea, hillyTundra, tundra, hillyTundra),
    )
  }
  help.run
}