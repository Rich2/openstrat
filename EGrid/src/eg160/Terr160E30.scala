/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** Terrain at 160km for 30E. Land and sea should be correct, but elevation has not been checked.
 * [[Isle10]] 7815.879km² => 9547.930km². Cyprus 9251km².
 * [[Isle6]] 2619.726km² => 3658.957km². Dodecanese 2714km².
 * [[Isle4]] 1060.881km² => 1753.701km². Rhodes 1401km² + Karpathos 220km² = 1621km². */
object Terr160E30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e30(254)
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
    TRow(262, mtainSub, sea * 2, hillyOce * 2, sea, hillyOce, hillyOce * 4, hillyOce * 5),
    VRow(261, BendIn(1514, HVDR, 13), BendIn(1516, HVDn), BendIn(1518, HVUp), OrigLt(1520, HVDL, 7)),
    TRow(260, sea * 2, hillySub, mtainSavannah, sea * 2, hillyOce * 10),
    VRow(259, BendIn(1514, HVUR, 13), BendOut(1516, HVDL, 7)),
    TRow(258, sea * 3, hillySavannah, sea, sea, Isle6(mtainSavannah), mtainSavannah, hillySavannah, mtainSavannah, hillySavannah, hillyOce * 5),
    VRow(257, BendIn(1516, HVUR, 13), BendIn(1518, HVUp, 13), OrigRt(1520, HVDL)),
    TRow(256, sea * 5, mtainSavannah * 2, sea * 3, Isle10(hillyOce), hillyOce * 6),
    TRow(254, sea * 11, hillySavannah, sahel, deshot * 2, sahel * 2),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(258, "" * 6, "Rhodes")
    str(256, "" * 5, "Crete west", "Crete East", "" * 3, "Cyprus")
  }
}