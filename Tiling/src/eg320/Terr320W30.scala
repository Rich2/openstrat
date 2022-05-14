/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320W30
{
  def apply(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Main = EGrid320.w30(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 11776, ice, sea)
    gs(158, 11774, ice * 2, sea)
    gs(156, 11772, ice * 2, sea)
    gs(154, 11770, ice, tundra, sea * 2)
//    gs(152, 11770, ice * taiga)
//    gs(150, 460 + 58, taiga)
//    gs(148, 460 + 56, taiga * 2)
//    gs(146, 460 + 50, hills, sea * 2, plain)
//    gs(144, 460 + 48, plain, sea * 2, plain)
//    gs(142, 460 + 46, plain, plain, sea, plain * 2)
//    gs(140, 516, plain * 3)
//    gs(138, 460 + 50, plain * 5)
    terrs
  }

  def sTerrs(): HSideBoolDGrid =
  { implicit val grid: EGrid320Main = EGrid320.e0(138)
    val sTerrs = grid.newSideBools
    //sTerrs.setTruesInts((142, 508), (143, 507))
    sTerrs
  }
}
