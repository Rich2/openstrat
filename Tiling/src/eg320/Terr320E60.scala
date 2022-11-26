/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E60 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e60(130)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](taiga)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 2308 + 256, sea)
    gs(158, 2308 + 254, sea, taiga)
    gs(156, 2308 + 248, sea * 2, taiga)
    gs(142, 2308 + 242, plain * 6)
    gs(140, 2308 + 244, plain * 6)
    gs(138, 2308 + 242, plain * 7)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507))
    res
  }
}