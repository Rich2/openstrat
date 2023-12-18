/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import egrid._, geom._, pglobe._, prid.phex._

/** object for creating earth grisds with 160km hexs, with a c scale of 80km. */
object EGrid160
{
  /** Returns an [[RArr]] sequence of 320km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 320): RArr[EGrid160LongFull] =
    iUntilMap(startIndex, startIndex + num) { i => EGrid160LongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 320): EGrid160LongMulti = new EGrid160LongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGrid160.grids(numGridsIn, headInt, bottomR, topR)
    override def headGridInt: Int = headInt
    override def gridsXSpacing: Double = 40
    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))
  }

  /** Factory method for creating a main Earth grid centred on 0° east of scale cScale 40Km or hex scale 160km. */
  def e0(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 0)

  /** Factory method for creating a main Earth grid centred on 30° east of scale cScale 40Km or hex scale 160km. */
  def e30(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen,  1)

  def e60(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen,11)

  def britGrid: EGrid160LongPart =
  { val array = Array[Int](1, 504, 3, 506, 3, 504, 1, 510, 2, 504, 1, 506, 2, 504)
    new EGrid160LongPart(280, 0, array)
  }

  def scen0: EScenBasic = EScenBasic(Terr160E0.grid, Terr160E0.terrs, Terr160E0.sTerrs, Terr160E0.corners, "!60km 0E")
  def scen1: EScenBasic = EScenBasic(Terr160E30.grid, Terr160E30.terrs, Terr160E30.sTerrs, Terr160E30.corners, "!60km 30E")

  def scen4: EScenBasic = EScenBasic(Terr160E120.grid, Terr160E120.terrs, Terr160E120.sTerrs, Terr160E120.corners)
  def scen5: EScenBasic = EScenBasic(Terr160E150.grid, Terr160E150.terrs, Terr160E150.sTerrs, Terr160E150.corners)

  def scen8: EScenBasic = EScenBasic(Terr160W120.grid, Terr160W120.terrs, Terr160W120.sTerrs, Terr160W120.corners, "!60km 120W")
  def scen9: EScenBasic = EScenBasic(Terr160W90.grid, Terr160W90.terrs, Terr160W90.sTerrs, Terr160W90.corners)
  def scen10: EScenBasic = EScenBasic(Terr160W60.grid, Terr160W60.terrs, Terr160W60.sTerrs, Terr160W60.corners)
  def scen11: EScenBasic = EScenBasic(Terr160W30.grid, Terr160W30.terrs, Terr160W30.sTerrs, Terr160W30.corners)
}