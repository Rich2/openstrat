/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, WTiles._

/** 80 Km tile width grid centred on the Greenwich meridian, 0°E form 15°W to 15°E. Covers North West Europe. The c or column offset is 512 which is G0
 *  in base 32. The c offset for North East Europe will be 1536 or 1G0 in base 32. Current y offset is 300 for the equator. The Old c offset was 200 so a diff of 312 */
object Terr80E0 extends Long80Terrs
{ implicit val grid: EGrid80LongFull = EGrid80.e0(416, 582)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(556, sea *6, Cape(4, 2, mtain)),
      TRow(554, sea * 6, mtain),
      TRow(552, sea * 7, mtain),

      TRow(518, sea * 14, taiga),
      TRow(516, sea * 15, taiga),
      TRow(514, sea * 15, taiga),
      TRow(512, sea * 15, taiga),
      TRow(510, sea * 15, taiga * 2),
      TRow(508, Cape(1, 3, hillyTundra), sea * 14, taiga * 2),
      TRow(506, sea * 15, taiga * 3),
      TRow(504, sea * 14, taiga * 4),
      TRow(502, sea * 13, taiga * 5),
      TRow(500, sea * 13, taiga * 6),
      TRow(498, sea * 4, Isle10(hilly), sea * 7, taiga * 7),
      TRow(496, sea * 13, taiga * 7),
      TRow(494, sea * 9, Cape(5, 4, hilly), sea * 3, taiga * 7),
      TRow(492, sea * 8, Cape(2, 4, hilly), sea * 4, taiga * 6),
      TRow(490, sea * 14, taiga * 3, hillyTaiga, taiga * 3),
      VRow(489, MouthRt(498, HVDL, 7), BendIn(500, HVDn, 13)),
      TRow(488, sea * 7, Cape(1, 1, hilly), Isle10(hilly), sea * 6, taiga * 2, sea, Cape(4, 1), taiga * 2),
      TRow(486, sea * 6, Isle10(hilly), mtain, Cape(1, 2, hilly), sea * 7, Cape(2, 3, hillyTaiga), sea * 2, Cape(4, 1), land * 2),
      VRow(485, MouthOld(502, HVDL)),
      TRow(484, sea * 5, hilly, mtain, hillyForest, hilly, sea * 9, Cape(5, 4), Cape(4, 1), land * 2),
      TRow(482, sea * 6, mtain * 2, hilly, sea * 8, land * 2, sea, Cape(4, 1), land),
      TRow(480, sea * 7, hilly * 3, sea * 8, land * 2, sea, land * 2),
      VRow(479, BendInOut(544, HVDR, 13, 4), BendInOut(546, HVUL, 5, 7), MouthOld(550, HVUL), BendAll(552, HVDL)),
      TRow(478, sea * 6, hilly, land * 3, sea * 7, land * 2, land * 2, land, sea),

      VRow(477, BendIn(484, HVDR, 13), MouthLt(484, HVUR, 7), MouthOld(492, HVUL), ThreeDown(494, 3, 10, 3), MouthOld(496, HVUR), BendIn(544, HVUR, 13),
        BendOut(546, HVDL), MouthOld(552, HVDn)),

      TRow(476, sea * 4, mtain, hilly, land, hilly, hilly * 2, land, sea * 7, land * 2, land, sea * 2),
      VRow(475, MouthRt(480, HVDL, 7), BendOut(482, HVUL, 7), BendInOut(494, HVUR, 8, 3), BendIn(496, HVDL, 8), BendIn(546, HVUR), MouthOld(548, HVDR, 7)),
      TRow(474, sea * 4, land, land * 2, hilly, SepB(), sea, hilly, land, hilly, sea * 7, land * 2, sea * 3),
      VRow(473, BendIn(494, HVDR, 7), BendIn(496, HVUL, 13)),
      TRow(472, sea * 3, land * 4, sea * 2, land, hilly, land, sea * 5, land * 7),
      VRow(471, MouthOld(492, HVDL), BendIn(494, HVUL, 13)),
      TRow(470, sea * 4, land * 3, sea, hilly * 2, land * 2, sea * 4, land * 9),
      TRow(468, sea * 4, land * 3, sea * 2, hilly * 2, land * 3, sea * 2, land * 9),
      TRow(466, sea * 3, land * 3, sea * 2, hilly * 2, land * 3, sea * 2, land * 4, hilly * 2, land * 4),

      VRow(465, MouthLt(482, HVDL, 7), BendOut(484, HVDn, 7), BendIn(486, HVUp, 13), MouthRt(488, HVUR, 7), MouthRt(498, HVDL, 7),
        BendIn(500, HVDn, 13), MouthOld(502, HVDR), MouthOld(514, HVDL), BendIn(516, HVDn), ThreeDown(518, 0, 13, 6), BendIn(520, HVDn, 13), MouthOld(522, HVDR)),

      TRow(464, sea * 9, land, land * 4, SepB(), land * 3, hilly * 7, land, hilly),
      VRow(463, BendIn(492, HVDR, 13), MouthLt(494, HVUR, 7), BendAll(516, HVDR), BendIn(518, HVUL)),
      TRow(462, sea * 8, hilly, hilly, Cape(3, 1, hilly), Cape(3, 1), Cape(2, 2), sea, land, land * 3, hilly * 8),
      VRow(461, BendIn(490, HVDR), BendOut(492, HVUL), MouthOld(514, HVDL), BendOut(516, HVUL, 7)),
      TRow(460, sea * 7, hilly, sea * 5, land * 3, hilly * 10),
      VRow(459, BendIn(490, HVUR, 13), BendIn(492, HVUp, 13), MouthRt(494, HVUR, 7), MouthOld(508, HVDL), MouthOld(512, HVDR)),
      TRow(458, sea * 11, Cape(4, 2), Cape(0, 1), land * 6, hilly * 3, land, hilly * 3, land),
      VRow(457, MouthOld(506, HVDR)),
      TRow(456, sea * 9, Cape(4, 3), Cape(0, 2), Cape(5, 2), land * 7, hilly * 3, land * 3, hilly * 2),
      VRow(455, MouthOld(502, HVDn)),
      TRow(454, sea * 9, land * 10, hillyForest * 2, land * 4, hilly, mtain),
      TRow(452, sea * 11, land * 9, hilly * 2, mtain * 6),
      TRow(450, sea * 12, land * 7, hilly, mtain * 8),
      TRow(448, sea * 12, land * 4, hilly * 2, land, mtain * 9),
      TRow(446, sea * 13, land * 2, hilly * 4, mtain * 3, land * 2, mtain, land * 2, hilly * 2),
      TRow(444, sea * 13, forest, land, hilly * 5, mtain * 2, hilly * 2, land * 3, sea, land),
      TRow(442, sea * 12, land, forest, hilly * 3, mtain, hilly, mtain * 2, hilly, mtain * 3, hilly, Cape(1, 1), sea * 2),
      TRow(440, sea * 13, land * 3, hilly, mtain, hilly * 3, mtain * 2, sea * 2, hilly, mtain, hilly, sea * 2),
      TRow(438, sea * 5, hilly * 3, mtain * 3, hilly, mtain * 2, hilly * 4, sea * 2, hillyForest * 2, sea * 4, hilly * 3, sea),
      TRow(436, sea * 5, hilly * 3, land * 3, desert * 2, hilly, mtain * 4, sea * 5, hilly, sea * 2, hilly * 2, mtain, sea),
      TRow(434, sea * 6, hilly * 2, land, desert, hillyDesert, land, hilly, land * 2, hilly * 3, sea * 6, hilly, sea * 2, Cape(3, 2, hilly), hilly, mtain, hilly),
      TRow(432, sea * 6, hilly * 3, land, desert, hilly * 3, desert, land, hilly * 2, sea * 7, Cape(5, 3, hilly), sea * 3, Cape(3, 2, hilly), hilly),
      TRow(430, sea * 5, land, hilly * 5, desert, hilly, hillyDesert, mtain, land, sea * 8, hilly, Cape(1, 2, hilly), sea * 4, hilly),
      TRow(428, sea * 6, hilly * 2, land * 3, desert * 2, hilly * 3, sea * 8, hilly * 2, sea * 5),
      TRow(426, sea * 6, hilly, land * 3, hilly * 2, desert * 2, hilly, land, sea * 3, land, sea * 5, hilly, sea * 6),
      TRow(424, sea * 5, land * 5, hilly * 6, sea * 16),
      TRow(422, sea * 6, land * 2, hilly * 4, hillyDesert, hilly, hillyDesert, land, sea * 14, Cape(0, 1, hilly), Cape(0, 1, mtain)),
      TRow(420, sea * 7, hilly, land * 3, hillyDesert, hilly * 4, sea * 14, hilly * 3),
      TRow(418, sea * 8, land, hilly * 2, mtain * 2, hillyDesert, sea * 5, hilly, mtain, sea, mtain * 2, hilly, mtain, hilly * 2, sea * 4, Cape(1, 4, hilly)),
      TRow(416, sea * 9, hilly * 2, sea * 6, mtain, hilly * 7, mtain, hilly * 2, hillyDesert, sea * 4),
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
  def wFrontScen : EScenBasic = EScenBasic(wfGrid, wfTerrs, wfSTerrs, wfCorners, "Western Front")
}
