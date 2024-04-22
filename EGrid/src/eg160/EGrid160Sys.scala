/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import egrid._, geom._, prid.phex._

trait EGrid160Sys extends EGridSys
{ override val cScale: MetricLength = 40.kiloMetres
}

/** A main non-polar grid with a hex span of 80Km */
//class EGrid160Warm(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
//  EGridWarm(rBottomCen, rTopCen, cenLongInt, 20000.metres, 300) with EGrid160Sys

/** object for creating 160km hex scale earth grids. */
/*object EGrid160Km
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 160km. */
  def es0(rBottomCen: Int, rTopCen: Int = 540): EGrid160Warm = new EGrid160Warm(rBottomCen, rTopCen, 0)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 20Km or hex scale 160km. */
  def e30(rBottomCen: Int, rTopCen: Int = 540): EGrid160Warm = new EGrid160Warm(rBottomCen, rTopCen, 30)

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 160km. */
  def e0b446: EGrid160Warm = new EGrid160Warm(446, 540, 0)
  def e30b446: EGrid160Warm = new EGrid160Warm(446, 540, 30)

  /*def scen: EScenBasic =
  { val grid: EGrid160Main = e0(446)
    EScenBasic(grid, EuropeNW160Terr(), grid.newSideBools)
  }

  def scen2: EScenBasic =
  { val grid: EGrid160Main = e30(446)
    EScenBasic(grid, EuropeNE160Terr(), grid.newSideBools)
  }*/
}

object EGrid160Warm
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid160Warm(rBottomCen, rTopCen, cenLongInt)
}*/

trait EGrid160LongMulti extends EGridLongMulti with EGrid160Sys
{
  /** H cost for A* path finding. To move 1 tile has a cost 2. This is because the G cost or actual cost is the sum of the terrain cost of the tile of
   * departure and the tile of arrival. */
  final override def getHCost(startCen: HCen, endCen: HCen): Int = ???
}