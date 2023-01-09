/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320W120 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w120(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    wr(160, sea * 2)
    wr(158, tundra * 3)
    wr(156, tundra, sea ,tundra)
    wr(154, tundra * 4)
    wr(152, taiga * 3, tundra)
    gs(150, 8698, mtain, taiga * 3)
    gs(148, 8696, hills * 2, taiga * 3)
    gs(146, 8698, mtain, taiga * 4)
    gs(144, 8696, mtain, taiga * 4)
    gs(142, 8694, sea ,mtain * 2, plain * 3)
    gs(140, 8696, sea, mtain * 2, plain * 3)
    gs(138, 8694, sea * 2, mtain * 2, plain * 3)
    wr(136, sea * 2, hills * 2, mtain * 2, hillDesert)
    wr(134, sea * 2, hills, desert * 4)
    wr(132, sea * 2, hills * 2, desert * 3)
    wr(130, sea * 4, hills, desert * 3)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(155,8707,  155,8709,  157,8701,  158,8704,  158,8712)
    res
  }
}