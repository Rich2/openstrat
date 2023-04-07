/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 120 west. */
object Terr160W120 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w120(314)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners) {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(320, sea * 3, tundraHills * 2),
      TRow(318, sea, tundra * 2, sea * 2),
      TRow(316, sea, tundra * 2, tundraHills * 3),
      TRow(314, sea * 2, tundra * 4),
      //    gs(312, 11764, ice * 5, sea * 2)
      //    gs(310, 11766, ice * 4, sea * 3)
      //    gs(308, 11764, ice * 3, sea * 5)
      //    gs(306, 11762, ice * 2, sea * 4, hills * 2)
      //    gs(304, 11764, ice, sea * 4, tundra * 3)
      //    gs(302, 11762, ice, sea * 5, tundra * 3)
      //    gs(300, 11760, tundra, sea * 8)
      //    gs(298, 11758, tundra, sea * 9)
    )
  }
  help.run
}