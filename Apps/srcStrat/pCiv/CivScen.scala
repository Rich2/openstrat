/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pCiv
import prid._, phex._

/** A Civ scenario turn state. */
trait CivScen  extends HSysTurnScen
{ override def title: String = "Civ Scenario"

  /** tile terrain. */
  val terrs: LayerHcRefSys[VTile]
  val sTerrs: LayerHSOptSys[VSep, VSepSome]
  val corners: HCornerLayer
  val lunits: LayerHcRArr[Warrior]
}

/** A Civ scenario state at turn 0. */
trait CivScenStart extends CivScen
{ override def turn: Int = 0
}

/** Civ scenario 1. */
object Civ1 extends CivScenStart
{ override implicit val gridSys: HGrid = HGridRect.minMax(2, 12, 4, 40)
  override val terrs: LayerHcRefSys[VTile] = LayerHcRefSys[VTile](Plain)
  override val sTerrs: LayerHSOptSys[VSep, VSepSome] = LayerHSOptSys[VSep, VSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new VTerrSetter(gridSys, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(12, Plain * 4, Hill, Mountain * 2, Plain * 3),
      VRow(9, BendAll(26, HVDR, River), Source(28, HVDL, River)),

      VRow(7, Source(22, HVDR, River), ThreeDown(24, River), ThreeUp(26, River), BendAll(28, HVUp, River), BendAll(30, HVDn, River),
        BendAll(32, HVUp, River), BendAll(34, HVDn, River), BendAll(36, HVUp, River), BendAll(38, HVDn, River), Source(40, HVUL, River)),

      TRow(6, Plain * 5, SepB(River)),
      VRow(5, Source(22, HVUR, River), BendAll(24, HVUL, River)),
      TRow(4, Hill * 3, Plain * 7),
    )
  }
  help.run

  val lunits: LayerHcRArr[Warrior] = LayerHcRArr[Warrior]()
  lunits.set1(10, 18, Warrior(Uruk))
  lunits.set1(6, 10, Warrior(Eridu))
}

/** Civ scenario 2. */
object Civ2 extends CivScenStart
{ override val title: String = "CivRise Scen 2"
  override implicit val gridSys: HGrid = HGridRect.minMax(2, 14, 4, 42)
  val terrs: LayerHcRefSys[VTile] = LayerHcRefSys[VTile](Sea)
  override val sTerrs: LayerHSOptSys[VSep, VSepSome] = LayerHSOptSys[VSep, VSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new VTerrSetter(gridSys, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(13, BendAll(18, HVUp), BendAll(22, HVUp)),
      TRow(12, Sea * 3, Cape(3, 4, Hill), Isthmus(0), Cape(0, 4, Hill), Sea * 4),
      VRow(11, Source(12, HVUL), BendAll(18, HVDn), BendAll(22, HVDn)),
      TRow(10, Plain, Cape(5, 2), Sea * 4, Isle10() * 2, Sea * 2),
      VRow(9, Source(8, HVUp), Source(18, HVUR)),
      TRow(8, Plain * 4, Cape(0, 4), Sea * 3, Plain, Sea),
      VRow(7, Source(18, HVDR), Source(28, HVUL), Source(40, HVDn, River)),
      TRow(6, Plain * 3, Sea * 2, Cape(5, 2, Mountain), Sea, Plain, Plain * 2),
      VRow(5, Source(24, HVUp), Source(30, HVDn), BendAll(38, HVDR, River), BendAll(40, HVUL, River)),
      TRow(4, Sea * 5, Mountain * 3, Plain * 2),
      VRow(3, Source(30, HVUp), Source(36, HVUR, River), ThreeUp(38, River), Source(40, HVUL, River)),
      TRow(2, Plain * 10),
    )
  }
  help.run

  val lunits: LayerHcRArr[Warrior] = LayerHcRArr[Warrior]()
  lunits.set1(8, 16, Warrior(Uruk))
  lunits.set1(6, 10, Warrior(Eridu))
}