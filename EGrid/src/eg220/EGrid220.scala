/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import egrid._

/** object for creating earth grids with 220km hexs, with a c scale of 80km. */
object EGrid220
{ /** Returns an [[RArr]] sequence of 220km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 188): RArr[EGrid220LongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGrid220LongFull(rBottomCen, rTopCen, i %% 12) }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 55Km or hex scale 220km. */
  def e0(rBottomCen: Int, rTopCen: Int = 188): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 0)

//  def e30(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen,  1)
//  def e60(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 2)
//  def e90(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 3)
//  def e120(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 4)
//  def e150(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 5)
//  def e180(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 6)
//  def w150(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 7)
//  def w120(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 8)
//  def w90(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 9)
//  def w60(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 10)
//  def w30(rBottomCen: Int, rTopCen: Int = 160): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen,11)
//
  def scen0: EScenBasic = EScenBasic(Terr220E0.grid, Terr220E0.terrs, Terr220E0.sTerrs, Terr220E0.corners, "220km 0E")
//
//  def scen1: EScenBasic = EScenBasic(Terr220E30.grid, Terr220E30.terrs, Terr220E30.sTerrs, Terr220E30.corners, "220km 30E")
//
//  def scen2: EScenBasic =
//  { val grid: EGridLongFull = Terr220E60.grid
//    EScenBasic(grid, Terr220E60.terrs, Terr220E60.sTerrs, Terr220E60.corners, "220km 60E")
//  }
//
//  def scen3: EScenBasic =
//  { val grid: EGridLongFull = Terr220E90.grid
//    EScenBasic(grid, Terr220E90.terrs, Terr220E90.sTerrs, Terr220E90.corners, "220km 90E")
//  }
//
//  def scen4: EScenBasic =
//  { val grid: EGridLongFull = Terr220E120.grid
//    EScenBasic(grid, Terr220E120.terrs, Terr220E120.sTerrs, Terr220E120.corners, "220km 120E")
//  }
//
//  def scen5: EScenBasic =
//  { val grid: EGridLongFull = Terr220E150.grid
//    EScenBasic(grid, Terr220E150.terrs, Terr220E150.sTerrs, Terr220E150.corners, "220km 150E")
//  }
//
//  def scen6: EScenBasic =
//  { val grid: EGridLongFull = Terr220E180.grid
//    EScenBasic(grid, Terr220E180.terrs, Terr220E180.sTerrs, Terr220E180.corners, "220km 180E")
//  }
//
//  def scen7: EScenBasic =
//  { val grid: EGridLongFull = Terr220W150.grid
//    EScenBasic(grid, Terr220W150.terrs, Terr220W150.sTerrs, Terr220W150.corners, "220km 150W")
//  }
//
//  def scen8: EScenBasic =
//  { val grid: EGridLongFull = Terr220W120.grid
//    EScenBasic(grid, Terr220W120.terrs, Terr220W120.sTerrs, Terr220W120.corners, "220km 120W")
//  }
//
//  def scen9: EScenBasic =
//  { val grid: EGridLongFull = Terr220W90.grid
//    EScenBasic(grid, Terr220W90.terrs, Terr220W90.sTerrs, Terr220W90.corners, "220km 90W")
//  }
//
//  def scen10: EScenBasic =
//  { val grid: EGridLongFull = Terr220W60.grid
//    EScenBasic(grid, Terr220W60.terrs, Terr220W60.sTerrs, Terr220W60.corners, "220km 60W")
//  }
//
//  def scen11: EScenBasic =
//  { val grid: EGridLongFull = Terr220W30.grid
//    EScenBasic(grid, Terr220W30.terrs, Terr220W30.sTerrs, Terr220W30.corners, "220km 30W")
//  }
}