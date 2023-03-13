/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E90 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e90(126)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(160, tundraHills * 2)
    wr(158, tundra * 3)
    wr(156, taiga, taigaHills * 2)
    wr(154, taiga * 2, taigaHills, taiga)
    wr(152, taiga * 4)
    wr(150, taiga * 4)
    wr(148, taiga * 5)
    wr(146, taiga * 5)
    wr(144, taiga, plain * 4)
    wr(142, desert, plain, hills * 3, mtain)
    wr(140, plain, mtain, desertHills, desert, mtain, hills)
    wr(138, desert * 3, desertHills, desert, desertHills, desert)
    wr(136, mtain * 2, desert * 5)
    wr(134, mtain * 3, desert * 4)
    wr(132, desert * 7)
    wr(130, desert * 3, desertHills * 5)
    wr(128, desertHills * 6, hills, mtain)
    wr(126, mtain, desertHills * 4, mtain * 3)
    res
  }
  override val sTerrs: HSideOptLayer[WSide] = {
    val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}