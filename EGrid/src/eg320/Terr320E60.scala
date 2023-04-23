/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km terrain for 60 east. */
object Terr320E60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e60(118)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, Hland(1, 5, Level(Tundra), WSeaIce), sea),
      TRow(158, Hland(4, 2, Level(Tundra)), sea, tundra),
      TRow(156, sea * 2, tundra),
      VRow(155, SetSide(2553)),
      TRow(154, SideB(), tundra * 4),
      TRow(152, taiga * 4),
      TRow(150, taiga * 4),
      TRow(148, forest, taiga * 4),
      TRow(146, forest * 2, taiga * 3),
      TRow(144, forest * 5),
      TRow(142, plain * 6),
      TRow(140, plain * 2, desert * 3, plain),
      TRow(138, plain, desert * 6),
      VRow(137, Mouth(2552, HVUR, Lake)),
      TRow(136, Hland(1, 2, Level(OpenTerrain), Lake), Hland(1, 5, Level(Desert), Lake), desert * 5),
      TRow(134, Lake, desert * 5, mtain),
      TRow(132, mtain, Lake, desert * 3, mtain * 2),
      TRow(130, mtain, Hland(1, 1, Mountains(OpenTerrain), Lake), Hland(1, 5, Level(Desert), Lake), desert * 3, mtain * 2),
      VRow(129, Mouth(2552, HVDn, Lake)),
      TRow(128, hillyDesert, desert * 5, mtain * 2),
      TRow(126, desert, mtain, desert * 5, plain),
      TRow(124, desert, plain, mtain, desert * 4, plain * 2),
      VRow(123, Mouth(2546, HVUL)),
      TRow(122, Hland(1, 1, Level(Desert)), Hland(2, 3, Hilly(Desert)), Hland(1, 3, Mountains()), desert * 3, plain, desert * 2),
      TRow(120, desert * 2, sea, Hland(2, 0, Hilly(Desert)), sea * 2, hillyDesert, plain, desert),
      TRow(118, desert * 4, sea * 3, plain * 2),
    )
  }

  help.run
}