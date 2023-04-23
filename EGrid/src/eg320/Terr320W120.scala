/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km terrain for 120 west. */
object Terr320W120 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w120(122)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce, tundra),
      TRow(160, Hland(4, 5, Level(Tundra), WSeaIce), Hland(4, 1, Level(Tundra), WSeaIce)),
      TRow(158, Hland(4, 2, Level(Tundra), WSeaIce), Hland(3, 4, Level(Tundra), WSeaIce), Hland(2, 0, Level(Tundra), WSeaIce)),
      TRow(156, Hland(1, 0, Level(Tundra), WSeaIce), Hland(2, 0, Level(Tundra), WSeaIce), Hland(3, 2, Level(Tundra), WSeaIce)),
      TRow(154, tundra * 4),
      VRow(153, Mouth(8702, HVDL, Lake), Mouth(8704, HVUR, Lake)),
      TRow(152, taiga * 3, tundra),
      VRow(151, Mouth(8708, HVUp, Lake)),
      TRow(150, mtain, taiga * 3),
      VRow(149, Mouth(8706, HVDL, Lake), VertIn(8708, HVUL, Lake)),
      TRow(148, hillyTaiga * 2, taiga * 3),
      TRow(146, mtain, taiga * 4),
      TRow(144, mtain, taiga * 4),
      TRow(142, sea, mtain * 2, plain * 3),
      TRow(140, sea, mtain * 2, plain * 3),
      TRow(138, sea * 2, mtain * 2, plain * 3),
      TRow(136, sea * 2, hillyForest, hills, mtain * 2, hillyDesert),
      TRow(134, sea * 2, hills, hillyDesert, desert, hillyDesert * 2),
      TRow(132, sea * 2, hills * 2, hillyDesert * 2, mtain),
      TRow(130, sea * 3, hills, hills, hillyDesert * 2, mtain),
      TRow(128, sea * 3, hills, hillyDesert * 2, desert, hillyDesert),
      TRow(126, sea * 5, hills, hillyDesert, desert),
      TRow(124, sea * 6, hills, hills, hillyDesert),
    )
  }

  help.run
}