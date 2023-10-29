/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTile._

/** [[WTile]] terrain for 15° east to 45° east centred on 30° east. A tile area of 34294.605km². A minimum island size of 1/6 5715.767km². */
object Terr220E30 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e30(132)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(182, Hland(2, 5, hillyTundra), Hland(2, 0, hillyTundra), sea * 3),
      TRow(180, hillyTundra, taiga, tundra, Hland(2, 0, hillyTundra), sea),
      TRow(178, taiga * 4, Hland(3, 5, tundra)),
      VRow(177, Mouth(1530, HVUp), Mouth(1538, HVDL), BendAll(1544, HVUL)),
      TRow(176, Hland(1, 2, taiga), taiga * 2, Hland(3, 0, taiga), Hland(2, 4, taiga), taiga),
      VRow(175, Mouth(1540, HVDL), Mouth(1544, HVDR)),
      TRow(174, Hland(1, 2, taiga), Hland(1, 5, taiga), taiga * 4),
      VRow(173, Mouth(1540, HVUL, Lake), BendAll(1542, HVDL, Lake)),
      TRow(172, Hland(1, 2, taiga), Hland(1, 5, taiga), taiga * 5),
      VRow(171, Mouth(1536, HVDL, Lake), Mouth(1538, HVUR, Lake), Mouth(1542, HVDn, Lake)),
      TRow(170, sea, Hland(2, 3, taiga), Hland(1, 3, taiga), taiga * 4),
      VRow(169, Mouth(1536, HVUR)),
      TRow(168, Hland(2, 1), Isle(), Hland(1, 0), forest * 2, taiga * 2),
      TRow(166, Hland(2, 2), Hland(1, 5), plain * 2, forest * 2, plain, forest),
      VRow(165, Mouth(1520, HVUL)),
      TRow(164, Hland(1, 5), plain * 7),
      TRow(162, plain * 9),
      TRow(160, plain * 3, forest, plain * 5),
      TRow(158, hills * 2, plain * 7),
      TRow(156, plain * 2, mtain, plain * 6, desert),
      VRow(155, Mouth(1544, HVUp), Mouth(1548, HVUp)),
      TRow(154, plain * 2, hills * 2, plain * 3, Hland(3, 2), plain * 2),
      VRow(153, Mouth(1540, HVUR)),
      TRow(152, hills * 2, plain * 2, sea, Hland(3, 3), Hland(3, 1), Hland(2, 3, hills), plain * 2),
      TRow(150, hills, hills * 3, sea * 5, Hland(1, 4, mtain), hills),
      VRow(149, Mouth(1554, HVDR)),
      TRow(148, Hland(2, 0, hills), Hland(1, 4, hills), plain, hills * 2, Hland(1, 0, hills), mtain, hills * 2, Hland(1, 0, mtain), hills),
      VRow(147, Mouth(1526, HVUL)),
      TRow(146, Hland(2, 1, hills), Hland(1, 4, hills), Hland(2, 1, hills), hills * 2, hillyDesert * 3, mtain, hillyDesert * 2),
      VRow(145, BendOut(1518, HVDR), Mouth(1522, HVDL), Mouth(1524, HVUR)),
      TRow(144, Hland(3, 2, hills), sea, hills, sea, hills * 4, hillyDesert, plain, mtain),
      VRow(143, Mouth(1544, HVUp)),
      TRow(142, sea * 5, Hland(3, 2, hills), sea, Hland(3, 2, hills), Hland(1, 4, hills), desert * 3),
      TRow(140, sea * 3, Isle(hills), sea * 2, Isle(hills), sea, hills, desert * 3),
      TRow(138, sea * 2, Hland(3, 5, desert), sea * 5, hills, desert * 3),
      TRow(136, desert, sea, desert * 4, plain, hillyDesert, desert * 5),
      VRow(135, Mouth(1540, HVUp), Mouth(1544, HVUp)),
      TRow(134, desert * 6, plain, desert, hillyDesert, hillyDesert, desert * 3),
      VRow(133, BendAll(1540, HVUR), Mouth(1542, HVDR), Mouth(1544, HVDn)),
      TRow(132, desert * 6, plain, desert, sea, desert * 4),
    )
  }
  help.run
}