/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320E60 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e60(120)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    wr(160, tundra, sea)
    wr(158, tundra, sea, tundra)
    wr(156, sea * 2, tundra)
    wr(154, tundra * 4)
    wr(152, taiga * 4)
    wr(150, taiga * 4)
    wr(148, forest, taiga * 4)
    wr(146, forest * 2, taiga * 3)
    wr(144, forest * 5)
    wr(142, plain * 6)
    wr(140, plain * 6)
    wr(138, plain * 7)
    wr(136, sea * 2, desert * 5)
    wr(134, sea, desert * 5, mtain)
    wr(132, mtain, sea, desert * 3, mtain * 2)
    wr(130, mtain, sea, desert * 4, mtain * 2)
    wr(128, hillDesert, desert * 5, mtain * 2)
    wr(126, desert, mtain, desert * 5, plain)
    wr(124, desert, plain, mtain, desert * 4, plain * 2)
    wr(122, desert, sea, mtain, desert * 3, plain, desert * 2)
    wr(120, desert * 2, sea, desert, sea * 3, plain, desert)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] = {
    val res: HSideOptLayer[WSide] = grid.newSideOpts[WSide]
    res.setSomeInts(Sea,  121,2555,  121,2557,  123,2547)
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}