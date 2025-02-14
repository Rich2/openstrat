/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid.phex._, egrid._, WTiles._

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
  { override val rows: RArr[DateRow] = RArr(
    TileRow(386, SeaIcePerm),
    TileRow(384, SeaIcePerm),
    TileRow(382, SeaIcePerm * 2),
    TileRow(380, SeaIcePerm * 2),
    TileRow(378, SeaIcePerm * 3),
    TileRow(376, SeaIcePerm * 3),
    TileRow(374, hillyTundra, SeaIcePerm * 2, SeaIceWinter),
    TileRow(372, SeaIcePerm * 2, SeaIceWinter * 2),

    VertRow(371, BendLtOut(522, 6, SeaIceWinter), BendMin(524, HVDL, 3, SeaIceWinter)),
    TileRow(370, SeaIcePerm * 2, SeaIceWinter * 2, mtainTundra),
    VertRow(369, OrigLt(518, HVDn, 7, SeaIceWinter)),
    TileRow(368, SeaIcePerm * 2, SeaIceWinter, sea, mtainTundra),
    VertRow(367, BendIn(518, HVUR, 13, sea, SeaIceWinter), BendOut(520, HVDL, 7)),
    TileRow(366, SeaIcePerm, SeaIceWinter, sea * 2, mtainTundra),
    TileRow(364, SeaIcePerm, SeaIceWinter, sea * 3, mtainIce),
    TileRow(346, sea * 9, mtainBoreal),
    TileRow(344, sea * 9, mtainOce),
    TileRow(342, sea * 10, mtainOce) ,
    TileRow(340, sea * 10, mtainOce),
    TileRow(338, sea * 9, hillyTundra * 2),
    TileRow(336, sea * 9, hillyTaiga * 2, taiga),
    VertRow(335, BendIn(520, HVDR, 13), BendIn(522, HVDn, 13), OrigRt(524, HVUL)),
    TileRow(334, sea * 8, mtainOceForest, hillyTaiga * 3),
    VertRow(333, BendIn(518, HVDR, 13), BendOut(520, HVUL, 7)),
    TileRow(332, sea * 3, Isle5(hillyOce), sea * 4, mtainOceForest, mtainContForest, hillyTaiga, taiga * 2),
    VertRow(331, BendIn(518, HVUR, 13), OrigMin(520, HVUL)),
    TileRow(330, sea * 5, Isle5(hillyOce), sea * 2, mtainDepr * 4, hillyTaiga),
    TileRow(328, sea * 6, sea * 2, mtainOceForest * 2, hillyTaiga * 3),
    TileRow(326, sea * 5, Isle5(hillyOce), sea * 3, hillyTaiga, hillyTaiga, taiga * 3),
    VertRow(325, BendOut(530, HVDR)),
    TileRow(324, sea * 3, oceanic, hillyOce, sea * 4, hillyTaiga, hillyTaiga, sea, oceanic * 2),
    VertRow(323, ThreeDown(528, 13, 10, 0), Bend(530, HVUL, 13, 7), OrigRt(534, HVDR), BendIn(536, HVDL, 13)),
    TileRow(322, sea * 3, hillyOce, hillyOce * 2, sea * 5, oceanic * 2, oceanic * 2),
    VertRow(321, OrigRt(508, HVDR, 7), BendIn(510, HVDL, 13)),
    TileRow(320, sea * 4, oceanic * 2, hillyOce, sea * 4, oceanic, oceanic, sea, oceanic),

    VertRow(319, BendIn(492, HVDR, 13), BendIn(494, HVDn, 13), BendOut(496, HVUp), BendIn(498, HVDn, 13), BendIn(500, HVDL, 13), BendOut(510, HVUR, 7),
      BendIn(512, HVDL, 13)),

    TileRow(318, sea * 2, Land(MountLakes, Oceanic), oceanic, hillyOce, hillyOce, oceanic, sea * 4, oceanic, oceanic, oceanic, sea),
    VertRow(317, OrigRt(490, HVUR, 7), BendOut(492, HVUL, 7), Bend(500, HVUR, 4, 2), BendIn(502, HVDL, 13), BendOut(512,HVUR, 7), BendIn(514, HVDL, 13)),
    TileRow(316, sea * 2, oceanic, oceanic * 2, hillyOce, oceanic, hillyOce, sea * 4, oceanic, sea * 3),
    VertRow(315, BendMax(500, HVDR), ThreeUp(502, 13, 13, 13), OrigMax(504, HVUL), OrigLt(514, HVUp, 7)),
    TileRow(314, sea * 2, oceanic, oceanic * 2, hillyOce, oceanic * 2, sea * 3, oceanic * 5),
    VertRow(313, BendIn(486, HVDR, 13), OrigMax(500, HVUp), OrigRt(516, HVDR, 7), BendIn(518, HVDL, 13)),
    TileRow(312, sea, hillyOce, oceanic * 2, sea, hillyOce, oceanic * 3, sea, oceanic * 6),
    VertRow(311, OrigRt(486, HVUp, 7), OrigRt(496, HVDn, 7), Bend(516, HVDR, 13, 6), ThreeUp(518, 0, 13, 13), OrigLt(520, HVUL)),
    TileRow(310, sea * 2, hillyOce * 2, sea, hillyOce, oceanic * 3, oceanic * 4, hillyOce * 2, oceanic * 2),

    VertRow(309, OrigLt(488, HVDR, 7), BendIn(490, HVUp, 13), BendOut(492, HVDn, 7), BendIn(494, HVUp, 13), BendIn(496, HVUL, 13), BendIn(498, HVDR, 13),
      BendInRt(500, HVDn, 13, 7), BendIn(502, HVUp, 13), OrigRt(504, HVDL), BendIn(514, HVDR, 9), Bend(516, HVUL, 2, 7)),

    TileRow(308, sea * 5, hillyOce * 2, oceanic * 2, oceanic * 3, hillyOce * 5),

    VertRow(307, OrigRt(498, HVUp), OrigLt(504, HVUR, 7), BendOut(506, HVDn, 7), BendInLt(508, HVUp, 13, 6), BendMax(510, HVDn), BendMax(512, HVUp),
      BendMax(514, HVUL)),

    TileRow(306, sea * 8, oceanic, oceanic * 3, hillyOce * 6),
    VertRow(305),
    TileRow(304, sea * 6, oceanic * 5, hillyOce * 4, oceanic, hillyOce * 2),
    TileRow(302, sea * 6, oceanic * 5, hillyOce * 2, hillyOceForest, hillyOce * 2, mtainDepr * 2),
    TileRow(300, sea * 8, oceanic * 4, hillyOce * 2, mtainDepr * 4, hillyOce),
    TileRow(298, sea * 8, oceanic, hillyOce * 4, mtainCont, hillyOce, mtainCont, hillyCont, oceanic, mtainCont),
    VertRow(297, BendIn(506, HVDR, 13), BendOut(508, HVUL, 7)),
    TileRow(296, sea * 8, oceanic, hillyOce, mtainOce * 2, hillyOce, mtainOce, hillyOce * 2, oceanic * 2, hillySub),
    VertRow(295, OrigMin(536, HVDn, 1)),
    TileRow(294, sea * 9, oceanic, hillySavannah, mtainOce * 2, hillyOce, mtainOceForest * 3, mtainOce, hillyOce),
    VertRow(293, OrigLt(534, HVUR, 7), ThreeUp(536, 13, 0, 13), BendOut(538, HVDL, 7)),
    TileRow(292, sea * 3, hillyOce * 2, mtainOceForest * 2, mtainOce, mtainOceForest, hillyOce * 2, hillySub, sea, mtainSubForest, sea * 2, hillySub * 3, sea),

    VertRow(291, BendIn(484, HVDR, 13), ThreeUp(538, 13, 3, 3), Bend(540, HVDL, 3, 7), OrigRt(552, HVUR, 7), ThreeUp(554, 7, 13, 0)),

    TileRow(290, sea * 3, mtainSub, mtainSubForest, hillySub, sahel, hillySavannah * 3, mtainSavannah * 2, sea * 4, Isle13(mtainSub), hillySavannah * 2,
      mtainSavannah),

    VertRow(289, BendIn(484, HVUR, 13), BendOut(486, HVDL, 7), BendIn(534, HVDR, 13), ThreeUp(536, 3, 13, 3), BendInRt(538, HVUp, 3, 7), ThreeUp(540, 13, 3, 3),
      BendIn(542, HVUp, 13), BendOut(544, HVDn, 7), BendMin(546, HVDL)),

    TileRow(288, sea * 3, hillySub, mtainSub, savannah * 2, mtainSavannah, hillySavannah * 3, sea * 4, hillySavannah, sea * 2, hillySub * 2),

    VertRow(287, BendIn(484, HVDR, 13), BendOut(486, HVUL, 7), BendOut(516, HVDR, 7), OrigRt(534, HVUp), BendIn(546, HVUR, 13), BendIn(548, HVUp, 13),
      BendOut(550, HVDn, 7)),

    TileRow(286, sea * 3, subtrop, mtainSubForest, hillySub, hillySavannah * 2, mtainSavannah * 2, hillySavannah, sea, Isle3(hillySavannah), sea * 3,
      mtainSub, sea * 3, mtainSub),

    VertRow(285, Bend(514, HVDR, 12, 7), ThreeUp(516, 0, 12, 13), OrigLt(534, HVDn, 7), OrigMin(540, HVDR), BendIn(542, HVDL, 13)),

    TileRow(284, sea * 4, savannah, hillySavannah * 3, savannah, hillySavannah * 2, Isle4(hillySavannah), Isle7(hillySavannah), sea * 3, hillySavannah,
      mtainSavannah),

    VertRow(283, BendIn(534, HVUR, 13), OrigRt(536, HVUL, 7), OrigLt(540, HVUR, 7), BendIn(542, HVUL, 13), BendIn(548, HVDR, 13), BendIn(550, HVDn, 13),
      BendOut(552, HVUp, 7), BendIn(554, HVUR, 13)),

    TileRow(282, sea * 3, hillySub * 2, hillySavannah, savannah, hillySavannah, mtainSavannah, hillySavannah, sea * 9, mtainSavannah, mtainSub),
    VertRow(281, BendIn(538, HVDR, 13), BendIn(540, HVDn, 13), OrigLt(542, HVUL, 7), BendIn(546, HVDR, 13), BendOut(548, HVUL, 7)),
    TileRow(280, sea * 4, hillySavannah * 6, sea * 7 , hillySavannah, sea, hillySavannah, mtainSavannah, hillySavannah),
    VertRow(279, OrigMin(538, HVUp, 2)),
    TileRow(278, sea * 6, hillySavannah, mtainSavannah * 2, hillySavannah, sea, mtainSahel, hillySavannah * 2, mtainSavannah, mtainSub, hillySavannah * 3),
    TileRow(276, sea * 6, mtainSavannah, sea * 2, mtainSavannah, hillySavannah * 2, sahel, hillySahel * 2, hillySavannah * 2, hillySahel),
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