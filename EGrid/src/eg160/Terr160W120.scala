/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain for 160km 120 west. */
object Terr160W120 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.w120(314)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }

    wr(320, sea * 3, tundraHills * 2)
    wr(318, sea, tundra * 2, sea * 2)
    wr(316, sea, tundra * 2, tundraHills * 3)
    wr(314, sea * 2, tundra * 4)
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
    res.setSomeInts(SCSea, 314,8704,  315,8705,  316,8706,  317,8707,  319,8707)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}