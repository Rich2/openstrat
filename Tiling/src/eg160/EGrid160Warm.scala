/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import egrid._

/** A main non-polar grid with a hex span of 160Km */
class EGrid160Warm (rBottomCen: Int, rTopCen: Int, cenLongInt: Int) extends
  EGridWarmFull(rBottomCen, rTopCen, cenLongInt, 40.kMetres, 200)

object EGrid160Warm
{ def apply(rBottomCen: Int, rTopCen: Int, cenLongInt: Int) = new EGrid160Warm(rBottomCen, rTopCen, cenLongInt)
}

/** object for creating earth grids with 160km hexs, with a c scale of 80km. */
object EGrid160
{ /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 40Km or hex scale 160km. */

  def e0(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 0)
  def e30(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160Warm = EGrid160Warm(rBottomCen, rTopCen,11)

  def scen0: EScenWarm =
  { val grid: EGridWarmFull = e0(276)
    EScenWarm(grid, Terr160E0.terrs, Terr160E0.sTerrs, "!60km 0E")
  }

//  def scen1: EScenWarm =
//  { val grid: EGridWarm = e30(276)
//    EScenWarm(grid, Terr160E30.terrs, )
//  }
//
//  def scen2: EScenWarm =
//  { val grid: EGridWarm = e60(276)
//    EScenWarm(grid, Terr160E60.terrs, )
//  }
//
//  def scen3: EScenWarm =
//  { val grid: EGridWarm = e90(276)
//    EScenWarm(grid, Terr160E90.terrs, )
//  }
//
//  def scen4: EScenWarm =
//  { val grid: EGridWarm = e120(276)
//    EScenWarm(grid, Terr160E120.terrs, )
//  }
//
//  def scen5: EScenWarm =
//  { val grid: EGridWarm = e150(276)
//    EScenWarm(grid, Terr160E150.terrs, )
//  }
//
//  def scen6: EScenWarm =
//  { val grid: EGridWarm = e180(276)
//    EScenWarm(grid, Terr160E180.terrs, )
//  }
//
//  def scen7: EScenWarm =
//  { val grid: EGridWarm = w150(276)
//    EScenWarm(grid, Terr160W150.terrs, )
//  }
//
//  def scen8: EScenWarm =
//  { val grid: EGridWarm = w120(276)
//    EScenWarm(grid, Terr160W120.terrs, )
//  }
//
//  def scen9: EScenWarm =
//  { val grid: EGridWarm = w90(276)
//    EScenWarm(grid, Terr160W90.terrs, )
//  }
//
//  def scen10: EScenWarm =
//  { val grid: EGridWarm = w60(276)
//    EScenWarm(grid, Terr160W60.terrs, )
//  }

  def scen11: EScenWarm =
  { val grid: EGridWarmFull = w30(276)
    EScenWarm(grid, Terr160W30.terrs,Terr160W30.sTerrs)
  }
}