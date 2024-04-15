/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import egrid._

/** object for creating earth grids with 320km hexs, with a c scale of 80km. */
object EGrid320
{ /** Returns an [[RArr]] sequence of 320km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 166): RArr[EGrid320LongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGrid320LongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 166): EGrid320LongMulti = new EGrid320LongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid320.grids(numGridsIn, headInt, bottomR, topR)
    override def headGridInt: Int = headInt
    override def gridsXSpacing: Double = 40
    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))
  }

  /** Factory method for creating a main Earth grid centred on 0° east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 0)

  def e30(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 166): EGrid320LongFull = EGrid320LongFull(rBottomCen, rTopCen,11)

  def scen0: EScenBasic = EScenBasic(Terr320E0.grid, Terr320E0.terrs, Terr320E0.sTerrs, Terr320E0.corners, Terr320E0.hexNames, "320km 0°E")
  def scen1: EScenBasic = EScenBasic(Terr320E30.grid, Terr320E30.terrs, Terr320E30.sTerrs, Terr320E30.corners, Terr320E0.hexNames, "320km 30°E")
  def scen2: EScenBasic = EScenBasic(Terr320E60.grid, Terr320E60.terrs, Terr320E60.sTerrs, Terr320E60.corners, Terr320E0.hexNames, "320km 60°E")
  def scen3: EScenBasic = EScenBasic(Terr320E90.grid, Terr320E90.terrs, Terr320E90.sTerrs, Terr320E90.corners, Terr320E0.hexNames, "320km 90°E")
  def scen4: EScenBasic = EScenBasic(Terr320E120.grid, Terr320E120.terrs, Terr320E120.sTerrs, Terr320E120.corners, Terr320E0.hexNames, "320km 120°E")
  def scen5: EScenBasic = EScenBasic(Terr320E150.grid, Terr320E150.terrs, Terr320E150.sTerrs, Terr320E150.corners, Terr320E0.hexNames, "320km 150°E")
  def scen6: EScenBasic = EScenBasic(Terr320E180.grid, Terr320E180.terrs, Terr320E180.sTerrs, Terr320E180.corners, Terr320E0.hexNames, "320km 180°E")
  def scen7: EScenBasic = EScenBasic(Terr320W150.grid, Terr320W150.terrs, Terr320W150.sTerrs, Terr320W150.corners, Terr320E0.hexNames, "320km 150°W")
  def scen8: EScenBasic = EScenBasic(Terr320W120.grid, Terr320W120.terrs, Terr320W120.sTerrs, Terr320W120.corners, Terr320E0.hexNames, "320km 120°W")
  def scen9: EScenBasic = EScenBasic(Terr320W90.grid, Terr320W90.terrs, Terr320W90.sTerrs, Terr320W90.corners, Terr320E0.hexNames, "320km 90°W")
  def scen10: EScenBasic = EScenBasic(Terr320W60.grid, Terr320W60.terrs, Terr320W60.sTerrs, Terr320W60.corners, Terr320E0.hexNames, "320km 60°W")
  def scen11: EScenBasic = EScenBasic(Terr320W30.grid, Terr320W30.terrs, Terr320W30.sTerrs, Terr320W30.corners, Terr320E0.hexNames, "320km 30°W")
}