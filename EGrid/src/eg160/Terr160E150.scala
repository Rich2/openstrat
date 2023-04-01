/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 120 west. Created elevations, but may need forests. */
object Terr160E150 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e150(252, 272)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(272, sea * 2, hills, sea * 11),
        TRow(270, sea * 2, hills * 3, sea * 10),
        TRow(268, sea * 2, hills * 3, sea * 10),
        TRow(266, sea * 2, hills, sea * 12),
        TRow(264, sea * 2, hills, sea * 12),
        TRow(262, sea * 2, hills * 2, sea * 12),
        TRow(260, hills * 3, sea * 13),
        TRow(258, hills * 3, sea * 13),
        TRow(256, hills * 3, sea * 14),
    )
  }
  help.run
}