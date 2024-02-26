/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** Terrain at 160km for 30E. Land and sea should be correct, but elevation has not been checked. */
object Terr160E30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e30(256)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

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
      TRow(294, temperate, sea, temperate * 7, taiga),
      TRow(292, temperate, sea, temperate * 9),
      TRow(290, sea, temperate * 10),
      TRow(288, sea * 2, temperate * 9),
      TRow(286, temperate * 12),
      TRow(284, temperate * 3, tempForest * 2, temperate * 7),
      TRow(282, temperate * 4, tempForest * 2, temperate * 7),
      TRow(280, hillyTemp, temperate * 12),
      TRow(278, hillyTemp * 3, temperate * 10),
      TRow(276, temperate, hillyTemp, temperate * 12),
      TRow(274, hillyTemp * 2, temperate, hillyTemp * 3, temperate * 4, sea, temperate * 3),
      TRow(272, hillyTemp, temperate, hillyTemp * 2, temperate * 2, temperate, sea, temperate, CapeOld(2, 2), CapeOld(3, 2), temperate * 3),
      TRow(270, hillyTemp * 3, temperate * 4, sea * 5, mtainOld, hillyTemp, temperate),
      TRow(268, sea, CapeOld(3, 2, mtainOld), hillyTemp * 3, temperate * 2, sea * 6, temperate, mtainOld),
      TRow(266, CapeOld(0, 2), CapeOld(4, 2, mtainOld), hillyTemp * 3, temperate * 2, hillyTemp * 8),
      VRow(265, MouthOld(1510, HVUp)),
      TRow(264, hillyTemp, CapeOld(1, 4), hillyTemp * 2, sea, CapeOld(4, 2, hillyTemp),  hillyTemp * 9),
      TRow(262, hillyTemp, sea * 2, hillyTemp * 2, sea, hillyTemp, hillyTemp * 4, hillyTemp * 5),
      TRow(260, sea * 3, hillyTemp, sea * 2, hillyTemp * 10),
      TRow(258, sea * 11, hillyTemp * 5),
      TRow(256, sea * 5, hillyTemp * 2, sea * 3, Isle10(hillyTemp), hillyTemp * 6),
    )
  }
  help.run
}