/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import egrid._

/** object for creating earth grids with 120km hexs, with a c scale of 80km. */
object EGrid120
{ /** Returns an [[RArr]] sequence of 120km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 360): RArr[EGrid120LongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGrid120LongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 360): EGrid120LongMulti = new EGrid120LongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid120.grids(numGridsIn, headInt, bottomR, topR)
    override def headGridInt: Int = headInt
    override def gridsXSpacing: Double = 40
    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))
  }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 55Km or hex scale 120km. */
  def e0(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 0)

  def e30(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 1)
  def e60(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 2)
//  def e90(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 3)
//  def e120(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 4)
//  def e150(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 5)
//  def e180(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 6)
//  def w150(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 7)
//  def w120(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 8)
//  def w90(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 9)
//  def w60(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 360): EGrid120LongFull = EGrid120LongFull(rBottomCen, rTopCen,11)
//
  def scen0: EScenBasic = EScenBasic(Terr120E0.grid, Terr120E0.terrs, Terr120E0.sTerrs, Terr120E0.corners, Terr120E0.hexNames, "120km 0E")
  def scen1: EScenBasic = EScenBasic(Terr120E30.grid, Terr120E30.terrs, Terr120E30.sTerrs, Terr120E30.corners, Terr120E30.hexNames, "120km 30E")
  def scen2: EScenBasic = EScenBasic(Terr120E60.grid, Terr120E60.terrs, Terr120E60.sTerrs, Terr120E60.corners, Terr120E60.hexNames, "120km 60E")

//
//  def scen2: EScenBasic =
//  { val grid: EGridLongFull = Terr120E60.grid
//    EScenBasic(grid, Terr120E60.terrs, Terr120E60.sTerrs, Terr120E60.corners, "120km 60E")
//  }
//
//  def scen3: EScenBasic =
//  { val grid: EGridLongFull = Terr120E90.grid
//    EScenBasic(grid, Terr120E90.terrs, Terr120E90.sTerrs, Terr120E90.corners, "120km 90E")
//  }
//
//  def scen4: EScenBasic =
//  { val grid: EGridLongFull = Terr120E120.grid
//    EScenBasic(grid, Terr120E120.terrs, Terr120E120.sTerrs, Terr120E120.corners, "120km 120E")
//  }
//
//  def scen5: EScenBasic =
//  { val grid: EGridLongFull = Terr120E150.grid
//    EScenBasic(grid, Terr120E150.terrs, Terr120E150.sTerrs, Terr120E150.corners, "120km 150E")
//  }
//
//  def scen6: EScenBasic =
//  { val grid: EGridLongFull = Terr120E180.grid
//    EScenBasic(grid, Terr120E180.terrs, Terr120E180.sTerrs, Terr120E180.corners, "120km 180E")
//  }
//
//  def scen7: EScenBasic =
//  { val grid: EGridLongFull = Terr120W150.grid
//    EScenBasic(grid, Terr120W150.terrs, Terr120W150.sTerrs, Terr120W150.corners, "120km 150W")
//  }
//
//  def scen8: EScenBasic =
//  { val grid: EGridLongFull = Terr120W120.grid
//    EScenBasic(grid, Terr120W120.terrs, Terr120W120.sTerrs, Terr120W120.corners, "120km 120W")
//  }
//
//  def scen9: EScenBasic =
//  { val grid: EGridLongFull = Terr120W90.grid
//    EScenBasic(grid, Terr120W90.terrs, Terr120W90.sTerrs, Terr120W90.corners, "120km 90W")
//  }
//
//  def scen10: EScenBasic =
//  { val grid: EGridLongFull = Terr120W60.grid
//    EScenBasic(grid, Terr120W60.terrs, Terr120W60.sTerrs, Terr120W60.corners, "120km 60W")
//  }
//
  def scen11: EScenBasic = EScenBasic(Terr120W30.grid, Terr120W30.terrs, Terr120W30.sTerrs, Terr120W30.corners, Terr120W30.hexNames, "120km 30W")
//  }
}