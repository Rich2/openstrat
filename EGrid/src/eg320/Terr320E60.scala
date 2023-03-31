/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km terrain for 60 east. */
object Terr320E60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e60(120, 166)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(160, Headland(1, 5, Level, Tundra), sea),
      TRow(158, Headland(4, 2, Level, Tundra), sea, tundra),
      TRow(156, sea * 2, tundra),
      TRow(154, tundra * 4),
      TRow(152, taiga * 4),
      TRow(150, taiga * 4),
      TRow(148, forest, taiga * 4),
      TRow(146, forest * 2, taiga * 3),
      TRow(144, forest * 5),
      TRow(142, plain * 6),
      TRow(140, plain * 2, desert * 3, plain),
      TRow(138, plain, desert * 6),
      VRow(137, MouthUR(2552, Lake)),
      TRow(136, Headland(1, 2, Level, OpenTerrain, Lake), Headland(1, 5, Level, Desert, Lake), desert * 5),
      TRow(134, Lake, desert * 5, mtain),
      TRow(132, mtain, Lake, desert * 3, mtain * 2),
      TRow(130, mtain, Headland(1, 1, Mountains, OpenTerrain, Lake), Headland(1, 5, Level, Desert, Lake), desert * 3, mtain * 2),
      VRow(129, MouthDn(2552, Lake)),
      TRow(128, desertHills, desert * 5, mtain * 2),
      TRow(126, desert, mtain, desert * 5, plain),
      TRow(124, desert, plain, mtain, desert * 4, plain * 2),
      TRow(122, desert, sea, mtain, desert * 3, plain, desert * 2),
      TRow(120, desert * 2, sea, desert, sea * 3, plain, desert),
    )
  }

  help.run
}