/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 90 west. */
object Terr160W90 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.w90(314)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }

    wr(320, sea * 2, tundraHills * 2, sea)
    wr(318, tundraHills * 4, sea)
    wr(316, tundraHills * 2, sea, tundraHills * 3)
    wr(314, sea, tundraHills, sea, tundraHills, tundra, tundraHills)
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
    res.setSomeInts(SCSea, 315,9721,  316,9722, 318,9724,  318,9728,  318,9732,  319,9727,  319,9729,  319,9731,  319,9733)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}