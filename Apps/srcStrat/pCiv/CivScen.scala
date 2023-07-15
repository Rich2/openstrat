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
  val lunits: HCenRArrLayer[Warrior]
}

/** A Civ scenario state at turn 0. */
trait CivScenStart extends CivScen
{ override def turn: Int = 0
}

/** Civ scenario 1. */
object Civ1 extends CivScenStart
{ override implicit val gridSys: HGrid = HGridReg(2, 12, 4, 40)
  override val terrs: HCenLayer[VTile] = HCenLayer[VTile](Plain)
  override val sTerrs: HSideOptLayer[VSide, VSideSome] = HSideOptLayer[VSide, VSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new VTerrSetter(gridSys, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(12, Plain * 4, Hill, Mountain * 2, Plain * 3),
      VRow(9, Mouth(26, HVUp, River)),

      VRow(7, Mouth(22, HVUL, River), ThreeWay(24, River), ThreeWay(26, River), VertIn(28, HVUp, River), VertIn(30, HVDn, River),
        VertIn(32, HVUp, River), VertIn(34, HVDn, River), VertIn(36, HVUp, River), VertIn(38, HVDn, River), Mouth(40, HVDR, River)),

      TRow(6, Plain * 5, SideB(River)),
      VRow(5, Mouth(22, HVDL, River), VertIn(24, HVUL, River)),
      TRow(4, Hill * 3, Plain * 7),
    )
  }
  help.run

  val lunits: HCenRArrLayer[Warrior] = HCenRArrLayer[Warrior]()
  lunits.set1(10, 18, Warrior(Uruk))
  lunits.set1(6, 10, Warrior(Eridu))
}

/** Civ scenario 2. */
object Civ2 extends CivScenStart
{ override val title: String = "CivRise Scen 2"
  override implicit val gridSys: HGrid = HGridReg(2, 14, 4, 42)
  val terrs: HCenLayer[VTile] = HCenLayer[VTile](Sea)
  override val sTerrs: HSideOptLayer[VSide, VSideSome] = HSideOptLayer[VSide, VSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new VTerrSetter(gridSys, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(11, Mouth(12, HVDR)),
      TRow(10, Plain, Hland(2, 5), Sea * 4, Isle() * 2, Sea * 2),
      VRow(9, Mouth(8, HVDn), Mouth(18, HVDL)),
      TRow(8, Plain * 4, Hland(4, 0), Sea * 3, Plain, Sea),
      VRow(7, Mouth(18, HVUL), Mouth(28, HVDR), Mouth(40, HVUp, River)),
      TRow(6, Plain * 3, Sea * 2, Hland(2, 5, Mountain), Sea, Plain, Plain * 2),
      VRow(5, Mouth(24, HVDn), Mouth(30, HVUp), VertIn(38, HVDR, River), VertIn(40, HVUL, River)),
      TRow(4, Sea * 5, Mountain * 3, Plain * 2),
      VRow(3, Mouth(30, HVDn), Mouth(36, HVDL, River), ThreeWay(38, River), Mouth(40, HVDR, River)),
      TRow(2, Plain * 10),
    )
  }
  help.run

  val lunits: HCenRArrLayer[Warrior] = HCenRArrLayer[Warrior]()
  lunits.set1(8, 16, Warrior(Uruk))
  lunits.set1(6, 10, Warrior(Eridu))
}