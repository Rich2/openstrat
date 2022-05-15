/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._, geom._, pglobe._

trait EGrid80Sys extends EGridSys
{ override val cScale: Length = 20.kMetres
}

/** A main non-polar grid with a hex span of 80Km */
class EGrid80Main(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLongInt, 20000.metres, 300) with EGrid80Sys

/** object for creating 80km hex scale earth grids. */
object EGrid80
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 540): EGrid80Main = new EGrid80Main(rBottomCen, rTopCen, 0)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 20Km or hex scale 80km. */
  def e30(rBottomCen: Int, rTopCen: Int = 540): EGrid80Main = new EGrid80Main(rBottomCen, rTopCen, 1)

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0b446: EGrid80Main = new EGrid80Main(446, 540, 0)
  def l30b446: EGrid80Main = new EGrid80Main(446, 540, 1)

  def scen0: EScenBasic =
  { val grid: EGrid80Main = e0(446)
    EScenBasic(grid, Terr80E0(), grid.newSideBools)
  }

  def scen1: EScenBasic =
  { val grid: EGrid80Main = e30(446)
    EScenBasic(grid, Terr80L30(), grid.newSideBools)
  }
}

trait EGrid80MainMulti extends EGridMainMulti with EGrid80Sys