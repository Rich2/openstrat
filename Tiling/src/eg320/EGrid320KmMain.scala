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
  def l30(rBottomCen: Int, rTopCen: Int = 160): EGrid320KmMain = new EGrid320KmMain(rBottomCen, rTopCen, 30.east, 200)

  def scen1: EScenBasic =
  { val grid: EGridMain = l0(138)
    EScenBasic(grid, terr0())
  }

  def scen2: EScenBasic =
  { val grid: EGridMain = l30(138)
    EScenBasic(grid, terr30())
  }

  def terr0(): HCenDGrid[WTile] =
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

  def terr30(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320KmMain = EGrid320Km.l30(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(156, 196, taiga * 2, sea)
    gs(154, 194, taiga * 3, sea)
    gs(152, 200, taiga, sea, taiga)
    gs(150, 198, taiga * 3)
    gs(148, 192, taiga, sea, taiga * 3)
    gs(146, 198, taiga * 4)
    gs(144, 196, plain * 4)
    gs(142, 190, plain * 6)
    gs(140, 192, plain * 6)
    gs(138, 190, mtain * 2, hills, plain * 4)
    terrs
  }
}