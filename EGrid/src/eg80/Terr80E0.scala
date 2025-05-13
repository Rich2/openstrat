/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 80 Km tile width grid centred on the Greenwich meridian, 0°E from 15°W to 15°E. Tile aree 5542.562km².
 * [[Isle8]] 1217.848km² => 1564.258km². Faroe Islands 1399km², Shetlands 1466km².
 * [[Isle7]] 914.739km² => 1217.848km². Orkneys 990km².
 * [[Isle6]] 654.931km² <= 914.739km². Menorca 695km².
 * [[Isle5]] 438.425km² => 654.931km². Isle of Man 572km². */
object Terr80E0 extends Long80Terrs
{ implicit val grid: EGrid80LongFull = EGrid80.e0(416, 582)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(582, SeaIcePerm),
    TileRow(580, SeaIcePerm),
    TileRow(578, SeaIcePerm * 2),
    TileRow(576, SeaIcePerm * 2),
    TileRow(574, SeaIcePerm * 3),
    TileRow(572, SeaIcePerm * 3),
    TileRow(570, SeaIcePerm * 4),
    TileRow(568, SeaIcePerm * 4),
    TileRow(566, SeaIcePerm * 5),
    TileRow(564, SeaIcePerm * 5),
    TileRow(556, sea * 6, mtainTundra),
    TileRow(554, sea * 6, mtainIce),
    TileRow(552, sea * 7, mtainTundra),

    TileRow(518, sea * 14, taiga),
    TileRow(516, sea * 15, taiga),
    TileRow(514, sea * 15, taiga),
    TileRow(512, sea * 15, taiga),
    TileRow(510, sea * 15, taiga * 2),
    TileRow(508, hillyTundra, sea * 14, taiga * 2),
    TileRow(506, sea * 15, taiga * 3),
    TileRow(504, sea * 14, taiga * 4),
    TileRow(502, sea * 13, taiga * 5),
    TileRow(500, sea * 13, taiga * 6),
    TileRow(498, sea * 4, Isle8(mtainOce), sea * 7, taiga * 7),
    TileRow(496, sea * 12, mtainOceForest, taiga * 7),
    TileRow(494, sea * 9, Isle8(hillyOce), sea * 3, taiga * 7),
    TileRow(492, sea * 13, mtainLakesCont, mtainLakesTaiga * 3, hillyLakesContForest * 3),
    TileRow(490, sea * 13, mtainOceForest, taiga * 3, hillyTaiga, taiga * 3),
    VertRow(489, OrigRt(498, HVUR, 7), BendIn(500, HVDn, 13), ThreeDown(502, 11, 13, 0), BendIn(504, HVDn, 11), BendIn(506, HVDL, 11)),
    TileRow(488, sea * 7, hillyOce, hillyOce, sea * 5, hillyLakesOce, taiga * 2, hillyLakesOceForest, oceanic, taiga * 2),
    TileRow(486, sea * 6, Isle10(hillyOce), mtainDepr, hillyOce, sea * 6, hillyLakesOceForest * 2, sea * 2, oceanic, oceanic * 2),
    VertRow(485, OrigLt(502, HVUR, 7), BendInRt(504, HVUL, 13, 7), BendInLt(544, HVUR, 13, 7), Bend(546, HVDL, 13, 4)),
    TileRow(484, sea * 5, hillyOce, mtainDepr, hillyOceForest, hillyOce, sea * 8, oceanic * 2, oceanic * 3),
    VertRow(483, Orig(544, HVUR, 4, 2), ThreeUp(546, 13, 0, 13), BendOut(548, HVDL, 7)),
    TileRow(482, sea * 6, mtainDepr * 2, hillyOce * 2, sea * 7, oceanic * 2, sea, oceanic, oceanic),
    TileRow(480, sea * 7, hillyOce * 3, sea * 8, oceanic * 2, sea, oceanic * 2),
    VertRow(479, Bend(544, HVDR, 13, 4), Bend(546, HVUL, 5, 7), OrigLt(550, HVDR), Bend(552, HVDL, 5, 1)),
    TileRow(478, sea * 6, hillyOce, oceanic * 3, sea * 7, oceanic * 2, oceanic * 2, oceanic, sea),

    VertRow(477, BendIn(484, HVDR, 13), OrigLt(484, HVDL, 7), Orig(492, HVDR, 7, 5), ThreeDown(494, 3, 10, 3), Orig(496, HVDL, 5, 2), BendIn(544, HVUR, 13),
      BendOut(546, HVDL), OrigMin(552, HVUp)),

    TileRow(476, sea * 4, mtainDepr, hillyOce, oceanic, hillyOce, hillyOce * 2, oceanic, sea * 7, oceanic * 2, oceanic, sea * 2),

    VertRow(475, OrigRt(480, HVDR, 7), BendOut(482, HVUL, 7), Bend(494, HVUR, 8, 3), ThreeDown(496, 2, 11, 10), BendIn(498, HVDn, 11), Bend(500, HVDL, 11, 2),
      BendIn(546, HVUR), OrigMax(548, HVUL)),

    TileRow(474, sea * 4, oceanic, oceanic * 2, hillyOce, hillyOce, hillyOce, oceanic, hillyOce, sea * 7, oceanic * 2, sea * 3),
    VertRow(473, BendIn(494, HVDR, 7), ThreeUp(496, 11, 0, 13), BendIn(498, HVUp, 11), ThreeUp(500, 7, 0, 11), BendIn(514, HVDL, 13)),
    TileRow(472, sea * 3, oceanic * 4, sea * 2, oceanic, hillyOce, oceanic, sea * 5, oceanic * 7),
    VertRow(471, Orig(492, HVUR, 4, 2), BendIn(494, HVUL, 13), BendIn(512, HVDR, 13), ThreeUp(514, 0, 13, 12), OrigLt(516, HVUL, 7)),
    TileRow(470, sea * 4, oceanic * 3, sea, hillyOce * 2, oceanic * 3, sea * 3, oceanic * 9),
    VertRow(469, OrigRt(512, HVUp)),
    TileRow(468, sea * 4, oceanic * 3, sea * 2, hillyOce * 2, oceanic * 3, sea * 2, oceanic * 9),
    TileRow(466, sea * 3, oceanic * 3, sea, hillyOce * 3, oceanic * 3, sea * 2, oceanic * 4, hillyOce * 2, oceanic * 4),

    VertRow(465, OrigLt(482, HVUR, 7), BendOut(484, HVDn, 7), BendIn(486, HVUp, 13), OrigRt(488, HVDL, 7), OrigRt(498, HVUR, 7),
      BendIn(500, HVDn, 13), OrigMin(502, HVUL), OrigMin(514, HVUR), BendIn(516, HVDn), ThreeDown(518, 0, 13, 6), BendIn(520, HVDn, 13), OrigMin(522, HVUL)),

    TileRow(464, sea * 9, oceanic, oceanic * 4, oceanic * 3, hillyOce * 7, oceanic, hillyOce),
    VertRow(463, BendIn(492, HVDR, 13), OrigLt(494, HVDL, 7), Bend(516, HVDR, 10, 7), Bend(518, HVUL, 8, 1)),
    TileRow(462, sea * 8, hillyOce, hillyOce, hillyOce, oceanic, oceanic, hillyOce, oceanic, oceanic * 3, hillyOce * 8),

    VertRow(461, BendIn(490, HVDR), BendOut(492, HVUL), OrigLt(500, HVDR, 7), BendIn(502, HVUp, 13), BendOut(504, HVDn, 7), BendIn(506, HVUp, 13),
      BendOut(508, HVDn, 7), BendIn(510, HVUp, 13), BendOut(512, HVDn, 7), BendInLt(514, HVUp, 13, 7), BendMax(516, HVUL)),

    TileRow(460, sea * 7, hillyOce, sea * 5, oceanic * 3, hillyOce * 10),
    VertRow(459, BendIn(490, HVUR, 13), BendIn(492, HVUp, 13), OrigRt(494, HVDL, 7), OrigMin(508, HVUR), BendIn(510, HVDn, 13), OrigMin(512, HVUL)),
    TileRow(458, sea * 11, oceanic * 8, hillyOce * 3, oceanic, hillyOce * 3, oceanic),
    VertRow(457, BendIn(494, HVDR, 13), BendIn(496, HVDn, 13)),
    TileRow(456, sea * 9, hillyOce, oceanic, oceanic, oceanic * 7, hillyOce * 3, oceanic * 3, hillyOce * 2),
    VertRow(455, BendIn(492, HVDR, 13)),
    TileRow(454, sea * 8, hillyOce, oceanic * 10, hillyOceForest * 2, oceanic * 4, hillyOce, mtainDepr),
    VertRow(453, BendIn(492, HVUR, 13)),
    TileRow(452, sea * 11, oceanic * 9, hillyOce * 2, mtainDepr * 6),
    TileRow(450, sea * 12, oceanic * 7, hillyOce, mtainDepr * 8),
    TileRow(448, sea * 12, oceanic * 4, hillyOce * 2, oceanic, mtainDepr * 9),
    TileRow(446, sea * 13, oceanic * 2, hillyOce * 4, mtainDepr * 3, oceanic * 2, mtainDepr, oceanic * 2, hillyOce * 2),
    TileRow(444, sea * 13, oceForest, oceanic, hillyOce * 5, mtainDepr * 2, hillyOce * 2, oceanic * 3, sea, oceanic),
    TileRow(442, sea * 12, oceanic, oceForest, hillyOce * 3, mtainDepr, hillyOce, mtainDepr * 2, hillyOce, mtainDepr * 3, hillyOce, oceanic),

    //Checked west of map below
    VertRow(441, BendIn(478, HVDR, 13), BendIn(480, HVDn, 13), BendOut(482, HVUp, 7), BendIn(484, HVDn, 13), BendOut(486, HVUp, 7), BendIn(488, HVDn, 13),
      BendOut(490, HVUp, 7), BendIn(492, HVDn, 13), BendOut(494, HVUp, 7), BendIn(496, HVDn, 13), BendIn(498, HVDL, 13)),

    TileRow(440, sea * 6, mtainOceForest * 5, sea * 2, oceanic * 3, hillyOce, mtainOceForest, hillyOce * 3, mtainOceForest * 2, sea, hillyOceForest, hillyOce,
      mtainDepr, hillyOce, sea * 2),

    VertRow(439, BendIn(472, HVDR, 13), BendIn(474, HVDn, 13), OrigRt(476, HVUL), OrigMin(478, HVUp), OrigMin(498, HVUp, 1), OrigRt(528, HVDn, 7)),

    TileRow(438, sea * 5, mtainOceForest, hillyOceForest * 2, mtainOceForest * 3, hillyOceForest, mtainOceForest * 2, hillyOce * 4, hillySavannah, sea,
      hillyOceForest * 2, sea * 3, hillySavannah, mtainSubForest,  hillySavannah * 2, sea),

    VertRow(437, BendIn(472, HVUR, 13), BendMin(474, HVDL, 2), BendOut(526, HVDR, 7), BendIn(528, HVUL, 13), BendIn(546, HVDR, 11), OrigLt(548, HVDL, 7),
      BendInLt(552, HVUR, 13, 7), BendMax(554, HVDL)),
    //Checked western map above

    //Checked Corsica
    TileRow(436, sea * 5, hillyOce * 3, oceanic * 3, deshot * 2, hillyOce, mtainDepr * 4, sea * 5, hillySubForest, hillySub, hillySavannah, hillyOce * 2,
      mtainDepr, sea),

    VertRow(435, OrigRt(546, HVUp, 7), BendOut(552, HVDR, 7), ThreeUp(554, 13, 0, 13)),

    TileRow(434, sea * 6, hillyOce * 2, oceanic, deshot, hillyDeshot, oceanic, hillyOce, oceanic * 2, hillyOce * 3, sea * 6, hillySubForest, sea * 2, hillyOce,
      hillyOce, mtainDepr, hillyOce),

    VertRow(433, OrigRt(550, HVUR, 7), ThreeUp(552, 0, 13, 6), BendIn(554, HVDL, 13)),
    TileRow(432, sea * 6, hillyOce * 3, oceanic, deshot, hillyOce * 3, deshot, oceanic, hillyOce * 2, sea * 7, hillySubForest, sea * 3, hillyOce, hillyOce),
    TileRow(430, sea * 5, oceanic, hillyOce * 5, deshot, hillyOce, hillyDeshot, mtainDepr, oceanic, sea * 7, hillySavannah * 2, hillySubForest, sea * 4, hillyOce),
    VertRow(429, BendIn(522, HVDR, 13), BendIn(524, HVDn, 13), ThreeDown(526, 0, 10, 8)),

    TileRow(428, sea * 6, hillyOce * 2, oceanic * 3, deshot * 2, hillyOce * 3, sea * 2, mtainSub, Isle6(hillySub), sea * 4, hillySavannah, mtainSubForest,
      sea * 5),

    VertRow(427, OrigRt(522, HVUp, 7), ThreeDown(528, 10, 0, 13)),

    TileRow(426, sea * 6, hillyOce, oceanic * 3, hillyOce * 2, deshot * 2, hillyOce, oceanic, sea * 3, hillySavannah, sea * 5, hillySavannah, mtainSubForest,
      sea * 5),

    VertRow(425, OrigLt(524, HVDR, 7), BendIn(526, HVUp, 13), BendIn(528, HVUL, 13)),
    TileRow(424, sea * 5, oceanic * 5, hillyOce * 6, sea * 16),
    TileRow(422, sea * 6, oceanic * 2, hillyOce * 4, hillyDeshot, hillyOce, hillyDeshot, oceanic, sea * 14, hillyOce, mtainDepr),
    TileRow(420, sea * 7, hillyOce, oceanic * 3, hillyDeshot, hillyOce * 4, sea * 14, hillyOce * 3),

    TileRow(418, sea * 8, oceanic, hillyOce * 2, mtainDepr * 2, hillyDeshot, sea * 5, hillyOce, mtainDepr, sea, mtainDepr * 2, hillyOce, mtainDepr, hillyOce * 2,
      sea * 4, hillyOce),

    TileRow(416, sea * 9, hillyOce * 2, sea * 6, mtainDepr, hillyOce * 7, mtainDepr, hillyOce * 2, hillyDeshot, sea * 4),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(498, "" * 4, "Faroes")
    str(494, "" * 9, "Shetlands")
    str(488, "" * 8, "Orkneys")
    str(474, "" * 8, "Isle of Man")
    str(460, "" * 7, "Cornwall west")
  }
}

/** Object for scenarios covering the western front at 80km. */
object WesternFront
{
  def wfGrid: EGrid80LongPart =
  { val array = Array[Int](10, 494, 10, 496, 11, 494, 11, 496, 15, 494, 15, 496, 15, 494, 15, 496, 12, 506, 3, 520)
    new EGrid80LongPart(446, 0, array)
  }

  def wfTerrs: LayerHcRefSys[WTile] = Terr80E0.terrs.spawn(Terr80E0.grid, wfGrid)
  def wfSTerrs:LayerHSOptSys[WSep, WSepSome] = Terr80E0.sTerrs.spawn(Terr80E0.grid, wfGrid)
  def wfCorners: HCornerLayer = Terr80E0.corners.spawn(Terr80E0.grid,wfGrid )
  def wFrontScen : EScenBasic = EScenBasic(wfGrid, wfTerrs, wfSTerrs, wfCorners, ???, "Western Front")
}
