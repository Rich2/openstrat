/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
  { override val rows: RArr[RowBase] = RArr(
    TRow(320, sea * 5),
    TRow(318, sea * 5),
    TRow(316, sea * 6),
    TRow(314, sea * 6),
    TRow(312, mtainBoreal, hillyTundra * 3, sea * 3),
    TRow(310, hillyTaiga * 2, taiga * 3, sea, taiga),
    TRow(308, hillyTaiga, taiga * 7),
    TRow(306, taiga * 5, sea, taiga * 2),
    TRow(304, taiga, sea, taiga * 6),
    TRow(302, taiga, sea, taiga * 7),
    TRow(300, taiga, sea, taiga * 7),
    TRow(298, taiga, sea, taiga * 3, lake, taiga * 4),
    TRow(296, taiga, sea * 3, taiga * 6),
    TRow(294, oceanic, sea, oceanic * 7, taiga),
    TRow(292, oceanic, sea, oceanic * 9),
    TRow(290, sea, oceanic * 10),
    TRow(288, sea * 2, oceanic * 9),
    TRow(286, oceanic * 12),
    TRow(284, oceanic * 3, oceForest * 2, oceanic * 7),
    TRow(282, oceanic * 4, oceForest * 2, oceanic * 7),
    TRow(280, hillyOce, oceanic * 12),
    TRow(278, hillyOce * 3, oceanic * 10),
    TRow(276, oceanic, hillyOce, oceanic * 12),
    TRow(274, hillyOce * 2, oceanic, hillyOce * 3, oceanic * 4, sea, oceanic * 3),
    TRow(272, hillyOce, oceanic, hillyOce * 2, oceanic * 2, oceanic, sea, hillySavannah, savannah, subtrop, oceanic * 3),
    TRow(270, hillySavannah, hillyOce * 2, oceanic * 4, sea * 4, mtainSub, mtainDepr, hillyOce, oceanic),
    VRow(269, BendMax(1506, HVUp)),
    TRow(268, sea, mtainSub, hillyOce * 3, oceanic * 2, sea * 6, oceanic, mtainDepr),
    VRow(267, OrigMax(1510, HVDR), BendMax(1512, HVDL)),
    TRow(266, savannah, hillySavannah, hillyOce * 3, oceanic * 2, hillyOce * 8),
    VRow(265, Bend(1512, HVUR, 9, 7), BendIn(1514, HVDL, 13)),
    TRow(264, hillySub, subtrop, hillyOce * 2, sea, hillyOce,  hillyOce * 9),
    VRow(263, BendIn(1512, HVUp, 13), BendIn(1514, HVUL, 13)),
    TRow(262, hillyOce, sea * 2, hillyOce * 2, sea, hillyOce, hillyOce * 4, hillyOce * 5),
    TRow(260, sea * 3, hillyOce, sea * 2, hillyOce * 10),
    TRow(258, sea * 11, hillyOce * 5),
    TRow(256, sea * 5, hillyOce * 2, sea * 3, Isle10(hillyOce), hillyOce * 6),
    )
  }
  help.run
}