/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTile._

object Terr320E30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e30(124, 166)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, Isle(Level(IceCap), WSeaIce)),
      TRow(162, Hland(2, 1, Mountains(IceCap))),
      TRow(156, Hland(2, 5, Hilly(Tundra)), Hland(2, 0, Hilly(Tundra)), sea),
      VRow(155, Mouth(1538, HVDn)),
      TRow(154, taigaHills, taiga * 2, Hland(4, 0, Level(Tundra))),
      VRow(153, Mouth(1534, HVUp)),
      TRow(152, Hland(1, 2, Level(Taiga)), taiga, Hland(2, 1, Level(Taiga)), taiga),
      VRow(151, VertIn(1532, HVDR), Mouth(1540, HVDL)),
      TRow(150, Hland(1, 2, Level(Taiga)), taiga * 3),
      VRow(149, Mouth(1536, HVUL, Lake), Mouth(1538, HVDR, Lake)),
      VRow(147, Mouth(1534, HVUR)),
      TRow(148, Hland(1, 2, Level(Taiga)), Hland(3, 3, Level(Taiga)), taiga * 3),
      TRow(146, Hland(2, 5), taiga * 4),
      VRow(145, SetSide(1527)),
      TRow(144, plain * 5),
      TRow(142, plain * 6),
      TRow(140, plain * 6),
      TRow(138, mtain * 2, hills, plain * 3, desert),
      VRow(137, Mouth(1522, HVUp), Mouth(1540, HVUR), VertIn(1542, HVDR), Mouth(1544, HVUR)),
      TRow(136, hills, plain * 2, Hland(1, 2), Hland(3, 3), Hland(2, 3), plain),
      VRow(135, SetSide(1523), VertIn(1522, HVUR)),
      TRow(134, Hland(1, 4, Hilly()), hills, hills, sea * 3, mtain),
      VRow(133, Mouth(1530, HVUp)),
      TRow(132, Hland(2, 1, Hilly()), hills, Hland(1, 4, Hilly()), hills * 4),
      VRow(131, Mouth(1528, HVDR)),
      TRow(130, Hland(2, 4, Hilly()), Hland(4, 3, Hilly()), Hland(3, 1, Hilly()), Hland(2, 3, Hilly()), hills * 4),
      VRow(129, Mouth(1536, HVUR)),
      TRow(128, sea * 2, Isle(Hilly()), sea, Isle(Hilly()), hills, desert * 2),
      VRow(127, VertIn(1528, HVUp)),
      TRow(126, sea, Hland(2, 5, Level(Desert)), Hland(2, 0, Level(Desert)), sea * 2, Hland(1, 5, Hilly()), desert * 2),
      VRow(125, Mouth(1524, HVDn), Mouth(1532, HVDn), Mouth(1540, HVDn)),
      TRow(124, desert * 4, plain, desert * 4),
    )
  }
  help.run
}