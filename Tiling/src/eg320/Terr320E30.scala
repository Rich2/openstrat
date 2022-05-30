/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import pEarth._, prid._, phex._, WTile._, egrid._

object Terr320E30 extends WarmTerrs
{
  implicit val grid: EGrid320Warm = EGrid320.e30(138)

  val terrs: HCenDGrid[WTile] =
  {

    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(156, 1384 + 148, taiga * 2, sea)
    gs(154, 1384 + 146, taiga * 3, sea)
    gs(152, 1384 + 152, taiga, sea, taiga)
    gs(150, 1384 + 150, taiga * 3)
    gs(148, 1384 + 144, taiga, sea, taiga * 3)
    gs(146, 1384 + 150, taiga * 4)
    gs(144, 1384 + 148, plain * 4)
    gs(142, 1384 + 142, plain * 6)
    gs(140, 1384 + 144, plain * 6)
    gs(138, 1384 + 142, mtain * 2, hills, plain * 4)
    terrs
  }

  def apply() = terrs

  def sTerrs(): HSideBoolDGrid =
  { val sTerrs = grid.newSideBools
    //sTerrs.setTruesInts((142, 508), (143, 507))
    sTerrs
  }
}
