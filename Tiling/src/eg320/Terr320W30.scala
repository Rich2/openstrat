/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320W30 extends WarmTerrs
{
  override implicit val grid: EGrid320Warm = EGrid320.w30(138)

  override val terrs: HCenDGrid[WTile] =
  { val res: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 11776, ice, sea)
    gs(158, 11774, ice * 2, sea)
    gs(156, 11772, ice * 2, sea)
    gs(154, 11770, ice, tundra, sea * 2)
    gs(152, 11780, hills * 2)
    res
  }

  override val sTerrs: HSideBoolDGrid =
  { implicit val grid: EGrid320Warm = EGrid320.e0(138)
    val sTerrs = grid.newSideBools
    //sTerrs.setTruesInts((142, 508), (143, 507))
    sTerrs
  }
}
