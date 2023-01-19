/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._

/** Terrain for 160km 120 west. The terrain here is only very rough firsrt approximation. */
object Terr160E120 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.e120(252, 272)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }

    wr(272, plain * 14)
    wr(270, plain * 14, sea)
    wr(268, plain * 13, sea * 2)
    wr(266, plain * 12, sea * 3)
    wr(264, plain * 12, sea * 3)
    wr(262, plain * 7, sea * 4, hills * 2, sea * 3)
    wr(260, plain * 9, sea, hills * 3, sea * 3)
    wr(258, plain * 9, sea * 2, hills * 2, sea * 3)
    wr(256, plain * 8, sea * 4, hills * 2, sea, hills * 2)
    wr(254, plain * 8, sea * 6, hills * 3)
    wr(252, plain * 9, sea * 5, hills * 2, sea)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
   // res.setSomeInts(Sea, 314,8704,  315,8705,  316,8706,  317,8707,  319,8707)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}