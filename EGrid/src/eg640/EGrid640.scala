/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import egrid._

/** object for creating earth grids with 640km hexs, with a c scale of 160km. */
object EGrid640
{ /** Returns an [[RArr]] sequence of 640km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 130): RArr[EGrid640LongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGrid640LongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 130): EGrid640LongMulti = new EGrid640LongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid640.grids(numGridsIn, headInt, bottomR, topR)

    override def headGridInt: Int = headInt

    override def gridsXSpacing: Double = 40

    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))
  }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 0)
  def e30(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 130): EGrid640LongFull = EGrid640LongFull(rBottomCen, rTopCen,11)

  def scen0: EScenBasic = EScenBasic(Terr640E0.grid, Terr640E0.terrs, Terr640E0.sTerrs, Terr640E0.corners, "640km 0E")
  def scen1: EScenBasic = EScenBasic(Terr640E30.grid, Terr640E30.terrs, Terr640E30.sTerrs, Terr640E30.corners, "640km 30E")
  def scen2: EScenBasic = EScenBasic(Terr640E60.grid, Terr640E60.terrs, Terr640E60.sTerrs, Terr640E60.corners, "640km 60E")
  def scen3: EScenBasic = EScenBasic(Terr640E90.grid, Terr640E90.terrs, Terr640E90.sTerrs, Terr640E90.corners, "640km 90E")
  def scen4: EScenBasic = EScenBasic(Terr640E120.grid, Terr640E120.terrs, Terr640E120.sTerrs, Terr640E120.corners, "640km 120E")
//  }
//
//  def scen5: EScenBasic =
//  { val grid: EGridLongFull = Terr640E150.grid
//    EScenBasic(grid, Terr640E150.terrs, Terr640E150.sTerrs, Terr640E150.corners, "640km 150E")
//  }
//
//  def scen6: EScenBasic =
//  { val grid: EGridLongFull = Terr640E180.grid
//    EScenBasic(grid, Terr640E180.terrs, Terr640E180.sTerrs, Terr640E180.corners, "640km 180E")
//  }
//
//  def scen7: EScenBasic =
//  { val grid: EGridLongFull = Terr640W150.grid
//    EScenBasic(grid, Terr640W150.terrs, Terr640W150.sTerrs, Terr640W150.corners, "640km 150W")
//  }
//
//  def scen8: EScenBasic =
//  { val grid: EGridLongFull = Terr640W120.grid
//    EScenBasic(grid, Terr640W120.terrs, Terr640W120.sTerrs, Terr640W120.corners, "640km 120W")
//  }
//
//  def scen9: EScenBasic =
//  { val grid: EGridLongFull = Terr640W90.grid
//    EScenBasic(grid, Terr640W90.terrs, Terr640W90.sTerrs, Terr640W90.corners, "640km 90W")
//  }
//
//  def scen10: EScenBasic =
//  { val grid: EGridLongFull = Terr640W60.grid
//    EScenBasic(grid, Terr640W60.terrs, Terr640W60.sTerrs, Terr640W60.corners, "640km 60W")
//  }
//
  def scen11: EScenBasic = EScenBasic(Terr640W30.grid, Terr640W30.terrs, Terr640W30.sTerrs, Terr640W30.corners, "640km 30W")
}