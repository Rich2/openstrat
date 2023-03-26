/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

/** Terrain at 160km for 30E. Land and sea should be correct, but elevation has not been checked. */
object Terr160E30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e30(256)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer


  val help = new WTerrSetter(grid, terrs, sTerrs, corners) {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(320, sea * 5),
      TRow(318, sea * 5),
      TRow(316, sea * 6),
      TRow(314, sea * 6),
      TRow(312, sea, tundraHills * 3, sea * 3),
      TRow(310, taigaHills * 2, taiga * 3, sea, taiga),
      TRow(308, taigaHills, taiga * 7),
      TRow(306, taiga * 5, sea, taiga * 2),
      TRow(304, taiga, sea, taiga * 6),
      TRow(302, taiga, sea, taiga * 7),
      TRow(300, taiga, sea, taiga * 7),
      TRow(298, taiga, sea, taiga * 3, lake, taiga * 4),
      TRow(296, taiga, sea * 3, taiga * 6),
      TRow(294, plain, sea, plain * 7, taiga),
      TRow(292, plain, sea, plain * 9),
      TRow(290, sea, plain * 10),
      TRow(288, sea * 2, plain * 9),
      TRow(286, plain * 12),
      TRow(284, plain * 3, forest * 2, plain * 7),
      TRow(282, plain * 4, forest * 2, plain * 7),
      TRow(280, hills, plain * 12),
      TRow(278, hills * 3, plain * 10),
      TRow(276, plain, hills, plain * 12),
      TRow(274, hills * 2, plain, hills * 3, plain * 4, sea, plain * 3),
      TRow(272, hills, plain, hills * 2, plain * 2, plain, sea, plain, Head2Land(2), Head2Land(3), plain * 3),
      TRow(270, hills * 3, plain * 4, sea * 5, mtain, hills, plain),
      TRow(268, sea, Head2Land(3, Mountains), hills * 3, plain * 2, sea * 6, plain, mtain),
      TRow(266, Head2Land(0), Head2Land(4, Mountains), hills * 3, plain * 2, hills * 8),
      VRow(265, MouthUp(1510)),
      TRow(264, hills, Head4Land(1), hills * 2, sea, Head2Land(4, Hilly),  hills * 9),
      TRow(262, hills, sea * 2, hills * 2, sea, hills, hills * 4, hills * 5),
      TRow(260, sea * 3, hills, sea * 2, hills * 10),
      TRow(258, sea * 11, hills * 5),
      TRow(256, sea * 5, hills * 2, sea * 3, Island(Hilly), hills * 6),
    )
  }
  help.run
}