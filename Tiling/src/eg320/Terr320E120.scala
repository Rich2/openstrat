/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

/** Terrain for 30km 129 degrees east. */
object Terr320E120 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e120(126)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](taiga)
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }

    wr(160, sea * 2)
    wr(158, tundra, hillTundra, sea)
    wr(156, taiga * 2, mtain)
    wr(154,taiga * 3, hills)
    wr(152, taiga * 2, mtain, hillTaiga)
    wr(150, taiga * 4)
    wr(148, taiga * 5)
    wr(146, hillTaiga, mtain, taiga, hillTaiga * 2)
    wr(144, mtain, hillTaiga * 4)
    wr(142, hillTaiga, taiga, hillTaiga *2, taiga, hillTaiga)
    wr(140, hillTaiga, plain, hillTaiga * 2, taiga, hillTaiga)
    wr(138, desert * 2, plain, hills, plain, taiga * 2)
    wr(136, plain * 7)
    wr(134, plain * 6, sea)
    wr(132, plain * 3, sea, hills * 2, sea)
    wr(130, desert, plain * 4, hills * 2, sea)
    wr(128, plain * 3, sea * 2, hills, sea, hills)
    wr(126, mtain, hills, plain * 2, sea * 3, hills)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(130,4612,  131,4611)
    res
  }
}