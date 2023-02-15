/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import pEarth._, prid._, phex._, WTile._

object Terr220E30 extends Long220Terrs
{
  override implicit val grid: EGrid220LongFull = EGrid220.e30(140)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(166, sea, plain * 7)
//    gs(156, 1532, taiga * 2, sea)
    wr(154, plain * 7, sea, plain * 2)
    wr(152, plain * 4, sea * 3, plain * 3)
    wr(150, plain * 4, sea * 5, plain * 2)
//    wr(148, taiga * 2, taiga * 3)
//    wr(146, plain, taiga * 4)
//    wr(144, plain * 5)
//    gs(142, 1526, plain * 6)
//    gs(140, 1528, plain * 6)
//    gs(138, 1526, mtain * 2, hills, plain * 3, desert)
//    gs(136, 1524, hills, plain * 2, sea, plain * 3)
//    gs(134, 1526, hills * 3, sea * 3, mtain)
//    gs(132, 1524, hills * 7)
//    wr(130, hills * 2, sea, hills * 5)
//    gs(128, 1544, hills, desert * 2)
//    wr(126, sea, desert, sea * 3, hills, desert * 2)
//    wr(124, desert * 4, plain, desert * 4)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    //res.setSomeInts(Sea, 153,1537,  153,1543,  154,1544,  155,1543)
   // res.setSomeInts(Lake, 149, 1537)

    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res
  }
}