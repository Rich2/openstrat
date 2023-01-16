/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320W120 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w120(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(160, sea * 2)
    wr(158, tundra * 3)
    wr(156, tundra, sea ,tundra)
    wr(154, tundra * 4)
    wr(152, taiga * 3, tundra)
    wr(150, mtain, taiga * 3)
    wr(148, hillTaiga * 2, taiga * 3)
    wr(146, mtain, taiga * 4)
    wr(144, mtain, taiga * 4)
    wr(142, sea ,mtain * 2, plain * 3)
    wr(140, sea, mtain * 2, plain * 3)
    wr(138, sea * 2, mtain * 2, plain * 3)
    wr(136, sea * 2, hillForest, hills, mtain * 2, hillDesert)
    wr(134, sea * 2, hills, hillDesert, desert, hillDesert *2)
    wr(132, sea * 2, hills * 2, hillDesert * 2, mtain)
    wr(130, sea * 4, hills, hillDesert * 2, mtain)
    wr(128, sea * 4, hillDesert * 2, desert, hillDesert)
    res
  }
  override val sTerrs: HSideOptLayer[WSide] = {
    val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res
  }
  override val sTerrsDepr: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(155,8707,  155,8709,  157,8701,  158,8704,  158,8712)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}