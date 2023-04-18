/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._

/** A Civ scenario turn state. */
trait CivScen  extends HSysTurnScen
{ override def title: String = "Civ Scenario"

  /** tile terrain. */
  val terrs: HCenLayer[VTile]
  val sTerrs: HSideOptLayer[VSide, VSideSome]
  val corners: HCornerLayer
  val lunits: HCenArrLayer[Warrior]
}

/** A Civ scenario state at turn 0. */
trait CivScenStart extends CivScen
{ override def turn: Int = 0
}

/** Civ scenario 1. */
object Civ1 extends CivScenStart
{ override implicit val gridSys: HGrid = HGridReg(2, 12, 4, 40)
  override val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Plain)
  override val sTerrs: HSideOptLayer[VSide, VSideSome] = gridSys.newSideOptLayer[VSide, VSideSome]
  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer

  val help = new VTerrSetter(gridSys, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(12, Plain * 4, Hill, Mountain * 2, Plain * 3),
      VRow(7, Mouth(22, HVUL, River), ThreeWay(24, River), ThreeWay(26, River)),
      VRow(5, Mouth(22, HVDL, River), VertIn(24, HVUL, River)),
      TRow(4, Hill * 3, Plain * 7),
    )
  }
  help.run

  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(10, 18, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))
}

/** Civ scenario 2. */
object Civ2 extends CivScenStart
{ override val title: String = "CivRise Scen 2"
  override implicit val gridSys: HGrid = HGridReg(2, 14, 4, 42)
  val terrs: HCenLayer[VTile] = gridSys.newHCenLayer[VTile](Sea)
  override val sTerrs: HSideOptLayer[VSide, VSideSome] = gridSys.newSideOptLayer[VSide, VSideSome]
  override val corners: HCornerLayer = gridSys.newHVertOffsetLayer

  val help = new VTerrSetter(gridSys, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(10, Plain, Hland(2, 5), Sea * 4, Isle() * 2, Sea * 2),
      VRow(9, Mouth(8, HVDn)),
      TRow(8, Plain * 4, Hland(4, 0), Sea * 3, Plain, Sea),
      VRow(7, Mouth(28, HVDR), Mouth(40, HVUp, River)),
      TRow(6, Plain * 3, Sea * 2, Hland(3, 4, Mountain), Sea, Plain, Plain * 2),
      VRow(5, Mouth(30, HVUp), VertIn(38, HVDR, River), VertIn(40, HVUL, River)),
      TRow(4, Sea * 5, Mountain * 3, Plain * 2),
      VRow(3, Mouth(30, HVDn), Mouth(36, HVDL, River), ThreeWay(38, River), Mouth(40, HVDR, River)),
      TRow(2, Plain * 10),
    )
  }
  help.run

  val lunits: HCenArrLayer[Warrior] = gridSys.newHCenArrLayer[Warrior]
  lunits.set(8, 16, Warrior(Uruk))
  lunits.set(6, 10, Warrior(Eridu))
}