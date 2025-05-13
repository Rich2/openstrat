/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 220km [[WTile]] terrain for 15° west to 15° east centred on 0° east. A tile area of 41915.629km².
 * [[Isle7]] 6917.716km² => 9209.977km². Zealand 7180km² shares its hex with Jutland, Corsica 8680km².
 * [[Isle6]] 4952.921km² => 6917.716km². Balearic Islands 5040km²
 * [[Isle5]] 3315.591km² => 4952.921km². Majorca 3640.11.km² + Menorca  695.8.km² = 4335.9km².
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
  { override val rows: RArr[DateRow] = RArr(
    TileRow(202, SeaIcePerm),
    TileRow(200, SeaIcePerm),
    TileRow(198, SeaIcePerm),
    TileRow(196, SeaIcePerm),
    TileRow(194, SeaIcePerm, SeaIceWinter),
    VertRow(193, OrigLt(514, HVDn, 7, SeaIceWinter)),
    TileRow(192, sea, mtainTundra),
    VertRow(191, BendIn(514, HVUR, 13, sea, SeaIceWinter), BendOut(516, HVDL)),
    TileRow(190, sea * 2, mtainIce),
    VertRow(189, BendIn(516, HVUR, 13, SeaIceWinter, sea), OrigRt(518, HVUL, 7, SeaIceWinter)),
    VertRow(179, OrigLt(520, HVDn, 7)),
    TileRow(178, sea * 4, hillyTaiga),
    VertRow(177, OrigRt(520, HVUp, 7)),
    TileRow(176, sea * 5, hillyTaiga),
    VertRow(175, BendIn(516, HVDR, 13), BendIn(518, HVDn, 13), OrigLt(520, HVUL)),
    TileRow(174, sea * 4, mtainDepr, hillyTaiga),
    VertRow(173, BendIn(514, HVDR, 13), BendOut(516, HVUL, 7)),
    TileRow(172, sea, Isle3(mtainDepr), sea * 2, mtainDepr, hillyTaiga, taiga),
    VertRow(171, BendIn(508, HVDR, 13), BendIn(510, HVDn, 13), BendIn(512, HVDL, 13), BendIn(514, HVUR, 13), BendOut(516, HVDL)),
    TileRow(170, sea * 2, Land(Hilly, Boreal, CivMix), sea, hillyTaiga, taiga * 2),

    VertRow(169, BendIn(502, HVDR, 13), BendIn(504, HVDn, 13), ThreeDown(506, 0, 6, 6), ThreeUp(508, 7, 7, 0), ThreeDown(510, 7, 0, 7), BendIn(512, HVUL, 13),
      OrigRt(516, HVUp, 7)),

    TileRow(168, sea, hillyTundra, hillyOce, sea * 2, mtainLakesOceForest, hillyLakesContForest),

    VertRow(167, BendIn(502, HVUR, 13), ThreeDown(504, 10, 6, 0), BendIn(506, HVUL, 6), BendIn(512, HVDL, 13), OrigLt(518, HVDR, 7), ThreeDown(520, 9, 10, 0),
      Bend(522, HVDn, 3, 7), BendOut(524, HVDL, 7)),

    TileRow(166, sea * 2, hillyOce * 2, sea * 2, oceanic, continental),

    VertRow(165, OrigRt(502, HVUR), ThreeUp(504, 6, 9, 0), BendIn(506, HVDL, 7), BendOut(512, HVUR, 7), BendIn(514, HVDL, 13), OrigRt(520, HVUp, 7),
      BendIn(524, HVUR, 13), OrigRt(526, HVUL), OrigMax(530, HVUR)),

    TileRow(164, sea, oceanic, hillyOce, oceanic, sea * 2, oceanic, sea),

    VertRow(163, BendIn(504, HVDR, 13), ThreeUp(506, 3, 13, 3), Orig(508, HVUL, 6, 4), BendOut(514, HVUR, 7), ThreeDown(516, 0, 13, 13), BendIn(518, HVDn, 13),
      Bend(520,HVUp, 3, 3), OrigLt(522, HVDL, 7)),

    TileRow(162, sea, oceanic, hillyOce, oceanic * 2, oceanic * 4),
    VertRow(161, OrigLt(498, HVDn), BendIn(504, HVUR, 13), BendIn(506, HVDL, 13), BendIn(514, HVDR, 13), Bend(516, HVUL, 13, 7)),
    TileRow(160, sea, hillyOce * 2, oceanic * 2, oceanic * 2, hillyOce, oceanic),

    VertRow(159, BendIn(498, HVUR, 13), BendIn(500, HVUp, 13), BendOut(502, HVDn, 7), ThreeDown(504, 13, 6, 0), BendIn(506, HVUL, 13), BendIn(508, HVDR, 12),
      BendIn(510, HVDn, 13), Bend(512, HVUp, 1, 7), Bend(514, HVUL, 3, 3)),

    TileRow(158, sea * 2, hillyOce, oceanic * 2, hillyOce * 4),
    VertRow(157, BendIn(504, HVUR, 13), BendIn(506, HVUp, 13), Bend(508, HVUL, 13, 7)),
    TileRow(156, sea * 3, oceanic * 3, hillyOce * 2, oceanic, hillyOce),
    VertRow(155, Orig(506, HVDR, 4, 2), BendOut(508, HVDL)),
    TileRow(154, sea * 4, oceanic * 2, hillyOce, mtainDepr * 3),
    VertRow(153, BendIn(508, HVUR), Orig(510, HVUL, 4, 2), Orig(530, HVDn, 6, 1)),
    TileRow(152, sea * 4, oceanic, hillyOce, mtainDepr, hillyOce, oceanic, hillyOce),
    VertRow(151, BendIn(496, HVDR, 13), OrigLt(498, HVDL, 7), BendIn(530, HVUR, 13), BendOut(532, HVDL, 7)),
    TileRow(150, sea, hillyOce * 4, hillySub * 3, Isle7(mtainSubForest), hillySub, mtainSubForest),

    VertRow(149, BendIn(496, HVUR, 13), BendMin(498, HVDL, 1), BendOut(518, HVDR, 7), BendOut(520, HVDn, 7), ThreeDown(522, 13, 13, 0), ThreeUp(524, 9, 13, 13),
      BendInRt(526, HVUp, 9, 6), BendIn(532, HVUR, 13), BendIn(534, HVUp, 13), Bend(536, HVDn, 6, 7)),

    TileRow(148, sea * 2, mtainSubForest, sahel, hillySavannah * 3, sea, hillySub, sea, hillyOce),

    VertRow(147, BendIn(496, HVDR, 13), BendMin(498, HVUL), OrigLt(514, HVDR), BendIn(516, HVUp, 13), BendIn(518, HVUL, 13), BendIn(522, HVUR, 13),
      BendOut(524, HVDL), OrigRt(528, HVDn, 7), OrigLt(530, HVDR, 7), OrigRt(532, HVUL, 7)),

    TileRow(146, sea, hillySavannah, hillySub, hillySavannah * 2, sea, Isle6(hillySavannah), sea, hillyOce, sea * 2),
    VertRow(145, OrigRt(496, HVUp, 7), OrigRt(514, HVDn, 7), BendIn(524, HVUR, 7), BendIn(526, HVUp), BendIn(528, HVUL), BendIn(530, HVDR, 10), OrigLt(532, HVDL, 7)),
    TileRow(144, sea * 2, savannah, hillySavannah, hillySahel * 2, sea * 4, hillySavannah),
    VertRow(143, OrigLt(500, HVDn, 7), BendIn(508, HVDR, 13), BendIn(510, HVDn, 13), BendMax(512, HVUp), BendInRt(514, HVUL, 13, 7), BendInLt(530, HVUR, 13, 2), ThreeDown(532, 10, 0, 13)),
    TileRow(142, sea * 3, hillyOce, mtainSavannah, hillySavannah * 6, sea),

    VertRow(141, BendIn(498, HVDR, 13), ThreeUp(500, 13, 13, 0), BendIn(502, HVUp, 13), BendOut(504, HVDn, 7), BendIn(506, HVUp, 13), BendIn(508, HVUL, 13), OrigLt(532, HVUp, 7)),

    TileRow(140, sea * 2, hillySavannah * 2, hillyDeshot * 2, deshot * 2, hillyDeshot * 2, sea * 2),
    VertRow(139, OrigRt(534, HVDR, 7), OrigLt(536, HVUL, 7)),
    TileRow(138, sea * 2, sahel, hillySavannah, hillyDeshot, deshot * 7),
    TileRow(136, sea * 2, mtainSahel * 2, hillySahel, deshot * 8),
    TileRow(134, sea, hillyDeshot * 2, deshot * 10),
    VertRow(133, BendIn(486, HVDR, 13), OrigLt(488, HVDL, 7)),
    TileRow(132, deshot * 13),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(150, "" * 8, "Corsica")
    str(146, "" * 6, "Majorca", "", "Sardinia south")
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