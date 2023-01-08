/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320E90 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e90(126)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](taiga)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(160, tundra * 2)
//    gs(158, 2308 + 254, sea, taiga)
//    gs(156, 2308 + 248, sea * 2, taiga)
//    gs(142, 2308 + 242, plain * 6)
//    gs(140, 2308 + 244, plain * 6)
    wr(138, forr * 7)
    wr(136, mtain * 2, desert * 5)
    wr(134, mtain * 3, desert * 4)
    wr(132, desert * 7)
    wr(130, desert * 3, hillDesert * 5)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507))
    res
  }
}