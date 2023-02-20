/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import egrid._

/** object for creating 80km hex scale earth grids. */
object EGrid80
{
  /** Returns an [[RArr]] sequence of 320km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 540): RArr[EGrid80LongFull] =
    iUntilMap(startIndex, startIndex + num) { i => EGrid80LongFull(rBottomCen, rTopCen, i %% 12) }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 540): EGrid80LongFull = new EGrid80LongFull(rBottomCen, rTopCen, 0)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 20Km or hex scale 80km. */
  def e30(rBottomCen: Int, rTopCen: Int = 540): EGrid80LongFull = new EGrid80LongFull(rBottomCen, rTopCen, 1)

  def scen0: EScenBasic = EScenBasic(Terr80E0.grid, Terr80E0.terrs, Terr80E0.sTerrs, Terr80E0.corners, "80km 0E")
  def scen1: EScenBasic = EScenBasic(Terr80E30.grid, Terr80E30.terrs, Terr80E30.sTerrs, Terr80E30.corners)
}