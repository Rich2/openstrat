/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg80
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** The 80 Km grid centred on 30°E for 15°E to 45°E, covers North East Europe. The c or column offset for 30E is 1536 which is 1G0 in base 32. Current r offset
 * is 300 for the equator.
 * [[Isle8]] 1217.848km² => 1564.258km². Rhodes 1401km². */
object Terr80E30  extends Long80Terrs
{ override implicit val grid: EGrid80LongFull = EGrid80.e30(410)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()
  
  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(582, SeaIcePerm),
      TileRow(580, SeaIcePerm),
      TileRow(578, SeaIcePerm * 2),
      TileRow(576, SeaIcePerm * 2),
      TileRow(574, SeaIcePerm * 3),
      TileRow(572, SeaIcePerm * 3),
      TileRow(570, SeaIcePerm * 4),
      TileRow(568, SeaIcePerm * 4),
      TileRow(558, mtainTundra),
      TileRow(556, mtainIce, ice * 2),
      VertRow(555, BendOut(1526, HVUR)),
      TileRow(554, mtainDepr, ice),
      TileRow(552, ice),
      TileRow(550, mtainDepr),
      TileRow(548, mtainDepr),

      TileRow(526, sea * 2, taiga * 4),
      TileRow(524, sea, taiga * 5),
      TileRow(522, sea, taiga * 8),
      TileRow(520, taiga * 11),
      TileRow(518, taiga * 12, sea * 2, taiga),
      TileRow(516, taiga * 13, sea * 2, taiga),
      TileRow(514, taiga * 14, sea * 2),
      TileRow(512, taiga * 10, sea * 2, taiga, sea, taiga * 2),
      TileRow(510, taiga * 4, sea, taiga * 6, sea * 3, taiga * 3),
      TileRow(508, taiga * 3, sea * 3, taiga * 5, sea, taiga, sea, taiga * 3),
      TileRow(506, taiga * 4, sea, taiga * 7, sea, taiga * 5),
      VertRow(505, OrigMin(1552, HVDR)),
      TileRow(504, taiga * 2, sea * 2, taiga * 14),
      VertRow(505, OrigMin(1554, HVUL)),
      TileRow(502, taiga * 2, sea * 2, taiga * 14),
      TileRow(500, taiga * 2, sea * 2, taiga * 15),
      TileRow(498, taiga, sea * 2, taiga * 9, Lake, taiga * 6),
      TileRow(496, taiga, sea * 3, taiga * 6, Lake, taiga * 2, Lake, taiga * 6),
      TileRow(494, taiga * 2, sea * 2, taiga * 6, Lake * 2, taiga * 8),
      TileRow(492, taiga * 2, sea * 2, taiga, sea * 4, taiga * 11),
      TileRow(490, taiga * 2, sea * 4, oceanic * 15),
      TileRow(488, taiga, sea * 4, continental * 16),
      TileRow(486, oceanic, sea * 4, continental, sea, continental * 15),
      TileRow(484, oceanic, sea, oceanic, sea, continental, sea, continental * 16),
      TileRow(482, oceanic, sea * 3, continental * 18),
      TileRow(480, oceanic, sea * 3, continental * 19),
      TileRow(478, sea * 4, oceanic * 19),
      TileRow(476, sea * 4, oceanic * 19),
      TileRow(474, sea, oceanic * 23),
      TileRow(472, oceanic * 24),
      TileRow(470, oceanic * 25),
      TileRow(468, oceanic * 25),
      TileRow(466, oceanic * 25),
      TileRow(464, oceanic * 26),
      TileRow(462, hillyOce, oceanic * 25),
      TileRow(460, hillyOce, oceanic * 25),
      TileRow(458, hillyOce * 3, mtainDepr * 3, oceanic * 21),
      TileRow(456, hillyOce, oceanic, hillyOce * 4, mtainDepr * 2, hillyOce, oceanic * 18),
      TileRow(454, oceanic * 7, mtainDepr * 2, hillyOce * 3, oceanic * 15),
      TileRow(452, mtainDepr, oceanic * 6, hillyOce, mtainDepr * 2, hillyOce * 3, oceanic * 15),
      VertRow(451, OrigMin(1570, HVDL)),
      TileRow(450, hillyOce, oceanic * 5, hillyOce * 3, mtainDepr * 2, hillyOce * 3, oceanic * 7, sea, steppe, oceanic * 5),
      TileRow(448, hillyOce, oceanic, hillyOce, oceanic * 3, hillyOce * 3, mtainDepr, hillyOce, oceanic * 3, sea * 2, oceanic * 2, sea * 3, oceanic * 7),
      TileRow(446, hillyOce * 2, oceanic * 4, mtainDepr * 4, hillyOce * 1, oceanic * 3, sea * 3, oceanic * 2, sea * 2, oceanic * 8),
      VertRow(445, OrigMin(1562, HVDn)),
      TileRow(444, hillyOce * 3, oceanic * 3, hillyOce * 4, oceanic * 4, sea * 4, hillyOce * 2, steppe, steppe, oceanic * 4, hillyOce, oceanic * 2),
      TileRow(442, mtainDepr * 3, hillyOce * 5, oceanic * 5, sea * 9, mtainDepr * 2, hillyOce * 2, oceanic * 3),
      TileRow(440, hillyOce, mtainDepr * 4, hillyOce, mtainDepr, oceanic * 3, hillyOce * 2, oceanic, sea * 11, mtainDepr * 3, hillyOce, oceanic * 2),
      TileRow(438, sea, mtainDepr, mtainDepr * 4, hillyOce, mtainDepr * 2, hillyOce * 4, sea * 13, mtainDepr * 3, oceanic),
      TileRow(436, Sea * 2, mtainDepr, mtainDepr, hillyOce * 2, mtainDepr * 2, hillyOce * 2, oceanic * 2, sea * 14, oceanic, hillyOce, mtainDepr * 2),

      TileRow(434, hillyOce, sea * 3, hillyOce, mtainDepr, hillyOce * 2, mtainDepr, hillyOce * 2, oceanic, hillyOce, sea * 4, hillyOce * 4, sea * 6, hillyOce,
        mtainDepr, hillyOce * 2),

      VertRow(433, BendOut(1480, HVUR, 7), BendIn(1482, HVDL, 13), BendIn(1490, HVDR, 13)),
      TileRow(432, hillyOce, savannah, sea * 2, hillyOce, mtainDepr, hillyOce * 6, oceanic * 2, hillyOce, hillyOce * 7, oceanic, hillyOce * 8),
      VertRow(431, OrigMin(1482, HVUp, 1), OrigRt(1486, HVDR), ThreeDown(1488, 0, 13, 10), BendOut(1490, HVUL, 7), OrigMin(1512, HVDn)),

      TileRow(430, hillyOce, savannah * 2, mtainDepr, hillyOce, mtainDepr, hillyOce, oceanic, hillyOce * 2, sea, hillyOce, sea, hillyOce * 2, mtainDepr * 5,
        hillyOce * 3, mtainDepr * 4, hillyOce * 3, mtainDepr),

      VertRow(429, BendIn(1488, HVUR, 13), BendOut(1490, HVDL, 7), OrigLt(1504, HVDR), Bend(1506, HVDL, 4, 7), Bend(1512, HVUR, 13, 6),
        ThreeDown(1514, 13, 0, 10)),

      TileRow(428, hillyOce, sea, sea * 2, mtainSub * 2, hillySub, hillySavannah, hillyOce * 2, sea, hillyOce * 3, mtainDepr, hillyOce * 4, hillyDeshot * 2, hillyOce * 4,
        hillyDeshot * 2, hillyOce * 3, oceanic),

      VertRow(427, OrigRt(1490, HVUp), BendIn(1506, HVUR, 13), BendInRt(1508, HVUp, 13, 7), BendIn(1512, HVUp, 13), BendIn(1514, HVUL, 13)),

      TileRow(426, sea, hillyOce, sea * 3, hillyOce, mtainDepr, oceanic, hillyOce, sea * 3, hillyOce * 2, mtainDepr, hillyOce * 2, deshot, hillyDeshot * 7,
        mtainDepr * 2, hillyOce, hillyDeshot * 2, mtainDepr, hillyDeshot),

      TileRow(424, hillySavannah, sea * 4, hillyOce, mtainDepr, hillyOce * 2, sea * 3, hillyOce * 5, hillyDeshot, deshot * 2, hillyDeshot * 6, mtainDepr * 2,
        hillyDeshot, lake, hillyDeshot, mtainDepr),

      VertRow(423, BendIn(1498, HVDn), Bend(1500, HVUp, 5, 1)),

      TileRow(422, mtainDepr, sea * 4, Isle10(hillyOce), hillyOce, mtainDepr, hillyOce * 2, sea * 2, hillyOce * 7, deshot,
        hillyDeshot, deshot, mtainDepr, hillyOce, hillyDeshot, mtainDepr, oceanic * 2, hillyDeshot, mtainDepr * 2, hillyDeshot),

      VertRow(421, OrigMin(1504, HVUp)),

      TileRow(420, sea * 7, hillyOce * 2, sea * 4, hillyOce, mtainDepr, hillyOce * 2, mtainDepr, hillyOce, oceanic, deshot, hillyOce * 2, mtainDepr, hillyOce * 2,
        oceanic * 2, deshot * 4, mtainDepr),

      VertRow(419, SetSep(1471)),

      TileRow(418, SepB(), sea * 7, hillyOce, sea * 5, hillyOce * 5, mtainDepr, hillyOce * 2, savannah, hillySavannah, hillyOce, deshot * 2, oceanic, deshot * 3,
        hillyDeshot * 2, mtainDepr), VertRow(417, OrigMin(1564, HVDL)),

      TileRow(416, sea * 10, sea * 4, Isle8(mtainSavannah), sea, mtainDepr, sea * 2, mtainDepr, mtainDepr, sea * 2, hillyOce, oceanic, deshot * 7, hillyDeshot),
      TileRow(414, sea * 9, hillyOce, sea * 10, hillyOce, sea, hillyOce * 2, deshot * 8, hillyDeshot),
      TileRow(412, sea * 10, hillyOce * 2, sea * 7, hillyOce, oceanic, sea * 2, hillyOce, deshot * 10),
      TileRow(410, sea * 11, sea * 12, hillyOce, hillyDeshot, deshot * 9),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(416, "" * 14, "Rhodes")
  }
}