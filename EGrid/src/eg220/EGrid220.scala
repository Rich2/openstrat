/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import egrid._

/** object for creating earth grids with 220km hexs, with a c scale of 80km. */
object EGrid220
{ /** Returns an [[RArr]] sequence of 220km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 202): RArr[EGrid220LongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGrid220LongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 202): EGrid220LongMulti = new EGrid220LongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid220.grids(numGridsIn, headInt, bottomR, topR)
    override def headGridInt: Int = headInt
    override def gridsXSpacing: Double = 40
    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))
  }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 55Km or hex scale 220km. */
  def e0(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 0)

  def e30(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 202): EGrid220LongFull = EGrid220LongFull(rBottomCen, rTopCen,11)

  def scen0: EScenBasic = EScenBasic(Terr220E0.grid, Terr220E0.terrs, Terr220E0.sTerrs, Terr220E0.corners, Terr220E0.hexNames, "220km 0E")
  def scen1: EScenBasic = EScenBasic(Terr220E30.grid, Terr220E30.terrs, Terr220E30.sTerrs, Terr220E30.corners, Terr220E30.hexNames, "220km 30E")
  def scen2: EScenBasic = EScenBasic(Terr220E60.grid, Terr220E60.terrs, Terr220E60.sTerrs, Terr220E60.corners, Terr220E60.hexNames, "220km 60E")
  //def scen3: EScenBasic = EScenBasic(Terr220E90.grid, Terr220E90.terrs, Terr220E90.sTerrs, Terr220E90.corners, "220km 90E")
//  }
//
//  def scen4: EScenBasic =
//  { val grid: EGridLongFull = Terr220E120.grid
//    EScenBasic(grid, Terr220E120.terrs, Terr220E120.sTerrs, Terr220E120.corners, "220km 120E")
//  }
  def scen5: EScenBasic = EScenBasic(Terr220E150.grid, Terr220E150.terrs, Terr220E150.sTerrs, Terr220E150.corners, Terr220E0.hexNames, "220km 150E")
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
  def scen8: EScenBasic = EScenBasic(Terr220W120.grid, Terr220W120.terrs, Terr220W120.sTerrs, Terr220W120.corners, Terr220E0.hexNames, "220km 120W")
  def scen9: EScenBasic = EScenBasic(Terr220W90.grid, Terr220W90.terrs, Terr220W90.sTerrs, Terr220W90.corners, Terr220E0.hexNames, "220km 90W")
  def scen10: EScenBasic = EScenBasic(Terr220W60.grid, Terr220W60.terrs, Terr220W60.sTerrs, Terr220W60.corners, Terr220E0.hexNames, "220km 60W")
  def scen11: EScenBasic = EScenBasic(Terr220W30.grid, Terr220W30.terrs, Terr220W30.sTerrs, Terr220W30.corners, Terr220E0.hexNames, "220km 30W")
}