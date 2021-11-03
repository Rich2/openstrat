/* Copyright 2018-21 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, pgui._

object ThreeLaunch extends GuiLaunchStd
{
  override def settingStr: String = "gThree"

  override def default: (CanvasPlatform => Any, String) = (GThreeGui(_, ThreeScen1), "JavaFx Game Three")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match {
    case 1 => (GThreeGui(_, ThreeScen1), "JavaFx Game Three")
    case 2 => (GThreeGui(_, ThreeScen2), "JavaFx Game Three")
    case _ => (GThreeGui(_, ThreeScen1), "JavaFx Game Three")
  }
}

object ThreeScen1 extends ThreeScenStart
{ override implicit val grid: HGrid = HGridReg(2, 8, 2, 18)
  override val terrs: HCenArr[Terr] = grid.newTileArr[Terr](Plain)
  import terrs.{setRow => sr}
  sr(6,2, Water * 2)
  sr(4, 4, Woods * 2)
  val units: HCenArrOpt[Lunit] = grid.newTileArrOpt
  units.unsafeSetSome(4, 4, Lunit(TeamA, HStepDR))
  units.unsafeSetSomes((4, 8, Lunit(TeamB, HStepLt, HStepDL)), (6, 10, Lunit(TeamA)))
}

/** 2nd Scenario of Game Three. Has a larger number of hexs. */
object ThreeScen2 extends ThreeScenStart
{ override implicit val grid: HGrid = HGridReg(2, 20, 4, 60)
  override val terrs: HCenArr[Terr] = grid.newTileArr[Terr](Plain)
  import terrs.{setRow => sr}
  sr(6,6, Water * 4)
  sr(8,4, Water * 5)
  sr(10,6, Water * 4)
  sr(16,4, Water * 4)
  sr(14, 42, Woods * 3)
  sr(16, 44, Woods * 3)
  sr(18, 42, Woods * 3)
  val units: HCenArrOpt[Lunit] = grid.newTileArrOpt
  units.unsafeSetSome(4, 4, Lunit(TeamA))

}

/** 3rd Scenario of Game Three. Has a larger number of hexs. */
object ThreeScen3 extends ThreeScenStart {
  override implicit val grid: HGridReg = HGridReg(2, 6, 2, 10)
  override val terrs: HCenArr[Terr] = grid.newTileArr[Terr](Plain)

  import terrs.{setRow => sr}
  val units: HCenArrOpt[Lunit] = grid.newTileArrOpt

}