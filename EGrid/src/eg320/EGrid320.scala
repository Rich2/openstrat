/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._, prid.phex._

/** object for creating earth grids with 320km hexs, with a c scale of 80km. */
object EGrid320
{ /** Returns an [[RArr]] sequence of 320km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 160): RArr[EGrid320LongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGrid320LongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 160): EGrid320LongMulti = new EGrid320LongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid320.grids(numGridsIn, headInt, bottomR, topR)

    override def headGridInt: Int = headInt

    override def gridsXSpacing: Double = 40

    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))

    override def adjTilesOfTile(tile: HCen): HCenArr = ???
  }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 0)

  def e30(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 160): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen,11)

  def scen0: EScenBasic = EScenBasic(Terr320E0.grid, Terr320E0.terrs, Terr320E0.sTerrs, Terr320E0.corners, "320km 0E")

  def scen1: EScenBasic = EScenBasic(Terr320E30.grid, Terr320E30.terrs, Terr320E30.sTerrs, Terr320E30.corners, "320km 30E")

  def scen2: EScenBasic =
  { val grid: EGridLongFull = Terr320E60.grid
    EScenBasic(grid, Terr320E60.terrs, Terr320E60.sTerrs, Terr320E60.corners, "320km 60E")
  }

  def scen3: EScenBasic =
  { val grid: EGridLongFull = Terr320E90.grid
    EScenBasic(grid, Terr320E90.terrs, Terr320E90.sTerrs, Terr320E90.corners, "320km 90E")
  }

  def scen4: EScenBasic =
  { val grid: EGridLongFull = Terr320E120.grid
    EScenBasic(grid, Terr320E120.terrs, Terr320E120.sTerrs, Terr320E120.corners, "320km 120E")
  }

  def scen5: EScenBasic =
  { val grid: EGridLongFull = Terr320E150.grid
    EScenBasic(grid, Terr320E150.terrs, Terr320E150.sTerrs, Terr320E150.corners, "320km 150E")
  }

  def scen6: EScenBasic =
  { val grid: EGridLongFull = Terr320E180.grid
    EScenBasic(grid, Terr320E180.terrs, Terr320E180.sTerrs, Terr320E180.corners, "320km 180E")
  }

  def scen7: EScenBasic =
  { val grid: EGridLongFull = Terr320W150.grid
    EScenBasic(grid, Terr320W150.terrs, Terr320W150.sTerrs, Terr320W150.corners, "320km 150W")
  }

  def scen8: EScenBasic =
  { val grid: EGridLongFull = Terr320W120.grid
    EScenBasic(grid, Terr320W120.terrs, Terr320W120.sTerrs, Terr320W120.corners, "320km 120W")
  }

  def scen9: EScenBasic =
  { val grid: EGridLongFull = Terr320W90.grid
    EScenBasic(grid, Terr320W90.terrs, Terr320W90.sTerrs, Terr320W90.corners, "320km 90W")
  }

  def scen10: EScenBasic =
  { val grid: EGridLongFull = Terr320W60.grid
    EScenBasic(grid, Terr320W60.terrs, Terr320W60.sTerrs, Terr320W60.corners, "320km 60W")
  }

  def scen11: EScenBasic =
  { val grid: EGridLongFull = Terr320W30.grid
    EScenBasic(grid, Terr320W30.terrs, Terr320W30.sTerrs, Terr320W30.corners, "320km 30W")
  }
}