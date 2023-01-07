/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E120 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e120(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](taiga)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    gs(160, 4608, sea * 2)
    gs(158, 4606, taiga * 2, sea)
//    gs(156, 2308 + 248, sea * 2, taiga)
//    gs(144, 4600, plain * 3, taiga * 2)
//    gs(142, 2308 + 242, plain * 6)
//    gs(140, 2308 + 244, plain * 6)
    gs(138, 4598, plain * 5, taiga * 2)
    wr(136, plain * 7)
    wr(134, plain * 6, sea)
    wr(132, plain * 3, sea, hills * 2, sea)
    wr(130, desert, plain * 4, hills * 2, sea)
    wr(128, plain * 3, sea * 2, hills, sea, hills)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(130,4612,  131,4611)
    res
  }
}