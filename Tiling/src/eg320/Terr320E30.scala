/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320E30 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.e30(124)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.toEndRow(r, cStart, tileValues :_*); () }
    def wr(r: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, tileValues :_*); () }
    gs(156, 1532, taiga * 2, sea)
    gs(154, 1530, taiga * 3, sea)
    gs(152, 1536, taiga, sea, taiga)
    gs(150, 1534, taiga * 3)
    gs(148, 1528, taiga, sea, taiga * 3)
    gs(146, 1534, taiga * 4)
    gs(144, 1532, plain * 4)
    gs(142, 1526, plain * 6)
    gs(140, 1528, plain * 6)
    gs(138, 1526, mtain * 2, hills, plain * 4)
    gs(136, 1524, hills, plain * 2, sea, plain * 3)
    gs(134, 1526, hills * 2, sea * 4, mtain)
    gs(132, 1524, hills * 7)
    wr(130, hills * 2, sea, hills * 5)
    gs(128, 1544, hills, desert * 2)
    wr(126, sea, desert, sea * 3, hills, desert * 2)
    wr(124, desert * 4, plain, desert * 4)
    res
  }

  val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    res.setTruesInts(130,1520,  130,1524,  131,1521,  131,1525,  131,1533,  132,1534,  132,1526,  133,1525,  134,1524,  135,1523,  136,1522,   136,1542)
    res
  }
}