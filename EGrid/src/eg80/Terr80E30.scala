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
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(556, mtainDepr, ice, ice),
      VRow(555, BendOut(1526, HVUR)),
      TRow(554, mtainDepr, ice),
      TRow(552, ice),
      TRow(550, mtainDepr),
      TRow(548, mtainDepr),

      TRow(526, sea * 2, taiga * 4),
      TRow(524, sea, taiga * 5),
      TRow(522, sea, taiga * 8),
      TRow(520, taiga * 11),
      TRow(518, taiga * 12, sea * 2, taiga),
      TRow(516, taiga * 13, sea * 2, taiga),
      TRow(514, taiga * 14, sea * 2),
      TRow(512, taiga * 10, sea * 2, taiga, sea, taiga * 2),
      TRow(510, taiga * 4, sea, taiga * 6, sea * 3, taiga * 3),
      TRow(508, taiga * 3, sea * 3, taiga * 5, sea, taiga, sea, taiga * 3),
      TRow(506, taiga * 4, sea, taiga * 7, sea, taiga * 5),
      VRow(505, OrigMinRevDepr(1552, HVUL)),
      TRow(504, taiga * 2, sea * 2, taiga * 14),
      VRow(505, OrigMinRevDepr(1554, HVDR)),
      TRow(502, taiga * 2, sea * 2, taiga * 14),
      TRow(500, taiga * 2, sea * 2, taiga * 15),
      TRow(498, taiga, sea * 2, taiga * 9, Lake, taiga * 6),
      TRow(496, taiga, sea * 3, taiga * 6, Lake, taiga * 2, Lake, taiga * 6),
      TRow(494, taiga * 2, sea * 2, taiga * 6, Lake * 2, taiga * 8),
      TRow(492, taiga * 2, sea * 2, taiga, sea * 4, taiga * 11),
      TRow(490, taiga * 2, sea * 4, oceanic * 15),
      TRow(488, taiga, sea * 4, continental * 16),
      TRow(486, oceanic, sea * 4, continental, sea, continental * 15),
      TRow(484, oceanic, sea, oceanic, sea, continental, sea, continental * 16),
      TRow(482, oceanic, sea * 3, continental * 18),
      TRow(480, oceanic, sea * 3, continental * 19),
      TRow(478, sea * 4, oceanic * 19),
      TRow(476, sea * 4, oceanic * 19),
      TRow(474, sea, oceanic * 23),
      TRow(472, oceanic * 24),
      TRow(470, oceanic * 25),
      TRow(468, oceanic * 25),
      TRow(466, oceanic * 25),
      TRow(464, oceanic * 26),
      TRow(462, hillyOce, oceanic * 25),
      TRow(460, hillyOce, oceanic * 25),
      TRow(458, hillyOce * 3, mtainDepr * 3, oceanic * 21),
      TRow(456, hillyOce, oceanic, hillyOce * 4, mtainDepr * 2, hillyOce, oceanic * 18),
      TRow(454, oceanic * 7, mtainDepr * 2, hillyOce * 3, oceanic * 15),
      TRow(452, mtainDepr, oceanic * 6, hillyOce, mtainDepr * 2, hillyOce * 3, oceanic * 15),
      VRow(451, OrigMinRevDepr(1570, HVUR)),
      TRow(450, hillyOce, oceanic * 5, hillyOce * 3, mtainDepr * 2, hillyOce * 3, oceanic * 7, sea, steppe, oceanic * 5),
      TRow(448, hillyOce, oceanic, hillyOce, oceanic * 3, hillyOce * 3, mtainDepr, hillyOce, oceanic * 3, sea * 2, oceanic * 2, sea * 3, oceanic * 7),
      TRow(446, hillyOce * 2, oceanic * 4, mtainDepr * 4, hillyOce * 1, oceanic * 3, sea * 3, oceanic * 2, sea * 2, oceanic * 8),
      VRow(445, OrigMinRevDepr(1562, HVUp)),
      TRow(444, hillyOce * 3, oceanic * 3, hillyOce * 4, oceanic * 4, sea * 4, hillyOce * 2, steppe, steppe, oceanic * 4, hillyOce, oceanic * 2),
      VRow(433, OrigMinRevDepr(1478, HVDL)),
      TRow(442, mtainDepr * 3, hillyOce * 5, oceanic * 5, sea * 9, mtainDepr * 2, hillyOce * 2, oceanic * 3),
      TRow(440, hillyOce, mtainDepr * 4, hillyOce, mtainDepr, oceanic * 3, hillyOce * 2, oceanic, sea * 11, mtainDepr * 3, hillyOce, oceanic * 2),
      TRow(438, sea, mtainDepr, mtainDepr * 4, hillyOce, mtainDepr * 2, hillyOce * 4, sea * 13, mtainDepr * 3, oceanic),
      TRow(436, Sea * 2, mtainDepr, mtainDepr, hillyOce * 2, mtainDepr * 2, hillyOce * 2, oceanic * 2, sea * 14, oceanic, hillyOce, mtainDepr * 2),

      TRow(434, hillyOce, sea * 3, hillyOce, mtainDepr, hillyOce * 2, mtainDepr, hillyOce * 2, oceanic, hillyOce, sea * 4, hillyOce * 4, sea * 6, hillyOce,
        mtainDepr, hillyOce * 2),

      VRow(433, BendIn(1490, HVDR, 13)),
      TRow(432, hillyOce, savannah, sea * 2, hillyOce, mtainDepr, hillyOce * 6, oceanic * 2, hillyOce, hillyOce * 7, oceanic, hillyOce * 8),
      VRow(431, OrigRtRevDepr(1486, HVUL), ThreeDown(1488, 0, 13, 10), BendOut(1490, HVUL, 7)),

      TRow(430, hillyOce, savannah * 2, mtainDepr, hillyOce, mtainDepr, hillyOce, oceanic, hillyOce * 2, sea, hillyOce, sea, hillyOce * 2, mtainDepr * 5,
        hillyOce * 3, mtainDepr * 4, hillyOce * 3, mtainDepr),

      VRow(429, BendIn(1488, HVUR, 13), BendOut(1490, HVDL, 7), MouthLt(1504, HVUL), Bend(1506, HVDL, 4, 7)),

      TRow(428, hillyOce, sea, sea * 2, mtainDepr * 2, hillyOce * 2, hillyOce * 2, sea, hillyOce * 3, mtainDepr, hillyOce * 4, hillyDeshot * 2, hillyOce * 4, hillyDeshot * 2,
      hillyOce * 3, oceanic),

      VRow(427, OrigRtRevDepr(1490, HVDn), BendIn(1506, HVUR)),

      TRow(426, sea, hillyOce, sea * 3, hillyOce, mtainDepr, oceanic, hillyOce, sea * 3, hillyOce * 2, mtainDepr, hillyOce * 2, deshot, hillyDeshot * 7,
        mtainDepr * 2, hillyOce, hillyDeshot * 2, mtainDepr, hillyDeshot),

      TRow(424, hillySavannah, sea * 4, hillyOce, mtainDepr, hillyOce * 2, sea * 3, hillyOce * 5, hillyDeshot, deshot * 2, hillyDeshot * 6, mtainDepr * 2,
        hillyDeshot, lake, hillyDeshot, mtainDepr),

      VRow(423, Bend(1500, HVUp, 5, 1)),

      TRow(422, mtainDepr, sea * 4, Isle10(hillyOce), hillyOce, mtainDepr, hillyOce * 2, sea * 2, hillyOce * 7, deshot,
        hillyDeshot, deshot, mtainDepr, hillyOce, hillyDeshot, mtainDepr, oceanic * 2, hillyDeshot, mtainDepr * 2, hillyDeshot),

      VRow(421, OrigMinRevDepr(1504, HVDn)),

      TRow(420, sea * 7, hillyOce * 2, sea * 4, hillyOce, mtainDepr, hillyOce * 2, mtainDepr, hillyOce, oceanic, deshot, hillyOce * 2, mtainDepr, hillyOce * 2,
        oceanic * 2, deshot * 4, mtainDepr),

      VRow(419, SetSep(1471)),

      TRow(418, SepB(), sea * 7, hillyOce, sea * 5, hillyOce * 5, mtainDepr, hillyOce * 2, savannah, hillySavannah, hillyOce, deshot * 2, oceanic, deshot * 3,
        hillyDeshot * 2, mtainDepr), VRow(417, OrigMinRevDepr(1564, HVUR)),

      TRow(416, sea * 10, sea * 4, Isle10(hillyOce), sea, mtainDepr, sea * 2, mtainDepr, mtainDepr, sea * 2, hillyOce, oceanic, deshot * 7, hillyDeshot),
      TRow(414, sea * 9, hillyOce, sea * 10, hillyOce, sea, hillyOce * 2, deshot * 8, hillyDeshot),
      TRow(412, sea * 10, hillyOce * 2, sea * 7, hillyOce, oceanic, sea * 2, hillyOce, deshot * 10),
      TRow(410, sea * 11, sea * 12, hillyOce, hillyDeshot, deshot * 9),
    )
  }
  help.run
}