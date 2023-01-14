/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import pEarth._, prid._, phex._, WTile._, egrid._

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
    res
  }

  override val sTerrs: HSideBoolLayer = {
    val res = grid.newSideBools
    //res.setTruesInts(279, 505, 281, 515, 282, 516, 284, 502, 288, 502)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}