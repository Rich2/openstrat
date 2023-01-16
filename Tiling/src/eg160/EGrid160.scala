/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import egrid._, geom._, pglobe._, prid.phex._

/** object for creating earth grisds with 160km hexs, with a c scale of 80km. */
object EGrid160
{
  /** Returns an [[RArr]] sequence of 320km full earth grids. */
  def grids(num: Int, startIndex: Int, rBottomCen: Int, rTopCen: Int = 320): RArr[EGrid160LongFull] =
    iUntilMap(startIndex, startIndex + num) { i => EGrid160LongFull(rBottomCen, rTopCen, i %% 12) }

  /** Factory method for creating a main Earth grid centred on 0 degrees east of scale cScale 40Km or hex scale 160km. */
  def e0(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 0)

  /** Factory method for creating a main Earth grid centred on 30 degrees east of scale cScale 40Km or hex scale 160km. */
  def e30(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen,  1)

  def e60(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 2)
  def e90(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 3)
  def e120(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 4)
  def e150(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 5)
  def e180(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 6)
  def w150(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 7)
  def w120(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 8)
  def w90(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 9)
  def w60(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen, 10)
  def w30(rBottomCen: Int = 276, rTopCen: Int = 320): EGrid160LongFull = EGrid160LongFull(rBottomCen, rTopCen,11)

  def britGrid: EGrid160LongPart =
  { val array = Array[Int](1, 504, 3, 506, 3, 504, 1, 510, 2, 504, 1, 506, 2, 504)
    new EGrid160LongPart(280, 0, array)
  }

  def scen0: EScenBasic =
  { val grid: EGridLongFull = e0(276)
    EScenBasic(grid, Terr160E0.terrs, Terr160E0.sTerrsDepr, Terr160E0.corners, "!60km 0E")
  }

  def scen1: EScenBasic =
  { val grid: EGridLongFull = e30(276)
    EScenBasic(grid, Terr160E30.terrs, Terr160E30.sTerrsDepr, Terr160E30.corners, "!60km 30E")
  }
  //
  //  def scen2: EScenWarm =
  //  { val grid: EGridWarm = e60(276)
  //    EScenWarm(grid, Terr160E60.terrs, )
  //  }
  //
  //  def scen3: EScenWarm =
  //  { val grid: EGridWarm = e90(276)
  //    EScenWarm(grid, Terr160E90.terrs, )
  //  }
  //
  //  def scen4: EScenWarm =
  //  { val grid: EGridWarm = e120(276)
  //    EScenWarm(grid, Terr160E120.terrs, )
  //  }
  //
  //  def scen5: EScenWarm =
  //  { val grid: EGridWarm = e150(276)
  //    EScenWarm(grid, Terr160E150.terrs, )
  //  }
  //
  //  def scen6: EScenWarm =
  //  { val grid: EGridWarm = e180(276)
  //    EScenWarm(grid, Terr160E180.terrs, )
  //  }
  //
  //  def scen7: EScenWarm =
  //  { val grid: EGridWarm = w150(276)
  //    EScenWarm(grid, Terr160W150.terrs, )
  //  }
  //
  //  def scen8: EScenWarm =
  //  { val grid: EGridWarm = w120(276)
  //    EScenWarm(grid, Terr160W120.terrs, )
  //  }
  //
  //  def scen9: EScenWarm =
  //  { val grid: EGridWarm = w90(276)
  //    EScenWarm(grid, Terr160W90.terrs, )
  //  }
  //
  //  def scen10: EScenWarm =
  //  { val grid: EGridWarm = w60(276)
  //    EScenWarm(grid, Terr160W60.terrs, )
  //  }

  def scen11: EScenBasic =
  { val grid: EGridLongFull = w30(276)
    EScenBasic(grid, Terr160W30.terrs,Terr160W30.sTerrsDepr, Terr160W30.corners)
  }

  /*def ScenBrit: EScenBasic = new EScenBasic
  {  override def title: String = "160KM Britain"

    override implicit val gridSys: EGrid160LongPart = britGrid
    override val terrs: HCenLayer[WTile] = ??? //regTerrs
    override val sTerrs: HSideBoolLayer = ??? //regSTerrs //gridSys.newSideBools
    //sTerrs.setTruesInts((142, 508), (143, 507))
  }*/
}
