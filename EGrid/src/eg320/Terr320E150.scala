/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

object Terr320E150 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e150(126)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }
    wr(160, sea * 2)
    gs(158, 5630, tundra, sea * 2)
    gs(156, 5628, tundra * 3)
    gs(154, 5626, tundra * 4)
    gs (152, 5628, tundra, taiga * 3)
    gs(150, 5626, taiga * 4)
    gs(148, 5624, taiga * 3, sea, taiga)
    gs(146, 5638, taiga, sea)
    gs(144, 5636, taiga, sea)
    gs(142, 5622, taiga * 2, sea * 2, taiga, sea)
    gs(140, 5624, taiga, sea * 5)
    wr(138, taiga, sea * 6)
    wr(136, sea, forestHills, sea * 5)
    wr(134, forestHills * 2, sea * 5)
    wr(132, hills, sea * 6)
    wr(130, sea, hills, sea * 6)
    wr(128, hills, sea * 7)
    res
  }
  override val sTerrs: HSideOptLayer[WSide] = {
    val res: HSideOptLayer[WSide] = grid.newSideOptLayer[WSide]
    res.setSomeInts(WSideMid(), 133,5621,  137,5623,  142,5624,  141,5625)
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer

    res.setMouth3(144, 5624)//Manchuria - Sakhalin north
    res.setVert4In(142, 5626)//Manchuria - Sakhalin
    res.setMouth5(140, 5628)//Manchuria - Sakhalin

    res.setMouth4(138, 5626)//Manchuria - Hokkaido east
    res.setMouth1(136, 5620)//Manchuria - Hokkaido west

    res.setCornerIn(134, 5622, 4)//Hokkaido - Japan west
    res.setCornerIn(132, 5620, 0)//Hokkaido - Japan west
    res.setMouth5(132, 5624)//Hokkaido - Japan east
    res
  }
}
