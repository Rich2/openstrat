/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 15° west to 15° east centred on 0° east. A tile area of 41915.629km².
 * [[Isle7]] 6917.716km² => 9209.977km². Zealand 7180km² shares its hex with Jutland.
 * [[Isle6]] 4952.921km² => 6917.716km².
 * [[Isle5]] 3315.591km² => 4952.921km².Mallorca 3640km².
 * [[Isle4]] 2005.728km² => 3315.591km². Outer Hebrides 3071km².
 * [[Isle3]] 1023.330km² => 2005.728km². Faroe Islands 1399 km².
 * Below 1023.330km². Isle of Man 572km². */
object Terr220E0 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e0(132)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(202, SeaIcePerm),
    TRow(200, SeaIcePerm),
    TRow(198, SeaIcePerm),
    TRow(192, sea, mtainOld),
    VRow(179, MouthLt(520, HVUp, 7)),
    TRow(178, sea * 4, hillyTaiga),
    VRow(177, MouthRt(520, HVDn, 7)),
    TRow(176, sea * 5, hillyTaiga),
    VRow(175, BendIn(516, HVDR, 13), BendIn(518, HVDn, 13), MouthLt(520, HVDR)),
    TRow(174, sea * 4, mtainOld, hillyTaiga),
    VRow(173, BendIn(514, HVDR, 13), BendOut(516, HVUL, 7)),
    TRow(172, sea, Isle3(mtainOld), sea * 2, mtainOld, hillyTaiga, taiga),
    VRow(171, BendIn(508, HVDR, 13), BendIn(510, HVDn, 13), BendIn(512, HVDL, 13), BendIn(514, HVUR, 13), BendOut(516, HVDL)),
    TRow(170, sea * 2, Land(Hilly, Boreal, CivMix), sea, hillyTaiga, taiga * 2),

    VRow(169, BendIn(502, HVDR, 13), BendIn(504, HVDn, 13), ThreeDown(506, 0, 6, 6), ThreeUp(508, 7, 7, 0), ThreeDown(510, 7, 0, 7), BendIn(512, HVUL, 13),
      MouthRt(516, HVDn, 7)),

    TRow(168, sea, hillyTundra, hillyOce, sea * 2, hillyTaiga, taiga),

    VRow(167, BendIn(502, HVUR, 13), ThreeDown(504, 10, 6, 0), BendIn(506, HVUL, 6), BendIn(512, HVDL, 13), MouthLt(518, HVUL, 7), ThreeDown(520, 9, 10, 0),
      Bend(522, HVDn, 3, 7), BendOut(524, HVDL, 7)),

    TRow(166, sea * 2, hillyOce * 2, sea * 2, oceanic, oceanic),

    VRow(165, MouthRt(502, HVDL), ThreeUp(504, 6, 9, 0), BendIn(506, HVDL, 7), BendOut(512, HVUR, 7), BendIn(514, HVDL, 13), MouthRt(520, HVDn, 7),
      BendIn(524, HVUR, 13), SourceRt(526, HVUL), SourceMax(530, HVUR)),

    TRow(164, sea, oceanic, hillyOce, oceanic, sea * 2, oceanic, sea),

    VRow(163, BendIn(504, HVDR, 13), ThreeUp(506, 3, 13, 3), Source(508, HVUL, 6, 4), BendOut(514, HVUR, 7), ThreeDown(516, 0, 13, 13), BendIn(518, HVDn, 13),
      Bend(520,HVUp, 3, 3), MouthLt(522, HVUR, 7)),

    TRow(162, sea, oceanic, hillyOce, oceanic * 2, oceanic * 4),
    VRow(161, MouthLt(498, HVUp), BendIn(504, HVUR, 13), BendIn(506, HVDL, 13), BendIn(514, HVDR, 13), Bend(516, HVUL, 13, 7)),
    TRow(160, sea, hillyOce * 2, oceanic * 2, oceanic * 2, hillyOce, oceanic),

    VRow(159, BendIn(498, HVUR, 13), BendIn(500, HVUp, 13), BendOut(502, HVDn, 7), ThreeDown(504, 13, 6, 0), BendIn(506, HVUL, 13), BendIn(508, HVDR, 12),
      BendIn(510, HVDn, 13), Bend(512, HVUp, 1, 7), Bend(514, HVUL, 3, 3)),

    TRow(158, sea * 2, hillyOce, oceanic * 2, hillyOce * 4),
    VRow(157, BendIn(504, HVUR, 13), BendIn(506, HVUp, 13), Bend(508, HVUL, 13, 7)),
    TRow(156, sea * 3, oceanic * 3, hillyOce * 2, oceanic, hillyOce),
    VRow(155, Source(506, HVDR, 4, 2), BendOut(508, HVDL)),
    TRow(154, sea * 4, oceanic * 2, hillyOce, mtainOld * 3),
    VRow(153, BendIn(508, HVUR), Source(510, HVUL, 4, 2), Source(530, HVDn, 6, 1)),
    TRow(152, sea * 4, oceanic, hillyOce, mtainOld, hillyOce, oceanic, hillyOce),
    VRow(151, BendIn(496, HVDR, 13), SourceLt(498, HVDL, 7), BendIn(530, HVUR, 13), BendOut(532, HVDL, 7)),
    TRow(150, sea, hillyOce, hillyOce * 4, hillyOce, hillyOce, hillyOce, hillyOce, mtainOld),
    VRow(149, BendIn(496, HVUR, 13), BendOut(518, HVDR), BendOut(520, HVDn), BendOut(526, HVUp), BendIn(532, HVUR, 13), BendIn(534, HVUp, 13), Bend(536, HVDn, 6, 7)),
    TRow(148, sea * 2, mtainOld, sahel, hillySavannah * 3, sea, hillyOce, sea, hillyOce),

    VRow(147, BendIn(496, HVDR, 13), BendMin(498, HVUL), MouthLt(514, HVUL), ThreeDown(516, 13, 6, 0), ThreeUp(518, 0, 6, 13), BendOut(524, HVDL), MouthLt(530, HVUL, 7),
      MouthRt(532, HVDR, 7)),

    TRow(146, sea, hillySavannah, hillyOce, hillySavannah * 2, sea, hillySavannah, sea, hillyOce, sea * 2),
    VRow(145, SourceLt(496, HVUp, 7), SourceRt(514, HVDn, 7)),
    TRow(144, sea * 2, savannah, hillySavannah, hillySahel * 2, sea * 4, hillySavannah),
    VRow(143, MouthLt(500, HVUp, 7), BendIn(508, HVDR, 13), BendIn(510, HVDn, 13), BendMax(512, HVUp), BendInRt(514, HVUL, 13, 7)),
    TRow(142, sea * 3, hillyOce, mtainSavannah, hillySavannah * 6, sea),

    VRow(141, BendIn(498, HVDR, 13), ThreeUp(500, 13, 13, 0), BendIn(502, HVUp, 13), BendOut(504, HVDn, 7), BendIn(506, HVUp, 13), BendIn(508, HVUL, 13)),

    TRow(140, sea * 2, hillySavannah, hillyOce, hillyDeshot * 2, deshot * 2, hillyDeshot * 2, sea * 2),
    TRow(138, sea * 2, oceanic, deshot, hillyDeshot, deshot * 7),
    TRow(136, sea * 2, mtainOld * 3, deshot * 8),
    TRow(134, sea, hillyDeshot * 2, deshot * 10),
    TRow(132, sea, deshot * 12)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(146, "" * 6, "Majorca")
  }
}

object BritReg220
{ def britGrid: EGrid220Long = EGrid220Long.reg(156, 170, 0, 500, 520)
  def britTerrs: LayerHcRefSys[WTile] = Terr220E0.terrs.spawn(Terr220E0.grid, britGrid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] = Terr220E0.sTerrs.spawn(Terr220E0.grid, britGrid)
  def britCorners: HCornerLayer = Terr220E0.corners.spawn(Terr220E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  { override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid220Long = britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}