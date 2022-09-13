/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E150 extends Warm320Terrs
{
  override implicit val grid: EGrid320WarmFull = EGrid320.e150(138)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
//    gs(160, 4608, sea * 2)
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
    gs(138, 5622, taiga, sea * 6)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507))
    res
  }
}
