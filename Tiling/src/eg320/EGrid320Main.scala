/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, geom.pglobe._, pEarth._, prid._, phex._, WTile._

/** A main non-polar grid with a hex span of 320Km */
class EGrid320Main (rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLongInt, 80.kMetres, 100)

/** object for creating earth grids with 320km hexs, with a c scale of 80km. */
object EGrid320
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def w30(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen,-1)
  def e0(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen, 0)
  def e30(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320Main = new EGrid320Main(rBottomCen, rTopCen, 2)

  def scen0: EScenBasic =
  { val grid: EGridMain = e0(138)
    EScenBasic(grid, Terr320E0(), Terr320E0.sTerrs())
  }

  def scen1: EScenBasic =
  { val grid: EGridMain = e30(138)
    EScenBasic(grid, Terr320E30(), grid.newSideBools)
  }

  def scen2: EScenBasic =
  { val grid: EGridMain = e60(138)
    EScenBasic(grid, Terr320E60(), grid.newSideBools)
  }

  def scen11: EScenBasic =
  { val grid: EGridMain = w30(138)
    EScenBasic(grid, Terr320W30(), grid.newSideBools)
  }
}