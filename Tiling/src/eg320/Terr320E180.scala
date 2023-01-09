/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320E180 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e180(128)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(154, tundra * 3, sea)
    wr(152, tundra, sea, tundra, taiga)
    wr(150, tundra * 2, sea * 2)
    wr(148, tundra, sea * 4)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(152,6662)
    res
  }
}