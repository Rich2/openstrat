/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** 160km [[WTile]] terrain for 15° west to 15° east, centred on 0° east.
 * [[Isle10]] 7815.879km² => 9547.930km² Corsica 8722 km2.
 * [[Isle8]] 4871.392km² => 6257.033km² Balearic Islands 5040 km².
 * [[Isle7]] 3658.957km² => 4871.392km². Majorca 3640.11.km² + Menorca  695.8.km² = 4335.9km².
 * [[Isle5]] 1753.701km² => 2619.726km². Isle of Lewis 2224 km².
 * [[Isle4]] 1060.881km² => 1753.701km².
 * [[Isle3]] 541.265km² => 1060.881km². Ibiza 654km², Isle of Man 572km².
 * South Uist 320.3km² + North Uist 303km² + Benbcuala 82.03km² + Berneray 10.1km² + Grimsay 8.33km² =723.76km² */
object Terr160E0 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e0(254)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(338, SeaIcePerm),
    TRow(336, SeaIcePerm),
    TRow(334, SeaIcePerm * 2),
    TRow(332, SeaIcePerm * 2),
    TRow(330, SeaIcePerm, SeaIceWinter * 2),
    VRow(329, BendIn(514, HVDR, 13, SeaIceWinter), BendIn(516, HVDn, 13, SeaIceWinter), BendOut(518, HVUp, 7, SeaIceWinter)),
    VRow(327, BendIn(514, HVUR, 13, SeaIceWinter)),
    TRow(328, SeaIcePerm, SeaIceWinter, mtainTundra),
    VRow(327, BendOut(516, HVDL, 7, SeaIceWinter)),
    TRow(326, SeaIcePerm, SeaIceWinter * 2, mtainTundra),
    VRow(325, BendIn(516, HVUR, 13, sea, SeaIceWinter), BendOut(518, HVDL, 7)),
    TRow(324, SeaIceWinter * 2, sea, mtainTundra),
    VRow(323, BendIn(518, HVUR, 13), OrigRt(520, HVUL)),
    VRow(311, BendIn(524, HVDR, 13), BendOut(526, HVUL, 7)),
    TRow(310, sea * 6, mtainCont),
    VRow(309, OrigRt(524, HVUp, 7)),
    TRow(308, sea * 7, mtainBoreal),
    TRow(306, sea * 7, mtainBoreal),
    TRow(304, sea * 6, mtainOce, mtainLakesTaiga),
    TRow(302, sea * 6, hillyTaiga, hillyTaiga, taiga),
    TRow(300, sea * 6, mtainDepr, hillyTaiga * 2),
    TRow(298, sea * 6, mtainDepr, mtainDepr, hillyTaiga * 2),
    TRow(296, sea * 6, hillyTaiga * 2, taiga * 2),
    VRow(295, BendIn(500, HVDR, 11), BendIn(502, HVDn, 11), ThreeDown(504, 0, 13, 11), OrigLt(506, HVDL, 7), OrigRt(528, HVDn, 7)),
    TRow(294, sea * 2, hillyOce, hillyOce, sea * 3, hillyOce, hillyTaiga, taiga),
    VRow(293, BendIn(498, HVDR, 13), ThreeUp(500, 11, 13, 0), ThreeDown(502, 11, 3, 13), Bend(504, HVUL, 11, 1), OrigRt(506, HVUR), BendIn(508, HVDn, 8)),
    TRow(292, sea * 2, hillyOce, hillyOce, hillyOce, sea * 4, oceanic, oceanic),
    VRow(291, BendIn(498, HVUR, 13), ThreeDown(500, 13, 13, 0), BendIn(502, HVUL, 13)),
    TRow(290, sea * 2, hillyOce, hillyOce, sea * 4, oceanic, Isle10(oceanic), oceanic),
    VRow(289, OrigRt(498, HVUR, 7), ThreeUp(500, 8, 8, 0), Bend(502, HVDL, 1, 5)),
    TRow(288, sea * 2, oceanic, hillyOce, oceanic, sea * 4, oceanic, sea),
    VRow(287, BendIn(502, HVUR, 13), ThreeDown(504, 10, 13, 12), Orig(506, HVDL, 1, 6), BendIn(520, HVDR, 13),  BendIn(522, HVDn, 13), OrigLt(524, HVUL, 7)),
    TRow(286, sea * 2, oceanic, oceanic, oceanic * 2, sea * 2, oceanic * 4),
    VRow(285, BendIn(504, HVUL, 13), OrigRt(516, HVDR, 7), ThreeDown(518, 0, 10, 13)),
    TRow(284, sea, oceanic * 2, hillyOce, oceanic * 3, oceanic * 5),
    VRow(283, Bend(516, HVDR, 8, 7), Bend(518, HVUL, 13, 4)),
    TRow(282, sea * 4, hillyOce, oceanic * 2, oceanic * 2, hillyOce * 2, oceanic * 2),
    VRow(281, Orig(510, HVUR, 6, 7), BendIn(512, HVDn, 13), BendIn(514, HVUp, 10), BendIn(516, HVUL, 13)),
    TRow(280, sea * 4, hillyOce, sea, oceanic * 2, hillyOce * 5),
    VRow(279, OrigLt(502, HVDR, 7), BendInLt(504, HVUp, 13, 7), BendInLt(506, HVDn, 13, 7), OrigLt(508, HVUL, 7)),
    TRow(278, sea * 4, oceanic * 5, hillyOce, oceanic * 2, hillyOce),
    VRow(277, OrigLt(502, HVDn, 7)),
    TRow(276, sea * 4, oceanic * 5, hillyOce * 2, mtainDepr * 3),
    VRow(275, BendIn(502, HVUR, 13), BendIn(504, HVUp, 13), BendOut(506, HVDn, 7), BendMin(508, HVDL)),
    TRow(274, sea * 6, oceanic * 3, hillyOce, mtainTaiga * 4),
    //Correct below not above
    VRow(273, BendIn(506, HVDR, 13), BendOut(508, HVUL, 7), Orig(538, HVDn, 2, 7)),
    TRow(272, sea * 5, oceanic * 2, hillyOce * 2, mtainTaiga, hillyOce, oceanic * 2, hillyOce),

    VRow(271, BendIn(492, HVDR, 13), BendIn(494, HVDn, 13), BendOut(496, HVUp, 7), BendIn(498, HVDn, 13), BendOut(500, HVUp, 7), BendIn(502, HVDn, 13),
      BendOut(504, HVUp, 7), ThreeUp(506, 13, 13, 0), OrigMax(508, HVUL), Bend(538, HVUR, 11, 7), Bend(540, HVDL, 11, 5)),

    TRow(270, sea * 2, hillyOce, hillyOce * 3, oceanic * 2, hillySub * 2, mtainOceForest, sea, hillyOce * 2, hillySavannah),

    VRow(269, BendIn(490, HVDR, 13), BendOut(492, HVUL, 7), OrigLt(526, HVUR, 7), OrigRt(528, HVDL, 7), OrigMax(532, HVDR), BendIn(534, HVDL, 13),
      Bend(540, HVUR, 13, 1), BendMax(542, HVUp)),

    TRow(268, sea * 2, mtainSubForest, hillySubForest, hillySub, hillyDeshot * 2, mtainSubForest * 2, sea * 3, hillySubForest, hillySub * 2),

    VRow(267, BendIn(490, HVUR, 13), BendOut(492, HVDL, 7), OrigRt(520, HVDn, 7), BendIn(528, HVDR, 13), BendIn(530, HVDn, 13), OrigLt(532, HVUL, 7),
      BendInRt(534, HVUR, 10, 7), BendOut(536, HVDL, 7)),

    TRow(266, sea * 2, hillySubForest, hillySub, deshot, hillyDeshot * 2, hillyOce, hillySavannah, sea * 2, hillySavannah, sea, hillySavannah, hillySub),

    VRow(265, BendIn(490, HVDR, 13), BendOut(492, HVUL, 7), BendOut(514, HVDR), BendOut(516, HVDn, 7), BendIn(518, HVUp, 13), BendIn(520, HVUL, 13),
      OrigRt(528, HVUp, 7), OrigRt(534, HVDn, 7), BendIn(536, HVUR, 13),  BendIn(538, HVUp, 13), OrigRt(540, HVDL, 7)),

    TRow(264, sea * 2, hillySubForest, hillySub * 2, hillyDeshot, hillySub * 2, sea, Isle7(hillySavannah), sea * 2, hillySavannah, sea * 2),
    VRow(263, OrigRt(490, HVUp, 7), BendIn(514, HVUL, 13), OrigLt(528, HVDn , 7), OrigLt(534, HVUp, 7)),
    TRow(262, sea * 3, subtrop * 2, hillySub * 3, Isle3(hillySavannah), sea * 3, hillySavannah),

    VRow(261, BendIn(490, HVDR, 13), OrigMin(492, HVDL, 5), BendIn(528, HVUR, 13), ThreeDown(530, 13, 13, 0), BendInLt(532, HVDn, 13, 7), BendOut(534, HVUp, 7),
      BendIn(536, HVDn, 13), BendIn(538, HVDL, 13), OrigLt(538, HVDn, 7), BendIn(544, HVUR, 9), BendIn(546, HVDL, 7)),

    TRow(260, sea * 2, hillySubForest, hillySavannah, savannah, hillySavannah * 2, sea * 5, hillySavannah * 2, hillySavannah * 2),

    VRow(259, BendIn(490, HVUR, 13), OrigRt(492, HVUL), OrigLt(496, HVDn, 7), BendInRt(508, HVDR, 13, 4), BendIn(510, HVDn, 13), BendOut(512, HVUp, 7),
      BendIn(514, HVDn, 10), BendMin(516, HVUp, 5), OrigLt(518, HVDL, 7), BendIn(538, HVUR, 13), OrigLt(538, HVUp, 7), BendIn(540, HVUp, 13),
      OrigMin(542, HVDL), OrigLt(546, HVUp, 7)),

    TRow(258, sea * 4, hillySavannah, mtainSavannah, sea, hillySavannah, mtainSavannah, hillySavannah * 4, hillySahel),

    VRow(257, BendIn(496, HVUR, 13), BendIn(498, HVUp, 13), BendOut(500, HVDn), BendIn(502, HVUp, 13), OrigMax(504, HVDL), OrigMin(506, HVUR, 4),
      BendOut(508, HVUL, 7), OrigRt(538, HVDn)),
    //Correct above not below

    TRow(256, sea * 5, hillySavannah, hillySahel, hillySavannah, hillySahel * 6, sahel),
    VRow(255, Orig(536, HVUR, 5, 7), ThreeUp(538, 0, 13, 8), BendIn(540, HVDL, 13)),
    TRow(254, sea * 3, savannah, hillySavannah, mtainSavannah, deshot * 2, hillyDeshot, deshot * 4, sahel * 2),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(294, "" * 2, "Isle of Lewis")
    str(292, "" * 2, "Uist")
    str(264, "" * 9, "Majorca")
    str(262, "" * 8, "Ibiza")
  }
}

/** 16okm terrain scenario for Britain */
object Brit160
{ def britTerrs: LayerHcRefSys[WTile] = Terr160E0.terrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] =Terr160E0.sTerrs.spawn(Terr160E0.grid, EGrid160.britGrid)
  def britCorners: HCornerLayer = Terr160E0.corners.spawn(Terr160E0.grid, EGrid160.britGrid)

  def britScen: EScenBasic = new EScenBasic
  { override implicit val gridSys: EGrid160LongPart = EGrid160.britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}