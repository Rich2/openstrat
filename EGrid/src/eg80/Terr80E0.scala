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
  {
    override val rowDatas: RArr[RowBase] = RArr(
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
      TRow(498, sea * 4, Isle10(hillyTemp), sea * 7, taiga * 7),
      TRow(496, sea * 13, taiga * 7),
      TRow(494, sea * 9, CapeOld(5, 4, hillyTemp), sea * 3, taiga * 7),
      TRow(492, sea * 8, CapeOld(2, 4, hillyTemp), sea * 4, taiga * 6),
      TRow(490, sea * 14, taiga * 3, hillyTaiga, taiga * 3),
      VRow(489, MouthRt(498, HVDL, 7), BendIn(500, HVDn, 13)),
      TRow(488, sea * 7, CapeOld(1, 1, hillyTemp), Isle10(hillyTemp), sea * 6, taiga * 2, sea, CapeOld(4, 1), taiga * 2),
      TRow(486, sea * 6, Isle10(hillyTemp), mtainOld, CapeOld(1, 2, hillyTemp), sea * 7, CapeOld(2, 3, hillyTaiga), sea * 2, CapeOld(4, 1), temperate * 2),
      VRow(485, MouthOld(502, HVDL)),
      TRow(484, sea * 5, hillyTemp, mtainOld, hillyTempForest, hillyTemp, sea * 9, CapeOld(5, 4), CapeOld(4, 1), temperate * 2),
      TRow(482, sea * 6, mtainOld * 2, hillyTemp, sea * 8, temperate * 2, sea, CapeOld(4, 1), temperate),
      TRow(480, sea * 7, hillyTemp * 3, sea * 8, temperate * 2, sea, temperate * 2),
      VRow(479, Bend(544, HVDR, 13, 4), Bend(546, HVUL, 5, 7), MouthOld(550, HVUL), BendAllOld(552, HVDL)),
      TRow(478, sea * 6, hillyTemp, temperate * 3, sea * 7, temperate * 2, temperate * 2, temperate, sea),

      VRow(477, BendIn(484, HVDR, 13), MouthLt(484, HVUR, 7), MouthOld(492, HVUL), ThreeDown(494, 3, 10, 3), MouthOld(496, HVUR), BendIn(544, HVUR, 13),
        BendOut(546, HVDL), MouthOld(552, HVDn)),

      TRow(476, sea * 4, mtainOld, hillyTemp, temperate, hillyTemp, hillyTemp * 2, temperate, sea * 7, temperate * 2, temperate, sea * 2),
      VRow(475, MouthRt(480, HVDL, 7), BendOut(482, HVUL, 7), Bend(494, HVUR, 8, 3), BendIn(496, HVDL, 8), BendIn(546, HVUR), MouthOld(548, HVDR, 7)),
      TRow(474, sea * 4, temperate, temperate * 2, hillyTemp, SepB(), sea, hillyTemp, temperate, hillyTemp, sea * 7, temperate * 2, sea * 3),
      VRow(473, BendIn(494, HVDR, 7), BendIn(496, HVUL, 13)),
      TRow(472, sea * 3, temperate * 4, sea * 2, temperate, hillyTemp, temperate, sea * 5, temperate * 7),
      VRow(471, MouthOld(492, HVDL), BendIn(494, HVUL, 13)),
      TRow(470, sea * 4, temperate * 3, sea, hillyTemp * 2, temperate * 2, sea * 4, temperate * 9),
      TRow(468, sea * 4, temperate * 3, sea * 2, hillyTemp * 2, temperate * 3, sea * 2, temperate * 9),
      TRow(466, sea * 3, temperate * 3, sea * 2, hillyTemp * 2, temperate * 3, sea * 2, temperate * 4, hillyTemp * 2, temperate * 4),

      VRow(465, MouthLt(482, HVDL, 7), BendOut(484, HVDn, 7), BendIn(486, HVUp, 13), MouthRt(488, HVUR, 7), MouthRt(498, HVDL, 7),
        BendIn(500, HVDn, 13), MouthOld(502, HVDR), MouthOld(514, HVDL), BendIn(516, HVDn), ThreeDown(518, 0, 13, 6), BendIn(520, HVDn, 13), MouthOld(522, HVDR)),

      TRow(464, sea * 9, temperate, temperate * 4, SepB(), temperate * 3, hillyTemp * 7, temperate, hillyTemp),
      VRow(463, BendIn(492, HVDR, 13), MouthLt(494, HVUR, 7), BendAllOld(516, HVDR), BendIn(518, HVUL)),
      TRow(462, sea * 8, hillyTemp, hillyTemp, CapeOld(3, 1, hillyTemp), CapeOld(3, 1), CapeOld(2, 2), sea, temperate, temperate * 3, hillyTemp * 8),
      VRow(461, BendIn(490, HVDR), BendOut(492, HVUL), MouthOld(514, HVDL), BendOut(516, HVUL, 7)),
      TRow(460, sea * 7, hillyTemp, sea * 5, temperate * 3, hillyTemp * 10),
      VRow(459, BendIn(490, HVUR, 13), BendIn(492, HVUp, 13), MouthRt(494, HVUR, 7), MouthOld(508, HVDL), MouthOld(512, HVDR)),
      TRow(458, sea * 11, CapeOld(4, 2), CapeOld(0, 1), temperate * 6, hillyTemp * 3, temperate, hillyTemp * 3, temperate),
      VRow(457, MouthOld(506, HVDR)),
      TRow(456, sea * 9, CapeOld(4, 3), CapeOld(0, 2), CapeOld(5, 2), temperate * 7, hillyTemp * 3, temperate * 3, hillyTemp * 2),
      VRow(455, MouthOld(502, HVDn)),
      TRow(454, sea * 9, temperate * 10, hillyTempForest * 2, temperate * 4, hillyTemp, mtainOld),
      TRow(452, sea * 11, temperate * 9, hillyTemp * 2, mtainOld * 6),
      TRow(450, sea * 12, temperate * 7, hillyTemp, mtainOld * 8),
      TRow(448, sea * 12, temperate * 4, hillyTemp * 2, temperate, mtainOld * 9),
      TRow(446, sea * 13, temperate * 2, hillyTemp * 4, mtainOld * 3, temperate * 2, mtainOld, temperate * 2, hillyTemp * 2),
      TRow(444, sea * 13, tempForest, temperate, hillyTemp * 5, mtainOld * 2, hillyTemp * 2, temperate * 3, sea, temperate),
      TRow(442, sea * 12, temperate, tempForest, hillyTemp * 3, mtainOld, hillyTemp, mtainOld * 2, hillyTemp, mtainOld * 3, hillyTemp, CapeOld(1, 1), sea * 2),
      TRow(440, sea * 13, temperate * 3, hillyTemp, mtainOld, hillyTemp * 3, mtainOld * 2, sea * 2, hillyTemp, mtainOld, hillyTemp, sea * 2),
      TRow(438, sea * 5, hillyTemp * 3, mtainOld * 3, hillyTemp, mtainOld * 2, hillyTemp * 4, sea * 2, hillyTempForest * 2, sea * 4, hillyTemp * 3, sea),
      TRow(436, sea * 5, hillyTemp * 3, temperate * 3, desert * 2, hillyTemp, mtainOld * 4, sea * 5, hillyTemp, sea * 2, hillyTemp * 2, mtainOld, sea),
      TRow(434, sea * 6, hillyTemp * 2, temperate, desert, hillyDesert, temperate, hillyTemp, temperate * 2, hillyTemp * 3, sea * 6, hillyTemp, sea * 2, CapeOld(3, 2, hillyTemp), hillyTemp, mtainOld, hillyTemp),
      TRow(432, sea * 6, hillyTemp * 3, temperate, desert, hillyTemp * 3, desert, temperate, hillyTemp * 2, sea * 7, CapeOld(5, 3, hillyTemp), sea * 3, CapeOld(3, 2, hillyTemp), hillyTemp),
      TRow(430, sea * 5, temperate, hillyTemp * 5, desert, hillyTemp, hillyDesert, mtainOld, temperate, sea * 8, hillyTemp, CapeOld(1, 2, hillyTemp), sea * 4, hillyTemp),
      TRow(428, sea * 6, hillyTemp * 2, temperate * 3, desert * 2, hillyTemp * 3, sea * 8, hillyTemp * 2, sea * 5),
      TRow(426, sea * 6, hillyTemp, temperate * 3, hillyTemp * 2, desert * 2, hillyTemp, temperate, sea * 3, temperate, sea * 5, hillyTemp, sea * 6),
      TRow(424, sea * 5, temperate * 5, hillyTemp * 6, sea * 16),
      TRow(422, sea * 6, temperate * 2, hillyTemp * 4, hillyDesert, hillyTemp, hillyDesert, temperate, sea * 14, CapeOld(0, 1, hillyTemp), CapeOld(0, 1, mtainOld)),
      TRow(420, sea * 7, hillyTemp, temperate * 3, hillyDesert, hillyTemp * 4, sea * 14, hillyTemp * 3),
      TRow(418, sea * 8, temperate, hillyTemp * 2, mtainOld * 2, hillyDesert, sea * 5, hillyTemp, mtainOld, sea, mtainOld * 2, hillyTemp, mtainOld, hillyTemp * 2, sea * 4, CapeOld(1, 4, hillyTemp)),
      TRow(416, sea * 9, hillyTemp * 2, sea * 6, mtainOld, hillyTemp * 7, mtainOld, hillyTemp * 2, hillyDesert, sea * 4),
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
