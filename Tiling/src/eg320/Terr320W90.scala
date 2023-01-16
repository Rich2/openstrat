/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

/** Terrain for 90 degrees west includes grid, tile terrain and straits [[Boolean]]s. */
object Terr320W90 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w90(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(160, sea * 2)
    wr(158, tundra * 3)
    wr(156, sea, tundra * 2)
    wr(154, tundra * 3, sea)
    wr(152, tundra * 4)
    wr(150, taiga, tundra, sea * 2)
    wr(148, taiga, tundra, sea * 2, tundra)
    wr(146, taiga * 2, sea * 2, taiga)
    wr(144, taiga * 5)
    wr(142, taiga * 6)
    wr(140, plain * 2, taiga * 4)
    wr(138, plain * 3, lake, taiga * 3)
    wr(136, plain * 5, lake, taiga)
    wr(134, plain * 6, hillForest)
    wr(132, desert, plain * 4, hillForest, hills)
    wr(130, desert, plain * 5, hills, plain)
    wr(128, desert, plain * 3, hills * 2, plain, sea)
    res
  }
  override val sTerrs: HSideOptLayer[WSide] = {
    val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res
  }
  override val sTerrsDepr: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(142,9736,  143,9735,  144,9734,  152,9730,  152,9734,  153,9731,  155, 9731,  156,9730,  157,9729,  158,9724,  158,9728,  159,9725)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}