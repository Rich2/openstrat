/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._

object Terr160E30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e30(276)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues: _*); () }

    wr(320, sea * 5)
    wr(318, sea * 5)
    wr(316, sea * 6)
    wr(314, sea * 6)
    wr(312, sea, hillTundra * 3, sea * 3)

    wr(300, taiga, sea, taiga * 7)
    wr(298, taiga, sea, taiga * 3, lake, taiga * 4)
    wr(296, taiga, sea * 3, taiga * 6)
    wr(294, plain, sea, plain * 7, taiga)
    wr(292, plain, sea, plain * 9)
    wr(290, sea, plain * 10)
    wr(288, sea * 2, plain * 9)
    wr(286, plain * 12)
    wr(284, plain * 12)
    wr(282, plain * 13)
    wr(280, plain * 13)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]

    res
  }

  override val sTerrsDepr: HSideBoolLayer =
  { val res = grid.newSideBools
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}