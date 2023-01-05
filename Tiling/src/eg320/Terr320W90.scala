/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

/** Terrain for 90 degrees west includes grid, tile terrain and straits [[Boolean]]s. */
object Terr320W90 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w90(130)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    gs(160, 9728, sea * 2)
    gs(158, 9726, tundra * 3)
    gs(156, 9728, tundra * 2)
    wr(154, tundra * 3, sea)
    wr(152, tundra * 4)
    wr(150, taiga, tundra, sea * 2)
    wr(148, taiga, tundra, sea * 2, tundra)
    wr(146, taiga * 2, sea * 2, taiga)
    wr(144, taiga * 5)//3, sea * 2)
    wr(142, taiga * 6)
    wr(140, plain * 2, forr * 4)
    wr(138, plain * 3, lake, forr * 3)
    wr(136, plain * 5, lake, forr)
    wr(134, plain * 6, forr)
    wr(132, plain * 7)
    wr(130, plain * 8)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesPairs((152, 9730))//, (143, 507))
    res
  }
}