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
      TRow(490, taiga * 2, sea * 4, plain * 15),
      TRow(488, taiga, sea * 4, CapeOld(1, 2), plain * 15),
      TRow(486, plain, sea * 4, CapeOld(2, 2), sea, plain * 15),
      TRow(484, plain, sea, plain, sea, CapeOld(5, 2), sea, plain * 16),
      TRow(482, plain, sea * 3, CapeOld(5, 1), plain * 17),
      TRow(480, plain, sea * 3, CapeOld(4, 2), plain * 18),
      TRow(478, sea * 4, plain * 19),
      TRow(476, sea * 4, plain * 19),
      TRow(474, sea, plain * 23),
      TRow(472, plain * 24),
      TRow(470, plain * 25),
      TRow(468, plain * 25),
      TRow(466, plain * 25),
      TRow(464, plain * 26),
      TRow(462, hilly, plain * 25),
      TRow(460, hilly, plain * 25),
      TRow(458, hilly * 3, mtainOld * 3, plain * 21),
      TRow(456, hilly, plain, hilly * 4, mtainOld * 2, hilly, plain * 18),
      TRow(454, plain * 7, mtainOld * 2, hilly * 3, plain * 15),
      TRow(452, mtainOld, plain * 6, hilly, mtainOld * 2, hilly * 3, plain * 15),
      VRow(451, MouthOld(1570, HVUR)),
      TRow(450, hilly, plain * 5, hilly * 3, mtainOld * 2, hilly * 3, plain * 7, sea, CapeOld(5, 1), plain * 5),
      TRow(448, hilly, plain, hilly, plain * 3, hilly * 3, mtainOld, hilly, plain * 3, sea * 2, plain * 2, sea * 3, plain * 7),
      TRow(446, hilly * 2, plain * 4, mtainOld * 4, hilly * 1, plain * 3, sea * 3, plain * 2, sea * 2, plain * 8),
      VRow(445, MouthOld(1562, HVUp)),
      TRow(444, hilly * 3, plain * 3, hilly * 4, plain * 4, sea * 4, hilly * 2, CapeOld(2, 2), CapeOld(2, 3), plain * 4, hilly, plain * 2),
      VRow(433, MouthOld(1478, HVDL)),
      TRow(442, mtainOld * 3, hilly * 5, plain * 5, sea * 9, mtainOld * 2, hilly * 2, plain * 3),
      TRow(440, hilly, mtainOld * 4, hilly, mtainOld, plain * 3, hilly * 2, plain, sea * 11, mtainOld * 3, hilly, plain * 2),
      TRow(438, sea, CapeOld(3, 2, mtainOld), mtainOld * 4, hilly, mtainOld * 2, hilly * 4, sea * 13, mtainOld * 3, plain),
      TRow(436, Sea * 2, CapeOld(3, 2, mtainOld), mtainOld, hilly * 2, mtainOld * 2, hilly * 2, plain * 2, sea * 14, plain, hilly, mtainOld * 2),

      TRow(434, CapeOld(1, 2, hilly), sea * 3, hilly, mtainOld, hilly * 2, mtainOld, hilly * 2, plain, hilly, sea * 4, hilly * 4, sea * 6, hilly, mtainOld,
        hilly * 2),

      VRow(433, BendIn(1490, HVDR, 13)),
      TRow(432, hilly, CapeOld(0, 2), sea * 2, hilly, mtainOld, hilly * 6, plain * 2, hilly, hilly * 7, plain, hilly * 8),
      VRow(431, MouthRt(1486, HVUL), ThreeDown(1488, 0, 13, 10), BendOut(1490, HVUL, 7)),

      TRow(430, hilly, savannah * 2, mtainOld, hilly, mtainOld, hilly, plain, hilly * 2, sea, hilly, sea, hilly * 2, mtainOld * 5, hilly * 3, mtainOld * 4, hilly * 3,
      mtainOld),

      VRow(429, BendIn(1488, HVUR, 13), BendOut(1490, HVDL, 7), MouthLt(1504, HVUL), Bend(1506, HVDL, 4, 7)),

      TRow(428, hilly, sea, sea * 2, mtainOld * 2, hilly * 2, hilly * 2, sea, hilly * 3, mtainOld, hilly * 4, hillyDesert * 2, hilly * 4, hillyDesert * 2,
      hilly * 3, plain),

      VRow(427, MouthRt(1490, HVDn), BendIn(1506, HVUR)),

      TRow(426, sea, hilly, sea * 3, hilly, mtainOld, plain, hilly, sea * 3, hilly * 2, mtainOld, hilly * 2, desert, hillyDesert * 7, mtainOld * 2, hilly,
      hillyDesert * 2, mtainOld, hillyDesert),

      TRow(424, CapeOld(5, 1, hilly), sea * 4, hilly, mtainOld, hilly * 2, sea * 3, hilly * 5, hillyDesert, desert * 2, hillyDesert * 6, mtainOld * 2, hillyDesert, lake,
      hillyDesert, mtainOld),

      VRow(423, BendAllOld(1500, HVUp)),

      TRow(422, CapeOld(2, 4, mtainOld), sea * 4, Isle10(hilly), CapeOld(4, 3, hilly), CapeOld(0, 2, mtainOld), hilly * 2, sea * 2, hilly * 7, desert,
        hillyDesert, desert, mtainOld, hilly, hillyDesert, mtainOld, plain * 2, hillyDesert, mtainOld * 2, hillyDesert),

      VRow(421, MouthOld(1504, HVDn)),

      TRow(420, sea * 7, hilly * 2, sea * 4, hilly, mtainOld, hilly * 2, mtainOld, hilly, plain, desert, hilly * 2, mtainOld, hilly * 2, plain * 2, desert * 4,
      mtainOld),

      VRow(419, SetSep(1471)),
      TRow(418, SepB(), sea * 7, hilly, sea * 5, hilly * 5, mtainOld, hilly * 2, CapeOld(3, 1) * 2, hilly, desert * 2, plain, desert * 3, hillyDesert * 2, mtainOld),
      VRow(417, MouthOld(1564, HVUR)),

      TRow(416, sea * 10, sea * 4, Isle10(hilly), sea, CapeOld(2, 3, mtainOld), sea * 2, CapeOld(3, 2, mtainOld), CapeOld(2, 2, mtainOld), sea * 2, hilly, plain,
        desert * 7, hillyDesert),

      TRow(414, sea * 9, hilly, sea * 10, CapeOld(5, 4, hilly), sea, hilly * 2, desert * 8, hillyDesert),
      TRow(412, sea * 10, hilly * 2, sea * 7, hilly, plain, sea * 2, hilly, desert * 10),
      TRow(410, sea * 11, sea * 12, hilly, hillyDesert, desert * 9),
    )
  }
  help.run
}