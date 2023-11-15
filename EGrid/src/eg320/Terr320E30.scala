/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale of 320km. */
object Terr320E30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e30(118)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, Isle(Level, IceCap, LandFree, WSeaIce)),
      TRow(162, Hland(2, 2, Mountains, IceCap, LandFree, Sea)),
      TRow(156, Hland(2, 5, hillyTundra), Hland(2, 0, hillyTundra), sea),
      VRow(155, Mouth(1538, HVDn)),
      TRow(154, hillyTaiga, taiga * 2, Hland(4, 0, tundra)),
      VRow(153, Mouth(1534, HVUp)),
      TRow(152, Hland(1, 2, taiga), taiga, Hland(2, 1, taiga), taiga),
      VRow(151, BendAll(1532, HVDR), Mouth(1540, HVDL)),
      TRow(150, Hland(1, 2, taiga), taiga * 3),
      VRow(149, Mouth(1536, HVUL, Lake), Mouth(1538, HVDR, Lake)),
      VRow(147, Mouth(1534, HVUR)),
      TRow(148, Hland(1, 2, taiga), Hland(3, 3, taiga), taiga * 3),
      TRow(146, Hland(2, 5), taiga * 4),
      VRow(145, SetSide(1527)),
      TRow(144, plain * 5),
      TRow(142, plain * 6),
      TRow(140, plain * 6),
      TRow(138, mtain * 2, hills, plain * 3, desert),
      VRow(137, Mouth(1522, HVUp), Mouth(1540, HVUR), BendAll(1542, HVDR), Mouth(1544, HVUR)),
      TRow(136, hills, plain * 2, Hland(1, 2), Hland(3, 3), Hland(2, 3), plain),
      VRow(135, SetSide(1523), BendAll(1522, HVUR), BendOut(1536, HVDR), ThreeWay(1542), Mouth(1546, HVUR)),
      TRow(134, Hland(1, 4, hills), hills, hills, sea * 3, mtain),
      VRow(133, Mouth(1530, HVUp), Mouth(1536, HVDn)),
      TRow(132, Hland(2, 1, hills), hills, Hland(1, 4, hills), hills * 4),
      VRow(131, VertRightsRight(1522), Mouth(1528, HVDR)),
      TRow(130, Hland(4, 2, hills), Hland(4, 3, hills), Hland(3, 1, hills), Hland(2, 3, hills), hills * 4),
      VRow(129, BendAll(1528, HVDn), Mouth(1536, HVUR)),
      TRow(128, sea * 2, Isle(hillySavannah), sea, Isle(hills), hills, desert * 2),
      VRow(127, BendAll(1528, HVUp)),
      TRow(126, sea, Hland(2, 5, sahel), Hland(2, 0, sahel), sea * 2, Hland(1, 5, hills), desert * 2),
      VRow(125, Mouth(1524, HVDn), Mouth(1532, HVDn), Mouth(1540, HVDn)),
      TRow(124, sahel * 4, savannah, desert * 4),
      VRow(123, Mouth(1538, HVUL), Mouth(1540, HVDR)),
      TRow(122, desert * 4, savannah, sea, desert * 3),
      TRow(120, desert * 6, sea, desert * 2),
      TRow(118, desert * 5, hillyDesert, sea, hillyDesert, desert),
    )
  }
  help.run
}