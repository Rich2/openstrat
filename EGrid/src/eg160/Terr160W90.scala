/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 90 west. */
object Terr160W90 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w90(314)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(320, sea * 2, tundraHills * 2, sea),
      TRow(318, tundraHills * 4, sea),
      TRow(316, tundraHills * 2, sea, tundraHills * 3),
      TRow(314, sea, tundraHills, sea, tundraHills, tundra, tundraHills),
      //    TRow(312, 11764, ice * 5, sea * 2),
      //    TRow(310, 11766, ice * 4, sea * 3),
      //    TRow(308, 11764, ice * 3, sea * 5),
      //    TRow(306, 11762, ice * 2, sea * 4, hills * 2),
      //    TRow(304, 11764, ice, sea * 4, tundra * 3),
      //    TRow(302, 11762, ice, sea * 5, tundra * 3),
      //    TRow(300, 11760, tundra, sea * 8),
      //    TRow(298, 11758, tundra, sea * 9),
    )
  }
  help.run
}