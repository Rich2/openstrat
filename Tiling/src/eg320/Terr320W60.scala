/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320W60 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w60(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    gs(160, 10756, ice)
    gs(158, 10754, ice * 2)
    gs(156, 10748, tundra, sea, ice)
    gs(154, 10746, tundra * 2, sea, ice)
    gs(152, 10748, tundra, sea * 2, ice)
    gs(150, 10758, tundra)
    wr(148, taiga, mtain, sea * 3)
    wr(146, taiga, hillTaiga, sea * 3)
    gs(144, 10744, taiga * 3, sea * 2)
    gs(142, 10742, taiga * 4, sea * 2)
    gs(140, 10744, taiga, sea * 2, taiga, sea * 2)
    gs(138, 10742, taiga * 2, sea, taiga * 2, sea * 2)
    wr(136, hillTaiga, taiga * 2, sea * 4)
    wr(134, hillForest, sea * 6)
    wr(132, sea * 7)
    res
  }

  override val sTerrsDepr: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(141,10755,  148,10746)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}