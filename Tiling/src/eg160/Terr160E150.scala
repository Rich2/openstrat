/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._

/** Terrain for 160km 120 west. Only just started filling in terrain. */
object Terr160E150 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.e150(252, 272)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }

    wr(272, sea * 2, hills, sea * 11)
    wr(270, sea * 2, hills * 3, sea * 10)
    wr(268, sea * 2, hills * 3, sea * 10)
    wr(266, sea * 2, hills, sea * 12)
//    gs(312, 11764, ice * 5, sea * 2)
//    gs(310, 11766, ice * 4, sea * 3)
//    gs(308, 11764, ice * 3, sea * 5)
//    gs(306, 11762, ice * 2, sea * 4, hills * 2)
//    gs(304, 11764, ice, sea * 4, tundra * 3)
//    gs(302, 11762, ice, sea * 5, tundra * 3)
//    gs(300, 11760, tundra, sea * 8)
//    gs(298, 11758, tundra, sea * 9)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}