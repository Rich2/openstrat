/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km per hex tile terrain centered on 180 east longitude, covering 30 degrees. */
object Terr320E180 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e180(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.setRow(r, tileValues :_*); () }

    wr(154, tundra * 3, sea)
    wr(152, tundra, sea, tundra, taiga)
    wr(150, tundra * 2, sea * 2)
    wr(148, tundra, sea * 4)
    res
  }

  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOptLayer[WSide]
    res.setSomeInts(WSideMid(), 152, 6662)
    res
  }

  override val corners: HCornerLayer =
  { val res = grid.newHVertOffsetLayer
    res.setMouth3(154, 6662)//Bearing Straits north
    res.setMouth0(150, 6662)//Bearing Straits south
    res
  }
}