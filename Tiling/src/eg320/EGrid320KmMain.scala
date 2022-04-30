/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, geom.pglobe._, pEarth._, prid._, phex._, WTile._

/** A main non-polar grid with a hex span of 320Km */
class EGrid320KmMain (rBottomCen: Int, rTopCen: Int, cenLong: Longitude, cOffset: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLong, 80.kMetres, 100, cOffset)

/** object for creating 80km hex scale earth grids. */
object EGrid320Km
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0(rBottomCen: Int, rTopCen: Int = 160): EGrid320KmMain = new EGrid320KmMain(rBottomCen, rTopCen, 0.east, 100)

  def scen1: EScenBasicFlat =
  { val grid: EGridMain = l0(138)
    EScenBasicFlat(grid, Terr00())
  }
}

object Terr00
{
  def apply(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320KmMain = EGrid320Km.l0(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(148, 104, taiga * 2)
    gs(146, 98, hills, sea * 2, plain)
    gs(144, 96, plain, sea * 2, plain)
    gs(142, 94, plain, plain, sea, plain * 2)
    gs(140, 96, plain * 5)
    gs(138, 98, plain * 5)
    terrs
  }
}