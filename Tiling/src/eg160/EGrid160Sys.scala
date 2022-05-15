/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import egrid._, geom._, pglobe._

trait EGrid160Sys extends EGridSys
{ override val cScale: Length = 40.kMetres
}

/** A main non-polar grid with a hex span of 80Km */
class EGrid160Main(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLongInt, 20000.metres, 300) with EGrid160Sys

/** object for creating 160km hex scale earth grids. */
object EGrid160Km
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 160km. */
  def es0(rBottomCen: Int, rTopCen: Int = 540): EGrid160Main = new EGrid160Main(rBottomCen, rTopCen, 0)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 20Km or hex scale 160km. */
  def e30(rBottomCen: Int, rTopCen: Int = 540): EGrid160Main = new EGrid160Main(rBottomCen, rTopCen, 30)

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 160km. */
  def e0b446: EGrid160Main = new EGrid160Main(446, 540, 0)
  def e30b446: EGrid160Main = new EGrid160Main(446, 540, 30)

  /*def scen: EScenBasic =
  { val grid: EGrid160Main = e0(446)
    EScenBasic(grid, EuropeNW160Terr(), grid.newSideBools)
  }

  def scen2: EScenBasic =
  { val grid: EGrid160Main = e30(446)
    EScenBasic(grid, EuropeNE160Terr(), grid.newSideBools)
  }*/
}

object EGrid160Main
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid160Main(rBottomCen, rTopCen, cenLongInt)
}

trait EGrid160MainMulti extends EGridMainMulti with EGrid160Sys