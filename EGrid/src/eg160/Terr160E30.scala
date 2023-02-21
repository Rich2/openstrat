/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._

/** Terrain at 160km for 30E. Land and sea should be correct, but elevation has not been checked. */
object Terr160E30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e30(256)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues: _*); () }

    wr(320, sea * 5)
    wr(318, sea * 5)
    wr(316, sea * 6)
    wr(314, sea * 6)
    wr(312, sea, tunHill * 3, sea * 3)
    wr(310, taigaHill * 2, taiga * 3, sea, taiga)
    wr(308, taigaHill, taiga * 7)
    wr(306, taiga * 5, sea, taiga * 2)
    wr(304, taiga, sea, taiga * 6)
    wr(302, taiga, sea, taiga * 7)
    wr(300, taiga, sea, taiga * 7)
    wr(298, taiga, sea, taiga * 3, lake, taiga * 4)
    wr(296, taiga, sea * 3, taiga * 6)
    wr(294, plain, sea, plain * 7, taiga)
    wr(292, plain, sea, plain * 9)
    wr(290, sea, plain * 10)
    wr(288, sea * 2, plain * 9)
    wr(286, plain * 12)
    wr(284, plain * 3, forest * 2, plain * 7)
    wr(282, plain * 4, forest * 2, plain * 7)
    wr(280, hills, plain * 12)
    wr(278, hills * 3, plain * 10)
    wr(276, plain, hills, plain * 12)
    wr(274, hills * 2, plain, hills * 3, plain * 4, sea, plain * 3)
    wr(272, hills, plain, hills * 2, plain * 2, plain, sea, plain * 2, plain * 4)
    wr(270, hills * 3, plain * 4, sea * 5, mtain, hills, plain)
    wr(268, sea, mtain, hills * 3, plain * 2, sea * 6, plain, mtain)
    wr(266, sea, mtain, hills * 3, plain * 2, hills * 8)

    wr(264, hills * 2, hills * 2, sea, hills * 10)
    wr(262, hills, sea * 2, hills * 2, sea, hills * 10)
    wr(260, sea * 3, hills, sea * 2, hills * 10)
    gs(258, 1550, hills * 5)
    wr(256, sea * 5, hills * 2, sea * 4, hills * 6)

    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setSomeInts(Sea, 293,1525,  293,1527,  303,1543,  304,1542,  304,1546,  305,1545,  305,1547,  306,1548,  307,1549,  308,1550,  309,1549)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}