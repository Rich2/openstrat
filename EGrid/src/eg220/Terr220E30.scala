/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east centred on 30° east. A tile area of 34294.605km². A minimum island size of 1/6 5715.767km². */
object Terr220E30 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e30(132)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(182, Cape(5, 2, hillyTundra), Cape(0, 2, hillyTundra), sea * 3),
      TRow(180, hillyTundra, taiga, tundra, Cape(0, 2, hillyTundra), sea),
      TRow(178, taiga * 4, Cape(5, 3, tundra)),
      VRow(177, Mouth(1530, HVUp), Mouth(1538, HVDL), BendAll(1544, HVUL)),
      TRow(176, Cape(2, 1, taiga), taiga * 2, Cape(0, 3, taiga), Cape(4, 2, taiga), taiga),
      VRow(175, Mouth(1540, HVDL), Mouth(1544, HVDR)),
      TRow(174, Cape(2, 1, taiga), Cape(5, 1, taiga), taiga * 4),
      VRow(173, Mouth(1540, HVUL, Lake), BendAll(1542, HVDL, Lake)),
      TRow(172, Cape(2, 1, taiga), Cape(5, 1, taiga), taiga * 5),
      VRow(171, Mouth(1536, HVDL, Lake), Mouth(1538, HVUR, Lake), Mouth(1542, HVDn, Lake)),
      TRow(170, sea, Cape(3, 2, taiga), Cape(3, 1, taiga), taiga * 4),
      VRow(169, Mouth(1536, HVUR)),
      TRow(168, Cape(1, 2), Isle(), Cape(0, 1), forest * 2, taiga * 2),
      TRow(166, Cape(2, 2), Cape(5, 1), land * 2, forest * 2, land, forest),
      VRow(165, Mouth(1520, HVUL)),
      TRow(164, Cape(5, 1), land * 7),
      TRow(162, land * 9),
      TRow(160, land * 3, forest, land * 5),
      TRow(158, hilly * 2, land * 7),
      TRow(156, land * 2, mtain, land * 6, desert),
      VRow(155, Mouth(1544, HVUp), Mouth(1548, HVUp)),
      TRow(154, land * 2, hilly * 2, land * 3, Cape(2, 3), land * 2),
      VRow(153, Mouth(1540, HVUR)),
      TRow(152, hilly * 2, land * 2, sea, Cape(3, 3), Cape(1, 3), Cape(3, 2, hilly), land * 2),
      TRow(150, hilly, hilly * 3, sea * 5, Cape(4, 1, mtain), hilly),
      VRow(149, Mouth(1554, HVDR)),
      TRow(148, Cape(0, 2, hilly), Cape(4, 1, hilly), land, hilly * 2, Cape(0, 1, hilly), mtain, hilly * 2, Cape(0, 1, mtain), hilly),
      VRow(147, Mouth(1526, HVUL)),
      TRow(146, Cape(1, 2, hilly), Cape(4, 1, hilly), Cape(1, 2, hilly), hilly * 2, hillyDesert * 3, mtain, hillyDesert * 2),
      VRow(145, BendOut(1518, HVDR), Mouth(1522, HVDL), Mouth(1524, HVUR)),
      TRow(144, Cape(2, 3, hilly), sea, hilly, sea, hilly * 4, hillyDesert, land, mtain),
      VRow(143, Mouth(1544, HVUp)),
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