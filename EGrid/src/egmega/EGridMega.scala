/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import egrid._

/** object for creating earth grids with 1000km hexs, with a c scale of 250km. */
object EGridMega
{ /** Returns an [[RArr]] sequence of Megakm full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 118): RArr[EGridMegaLongFull] =
    iUntilMap(startIndex, startIndex + num){ i => EGridMegaLongFull(rBottomCen, rTopCen, i %% 12) }

  def multi(numGridsIn: Int, headInt: Int, bottomR: Int, topR: Int = 118): EGridMegaLongMulti = new EGridMegaLongMulti
  { ThisSys =>
    override val grids: RArr[EGridLongFull] = EGridMega.grids(numGridsIn, headInt, bottomR, topR)

    override def headGridInt: Int = headInt

    override def gridsXSpacing: Double = 40

    override val gridMans: RArr[EGridLongMan] = iUntilMap(numGridsIn)(EGridLongMan(_, ThisSys))
  }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 20Km or hex scale 80km. */
  def e0(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 0)
  def e30(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen,  1)
  def e60(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 11)

  def scen0: EScenBasic = EScenBasic(TerrMegaE0.grid, TerrMegaE0.terrs, TerrMegaE0.sTerrs, TerrMegaE0.corners, TerrMegaE0.hexNames, "1000km 0E")
  def scen1: EScenBasic = EScenBasic(TerrMegaE30.grid, TerrMegaE30.terrs, TerrMegaE30.sTerrs, TerrMegaE30.corners, TerrMegaE0.hexNames, "1000km 30E")
  def scen2: EScenBasic = EScenBasic(TerrMegaE60.grid, TerrMegaE60.terrs, TerrMegaE60.sTerrs, TerrMegaE60.corners, TerrMegaE0.hexNames, "1000km 60E")
  def scen3: EScenBasic = EScenBasic(TerrMegaE90.grid, TerrMegaE90.terrs, TerrMegaE90.sTerrs, TerrMegaE90.corners, TerrMegaE0.hexNames, "1000km 90E")
  def scen4: EScenBasic = EScenBasic(TerrMegaE120.grid, TerrMegaE120.terrs, TerrMegaE120.sTerrs, TerrMegaE120.corners, TerrMegaE0.hexNames, "1000km 120E")
  def scen5: EScenBasic = EScenBasic(TerrMegaE150.grid, TerrMegaE150.terrs, TerrMegaE150.sTerrs, TerrMegaE150.corners, TerrMegaE0.hexNames, "1000km 150E")
  def scen6: EScenBasic = EScenBasic(TerrMegaE180.grid, TerrMegaE180.terrs, TerrMegaE180.sTerrs, TerrMegaE180.corners, TerrMegaE0.hexNames, "1000km 180E")
  def scen7: EScenBasic = EScenBasic(TerrMegaW150.grid, TerrMegaW150.terrs, TerrMegaW150.sTerrs, TerrMegaW150.corners, TerrMegaE0.hexNames, "1000km 150W")
  def scen8: EScenBasic = EScenBasic(TerrMegaW120.grid, TerrMegaW120.terrs, TerrMegaW120.sTerrs, TerrMegaW120.corners, TerrMegaE0.hexNames, "1000km 120W")
  def scen9: EScenBasic = EScenBasic(TerrMegaW90.grid, TerrMegaW90.terrs, TerrMegaW90.sTerrs, TerrMegaW90.corners, TerrMegaE0.hexNames, "1000km 90W")
  def scen10: EScenBasic = EScenBasic(TerrMegaW60.grid, TerrMegaW60.terrs, TerrMegaW60.sTerrs, TerrMegaW60.corners, TerrMegaE0.hexNames, "1000km 60W")
  def scen11: EScenBasic = EScenBasic(TerrMegaW30.grid, TerrMegaW30.terrs, TerrMegaW30.sTerrs, TerrMegaW30.corners, TerrMegaE0.hexNames, "1000km 30W")
}