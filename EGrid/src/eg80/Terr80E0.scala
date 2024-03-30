/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, WTiles._

/** 80 Km tile width grid centred on the Greenwich meridian, 0°E form 15°W to 15°E. Covers North West Europe. The c or column offset is 512 which is G0
 *  in base 32. The c offset for North East Europe will be 1536 or 1G0 in base 32. Current y offset is 300 for the equator. The Old c offset was 200 so a diff of 312 */
object Terr80E0 extends Long80Terrs
{ implicit val grid: EGrid80LongFull = EGrid80.e0(416, 582)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(556, sea *6, CapeOld(4, 2, mtainOld)),
    TRow(554, sea * 6, mtainOld),
    TRow(552, sea * 7, mtainOld),

    TRow(518, sea * 14, taiga),
    TRow(516, sea * 15, taiga),
    TRow(514, sea * 15, taiga),
    TRow(512, sea * 15, taiga),
    TRow(510, sea * 15, taiga * 2),
    TRow(508, CapeOld(1, 3, hillyTundra), sea * 14, taiga * 2),
    TRow(506, sea * 15, taiga * 3),
    TRow(504, sea * 14, taiga * 4),
    TRow(502, sea * 13, taiga * 5),
    TRow(500, sea * 13, taiga * 6),
    TRow(498, sea * 4, Isle10(hillyOce), sea * 7, taiga * 7),
    TRow(496, sea * 13, taiga * 7),
    TRow(494, sea * 9, CapeOld(5, 4, hillyOce), sea * 3, taiga * 7),
    TRow(492, sea * 8, CapeOld(2, 4, hillyOce), sea * 4, taiga * 6),
    TRow(490, sea * 14, taiga * 3, hillyTaiga, taiga * 3),
    VRow(489, SourceRt(498, HVUR, 7), BendIn(500, HVDn, 13)),
    TRow(488, sea * 7, CapeOld(1, 1, hillyOce), Isle10(hillyOce), sea * 6, taiga * 2, sea, CapeOld(4, 1), taiga * 2),
    TRow(486, sea * 6, Isle10(hillyOce), mtainOld, hillyOce, sea * 7, CapeOld(2, 3, hillyTaiga), sea * 2, CapeOld(4, 1), oceanic * 2),
    VRow(485, SourceLt(502, HVUR, 7), BendInRt(504, HVUL, 13, 7)),
    TRow(484, sea * 5, hillyOce, mtainOld, hillyOceForest, hillyOce, sea * 9, CapeOld(5, 4), CapeOld(4, 1), oceanic * 2),
    TRow(482, sea * 6, mtainOld * 2, hillyOce * 2, sea * 7, oceanic * 2, sea, CapeOld(4, 1), oceanic),
    TRow(480, sea * 7, hillyOce * 3, sea * 8, oceanic * 2, sea, oceanic * 2),
    VRow(479, Bend(544, HVDR, 13, 4), Bend(546, HVUL, 5, 7), SourceLt(550, HVDR), Bend(552, HVDL, 5, 1)),
    TRow(478, sea * 6, hillyOce, oceanic * 3, sea * 7, oceanic * 2, oceanic * 2, oceanic, sea),

    VRow(477, BendIn(484, HVDR, 13), SourceLt(484, HVDL, 7), MouthOld(492, HVUL), ThreeDown(494, 3, 10, 3), MouthOld(496, HVUR), BendIn(544, HVUR, 13),
      BendOut(546, HVDL), MouthOld(552, HVDn)),

    TRow(476, sea * 4, mtainOld, hillyOce, oceanic, hillyOce, hillyOce * 2, oceanic, sea * 7, oceanic * 2, oceanic, sea * 2),
    VRow(475, MouthRt(480, HVDL, 7), BendOut(482, HVUL, 7), Bend(494, HVUR, 8, 3), BendIn(496, HVDL, 8), BendIn(546, HVUR), MouthOld(548, HVDR, 7)),
    TRow(474, sea * 4, oceanic, oceanic * 2, hillyOce, SepB(), sea, hillyOce, oceanic, hillyOce, sea * 7, oceanic * 2, sea * 3),
    VRow(473, BendIn(494, HVDR, 7), BendIn(496, HVUL, 13)),
    TRow(472, sea * 3, oceanic * 4, sea * 2, oceanic, hillyOce, oceanic, sea * 5, oceanic * 7),
    VRow(471, MouthOld(492, HVDL), BendIn(494, HVUL, 13)),
    TRow(470, sea * 4, oceanic * 3, sea, hillyOce * 2, oceanic * 2, sea * 4, oceanic * 9),
    TRow(468, sea * 4, oceanic * 3, sea * 2, hillyOce * 2, oceanic * 3, sea * 2, oceanic * 9),
    TRow(466, sea * 3, oceanic * 3, sea * 2, hillyOce * 2, oceanic * 3, sea * 2, oceanic * 4, hillyOce * 2, oceanic * 4),

    VRow(465, MouthLt(482, HVDL, 7), BendOut(484, HVDn, 7), BendIn(486, HVUp, 13), MouthRt(488, HVUR, 7), MouthRt(498, HVDL, 7),
      BendIn(500, HVDn, 13), MouthOld(502, HVDR), MouthOld(514, HVDL), BendIn(516, HVDn), ThreeDown(518, 0, 13, 6), BendIn(520, HVDn, 13), MouthOld(522, HVDR)),

    TRow(464, sea * 9, oceanic, oceanic * 4, oceanic * 3, hillyOce * 7, oceanic, hillyOce),
    VRow(463, BendIn(492, HVDR, 13), MouthLt(494, HVUR, 7), Bend(516, HVDR, 10, 7), Bend(518, HVUL, 8, 1)),
    TRow(462, sea * 8, hillyOce, hillyOce, hillyOce, oceanic, oceanic, hillyOce, oceanic, oceanic * 3, hillyOce * 8),

    VRow(461, BendIn(490, HVDR), BendOut(492, HVUL), SourceLt(500, HVDR, 7), BendIn(502, HVUp, 13), BendOut(504, HVDn, 7), BendIn(506, HVUp, 13),
      BendOut(508, HVDn, 7), BendIn(510, HVUp, 13), BendOut(512, HVDn, 7), BendInLt(514, HVUp, 13, 7), BendMax(516, HVUL)),

    TRow(460, sea * 7, hillyOce, sea * 5, oceanic * 3, hillyOce * 10),
    VRow(459, BendIn(490, HVUR, 13), BendIn(492, HVUp, 13), MouthRt(494, HVUR, 7), MouthOld(508, HVDL), MouthOld(512, HVDR)),
    TRow(458, sea * 11, CapeOld(4, 2), CapeOld(0, 1), oceanic * 6, hillyOce * 3, oceanic, hillyOce * 3, oceanic),
    VRow(457, MouthOld(506, HVDR)),
    TRow(456, sea * 9, CapeOld(4, 3), CapeOld(0, 2), CapeOld(5, 2), oceanic * 7, hillyOce * 3, oceanic * 3, hillyOce * 2),
    VRow(455, MouthOld(502, HVDn)),
    TRow(454, sea * 9, oceanic * 10, hillyOceForest * 2, oceanic * 4, hillyOce, mtainOld),
    TRow(452, sea * 11, oceanic * 9, hillyOce * 2, mtainOld * 6),
    TRow(450, sea * 12, oceanic * 7, hillyOce, mtainOld * 8),
    TRow(448, sea * 12, oceanic * 4, hillyOce * 2, oceanic, mtainOld * 9),
    TRow(446, sea * 13, oceanic * 2, hillyOce * 4, mtainOld * 3, oceanic * 2, mtainOld, oceanic * 2, hillyOce * 2),
    TRow(444, sea * 13, oceForest, oceanic, hillyOce * 5, mtainOld * 2, hillyOce * 2, oceanic * 3, sea, oceanic),
    TRow(442, sea * 12, oceanic, oceForest, hillyOce * 3, mtainOld, hillyOce, mtainOld * 2, hillyOce, mtainOld * 3, hillyOce, CapeOld(1, 1), sea * 2),
    TRow(440, sea * 13, oceanic * 3, hillyOce, mtainOld, hillyOce * 3, mtainOld * 2, sea * 2, hillyOce, mtainOld, hillyOce, sea * 2),
    TRow(438, sea * 5, hillyOce * 3, mtainOld * 3, hillyOce, mtainOld * 2, hillyOce * 4, sea * 2, hillyOceForest * 2, sea * 4, hillyOce * 3, sea),
    TRow(436, sea * 5, hillyOce * 3, oceanic * 3, deshot * 2, hillyOce, mtainOld * 4, sea * 5, hillyOce, sea * 2, hillyOce * 2, mtainOld, sea),
    TRow(434, sea * 6, hillyOce * 2, oceanic, deshot, hillyDeshot, oceanic, hillyOce, oceanic * 2, hillyOce * 3, sea * 6, hillyOce, sea * 2, CapeOld(3, 2, hillyOce), hillyOce, mtainOld, hillyOce),
    TRow(432, sea * 6, hillyOce * 3, oceanic, deshot, hillyOce * 3, deshot, oceanic, hillyOce * 2, sea * 7, CapeOld(5, 3, hillyOce), sea * 3, CapeOld(3, 2, hillyOce), hillyOce),
    TRow(430, sea * 5, oceanic, hillyOce * 5, deshot, hillyOce, hillyDeshot, mtainOld, oceanic, sea * 8, hillyOce, CapeOld(1, 2, hillyOce), sea * 4, hillyOce),
    TRow(428, sea * 6, hillyOce * 2, oceanic * 3, deshot * 2, hillyOce * 3, sea * 8, hillyOce * 2, sea * 5),
    TRow(426, sea * 6, hillyOce, oceanic * 3, hillyOce * 2, deshot * 2, hillyOce, oceanic, sea * 3, oceanic, sea * 5, hillyOce, sea * 6),
    TRow(424, sea * 5, oceanic * 5, hillyOce * 6, sea * 16),
    TRow(422, sea * 6, oceanic * 2, hillyOce * 4, hillyDeshot, hillyOce, hillyDeshot, oceanic, sea * 14, CapeOld(0, 1, hillyOce), CapeOld(0, 1, mtainOld)),
    TRow(420, sea * 7, hillyOce, oceanic * 3, hillyDeshot, hillyOce * 4, sea * 14, hillyOce * 3),
    TRow(418, sea * 8, oceanic, hillyOce * 2, mtainOld * 2, hillyDeshot, sea * 5, hillyOce, mtainOld, sea, mtainOld * 2, hillyOce, mtainOld, hillyOce * 2, sea * 4, CapeOld(1, 4, hillyOce)),
    TRow(416, sea * 9, hillyOce * 2, sea * 6, mtainOld, hillyOce * 7, mtainOld, hillyOce * 2, hillyDeshot, sea * 4),
    )
  }
  help.run
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
