/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid.phex.*, egrid.*, WTiles.*

/** [[WTile]] terrain for 15° west to 15° east, centred on 0° east.
 * [[Isle13]] 7611.551km² => 8878.113km². Corsica 8680km².
 * [[Isle7]] 3658.957km² => 4871.392km². Mallorca 3640.11km².
 * [[Isle5]] 986.457km² => 1473.596km². Orkneys 990km², Faroe Islands 1399km², Shetlands 1466km².
 * [[Isle4]] 596.745km² => 986.457km². Menorca 695.8 km², Ibiza + Formentera 654km².
 * [[Isle3]] 304.462km² => 596.745km². Isle of Man 572km². */
object Terr120E0 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e0(274)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { import TileRow as TRow, VertRow as VRow
    override val rows: RArr[DateRow] = RArr(
    TRow(386, SeaIcePerm),
    TRow(384, SeaIcePerm),
    TRow(382, SeaIcePerm * 2),
    TRow(380, SeaIcePerm * 2),
    TRow(378, SeaIcePerm * 3),
    TRow(376, SeaIcePerm * 3),
    TRow(374, hillyTundra, SeaIcePerm * 2, SeaIceWinter),
    TRow(372, SeaIcePerm * 2, SeaIceWinter * 2),

    VRow(371, BendLtOut(522, 6, SeaIceWinter), BendMin(524, HVDL, 3, SeaIceWinter)),
    TRow(370, SeaIcePerm * 2, SeaIceWinter * 2, mtainTundra),
    VRow(369, OrigLt(518, HVDn, 7, SeaIceWinter)),
    TRow(368, SeaIcePerm * 2, SeaIceWinter, sea, mtainTundra),
    VRow(367, BendIn(518, HVUR, 13, sea, SeaIceWinter), BendOut(520, HVDL, 7)),
    TRow(366, SeaIcePerm, SeaIceWinter, sea * 2, mtainTundra),
    TRow(364, SeaIcePerm, SeaIceWinter, sea * 3, mtainIce),
    TRow(346, sea * 9, mtainBoreal),
    TRow(344, sea * 9, mtainOce),
    TRow(342, sea * 10, mtainOce) ,
    TRow(340, sea * 10, mtainOce),
    TRow(338, sea * 9, hillyTundra * 2),
    TRow(336, sea * 9, hillyTaiga * 2, taiga),
    VRow(335, BendIn(520, HVDR, 13), BendIn(522, HVDn, 13), OrigRt(524, HVUL)),
    TRow(334, sea * 8, mtainOceForest, hillyTaiga * 3),
    VRow(333, BendIn(518, HVDR, 13), BendOut(520, HVUL, 7)),
    TRow(332, sea * 3, Isle5(hillyOce), sea * 4, mtainOceForest, mtainContForest, hillyTaiga, taiga * 2),
    VRow(331, BendIn(518, HVUR, 13), OrigMin(520, HVUL)),
    TRow(330, sea * 5, Isle5(hillyOce), sea * 2, mtainDepr * 4, hillyTaiga),
    TRow(328, sea * 6, sea * 2, mtainOceForest * 2, hillyTaiga * 3),
    TRow(326, sea * 5, Isle5(hillyOce), sea * 3, hillyTaiga, hillyTaiga, taiga * 3),
    VRow(325, BendOut(530, HVDR)),
    TRow(324, sea * 3, oceanic, hillyOce, sea * 4, hillyTaiga, hillyTaiga, sea, oceanic * 2),
    VRow(323, ThreeDown(528, 13, 10, 0), Bend(530, HVUL, 13, 7), OrigRt(534, HVDR), BendIn(536, HVDL, 13)),
    TRow(322, sea * 3, hillyOce, hillyOce * 2, sea * 5, oceanic * 2, oceanic * 2),
    VRow(321, OrigRt(508, HVDR, 7), BendIn(510, HVDL, 13)),
    TRow(320, sea * 4, oceanic * 2, hillyOce, sea * 4, oceanic, oceanic, sea, oceanic),

    VRow(319, BendIn(492, HVDR, 13), BendIn(494, HVDn, 13), BendOut(496, HVUp), BendIn(498, HVDn, 13), BendIn(500, HVDL, 13), BendOut(510, HVUR, 7),
      BendIn(512, HVDL, 13)),

    TRow(318, sea * 2, Land(MountLakes, Oceanic), oceanic, hillyOce, hillyOce, oceanic, sea * 4, oceanic, oceanic, oceanic, sea),
    VRow(317, OrigRt(490, HVUR, 7), BendOut(492, HVUL, 7), Bend(500, HVUR, 4, 2), BendIn(502, HVDL, 13), BendOut(512,HVUR, 7), BendIn(514, HVDL, 13)),
    TRow(316, sea * 2, oceanic, oceanic * 2, hillyOce, oceanic, hillyOce, sea * 4, oceanic, sea * 3),
    VRow(315, BendMax(500, HVDR), ThreeUp(502, 13, 13, 13), OrigMax(504, HVUL), OrigLt(514, HVUp, 7)),
    TRow(314, sea * 2, oceanic, oceanic * 2, hillyOce, oceanic * 2, sea * 3, oceanic * 5),
    VRow(313, BendIn(486, HVDR, 13), OrigMax(500, HVUp), OrigRt(516, HVDR, 7), BendIn(518, HVDL, 13)),
    TRow(312, sea, hillyOce, oceanic * 2, sea, hillyOce, oceanic * 3, sea, oceanic * 6),
    VRow(311, OrigRt(486, HVUp, 7), OrigRt(496, HVDn, 7), Bend(516, HVDR, 13, 6), ThreeUp(518, 0, 13, 13), OrigLt(520, HVUL)),
    TRow(310, sea * 2, hillyOce * 2, sea, hillyOce, oceanic * 3, oceanic * 4, hillyOce * 2, oceanic * 2),

    VRow(309, OrigLt(488, HVDR, 7), BendIn(490, HVUp, 13), BendOut(492, HVDn, 7), BendIn(494, HVUp, 13), BendIn(496, HVUL, 13), BendIn(498, HVDR, 13),
      BendInRt(500, HVDn, 13, 7), BendIn(502, HVUp, 13), OrigRt(504, HVDL), BendIn(514, HVDR, 9), Bend(516, HVUL, 2, 7)),

    TRow(308, sea * 5, hillyOce * 2, oceanic * 2, oceanic * 3, hillyOce * 5),

    VRow(307, OrigRt(498, HVUp), OrigLt(504, HVUR, 7), BendOut(506, HVDn, 7), BendInLt(508, HVUp, 13, 6), BendMax(510, HVDn), BendMax(512, HVUp),
      BendMax(514, HVUL)),

    TRow(306, sea * 8, oceanic, oceanic * 3, hillyOce * 6),
    VRow(305, BendIn(498, HVDR, 13), BendIn(500, HVDn, 13), BendOut(502, HVUp, 7), BendIn(504, HVDn, 13), OrigLt(506, HVUL, 7)),
    TRow(304, sea * 5, oceanic, hillyOce, oceanic * 4, hillyOce * 4, oceanic, hillyOce * 2),
    VRow(303, BendIn(498, HVUR, 13), BendMin(500, HVDL, 4)),
    TRow(302, sea * 6, hillyOce, oceanic * 4, hillyOce * 2, hillyOceForest, hillyOce * 2, mtainDepr * 2),
    VRow(301, BendIn(500, HVUR, 13), BendIn(502, HVUp, 13)),
    TRow(300, sea * 8, oceanic * 4, hillyOce * 2, mtainDepr * 4, hillyOce),
    TRow(298, sea * 8, oceanic, hillyOce * 4, mtainCont, hillyOce, mtainCont, hillyCont, oceanic, mtainCont),
    VRow(297, BendIn(506, HVDR, 13), BendOut(508, HVUL, 7)),
    TRow(296, sea * 8, oceanic, hillyOce, mtainOce * 2, hillyOce, mtainOce, hillyOce * 2, oceanic * 2, hillySub),
    VRow(295, OrigMin(536, HVDn, 1)),
    TRow(294, sea * 9, oceanic, hillySavannah, mtainOce * 2, hillyOce, mtainOceForest * 3, mtainOce, hillyOce),
    VRow(293, BendIn(486, HVDR, 13), OrigLt(534, HVUR, 7), ThreeUp(536, 13, 0, 13), BendOut(538, HVDL, 7)),
    TRow(292, sea * 3, hillyOce * 2, mtainOceForest * 2, mtainOce, mtainOceForest, hillyOce * 2, hillySub, sea, mtainSubForest, sea * 2, hillySub * 3, sea),

    VRow(291, BendIn(484, HVDR, 13), BendOut(486, HVUL, 7), OrigLt(526, HVDR, 7), BendIn(528, HVUp, 13), OrigRt(530, HVDL, 7), BendInLt(538, HVUR, 13, 2),
      BendMax(540, HVDL), OrigRt(552, HVUR, 7), ThreeUp(554, 7, 13, 0)),

    TRow(290, sea * 3, mtainSub, mtainSubForest, hillySub, sahel, hillySavannah * 3, mtainSavannah * 2, sea * 4, mtainSubForest, hillySavannah * 2,
      mtainSavannah),

    VRow(289, BendIn(484, HVUR, 13), BendOut(486, HVDL, 7), BendIn(534, HVDR, 13), BendIn(536, HVDn, 13), OrigLt(538, HVUL, 7), BendInRt(540, HVUR, 13, 7),
      BendIn(542, HVUp, 13), BendOut(544, HVDn, 7), BendMin(546, HVDL)),

    TRow(288, sea * 3, hillySub, mtainSub, savannah * 2, mtainSavannah, hillySavannah * 3, sea * 4, hillySavannah, sea * 2, hillySub * 2),

    VRow(287, BendIn(484, HVDR, 13), BendOut(486, HVUL, 7), BendOut(516, HVDR, 7), OrigRt(518, HVDL, 7), OrigRt(534, HVUp), BendIn(546, HVUR, 13),
      BendIn(548, HVUp, 13), BendOut(550, HVDn, 7)),

    TRow(286, sea * 3, subtrop, mtainSubForest, hillySub, hillySavannah * 2, mtainSavannah * 2, hillySavannah, sea, Isle3(hillySavannah), sea * 3,
      mtainSub, sea * 3, mtainSub),

    VRow(285, OrigRt(484, HVUp, 7), Bend(514, HVDR, 12, 7), ThreeUp(516, 0, 12, 13), OrigLt(534, HVDn, 7), OrigMin(540, HVDR), BendIn(542, HVDL, 13)),

    TRow(284, sea * 4, savannah, hillySavannah * 3, savannah, hillySavannah * 2, Isle4(hillySavannah), Isle7(hillySavannah), sea * 3, hillySavannah,
      mtainSavannah),

    VRow(283, OrigLt(484, HVDn), BendInRt(514, HVUR, 12, 7), BendIn(534, HVUR, 13), OrigRt(536, HVUL, 7), OrigLt(540, HVUR, 7), BendIn(542, HVUL, 13),
      BendIn(548, HVDR, 13), BendIn(550, HVDn, 13), BendOut(552, HVUp, 7), BendIn(554, HVUR, 13)),

    TRow(282, sea * 3, hillySub * 2, hillySavannah, savannah, hillySavannah, mtainSavannah, hillySavannah, sea * 9, mtainSavannah, mtainSub),

    VRow(281, BendIn(484, HVUR, 11), OrigMin(486, HVUL), BendIn(538, HVDR, 13), BendIn(540, HVDn, 13), OrigLt(542, HVUL, 7), BendIn(546, HVDR, 13),
      BendOut(548, HVUL, 7)),

    TRow(280, sea * 4, hillySavannah * 6, sea * 7 , hillySavannah, sea, hillySavannah, mtainSavannah, hillySavannah),

    VRow(279, BendOut(508, HVDR, 7), OrigRt(510, HVDL, 7), BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), BendOut(516, HVUp), BendIn(518, HVDn, 13),
      BendOut(520, HVUp), OrigLt(522, HVDL, 7), OrigMin(538, HVUp, 2)),

    TRow(278, sea * 6, hillySavannah, mtainSavannah * 2, hillySavannah, sea, mtainSahel, hillySavannah * 2, mtainSavannah, mtainSub, hillySavannah * 3),

    VRow(277, BendIn(492, HVUR, 11), Bend(494, HVUp, 4, 3), BendIn(496, HVDn, 8), BendInRt(498, HVUp, 13, 7), BendOut(500, HVDn, 7), BendInLt(502, HVUp, 13, 7),
      BendMax(504, HVDn), ThreeDown(506, 13, 13, 13), ThreeUp(508, 0, 13, 13), BendMin(510, HVUp, 4), BendOut(512, HVUL)),

    TRow(276, sea * 6, mtainSavannah, sea, hillySavannah, mtainSavannah, hillySavannah * 2, sahel, hillySahel * 2, hillySavannah * 2, hillySahel, sahel),
    TRow(274, sea * 6, savannah, mtainSavannah, hillySahel * 3, sahel * 2, hillySahel * 2, sahel, hillySahel * 2, sahel),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(332, "" * 3, "Faroes")
    str(330, "" * 5, "Shetlands")
    str(326, "" * 5, "Orkneys")
    str(286, "" * 12, "Minorca")
    str(284, "" * 11, "Ibiza", "Mallorca")
  }
}

object BritReg120
{ implicit def britGrid: EGrid120Long = EGrid120Long.reg(138, 148, 0, 504, 520)
  def britTerrs: LayerHcRefSys[WTile] = Terr120E0.terrs.spawn(Terr120E0.grid)
  def britSTerrs: LayerHSOptSys[WSep, WSepSome] = Terr120E0.sTerrs.spawn(Terr120E0.grid, britGrid)
  def britCorners: HCornerLayer = Terr120E0.corners.spawn(Terr120E0.grid, britGrid)

  def regScen: EScenBasic = new EScenBasic
  {  override def title: String = "Regular Britain"
    override implicit val gridSys: EGrid120Long = britGrid
    override val terrs: LayerHcRefSys[WTile] = britTerrs
    override val sTerrs: LayerHSOptSys[WSep, WSepSome] = britSTerrs
    override val corners: HCornerLayer = britCorners
    override def hexNames: LayerHcRefSys[String] = LayerHcRefSys[String](gridSys, "")
  }
}