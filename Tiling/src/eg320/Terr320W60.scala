/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320W60
{
  def apply(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Warm = EGrid320.w60(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 10756, ice)
//    gs(158, 2308 + 254, sea, taiga)
//    gs(156, 2308 + 248, sea * 2, taiga)
//    gs(142, 2308 + 242, plain * 6)
//    gs(140, 2308 + 244, plain * 6)
//    gs(138, 2308 + 242, plain * 7)
    terrs
  }
}
