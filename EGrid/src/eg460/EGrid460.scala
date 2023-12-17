/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import egrid._

/** object for creating earth grids with 460km hexs, with a c scale of 160km. */
object EGrid460
{ /** Returns an [[RArr]] sequence of 460km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 146): RArr[EGrid460LongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGrid460LongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 146): EGrid460LongMulti = new EGrid460LongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid460.grids(numGridsIn, headInt, bottomR, topR)

    override def headGridInt: Int = headInt

    override def gridsXSpacing: Double = 40

    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))
  }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 0)
  def e30(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 146): EGrid460LongFull = EGrid460LongFull(rBottomCen, rTopCen,11)

  def scen0: EScenBasic = EScenBasic(Terr460E0.grid, Terr460E0.terrs, Terr460E0.sTerrs, Terr460E0.corners, "460km 0E")
  def scen1: EScenBasic = EScenBasic(Terr460E30.grid, Terr460E30.terrs, Terr460E30.sTerrs, Terr460E30.corners, "460km 30E")
  def scen2: EScenBasic = EScenBasic(Terr460E60.grid, Terr460E60.terrs, Terr460E60.sTerrs, Terr460E60.corners, "460km 60E")
  def scen3: EScenBasic = EScenBasic(Terr460E90.grid, Terr460E90.terrs, Terr460E90.sTerrs, Terr460E90.corners, "460km 90E")
  def scen4: EScenBasic = EScenBasic(Terr460E120.grid, Terr460E120.terrs, Terr460E120.sTerrs, Terr460E120.corners, "460km 120E")
  def scen5: EScenBasic = EScenBasic(Terr460E150.grid, Terr460E150.terrs, Terr460E150.sTerrs, Terr460E150.corners, "460km 150E")
//  def scen6: EScenBasic = EScenBasic(Terr460E180.grid, Terr460E180.terrs, Terr460E180.sTerrs, Terr460E180.corners, "460km 180E")
//  def scen7: EScenBasic = EScenBasic(Terr460W150.grid, Terr460W150.terrs, Terr460W150.sTerrs, Terr460W150.corners, "460km 150W")
//  def scen8: EScenBasic = EScenBasic(Terr460W120.grid, Terr460W120.terrs, Terr460W120.sTerrs, Terr460W120.corners, "460km 120W")
//  def scen9: EScenBasic = EScenBasic(Terr460W90.grid, Terr460W90.terrs, Terr460W90.sTerrs, Terr460W90.corners, "460km 90W")
//  def scen10: EScenBasic = EScenBasic(Terr460W60.grid, Terr460W60.terrs, Terr460W60.sTerrs, Terr460W60.corners, "460km 60W")
//  def scen11: EScenBasic = EScenBasic(Terr460W30.grid, Terr460W30.terrs, Terr460W30.sTerrs, Terr460W30.corners, "460km 30W")
}