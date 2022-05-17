/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._

object Terr320W90
{
  def apply(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Warm = EGrid320.w90(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](taiga)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 9728, sea * 2)
    gs(158, 9726, sea * 3)
    gs(156, 9724, sea * 3)
    gs(154, 9734, sea)
//    gs(152, 10760, ice)
//    gs(150, 10758, tundra)
//    gs(148, 10744, taiga, sea * 4)
//    gs(146, 10746, taiga * 2, sea * 3)
//    gs(144, 10744, taiga * 3, sea * 2)
//    gs(142, 10742, taiga * 4, sea * 2)
//    gs(140, 10744, taiga, sea * 2, taiga, sea * 2)
//    gs(138, 10742, taiga * 2, sea, taiga * 2, sea * 2)
    terrs
  }
}