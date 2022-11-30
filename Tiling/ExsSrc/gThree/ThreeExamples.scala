/* Copyright 2018-22 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package gThree
import prid._, phex._, gOne.hp1.GSys

object ThreeScen1 extends ThreeScen(0)
{ override implicit val gridSys: HGrid = GSys.g1
  override val terrs: HCenLayer[Terr] = gridSys.newHCenLayer[Terr](Plain)
  import terrs.{setRowPart => srp}
  srp(6, 2, 2, Water)
  srp(4, 4, 2, Woods)
  val units: HCenOptLayer[Lunit] = gridSys.newHCenOptLayer
  units.unsafeSetSome(4, 4, Lunit(TeamA, HexDR))
  units.unsafeSetSomes((4, 8, Lunit(TeamB, HexLt, HexDL)), (6, 10, Lunit(TeamA)))
}

object ThreeScen2 extends ThreeScen(0)
{ override implicit val gridSys: HGrid = HGridReg(2, 8, 2, 18)
  override val terrs: HCenLayer[Terr] = gridSys.newHCenLayer[Terr](Plain)
  import terrs.{setRowPart => srp}
  srp(6, 2, 2, Water)
  srp(4, 4, 2, Woods)
  val units: HCenOptLayer[Lunit] = gridSys.newHCenOptLayer
  units.unsafeSetSome(4, 4, Lunit(TeamA, HexDR))
  units.unsafeSetSomes((4, 8, Lunit(TeamB, HexLt, HexDL)), (6, 10, Lunit(TeamA)))
}

/** 2nd Scenario of Game Four. Has a larger number of hexs. */
object ThreeScen3 extends ThreeScen(0)
{ override implicit val gridSys: HGrid = HGridReg(2, 20, 4, 60)
  override val terrs: HCenLayer[Terr] = gridSys.newHCenLayer[Terr](Plain)
  import terrs.{setRowPart => sr}
  sr(6,6, 4, Water)
  sr(8,4, 5, Water)
  sr(10, 6, 4, Water)
  sr(16, 4, 4, Water)
  sr(14, 42, 3, Woods)
  sr(16, 44, 3, Woods)
  sr(18, 42, 3, Woods)
  val units: HCenOptLayer[Lunit] = gridSys.newHCenOptLayer
  units.unsafeSetSome(4, 4, Lunit(TeamA))

}

/** 3rd Scenario of Game Four. Has a larger number of hexs. */
object ThreeScen4 extends ThreeScen(0)
{
  override implicit val gridSys: HGridReg = HGridReg(2, 6, 2, 10)
  override val terrs: HCenLayer[Terr] = gridSys.newHCenLayer[Terr](Plain)

  import terrs.{completeRow => sr}
  val units: HCenOptLayer[Lunit] = gridSys.newHCenOptLayer
}