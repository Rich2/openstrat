/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east centred on 30° east. A tile area of 34294.605km². A minimum island size of 1/6 5715.767km². */
object Terr220E30 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e30(132)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      VRow(183, BendIn(1528, HVDR, 12), BendIn(1530, HVDn, 13), MouthRt(1532, HVDR), MouthRt(1534, HVUL, 7), MouthLt(1536, HVDR)),
      TRow(182, hillyTundra, hillyTundra, sea * 3),
      VRow(181, MouthRt(1528, HVDn), MouthRt(1540, HVUL, 7), MouthLt(1542, HVDR, 7)),
      TRow(180, hillyTundra, taiga, tundra, hillyTundra, sea),
      VRow(179, MouthLt(1544, HVUp, 7)),
      TRow(178, taiga * 4, tundra),
      VRow(177, MouthLtRt(1530, HVUp, 7, 1, SeaIceWinter), MouthRt(1540, HVUL), ThreeDown(1542, 3, 13, 13), BendOut(1544, HVUL, 7)),
      TRow(176, taiga, taiga * 2, taiga, taiga, taiga),
      VRow(175, BendIn(1528, HVDR, 13), BendInOut(1530, HVUL, 3, 7, SeaIceWinter, sea), BendOut(1542, HVUR, 7), MouthLt(1544, HVDR)),
      TRow(174, taiga, taiga, taiga * 4),
      VRow(173, BendInOut(1526, HVDR, 12, 5), BendIn(1528, HVUL), Mouth(1540, HVUL, 3, Lake), BendAll(1542, HVDL, Lake)),
      TRow(172, taiga, taiga, taiga * 5),
      VRow(171, BendInOut(1526, HVUR, 7, 7), BendIn(1528, HVDL, 13), Mouth(1536, HVDL, 3, Lake), Mouth(1538, HVUR, 3, Lake), Mouth(1542, HVDn, 3, Lake)),
      TRow(170, hillyLakesTaiga, taiga, taiga, taiga * 4),

      VRow(169, BendInOut(1526, HVDR, 10, 7), ThreeUp(1528, 3, 4, 13), ThreeDown(1530, 13, 0, 6), BendOut(1532, HVDn, 7, sea, SeaIceWinter),
          BendIn(1534, HVUp, 13, SeaIceWinter), MouthRt(1536, HVUR, 7, SeaIceWinter)),

      TRow(168, land, land * 2, forest * 2, taiga * 2),
      VRow(167, BendInOut(1524, HVDR, 13, 7), ThreeUp(1526, 6, 13, 13), BendIn(1528, HVUp), BendIn(1530, HVUL)),
      TRow(166, land * 4, forest * 2, land, forest),
      VRow(165, Mouth(1522, HVDL, 7), BendInOut(1524, HVUL, 13, 7)),
      TRow(164, land, land * 7),
      TRow(162, land * 9),
      TRow(160, land * 3, forest, land * 5),
      TRow(158, hilly * 2, land * 7),
      TRow(156, land * 2, mtain, land * 6, desert),
      VRow(155, Mouth(1544, HVUp), Mouth(1548, HVUp)),
      TRow(154, land * 2, hilly * 2, land * 3, Cape(2, 3), land * 2),
      VRow(153, Mouth(1540, HVUR)),
      TRow(152, hilly * 2, land * 2, sea, Cape(3, 3), Cape(1, 3), Cape(3, 2, hilly), land * 2),
      TRow(150, hilly, hilly * 3, sea * 5, Cape(4, 1, mtain), hilly),
      VRow(149, BendIn(1514, HVUp, 13), BendInOut(1516, HVDn, 6, 7), BendInOut(1518, HVDL, 13, 7), Mouth(1554, HVDR)),
      TRow(148, hilly * 2, land, hilly * 2, Cape(0, 1, hilly), mtain, hilly * 2, Cape(0, 1, mtain), hilly),
      VRow(147, BendInOut(1518, HVUR, 10, 2), BendIn(1520, HVDL, 13), Mouth(1526, HVUL)),
      TRow(146, hilly, hilly * 2, hilly * 2, hillyDesert * 3, mtain, hillyDesert * 2),
      VRow(145, BendOut(1518, HVDR, 7), BendIn(1520, HVUL, 13), Mouth(1522, HVDL), Mouth(1524, HVUR)),
      TRow(144, hilly, sea, hilly, sea, hilly * 4, hillyDesert, land, mtain),
      VRow(143, BendIn(1514, HVUR, 6), BendIn(1516, HVUp, 13), BendIn(1518, HVUL, 13), Mouth(1544, HVUp)),
      TRow(142, sea * 5, Cape(2, 3, hilly), sea, Cape(2, 3, hilly), Cape(4, 1, hilly), desert * 3),
      TRow(140, sea * 3, Isle(hilly), sea * 2, Isle(hilly), sea, hilly, desert * 3),
      TRow(138, sea * 2, Cape(5, 3, desert), sea * 5, hilly, desert * 3),
      TRow(136, desert, sea, desert * 4, land, hillyDesert, desert * 5),
      VRow(135, Mouth(1540, HVUp), Mouth(1544, HVUp)),
      TRow(134, desert * 6, land, desert, hillyDesert, hillyDesert, desert * 3),
      VRow(133, BendAll(1540, HVUR), Mouth(1542, HVDR), Mouth(1544, HVDn)),
      TRow(132, desert * 6, land, desert, sea, desert * 4),
    )
  }
  help.run
}