/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320W150 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w150(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(156, tundra * 2, sea)
    wr(154, taigaHills * 3, taiga)
    wr(152, taiga * 3, taigaHills)
    wr(150, tundraHills, mtain * 2, taiga)
    wr(148, taigaHills * 2, sea * 2, mtain)
    wr(146, tundraHills, sea * 3, mtain)
    wr(144, taigaHills, sea * 4)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]

    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer
    res.setMouth2Corner(158, 7686)
    res
  }
}