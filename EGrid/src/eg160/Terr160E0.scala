/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** 160km [[WTile]] terrain for 15° west to 15° east, centred on 0° east.
 * [[Isle5]] 1753.701km² => 2619.726km². Isle of Lewis 2224 km².
 * [[Isle4]] 1060.881km² => 1753.701km².
 * [[Isle3]] 541.265km² => 1060.881km². Isle of Man 572km².
 * South Uist 320.3km² + North Uist 303km² + Benbcuala 82.03km² + Berneray 10.1km² + Grimsay 8.33km² =723.76km² */
object Terr160E0 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e0(262)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(310, sea * 6, mtainBoreal),
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
    TRow(292, sea * 2, hillyOce, hillyOce, hillyOce, sea * 4, oceanic),
    VRow(291, BendIn(498, HVUR, 13), ThreeDown(500, 13, 13, 0), BendIn(502, HVUL, 13)),
    TRow(290, sea * 2, hillyOce, hillyOce, sea * 4, oceanic, Isle10(), oceanic),
    VRow(289, OrigRt(498, HVUR, 7), ThreeUp(500, 8, 8, 0), Bend(502, HVDL, 1, 5)),
    TRow(288, sea * 2, oceanic, hillyOce, oceanic, sea * 4, oceanic, sea),
    VRow(287, BendIn(502, HVUR, 13), ThreeDown(504, 10, 13, 12), Orig(506, HVDL, 1, 6), BendIn(520, HVDR, 13),  BendIn(522, HVDn, 13), MouthLt(524, HVDR, 7)),
    TRow(286, sea * 2, oceanic, oceanic, oceanic * 2, sea * 2, oceanic * 4),
    VRow(285, BendIn(504, HVUL, 13), OrigRt(516, HVDR, 7), ThreeDown(518, 0, 10, 13)),
    TRow(284, sea, oceanic * 2, hillyOce, oceanic * 3, oceanic * 5),
    VRow(283, Bend(516, HVDR, 8, 7), Bend(518, HVUL, 13, 4)),
    TRow(282, sea * 4, hillyOce, oceanic * 2, oceanic * 2, hillyOce * 2, oceanic * 2),
    VRow(281, Orig(510, HVUR, 6, 7), BendIn(512, HVDn, 13), BendIn(514, HVUp, 10), BendIn(516, HVUL, 13)),
    TRow(280, sea * 4, hillyOce, sea, oceanic * 2, hillyOce * 5),
    VRow(279, OrigLt(502, HVDR, 7), BendInLt(504, HVUp, 13, 7), BendInLt(506, HVDn, 13, 7), OrigLt(508, HVUL, 7)),
    TRow(278, sea * 4, oceanic * 5, hillyOce, oceanic * 2, hillyOce),
    TRow(276, sea * 5, oceanic * 4, hillyOce * 2, mtainDepr * 3),
    TRow(274, sea * 6, oceanic * 3, hillyOce, mtainDepr * 4),
    VRow(273, Orig(538, HVDn, 2, 7)),
    TRow(272, sea * 6, oceanic, hillyOce * 2, mtainDepr, hillyOce, mtainDepr, oceanic, hillyOce),
    VRow(271, Bend(538, HVUR, 11, 7), Bend(540, HVDL, 11, 5)),
    TRow(270, sea * 2, hillyOce, hillyOce * 3, oceanic * 2, hillyOce * 2, mtainDepr, sea, hillyOce * 2, hillySavannah),
    TRow(268, sea * 3, hillyOce, oceanic, hillyDeshot * 2, mtainDepr * 2, sea * 3, Isle10(hillyOce), hillyOce * 2),
    TRow(266, sea * 2, hillyOce * 2, deshot, hillyDeshot * 2, hillyOce, sea * 3, hillyOce, sea * 2, hillyOce),
    VRow(265, BendOut(514, HVDR)),
    TRow(264, sea * 2, hillySubForest, hillyOce * 2, hillyDeshot, hillyOce, hillyOce, sea, Isle10(hillyOce), sea * 2, hillyOce, sea * 2),
    TRow(262, sea * 3, oceanic * 2, hillyOce * 3)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(294, "" * 2, "Isle of Lewis")
    str(292, "" * 2, "Uist")
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