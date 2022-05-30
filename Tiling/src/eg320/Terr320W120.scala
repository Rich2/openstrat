/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, egrid._, WTile._

object Terr320W120 extends WarmTerrs
{
  override implicit val grid: EGrid320Warm = EGrid320.w120(138)

  override val terrs: HCenDGrid[WTile] =
  { val res: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](taiga)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 8704, sea * 2)
    gs(158, 8702, tundra * 3)
    gs(156, 8700, tundra, sea ,tundra)
    gs(154, 8698, tundra * 4)
    gs(152, 8712, tundra)
    gs(150, 8698, mtain, taiga * 3)
    gs(148, 8696, hills * 2, taiga * 3)
    gs(146, 8698, mtain, taiga * 4)
    gs(144, 8696, mtain, taiga * 4)
    gs(142, 8694, sea ,mtain * 2, plain * 3)
    gs(140, 8696, sea, mtain * 2, plain * 3)
    gs(138, 8694, sea * 2, mtain * 2, plain * 3)
    res
  }

  override val sTerrs: HSideBoolDGrid =
  { implicit val grid: EGrid320Warm = EGrid320.e0(138)
    val res = grid.newSideBools
    //res.setTruesInts((142, 508), (143, 507))
    res
  }
}