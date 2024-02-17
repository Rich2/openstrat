/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** Terrain at 160km for 30E. Land and sea should be correct, but elevation has not been checked. */
object Terr160E30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e30(256)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(320, sea * 5),
      TRow(318, sea * 5),
      TRow(316, sea * 6),
      TRow(314, sea * 6),
      TRow(312, sea, hillyTundra * 3, sea * 3),
      TRow(310, hillyTaiga * 2, taiga * 3, sea, taiga),
      TRow(308, hillyTaiga, taiga * 7),
      TRow(306, taiga * 5, sea, taiga * 2),
      TRow(304, taiga, sea, taiga * 6),
      TRow(302, taiga, sea, taiga * 7),
      TRow(300, taiga, sea, taiga * 7),
      TRow(298, taiga, sea, taiga * 3, lake, taiga * 4),
      TRow(296, taiga, sea * 3, taiga * 6),
      TRow(294, land, sea, land * 7, taiga),
      TRow(292, land, sea, land * 9),
      TRow(290, sea, land * 10),
      TRow(288, sea * 2, land * 9),
      TRow(286, land * 12),
      TRow(284, land * 3, forest * 2, land * 7),
      TRow(282, land * 4, forest * 2, land * 7),
      TRow(280, hilly, land * 12),
      TRow(278, hilly * 3, land * 10),
      TRow(276, land, hilly, land * 12),
      TRow(274, hilly * 2, land, hilly * 3, land * 4, sea, land * 3),
      TRow(272, hilly, land, hilly * 2, land * 2, land, sea, land, Cape(2, 2), Cape(3, 2), land * 3),
      TRow(270, hilly * 3, land * 4, sea * 5, mtain, hilly, land),
      TRow(268, sea, Cape(3, 2, mtain), hilly * 3, land * 2, sea * 6, land, mtain),
      TRow(266, Cape(0, 2), Cape(4, 2, mtain), hilly * 3, land * 2, hilly * 8),
      VRow(265, MouthOld(1510, HVUp)),
      TRow(264, hilly, Cape(1, 4), hilly * 2, sea, Cape(4, 2, hilly),  hilly * 9),
      TRow(262, hilly, sea * 2, hilly * 2, sea, hilly, hilly * 4, hilly * 5),
      TRow(260, sea * 3, hilly, sea * 2, hilly * 10),
      TRow(258, sea * 11, hilly * 5),
      TRow(256, sea * 5, hilly * 2, sea * 3, Isle10(hilly), hilly * 6),
    )
  }
  help.run
}