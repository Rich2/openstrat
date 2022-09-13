/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._

/** A main non-polar grid with a hex span of 320Km */
class EGrid320WarmFull(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridWarmFull(rBottomCen, rTopCen, cenLongInt, 80.kMetres, 100)

object EGrid320WarmFull
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid320WarmFull(rBottomCen, rTopCen, cenLongInt)
}

/** Terrain data grid for [[EGrid320WarmFull]]s. */
trait Warm320Terrs extends WarmTerrs
{ override implicit val grid: EGrid320WarmFull
}

/** object for creating earth grids with 320km hexs, with a c scale of 80km. */
object EGrid320
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */

  def e0(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 0)
  def e30(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int = 138, rTopCen: Int = 160): EGrid320WarmFull = EGrid320WarmFull(rBottomCen, rTopCen,11)

  def scen0: EScenWarm =
  { val grid: EGridWarmFull = e0(138)
    EScenWarm(grid, Terr320E0.terrs, Terr320E0.sTerrs, "320km 0E")
  }

  def scen1: EScenWarm =
  { val grid: EGridWarmFull = e30(138)
    EScenWarm(grid, Terr320E30.terrs, Terr320E30.sTerrs, "320km 30E")
  }

  def scen2: EScenWarm =
  { val grid: EGridWarmFull = e60(138)
    EScenWarm(grid, Terr320E60.terrs, Terr320E60.sTerrs, "320km 60E")
  }

  def scen3: EScenWarm =
  { val grid: EGridWarmFull = e90(138)
    EScenWarm(grid, Terr320E90.terrs, Terr320E90.sTerrs, "320km 90E")
  }

  def scen4: EScenWarm =
  { val grid: EGridWarmFull = e120(138)
    EScenWarm(grid, Terr320E120.terrs, Terr320E120.sTerrs, "320km 120E")
  }

  def scen5: EScenWarm =
  { val grid: EGridWarmFull = e150(138)
    EScenWarm(grid, Terr320E150.terrs, Terr320E150.sTerrs, "320km 150E")
  }

  def scen6: EScenWarm =
  { val grid: EGridWarmFull = e180(138)
    EScenWarm(grid, Terr320E180.terrs, Terr320E180.sTerrs, "320km 180E")
  }

  def scen7: EScenWarm =
  { val grid: EGridWarmFull = w150(138)
    EScenWarm(grid, Terr320W150.terrs, Terr320W150.sTerrs, "320km 150W")
  }

  def scen8: EScenWarm =
  { val grid: EGridWarmFull = w120(138)
    EScenWarm(grid, Terr320W120.terrs, Terr320W120.sTerrs, "320km 120W")
  }

  def scen9: EScenWarm =
  { val grid: EGridWarmFull = w90(138)
    EScenWarm(grid, Terr320W90.terrs, Terr320W90.sTerrs, "320km 90W")
  }

  def scen10: EScenWarm =
  { val grid: EGridWarmFull = w60(138)
    EScenWarm(grid, Terr320W60.terrs, Terr320W60.sTerrs, "320km 60W")
  }

  def scen11: EScenWarm =
  { val grid: EGridWarmFull = w30(138)
    EScenWarm(grid, Terr320W30.terrs, Terr320W30.sTerrs, "320km 30W")
  }
}