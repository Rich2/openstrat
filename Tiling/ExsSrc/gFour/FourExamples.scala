/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gFour
import prid._, phex._, pgui._

object FourLaunch extends GuiLaunchStd
{
  override def settingStr: String = "gFour"

  override def default: (CanvasPlatform => Any, String) = (GFourGui(_, FourScen1), "JavaFx Game Four")

  override def launch(s2: Int, s3: String): (CanvasPlatform => Any, String) = s2 match {
    case 1 => (GFourGui(_, FourScen1), "JavaFx Game Four")
    case 2 => (GFourGui(_, FourScen2), "JavaFx Game Four")
    case _ => (GFourGui(_, FourScen1), "JavaFx Game Four")
  }
}

object FourScen1 extends FourScen(0)
{ override implicit val gridSys: HGrid = HGridRegOrig(2, 8, 2, 18)
  override val terrs: HCenDGrid[Terr] = gridSys.newHCenDGrid[Terr](Plain)
  import terrs.{setRowPart => srp}
  srp(6, 2, 2, Water)
  srp(4, 4, 2, Woods)
  val units: HCenOptDGrid[Lunit] = gridSys.newHCenOptDGrid
  units.unsafeSetSome(4, 4, Lunit(TeamA, HexDR))
  units.unsafeSetSomes((4, 8, Lunit(TeamB, HStepLt, HexDL)), (6, 10, Lunit(TeamA)))
}

/** 2nd Scenario of Game Four. Has a larger number of hexs. */
object FourScen2 extends FourScen(0)
{ override implicit val gridSys: HGrid = HGridRegOrig(2, 20, 4, 60)
  override val terrs: HCenDGrid[Terr] = gridSys.newHCenDGrid[Terr](Plain)
  import terrs.{setRowPart => sr}
  sr(6,6, 4, Water)
  sr(8,4, 5, Water)
  sr(10, 6, 4, Water)
  sr(16, 4, 4, Water)
  sr(14, 42, 3, Woods)
  sr(16, 44, 3, Woods)
  sr(18, 42, 3, Woods)
  val units: HCenOptDGrid[Lunit] = gridSys.newHCenOptDGrid
  units.unsafeSetSome(4, 4, Lunit(TeamA))

}

/** 3rd Scenario of Game Four. Has a larger number of hexs. */
object FourScen3 extends FourScen(0)
{
  override implicit val gridSys: HGridRegOrig = HGridRegOrig(2, 6, 2, 10)
  override val terrs: HCenDGrid[Terr] = gridSys.newHCenDGrid[Terr](Plain)

  import terrs.{completeRow => sr}
  val units: HCenOptDGrid[Lunit] = gridSys.newHCenOptDGrid
}