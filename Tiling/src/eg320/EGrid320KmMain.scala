/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, geom.pglobe._, pEarth._, prid._, WTile._

/** A main non-polar grid with a hex span of 320Km */
class EGrid320KmMain (rBottomCen: Int, rTopCen: Int, cenLong: Longitude, cOffset: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLong, 80000.metres, 100, cOffset)

/** object for creating 80km hex scale earth grids. */
object EGrid320Km
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0(rBottomCen: Int, rTopCen: Int = 160): EGrid320KmMain = new EGrid320KmMain(rBottomCen, rTopCen, 0.east, 100)

  def scen1: EScenBasic = {
    val grid: EGridMain = l0(160)
    new EScenBasic(grid, Terr00())
  }
}

object Terr00
{
  def apply(): HCenArr[WTile] =
  {
    implicit val grid: EGrid320KmMain = EGrid320Km.l0(160)
    val terrs: HCenArr[WTile] = grid.newTileArr[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.setRow(r, cStart, tileValues :_*); () }
   // gs(518, 96, taiga)
    terrs
  }
}

