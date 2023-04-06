/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTile._

/** Terrain for 90 degrees west includes grid, tile terrain and straits [[Boolean]]s. */
object Terr320W90 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w90(128, 166)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, mtain),
      TRow(164, mtain),
      TRow(162, WSeaIce,  mtain),
      TRow(160, Isle(Level(Tundra)), WSeaIce),
      VRow(156, SetSide(9722)),
      TRow(158, Hland(4, 4, Level(Tundra), WSeaIce), Hland(1, 4, Level(Tundra), WSeaIce), Hland(2, 0, Level(Tundra), WSeaIce)),
      TRow(156, sea, Hland(1, 1, Level(Tundra)), Hland(2, 3, Level(Tundra))),
      TRow(154, tundra * 3, sea),
      TRow(152, tundra * 2, Isle(Level(Tundra)), Hland(2, 4, Level(Tundra))),
      TRow(150, taiga, tundra, sea * 2),
      TRow(148, taiga, tundra, sea * 2, Hland(2, 4, Level(Tundra))),
      TRow(146, taiga * 2, sea * 2, taiga),
      TRow(144, taiga * 3, Hland(2, 0, Level(Taiga)), Hland(2, 4, Level(Taiga))),
      VRow(143, MouthUp(9720, Lake)),
      TRow(142, taiga * 4, Hland(1, 1, Level(Taiga)), taiga),
      VRow(141, VertInUR(9720, Lake, Lake), MouthDR(9722, Lake), MouthDn(9736)),
      TRow(140, plain, taiga * 5),
      VRow(139, MouthDR(9732, Lake)),
      TRow(138, plain * 2, taiga, Hland(2, 5, Level(OpenTerrain), Lake), taiga * 3),
      VRow(137, MouthDn(9728, Lake), VertInDR(9730, Lake, Lake), MouthUR(9732, Lake)),
      TRow(136, plain * 5, lake, taiga),
      VRow(135, VertInUR(9730, Lake, Lake), MouthDR(9732, Lake), MouthDL(9740, Lake), MouthUR(9742, Lake)),
      TRow(134, plain * 6, forestHills),
      TRow(132, desert, plain * 4, forestHills, hills),
      TRow(130, desert, plain * 5, hills, plain),
      TRow(128, desert, plain * 3, hills * 2, plain, sea),
    )
  }
  help.run
}