/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, geom.pglobe._, pEarth._, prid._, phex._, WTile._

/** A main non-polar grid with a hex span of 320Km */
class EGrid320Main (rBottomCen: Int, rTopCen: Int, cenLong: Longitude, cOffset: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLong, 80.kMetres, 100, cOffset)

/** object for creating 80km hex scale earth grids. */
object EGrid320Km
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen, 0.east, t"G0" /* 512 */)
  def l30(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen, 30.east, t"1G0" /* 1536 */)
  def l60(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen, 60.east, t"2G0" /* 2560 */)

  def scen1: EScenBasic =
  { val grid: EGridMain = l0(138)
    EScenBasic(grid, terr0())
  }

  def scen2: EScenBasic =
  { val grid: EGridMain = l30(138)
    EScenBasic(grid, terr30())
  }

  def scen3: EScenBasic =
  { val grid: EGridMain = l60(138)
    EScenBasic(grid, terr60())
  }

  def terr0(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Main = EGrid320Km.l0(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(152, 460 + 60, taiga)
    gs(150, 460 + 58, taiga)
    gs(148, 460 + 56, taiga * 2)
    gs(146, 460 + 50, hills, sea * 2, plain)
    gs(144, 460 + 48, plain, sea * 2, plain)
    gs(142, 460 + 46, plain, plain, sea, plain * 2)
    gs(140, 460 + 48, plain * 5)
    gs(138, 460 + 50, plain * 5)
    terrs
  }

  def terr30(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Main = EGrid320Km.l30(138)
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

  def terr60(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Main = EGrid320Km.l60(138)
    val terrs: HCenDGrid[WTile] = grid.newHCenDGrid[WTile](taiga)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { terrs.completeRow(r, cStart, tileValues :_*); () }
    gs(160, 2308 + 256, sea)
    gs(158, 2308 + 254, sea, taiga)
    gs(156, 2308 + 248, sea * 2, taiga)
    gs(142, 2308 + 242, plain * 6)
    gs(140, 2308 + 244, plain * 6)
    gs(138, 2308 + 242, plain * 7)
    terrs
  }
}