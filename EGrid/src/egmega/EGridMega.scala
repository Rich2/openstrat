/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import egrid._

/** object for creating earth grids with 640km hexs, with a c scale of 80km. */
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
//  def e90(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 3)
//  def e120(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 4)
//  def e150(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 5)
//  def e180(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 6)
//  def w150(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 7)
//  def w120(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 8)
//  def w90(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 9)
//  def w60(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen, 10)
//  def w30(rBottomCen: Int, rTopCen: Int = 118): EGridMegaLongFull = EGridMegaLongFull(rBottomCen, rTopCen,11)
//
  def scen0: EScenBasic = EScenBasic(TerrMegaE0.grid, TerrMegaE0.terrs, TerrMegaE0.sTerrs, TerrMegaE0.corners, "1000km 0E")
  def scen1: EScenBasic = EScenBasic(TerrMegaE30.grid, TerrMegaE30.terrs, TerrMegaE30.sTerrs, TerrMegaE30.corners, "1000km 30E")
  def scen2: EScenBasic = EScenBasic(TerrMegaE60.grid, TerrMegaE60.terrs, TerrMegaE60.sTerrs, TerrMegaE60.corners, "1000km 60E")
//  }
//
//  def scen3: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaE90.grid
//    EScenBasic(grid, TerrMegaE90.terrs, TerrMegaE90.sTerrs, TerrMegaE90.corners, "Megakm 90E")
//  }
//
//  def scen4: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaE120.grid
//    EScenBasic(grid, TerrMegaE120.terrs, TerrMegaE120.sTerrs, TerrMegaE120.corners, "Megakm 120E")
//  }
//
//  def scen5: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaE150.grid
//    EScenBasic(grid, TerrMegaE150.terrs, TerrMegaE150.sTerrs, TerrMegaE150.corners, "Megakm 150E")
//  }
//
//  def scen6: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaE180.grid
//    EScenBasic(grid, TerrMegaE180.terrs, TerrMegaE180.sTerrs, TerrMegaE180.corners, "Megakm 180E")
//  }
//
//  def scen7: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaW150.grid
//    EScenBasic(grid, TerrMegaW150.terrs, TerrMegaW150.sTerrs, TerrMegaW150.corners, "Megakm 150W")
//  }
//
//  def scen8: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaW120.grid
//    EScenBasic(grid, TerrMegaW120.terrs, TerrMegaW120.sTerrs, TerrMegaW120.corners, "Megakm 120W")
//  }
//
//  def scen9: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaW90.grid
//    EScenBasic(grid, TerrMegaW90.terrs, TerrMegaW90.sTerrs, TerrMegaW90.corners, "Megakm 90W")
//  }
//
//  def scen10: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaW60.grid
//    EScenBasic(grid, TerrMegaW60.terrs, TerrMegaW60.sTerrs, TerrMegaW60.corners, "Megakm 60W")
//  }
//
//  def scen11: EScenBasic =
//  { val grid: EGridLongFull = TerrMegaW30.grid
//    EScenBasic(grid, TerrMegaW30.terrs, TerrMegaW30.sTerrs, TerrMegaW30.corners, "Megakm 30W")
//  }
}