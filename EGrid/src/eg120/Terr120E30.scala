/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import pEarth._, prid._, phex._, WTile._

object Terr120E30 extends Long120Terrs
{
  override implicit val grid: EGrid120LongFull = EGrid120.e30(274)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(350, sea, mtain, hillTundra * 2, sea * 5)
    wr(348, mtain * 2, tundra * 4, sea * 3)

    wr(286, hills, plain, hills * 3, sea, hills * 2, hills * 13)
    wr(284, hills, sea * 2, hills * 3, sea * 2, hills * 13)
    wr(282, hills, sea * 2, hills * 3, sea * 2, hills * 13)
    wr(280, sea * 4, hills * 2, sea * 2, hills * 14)
    wr(278, sea * 5, hills, sea * 9, hills * 7)
    wr(276, sea * 6, hills * 2, sea * 7, hills * 7)
    wr(274, sea * 8, sea * 7, hills * 7)

    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]

    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res
  }
}