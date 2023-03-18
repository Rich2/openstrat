/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 120 west. Created elevations, but may need forests. */
object Terr160E150 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.e150(252, 272)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }

    wr(272, sea * 2, hills, sea * 11)
    wr(270, sea * 2, hills * 3, sea * 10)
    wr(268, sea * 2, hills * 3, sea * 10)
    wr(266, sea * 2, hills, sea * 12)
    wr(264, sea * 2, hills, sea * 12)
    wr(262, sea * 2, hills * 2, sea * 12)
    wr(260, hills * 3, sea * 13)
    wr(258, hills * 3, sea * 13)
    wr(256, hills * 3, sea * 14)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOptLayer[WSide]
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}