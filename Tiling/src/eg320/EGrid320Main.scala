/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, geom.pglobe._, pEarth._, prid._, phex._, WTile._

/** A main non-polar grid with a hex span of 320Km */
class EGrid320Main (rBottomCen: Int, rTopCen: Int, cenLong: Longitude, cOffset: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLong, 80.kMetres, 100, cOffset)

/** object for creating earth grids with 320km hexs, with a c scale of 80km. */
object EGrid320
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def w30(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen,  Longitude.degs(-1 * 30), t"BG0" /* 11776 */)
  def e0(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen, Longitude.degs(0 * 30), t"G0" /* 512 */)
  def e30(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen,  Longitude.degs(1 * 30), t"1G0" /* 1536 */)
  def e60(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen, Longitude.degs(2 * 30), t"2G0" /* 2560 */)

  def scen0: EScenBasic =
  { val grid: EGridMain = e0(138)
    EScenBasic(grid, Terr320E0(), Terr320E0.sTerrs())
  }

  def scen1: EScenBasic =
  { val grid: EGridMain = e30(138)
    EScenBasic(grid, terr30(), grid.newSideBools)
  }

  def scen2: EScenBasic =
  { val grid: EGridMain = e60(138)
    EScenBasic(grid, terr60(), grid.newSideBools)
  }

  def terr30(): HCenDGrid[WTile] =
  {
    implicit val grid: EGrid320Main = EGrid320.e30(138)
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
    implicit val grid: EGrid320Main = EGrid320.e60(138)
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