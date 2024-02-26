/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, WTiles._

/** The 80 Km grid centred on 30°E for 15°E to 45°E, covers North East Europe. The c or column offset for 30E is 1536 which is 1G0 in base 32. Current
 * r offset is 300 for the equator.
 *  Isle8 1060.881km² <= 1753.701km² includes Rhodes */
object Terr80E30  extends Long80Terrs
{
  override implicit val grid: EGrid80LongFull = EGrid80.e30(410)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()
  
  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(556, CapeOld(1, 1, mtainOld), ice, CapeOld(2, 1, ice), sea * 4),
      VRow(555, BendOut(1526, HVUR)),
      TRow(554, CapeOld(1, 2, mtainOld), CapeOld(2, 3, ice), sea * 5),
      TRow(552, ice, sea * 7),
      TRow(550, mtainOld, sea * 7),
      TRow(548, mtainOld, sea * 8),

      TRow(526, sea * 2, taiga * 4, sea * 7),
      TRow(524, sea, taiga * 5, sea * 8),
      TRow(522, sea, taiga * 8, sea * 5),
      TRow(520, taiga * 11, sea * 4),
      TRow(518, taiga * 12, sea * 2, taiga),
      TRow(516, taiga * 13, sea * 2, taiga),
      TRow(514, taiga * 14, sea * 2),
      TRow(512, taiga * 10, sea * 2, taiga, sea, taiga * 2),
      TRow(510, taiga * 4, sea, taiga * 6, sea * 3, taiga * 3),
      TRow(508, taiga * 3, sea * 3, taiga * 5, sea, taiga, sea, taiga * 3),
      TRow(506, taiga * 4, sea, taiga * 7, sea, taiga * 5),
      VRow(505, MouthOld(1552, HVUL)),
      TRow(504, taiga * 2, sea * 2, taiga * 14),
      VRow(505, MouthOld(1554, HVDR)),
      TRow(502, taiga * 2, sea * 2, taiga * 14),
      TRow(500, taiga * 2, sea * 2, taiga * 15),
      TRow(498, taiga, sea * 2, taiga * 9, Lake, taiga * 6),
      TRow(496, taiga, sea * 3, taiga * 6, Lake, taiga * 2, Lake, taiga * 6),
      TRow(494, taiga * 2, sea * 2, taiga * 6, Lake * 2, taiga * 8),
      TRow(492, taiga * 2, sea * 2, taiga, sea * 4, taiga * 11),
      TRow(490, taiga * 2, sea * 4, temperate * 15),
      TRow(488, taiga, sea * 4, CapeOld(1, 2), temperate * 15),
      TRow(486, temperate, sea * 4, CapeOld(2, 2), sea, temperate * 15),
      TRow(484, temperate, sea, temperate, sea, CapeOld(5, 2), sea, temperate * 16),
      TRow(482, temperate, sea * 3, CapeOld(5, 1), temperate * 17),
      TRow(480, temperate, sea * 3, CapeOld(4, 2), temperate * 18),
      TRow(478, sea * 4, temperate * 19),
      TRow(476, sea * 4, temperate * 19),
      TRow(474, sea, temperate * 23),
      TRow(472, temperate * 24),
      TRow(470, temperate * 25),
      TRow(468, temperate * 25),
      TRow(466, temperate * 25),
      TRow(464, temperate * 26),
      TRow(462, hillyTemp, temperate * 25),
      TRow(460, hillyTemp, temperate * 25),
      TRow(458, hillyTemp * 3, mtainOld * 3, temperate * 21),
      TRow(456, hillyTemp, temperate, hillyTemp * 4, mtainOld * 2, hillyTemp, temperate * 18),
      TRow(454, temperate * 7, mtainOld * 2, hillyTemp * 3, temperate * 15),
      TRow(452, mtainOld, temperate * 6, hillyTemp, mtainOld * 2, hillyTemp * 3, temperate * 15),
      VRow(451, MouthOld(1570, HVUR)),
      TRow(450, hillyTemp, temperate * 5, hillyTemp * 3, mtainOld * 2, hillyTemp * 3, temperate * 7, sea, CapeOld(5, 1), temperate * 5),
      TRow(448, hillyTemp, temperate, hillyTemp, temperate * 3, hillyTemp * 3, mtainOld, hillyTemp, temperate * 3, sea * 2, temperate * 2, sea * 3, temperate * 7),
      TRow(446, hillyTemp * 2, temperate * 4, mtainOld * 4, hillyTemp * 1, temperate * 3, sea * 3, temperate * 2, sea * 2, temperate * 8),
      VRow(445, MouthOld(1562, HVUp)),
      TRow(444, hillyTemp * 3, temperate * 3, hillyTemp * 4, temperate * 4, sea * 4, hillyTemp * 2, CapeOld(2, 2), CapeOld(2, 3), temperate * 4, hillyTemp, temperate * 2),
      VRow(433, MouthOld(1478, HVDL)),
      TRow(442, mtainOld * 3, hillyTemp * 5, temperate * 5, sea * 9, mtainOld * 2, hillyTemp * 2, temperate * 3),
      TRow(440, hillyTemp, mtainOld * 4, hillyTemp, mtainOld, temperate * 3, hillyTemp * 2, temperate, sea * 11, mtainOld * 3, hillyTemp, temperate * 2),
      TRow(438, sea, CapeOld(3, 2, mtainOld), mtainOld * 4, hillyTemp, mtainOld * 2, hillyTemp * 4, sea * 13, mtainOld * 3, temperate),
      TRow(436, Sea * 2, CapeOld(3, 2, mtainOld), mtainOld, hillyTemp * 2, mtainOld * 2, hillyTemp * 2, temperate * 2, sea * 14, temperate, hillyTemp, mtainOld * 2),

      TRow(434, CapeOld(1, 2, hillyTemp), sea * 3, hillyTemp, mtainOld, hillyTemp * 2, mtainOld, hillyTemp * 2, temperate, hillyTemp, sea * 4, hillyTemp * 4, sea * 6, hillyTemp, mtainOld,
        hillyTemp * 2),

      VRow(433, BendIn(1490, HVDR, 13)),
      TRow(432, hillyTemp, CapeOld(0, 2), sea * 2, hillyTemp, mtainOld, hillyTemp * 6, temperate * 2, hillyTemp, hillyTemp * 7, temperate, hillyTemp * 8),
      VRow(431, MouthRt(1486, HVUL), ThreeDown(1488, 0, 13, 10), BendOut(1490, HVUL, 7)),

      TRow(430, hillyTemp, savannah * 2, mtainOld, hillyTemp, mtainOld, hillyTemp, temperate, hillyTemp * 2, sea, hillyTemp, sea, hillyTemp * 2, mtainOld * 5, hillyTemp * 3, mtainOld * 4, hillyTemp * 3,
      mtainOld),

      VRow(429, BendIn(1488, HVUR, 13), BendOut(1490, HVDL, 7), MouthLt(1504, HVUL), Bend(1506, HVDL, 4, 7)),

      TRow(428, hillyTemp, sea, sea * 2, mtainOld * 2, hillyTemp * 2, hillyTemp * 2, sea, hillyTemp * 3, mtainOld, hillyTemp * 4, hillyDesert * 2, hillyTemp * 4, hillyDesert * 2,
      hillyTemp * 3, temperate),

      VRow(427, MouthRt(1490, HVDn), BendIn(1506, HVUR)),

      TRow(426, sea, hillyTemp, sea * 3, hillyTemp, mtainOld, temperate, hillyTemp, sea * 3, hillyTemp * 2, mtainOld, hillyTemp * 2, desert, hillyDesert * 7, mtainOld * 2, hillyTemp,
      hillyDesert * 2, mtainOld, hillyDesert),

      TRow(424, CapeOld(5, 1, hillyTemp), sea * 4, hillyTemp, mtainOld, hillyTemp * 2, sea * 3, hillyTemp * 5, hillyDesert, desert * 2, hillyDesert * 6, mtainOld * 2, hillyDesert, lake,
      hillyDesert, mtainOld),

      VRow(423, BendAllOld(1500, HVUp)),

      TRow(422, CapeOld(2, 4, mtainOld), sea * 4, Isle10(hillyTemp), CapeOld(4, 3, hillyTemp), CapeOld(0, 2, mtainOld), hillyTemp * 2, sea * 2, hillyTemp * 7, desert,
        hillyDesert, desert, mtainOld, hillyTemp, hillyDesert, mtainOld, temperate * 2, hillyDesert, mtainOld * 2, hillyDesert),

      VRow(421, MouthOld(1504, HVDn)),

      TRow(420, sea * 7, hillyTemp * 2, sea * 4, hillyTemp, mtainOld, hillyTemp * 2, mtainOld, hillyTemp, temperate, desert, hillyTemp * 2, mtainOld, hillyTemp * 2, temperate * 2, desert * 4,
      mtainOld),

      VRow(419, SetSep(1471)),
      TRow(418, SepB(), sea * 7, hillyTemp, sea * 5, hillyTemp * 5, mtainOld, hillyTemp * 2, CapeOld(3, 1) * 2, hillyTemp, desert * 2, temperate, desert * 3, hillyDesert * 2, mtainOld),
      VRow(417, MouthOld(1564, HVUR)),

      TRow(416, sea * 10, sea * 4, Isle10(hillyTemp), sea, CapeOld(2, 3, mtainOld), sea * 2, CapeOld(3, 2, mtainOld), CapeOld(2, 2, mtainOld), sea * 2, hillyTemp, temperate,
        desert * 7, hillyDesert),

      TRow(414, sea * 9, hillyTemp, sea * 10, CapeOld(5, 4, hillyTemp), sea, hillyTemp * 2, desert * 8, hillyDesert),
      TRow(412, sea * 10, hillyTemp * 2, sea * 7, hillyTemp, temperate, sea * 2, hillyTemp, desert * 10),
      TRow(410, sea * 11, sea * 12, hillyTemp, hillyDesert, desert * 9),
    )
  }
  help.run
}