/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._, geom.pglobe._

/** A main non-polar grid with a hex span of 80Km */
class EGrid80KmMain(rBottomCen: Int, rTopCen: Int, cenLong: Longitude, cOffset: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLong, 20000.metres, 300, cOffset)

/** object for creating 80km hex scale earth grids. */
object EGrid80Km
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0(rBottomCen: Int, rTopCen: Int = 540): EGrid80KmMain = new EGrid80KmMain(rBottomCen, rTopCen, 0.east, 512)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 20Km or hex scale 80km. */
  def l30(rBottomCen: Int, rTopCen: Int = 540): EGrid80KmMain = new EGrid80KmMain(rBottomCen, rTopCen, 30.east, 1536)

  def scen1: EScenBasic ={
    val grid = l0(446)
    new EScenBasic(grid, EuropeNW80Terr())
  }

  def scen2: EScenBasic ={
    val grid = l30(446)
    new EScenBasic(grid, EuropeNE80Terr())
  }
}

