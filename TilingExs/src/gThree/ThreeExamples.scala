/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, phex._, pgui._

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
{ override implicit val grider: HGrid = HGridReg(2, 8, 2, 18)
  override val terrs: HCenArr[Terr] = grider.newTileArr[Terr](Plain)
  import terrs.{setRowPart => srp}
  srp(6, 2, 2, Water)
  srp(4, 4, 2, Woods)
  val units: HCenArrOpt[Lunit] = grider.newTileArrOpt
  units.unsafeSetSome(4, 4, Lunit(TeamA, HStepDR))
  units.unsafeSetSomes((4, 8, Lunit(TeamB, HStepLt, HStepDL)), (6, 10, Lunit(TeamA)))
}

/** 2nd Scenario of Game Three. Has a larger number of hexs. */
object ThreeScen2 extends ThreeScenStart
{ override implicit val grider: HGrid = HGridReg(2, 20, 4, 60)
  override val terrs: HCenArr[Terr] = grider.newTileArr[Terr](Plain)
  import terrs.{setRowPart => sr}
  sr(6,6, 4, Water)
  sr(8,4, 5, Water)
  sr(10, 6, 4, Water)
  sr(16, 4, 4, Water)
  sr(14, 42, 3, Woods)
  sr(16, 44, 3, Woods)
  sr(18, 42, 3, Woods)
  val units: HCenArrOpt[Lunit] = grider.newTileArrOpt
  units.unsafeSetSome(4, 4, Lunit(TeamA))

}

/** 3rd Scenario of Game Three. Has a larger number of hexs. */
object ThreeScen3 extends ThreeScenStart {
  override implicit val grider: HGridReg = HGridReg(2, 6, 2, 10)
  override val terrs: HCenArr[Terr] = grider.newTileArr[Terr](Plain)

  import terrs.{completeRow => sr}
  val units: HCenArrOpt[Lunit] = grider.newTileArrOpt
}