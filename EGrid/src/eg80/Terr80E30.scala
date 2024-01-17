/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, WTiles._

/** The 80 Km grid centred on 30E for 15E to 45E, covers North East Europe. The c or column offset for 30E is 1536 which is 1G0 in base 32. Current y offset is 300 for
 *  the equator. The Old c offset was 400 so a diff of 1136. */
object Terr80E30  extends Long80Terrs
{ override implicit val grid: EGrid80LongFull = EGrid80.e30(410)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()
  
  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(556, Cape(1, 1, mtain), ice, Cape(2, 1, ice), sea * 4),
      VRow(555, BendOut(1526, HVUR)),
      TRow(554, Cape(1, 2, mtain), Cape(2, 3, ice), sea * 5),
      TRow(552, ice, sea * 7),
      TRow(550, mtain, sea * 7),
      TRow(548, mtain, sea * 8),

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
      VRow(505, Mouth(1552, HVUL)),
      TRow(504, taiga * 2, sea * 2, taiga * 14),
      VRow(505, Mouth(1554, HVDR)),
      TRow(502, taiga * 2, sea * 2, taiga * 14),
      TRow(500, taiga * 2, sea * 2, taiga * 15),
      TRow(498, taiga, sea * 2, taiga * 9, Lake, taiga * 6),
      TRow(496, taiga, sea * 3, taiga * 6, Lake, taiga * 2, Lake, taiga * 6),
      TRow(494, taiga * 2, sea * 2, taiga * 6, Lake * 2, taiga * 8),
      TRow(492, taiga * 2, sea * 2, taiga, sea * 4, taiga * 11),
      TRow(490, taiga * 2, sea * 4, land * 15),
      TRow(488, taiga, sea * 4, Cape(1, 2), land * 15),
      TRow(486, land, sea * 4, Cape(2, 2), sea, land * 15),
      TRow(484, land, sea, land, sea, Cape(5, 2), sea, land * 16),
      TRow(482, land, sea * 3, Cape(5, 1), land * 17),
      TRow(480, land, sea * 3, Cape(4, 2), land * 18),
      TRow(478, sea * 4, land * 19),
      TRow(476, sea * 4, land * 19),
      TRow(474, sea, land * 23),
      TRow(472, land * 24),
      TRow(470, land * 25),
      TRow(468, land * 25),
      TRow(466, land * 25),
      TRow(464, land * 26),
      TRow(462, hilly, land * 25),
      TRow(460, hilly, land * 25),
      TRow(458, hilly * 3, mtain * 3, land * 21),
      TRow(456, hilly, land, hilly * 4, mtain * 2, hilly, land * 18),
      TRow(454, land * 7, mtain * 2, hilly * 3, land * 15),
      TRow(452, mtain, land * 6, hilly, mtain * 2, hilly * 3, land * 15),
      VRow(451, Mouth(1570, HVUR)),
      TRow(450, hilly, land * 5, hilly * 3, mtain * 2, hilly * 3, land * 7, sea, Cape(5, 1), land * 5),
      TRow(448, hilly, land, hilly, land * 3, hilly * 3, mtain, hilly, land * 3, sea * 2, land * 2, sea * 3, land * 7),
      TRow(446, hilly * 2, land * 4, mtain * 4, hilly * 1, land * 3, sea * 3, land * 2, sea * 2, land * 8),
      VRow(445, Mouth(1562, HVUp)),
      TRow(444, hilly * 3, land * 3, hilly * 4, land * 4, sea * 4, hilly * 2, Cape(2, 2), Cape(2, 3), land * 4, hilly, land * 2),
      VRow(433, Mouth(1478, HVDL)),
      TRow(442, mtain * 3, hilly * 5, land * 5, sea * 9, mtain * 2, hilly * 2, land * 3),
      TRow(440, hilly, mtain * 4, hilly, mtain, land * 3, hilly * 2, land, sea * 11, mtain * 3, hilly, land * 2),
      TRow(438, sea, Cape(3, 2, mtain), mtain * 4, hilly, mtain * 2, hilly * 4, sea * 13, mtain * 3, land),
      TRow(436, Sea * 2, Cape(3, 2, mtain), mtain, hilly * 2, mtain * 2, hilly * 2, land * 2, sea * 14, land, hilly, mtain * 2),

      TRow(434, Cape(1, 2, hilly), sea * 3, hilly, mtain, hilly * 2, mtain, hilly * 2, land, hilly, sea * 4, hilly * 4, sea * 6, hilly, mtain,
        hilly * 2),

      VRow(433, BendIn(1490, HVDR, 13)),

      TRow(432, hilly, Cape(0, 2), sea * 2, hilly, mtain, hilly * 6, land * 2, hilly, hilly * 7, land, hilly * 8),

      TRow(430, hilly, savannah * 2, mtain, hilly, mtain, hilly, land, hilly * 2, sea, hilly, sea, hilly * 2, mtain * 5, hilly * 3, mtain * 4, hilly * 3,
      mtain),

      TRow(428, hilly, sea, savannah, sea, mtain * 2, hilly * 2, hilly * 2, sea, hilly * 3, mtain, hilly * 4, hillyDesert * 2, hilly * 4, hillyDesert * 2,
      hilly * 3, land),

      TRow(426, sea, hilly, sea * 3, hilly, mtain, land, hilly, sea * 3, hilly * 2, mtain, hilly * 2, desert, hillyDesert * 7, mtain * 2, hilly,
      hillyDesert * 2, mtain, hillyDesert),

      TRow(424, Cape(5, 1, hilly), sea * 4, hilly, mtain, hilly * 2, sea * 3, hilly * 5, hillyDesert, desert * 2, hillyDesert * 6, mtain * 2, hillyDesert, lake,
      hillyDesert, mtain),

      VRow(423, BendAll(1500, HVUp)),

      TRow(422, Cape(2, 4, mtain), sea * 4, Isle(hilly), Cape(4, 3, hilly), Cape(0, 2, mtain), hilly * 2, sea * 2, hilly * 7, desert,
        hillyDesert, desert, mtain, hilly, hillyDesert, mtain, land * 2, hillyDesert, mtain * 2, hillyDesert),

      VRow(421, Mouth(1504, HVDn)),

      TRow(420, sea * 7, hilly * 2, sea * 4, hilly, mtain, hilly * 2, mtain, hilly, land, desert, hilly * 2, mtain, hilly * 2, land * 2, desert * 4,
      mtain),

      VRow(419, SetSide(1471)),
      TRow(418, SideB(), sea * 7, hilly, sea * 5, hilly * 5, mtain, hilly * 2, Cape(3, 1) * 2, hilly, desert * 2, land, desert * 3, hillyDesert * 2, mtain),
      VRow(417, Mouth(1564, HVUR)),

      TRow(416, sea * 10, sea * 4, Isle(hilly), sea, Cape(2, 3, mtain), sea * 2, Cape(3, 2, mtain), Cape(2, 2, mtain), sea * 2, hilly, land,
        desert * 7, hillyDesert),

      TRow(414, sea * 9, hilly, sea * 10, Cape(5, 4, hilly), sea, hilly * 2, desert * 8, hillyDesert),
      TRow(412, sea * 10, hilly * 2, sea * 7, hilly, land, sea * 2, hilly, desert * 10),
      TRow(410, sea * 11, sea * 12, hilly, hillyDesert, desert * 9),
    )
  }
  help.run
}