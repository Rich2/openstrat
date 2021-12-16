/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, geom.pglobe._

/** A main non-polar grid with a hex span of 320Km */
class EGrid320KmMain (rBottomCen: Int, rTopCen: Int, cenLong: Longitude, cOffset: Int) extends
  EGridMain(rBottomCen, rTopCen, cenLong, 80000.metres, 100, cOffset)

/** object for creating 80km hex scale earth grids. */
object EGrid320Km
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def l0(rBottomCen: Int, rTopCen: Int = 160): EGrid320KmMain = new EGrid320KmMain(rBottomCen, rTopCen, 0.east, 100)

  def scen1: EScenBasic = new EScenBasic(l0(160))
}

