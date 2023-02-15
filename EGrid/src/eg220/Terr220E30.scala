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

    wr(182, hillTundra * 2, sea * 3)
    wr(180, hillTundra, taiga, tundra, hillTundra, sea)
    wr(178, taiga * 4, tundra)
    wr(176, taiga, taiga * 3, taiga * 2)
    wr(174, taiga, taiga * 5)
    wr(172, taiga, taiga * 6)
    wr(170, sea, taiga * 6)
    wr(168, plain, plain, plain * 5)
    wr(166, sea, plain * 7)
    wr(164, plain * 8)
    wr(162, plain * 9)
    wr(160, plain * 9)
    wr(158, plain * 9)
    wr(156, plain * 10)
    wr(154, plain * 7, sea, plain * 2)
    wr(152, plain * 4, sea * 3, plain * 3)
    wr(150, plain * 4, sea * 5, plain * 2)
    wr(148, hills, hills * 10)
    wr(146, hills, hills * 4, plain * 4, plain * 2)
    wr(144, hills, sea, hills, sea, plain * 7)
    wr(142, sea * 8, plain * 4)
    wr(140, sea * 8, plain * 4)
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

    res.setSomeInts(Sea, 176,1542,  177,1541,  177,1543,  178,1544)//White Sea
    res.setSomeInts(Sea, 167,1525,  168,1526,  167,1527,  167,1529)
   // res.setSomeInts(Lake, 149, 1537)

    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res
  }
}