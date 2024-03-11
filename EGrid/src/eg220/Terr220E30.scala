/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 15° east to 45° east centred on 30° east. A tile area of 41915.629km².
 * [[Isle7]] 6917.716km² => 9209.977km². Crete 8450 km². */
object Terr220E30 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e30(132)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      VRow(183, BendIn(1528, HVDR, 12), BendIn(1530, HVDn, 13), MouthRt(1532, HVDR), MouthRt(1534, HVUL, 7), MouthLt(1536, HVDR)),
      TRow(182, hillyTundra, hillyTundra, sea * 3),
      VRow(181, MouthRt(1528, HVDn), MouthRt(1540, HVUL, 7), MouthLt(1542, HVDR, 7)),
      TRow(180, hillyTundra, taiga, tundra, hillyTundra, sea),
      VRow(179, MouthLt(1544, HVUp, 7)),
      TRow(178, taiga * 4, tundra),
      VRow(177, Mouth(1530, HVUp, 7, 1, SeaIceWinter), MouthRt(1540, HVUL), ThreeDown(1542, 3, 13, 13), BendOut(1544, HVUL, 7)),
      TRow(176, taiga, taiga * 2, taiga, taiga, taiga),
      VRow(175, BendIn(1528, HVDR, 13), Bend(1530, HVUL, 3, 7, SeaIceWinter, sea), BendOut(1542, HVUR, 7), MouthLt(1544, HVDR)),
      TRow(174, taiga, taiga, taiga * 4),
      VRow(173, Bend(1526, HVDR, 12, 5), BendIn(1528, HVUL), MouthOld(1540, HVUL, 3, Lake), BendAllOld(1542, HVDL, Lake)),
      TRow(172, taiga, taiga, taiga * 5),
      VRow(171, Bend(1526, HVUR, 7, 7), BendIn(1528, HVDL, 13), MouthOld(1536, HVDL, 3, Lake), MouthOld(1538, HVUR, 3, Lake), MouthOld(1542, HVDn, 3, Lake)),
      TRow(170, hillyLakesTaiga, taiga, taiga, taiga * 4),

      VRow(169, Bend(1526, HVDR, 10, 7), ThreeUp(1528, 3, 4, 13), ThreeDown(1530, 13, 0, 6), BendOut(1532, HVDn, 7, sea, SeaIceWinter),
          BendIn(1534, HVUp, 13, SeaIceWinter), MouthRt(1536, HVUR, 7, SeaIceWinter)),

      TRow(168, oceanic, oceanic * 2, oceForest * 2, taiga * 2),
      VRow(167, Bend(1524, HVDR, 13, 7), ThreeUp(1526, 6, 13, 13), BendIn(1528, HVUp), BendIn(1530, HVUL)),
      TRow(166, oceanic * 4, oceForest * 2, oceanic, oceForest),
      VRow(165, MouthOld(1522, HVDL, 7), Bend(1524, HVUL, 13, 7)),
      TRow(164, oceanic, oceanic * 7),
      TRow(162, oceanic * 9),
      TRow(160, oceanic * 3, oceForest, oceanic * 5),
      TRow(158, hillyOce * 2, oceanic * 7),
      TRow(156, oceanic * 2, mtainOld, oceanic * 6, desert),
      VRow(155, MouthOld(1544, HVUp), MouthOld(1548, HVUp)),
      TRow(154, oceanic * 2, hillyOce * 2, oceanic * 3, CapeOld(2, 3), oceanic * 2),
      VRow(153, MouthOld(1540, HVUR)),
      TRow(152, hillyOce * 2, oceanic * 2, sea, CapeOld(3, 3), CapeOld(1, 3), CapeOld(3, 2, hillyOce), oceanic * 2),
      TRow(150, hillyOce, hillyOce * 3, sea * 5, CapeOld(4, 1, mtainOld), hillyOce),
      VRow(149, BendIn(1514, HVUp, 13), Bend(1516, HVDn, 6, 7), Bend(1518, HVDL, 13, 7), MouthRt(1534, HVDL), BendOut(1538, HVUp, 7), MouthLt(1540, HVUR, 7), MouthOld(1554, HVDR)),
      TRow(148, hillyOce * 2, oceanic, hillyOce * 2, CapeOld(0, 1, hillyOce), mtainOld, hillyOce * 2, CapeOld(0, 1, mtainOld), hillyOce),
      VRow(147, Bend(1518, HVUR, 10, 2), BendIn(1520, HVDL, 13), Bend(1528, HVDR, 13, 6), Mouth(1530, HVUR, 5, 1), MouthLt(1532, HVDL, 7), MouthRt(1534, HVUR)),
      TRow(146, hillyOce, hillyOce * 2, hillyOce * 2, hillyDesert * 3, mtainOld, hillyDesert * 2),
      VRow(145, BendOut(1518, HVDR, 7), BendIn(1520, HVUL, 13), MouthOld(1522, HVDL), MouthOld(1524, HVUR), BendIn(1530, HVDL, 13), BendMax(1528, HVUR)),
      TRow(144, hillyOce, sea, hillyOce, hillySavannah, hillyOce * 4, hillyDesert, oceanic, mtainOld),

      VRow(143, BendIn(1514, HVUR, 6), BendIn(1516, HVUp, 13), BendIn(1518, HVUL, 13), BendIn(1524, HVDR, 13), BendInRt(1526, HVDn, 13, 6), ThreeDown(1528, 13, 0, 13), BendIn(1530, HVUL, 13),
          MouthOld(1544, HVUp)),

      TRow(142, sea * 3, mtainSubForest, sea, CapeOld(2, 3, hillyOce), sea, CapeOld(2, 3, hillyOce), CapeOld(4, 1, hillyOce), desert * 3),
      VRow(141, BendIn(1524, HVUR, 13), BendOut(1526, HVDL), BendIn(1528, HVUR), BendIn(1530, HVDL, 13)),
      TRow(140, sea * 3, mtainSubForest, sea * 2, Isle10(hillyOce), sea, hillyOce, desert * 3),
      VRow(139, MouthRt(1520, HVDL, 7), BendIn(1522, HVDn, 11), BendOut(1524, HVUp, 7), ThreeUp(1526, 13, 13, 0), ThreeDown(1528, 13, 0, 13), BendIn(1530, HVUL, 13)),
      TRow(138, sea * 2, sahel, desert, sea * 4, hillyOce, desert * 3),
      VRow(137, MouthRt(1514, HVDL), BendIn(1516, HVDn, 13), Mouth(1528, HVDn, 4, 2), MouthRt(1532, HVUL, 7), BendOut(1534, HVUp, 7), MouthLt(1536, HVUR, 7)),
      TRow(136, desert * 6, savannah, hillyDesert, desert * 5),
      VRow(135, MouthOld(1540, HVUp), MouthOld(1544, HVUp)),
      TRow(134, desert * 6, sahel, desert, hillyDesert, hillyDesert, desert * 3),
      VRow(133, BendAllOld(1540, HVUR), MouthOld(1542, HVDR), MouthOld(1544, HVDn)),
      TRow(132, desert * 6, sahel, desert, sea, desert * 4),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(142, "" * 3, "Crete west")
    str(140, "" * 3, "Crete east")
  }
}