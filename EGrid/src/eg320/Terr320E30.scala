/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTile._

object Terr320E30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e30(124, 166)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, Island(Level, IceCap, WSeaIce)),
      TRow(162, Island(Mountains, IceCap)),
      TRow(156, Headland(2, 5, Hilly, Tundra), Headland(2, 0, Hilly, Tundra), sea),
      VRow(155, MouthDn(1538)),
      TRow(154, taigaHills, taiga * 2, Headland(4, 0, Level, Tundra)),
      VRow(153, MouthUp(1534)),
      TRow(152, Headland(1, 2, Level, Taiga), taiga, Headland(2, 1, Level, Taiga), taiga),
      VRow(151, VertInDR(1532), MouthDL(1540)),
      TRow(150, Headland(1, 2, Level, Taiga), taiga * 3),
      VRow(149, MouthUL(1536, Lake), MouthDR(1538, Lake)),
      VRow(147, MouthUR(1534)),
      TRow(148, Headland(1, 2, Level, Taiga), Headland(3, 3, Level, Taiga), taiga * 3),
      TRow(146, Headland(2, 5), taiga * 4),
      VRow(145, SetSide(1527)),
      TRow(144, plain * 5),
      TRow(142, plain * 6),
      TRow(140, plain * 6),
      TRow(138, mtain * 2, hills, plain * 3, desert),
      VRow(137, MouthUp(1522), MouthUR(1540), VertInDR(1542), MouthUR(1544)),
      TRow(136, hills, plain * 2, Headland(1, 2), Headland(3, 3), Headland(2, 3), plain),
      VRow(135, VertInUR(1522)),
      TRow(134, Headland(1, 4, Hilly), hills, hills, sea * 3, mtain),
      VRow(133, MouthUp(1530)),
      TRow(132, Headland(2, 1, Hilly), hills, Headland(1, 4, Hilly), hills * 4),
      //VRow(131, SetSide(1523)),
      VRow(131, MouthDR(1528)),
      TRow(130, Headland(2, 4, Hilly), Headland(4, 3, Hilly), Headland(3, 1, Hilly), Headland(2, 3, Hilly), hills * 4),
      VRow(129, MouthUR(1536)),
      TRow(128, sea * 2, Island(Hilly), sea, Island(Hilly), hills, desert * 2),
      VRow(127, VertInUp(1528)),
      TRow(126, sea, Headland(2, 5, Level, Desert), Headland(2, 0, Level, Desert), sea * 2, Headland(1, 5, Hilly), desert * 2),
      VRow(125, MouthDn(1524), MouthDn(1532), MouthDn(1540)),
      TRow(124, desert * 4, plain, desert * 4),
    )
  }

  help.run
}