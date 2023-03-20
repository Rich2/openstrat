/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

object Terr160W30 extends Long160Terrs
{
  override implicit val grid: EGrid160LongFull = EGrid160.w30(276)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues: _*); () }
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }

    wr(320, ice * 4, sea)
    wr(318, ice * 4, sea)
    wr(316, ice * 4, sea * 2)
    wr(314, ice * 5, sea)
    gs(312, 11764, ice * 5, sea * 2)
    gs(310, 11766, ice * 4, sea * 3)
    gs(308, 11764, ice * 3, sea * 5)
    gs(306, 11762, ice * 2, sea * 4, hills * 2)
    gs(304, 11764, ice, sea * 4, tundra * 3)
    gs(302, 11762, ice, sea * 5, tundra * 3)
    gs(300, 11760, tundra, sea * 8)
    gs(298, 11758, tundra, sea * 9)
    res
  }

  override val sTerrs: HSideLayer[WSide] =
  { val res: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
    res.setSomeInts(WSideMid(), 303,11785,  305,11785,  306,11788)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}