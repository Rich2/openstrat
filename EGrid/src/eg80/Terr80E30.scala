/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid._, phex._, egrid._, WTile._

/** The 80 Km grid centred on 30E for 15E to 45E, covers North East Europe. The c or column offset for 30E is 1536 which is 1G0 in base 32. Current y offset is 300 for
 *  the equator. The Old c offset was 400 so a diff of 1136. */
object Terr80E30  extends Long80Terrs
{ override implicit val grid: EGrid80LongFull = EGrid80.e30(410)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()
  
  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
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
      TRow(490, taiga * 2, sea * 4, plain * 15),
      TRow(488, taiga, sea * 4, plain * 16),
      TRow(486, plain, sea * 4, plain, sea, plain * 15),
      TRow(484, plain, sea, plain, sea, plain, sea, plain * 16),
      TRow(482, plain, sea * 3, plain * 18),
      TRow(480, plain, sea * 4, plain * 18),
      TRow(478, sea * 4, plain * 19),
      TRow(476, sea * 4, plain * 19),
      TRow(474, sea, plain * 23),
      TRow(472, plain * 24),
      TRow(470, plain * 25),
      TRow(468, plain * 25),
      TRow(466, plain * 25),
      TRow(464, plain * 26),
      TRow(462, hills, plain * 25),
      TRow(460, hills, plain * 25),
      TRow(458, hills * 3, mtain * 3, plain * 21),
      TRow(456, hills, plain, hills * 4, mtain * 2, hills, plain * 18),
      TRow(454, plain * 7, mtain * 2, hills * 3, plain * 15),
      TRow(452, mtain, plain * 6, hills, mtain * 2, hills * 3, plain * 15),
      VRow(451, Mouth(1570, HVUR)),
      TRow(450, hills, plain * 5, hills * 3, mtain * 2, hills * 3, plain * 7, sea, Hland(1, 5), plain * 5),
      TRow(448, hills, plain, hills, plain * 3, hills * 3, mtain, hills, plain * 3, sea * 2, plain * 2, sea * 3, plain * 7),
      TRow(446, hills * 2, plain * 4, mtain * 4, hills * 1, plain * 3, sea * 3, plain * 2, sea * 2, plain * 8),
      VRow(445, Mouth(1562, HVUp)),
      TRow(444, hills * 3, plain * 3, hills * 4, plain * 4, sea * 4, hills * 2, Hland(2, 2), Hland(2, 3), plain * 4, hills, plain * 2),
      VRow(433, Mouth(1478, HVDL)),
      TRow(442, mtain * 3, hills * 5, plain * 5, sea * 9, mtain * 2, hills * 2, plain * 3),
      TRow(440, hills, mtain * 4, hills, mtain, plain * 3, hills * 2, plain, sea * 11, mtain * 3, hills, plain * 2),
      TRow(438, sea, Hland(2, 3, mtain), mtain * 4, hills, mtain * 2, hills * 4, sea * 13, mtain * 3, plain),
      TRow(436, Sea * 2, Hland(2, 3, mtain), mtain, hills * 2, mtain * 2, hills * 2, plain * 2, sea * 14, plain, hills, mtain * 2),

      TRow(434, Hland(2, 1, hills), sea * 3, hills, mtain, hills * 2, mtain, hills * 2, plain, hills, sea * 4, hills * 4, sea * 6, hills, mtain,
        hills * 2),

      TRow(432, hills, Hland(2, 0), sea * 2, Hland(2, 4, hills), mtain, hills * 6, plain * 2, hills, hills * 7, plain, hills * 8),

      TRow(430, hills, plain, sea * 2, hills, mtain, hills, plain, hills * 2, sea, hills, sea, hills * 2, mtain * 5, hills * 3, mtain * 4, hills * 3,
      mtain),

      TRow(428, hills, sea, plain, sea, mtain * 2, hills * 2, hills * 2, sea, hills * 3, mtain, hills * 4, hillyDesert * 2, hills * 4, hillyDesert * 2,
      hills * 3, plain),

      TRow(426, sea, hills, sea * 3, hills, mtain, plain, hills, sea * 3, hills * 2, mtain, hills * 2, desert, hillyDesert * 7, mtain * 2, hills,
      hillyDesert * 2, mtain, hillyDesert),

      TRow(424, Hland(1, 5, hills), sea * 4, hills, mtain, hills * 2, sea * 3, hills * 5, hillyDesert, desert * 2, hillyDesert * 6, mtain * 2, hillyDesert, lake,
      hillyDesert, mtain),

      VRow(423, VertIn(1500, HVUp)),

      TRow(422, Hland(4, 2, mtain), sea * 4, Isle(hills), Hland(3, 4, hills), Hland(2, 0, mtain), hills * 2, sea * 2, hills * 7, desert,
        hillyDesert, desert, mtain, hills, hillyDesert, mtain, plain * 2, hillyDesert, mtain * 2, hillyDesert),

      VRow(421, Mouth(1504, HVDn)),

      TRow(420, sea * 7, hills * 2, sea * 4, hills, mtain, hills * 2, mtain, hills, plain, desert, hills * 2, mtain, hills * 2, plain * 2, desert * 4,
      mtain),

      VRow(419, SetSide(1471)),
      TRow(418, SideB(), sea * 7, hills, sea * 5, hills * 5, mtain, hills * 2, Hland(1, 3) * 2, hills, desert * 2, plain, desert * 3, hillyDesert * 2, mtain),
      VRow(417, Mouth(1564, HVUR)),

      TRow(416, sea * 10, sea * 4, Isle(hills), sea, Hland(3, 2, mtain), sea * 2, Hland(2, 3, mtain), Hland(2, 2, mtain), sea * 2, hills, plain,
        desert * 7, hillyDesert),

      TRow(414, sea * 9, hills, sea * 10, Hland(4, 5, hills), sea, hills * 2, desert * 8, hillyDesert),
      TRow(412, sea * 10, hills * 2, sea * 7, hills, plain, sea * 2, hills, desert * 10),
      TRow(410, sea * 11, sea * 12, hills, hillyDesert, desert * 9),
    )
  }
  help.run
}