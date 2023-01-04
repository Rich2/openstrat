/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E120 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e120(130)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](taiga)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    gs(160, 4608, sea * 2)
    gs(158, 4606, taiga * 2, sea)
//    gs(156, 2308 + 248, sea * 2, taiga)
//    gs(144, 4600, plain * 3, taiga * 2)
//    gs(142, 2308 + 242, plain * 6)
//    gs(140, 2308 + 244, plain * 6)
    gs(138, 4598, plain * 5, taiga * 2)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507))
    res
  }
}