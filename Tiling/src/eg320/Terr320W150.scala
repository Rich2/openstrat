/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320W150 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w150(130)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
    gs(156, 7676, tundra * 2, sea)
    gs(154, 7674, taiga * 4)
    gs(152, 7676, taiga * 4)
    gs(150, 7674, tundra, mtain * 2, taiga)
    gs(148, 7688, mtain)
    gs(146, 7690, mtain)
    res
  }

  override val sTerrs: HSideBoolLayer =
  { val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507))
    res
  }
}