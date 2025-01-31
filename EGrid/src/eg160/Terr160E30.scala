/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** Terrain at 160km for 15° east to 45° east, centred on 30° east. Land and sea should be correct, but elevation has not been checked.
 * [[Isle13]] 13531.646km² => 15783.312km². Nordauslandet 15125km².
 * [[Isle10]] 7815.879km² => 9547.930km². [[Cyprus]] 9251km² [[Crete]] 8450km².
 * [[Isle8]] 4871.392km² => 6257.033km². Edge Island 5037km².
 * [[Isle6]] 2619.726km² => 3658.957km². [[Dodecanese]] 2714km².
 * [[Isle5]] 1753.701km² => 2619.726km². [[Cyclades]] 2572km², [[LesbosChios]] 2520km², [[IonianIs]] 1986km².
 * [[Isle4]] 1060.881km² => 1753.701km². [[Rhodes]] 1401km² + Karpathos 220km² = 1621km².
 * [[Isle3]] 541.265km² => 1060.881km². Samos-Ikaria 737km².
 * Below 541.265km². Karpathos 324.8 km² + Kasos 69.4km². + Saria 20.4km² = 414.2km². */
object Terr160E30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e30(254)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(338, SeaIcePerm),
    TRow(336, SeaIcePerm),
    TRow(334, SeaIcePerm * 2),
    TRow(332, SeaIceWinter * 2),
    TRow(330, SeaIceWinter * 3),
    VRow(329, BendOut(1530, HVUp, 7, SeaIceWinter), OrigLt(1532, HVDL, 6, SeaIceWinter)),
    TRow(328, hillyIce, SeaIceWinter * 2),
    TRow(326, hillyIce, SeaIceWinter * 3),
    TRow(324, Isle8(hillyTundra, SeaIceWinter), SeaIceWinter * 3),
    TRow(322, SeaIceWinter),
    VRow(315, BendIn(1528, HVDR, 13), BendIn(1530, HVDn, 13), BendOut(1532, HVUp, 7), BendIn(1534, HVDn, 13), OrigLt(1536, HVUL, 7)),
    TRow(314, sea, mtainTundra, mtainBoreal),

    VRow(313, BendIn(1522, HVDR, 13), BendIn(1524, HVDn, 13), BendMin(1526, HVUp), BendMin(1528, HVUL, 1), OrigRt(1538, HVUR, 7), BendIn(1540, HVDn, 13),
      BendIn(1542, HVDL, 13)),

    TRow(312, mtainBoreal, hillyTundra * 3, hillyTundra),
    VRow(311, BendOut(1522, HVUL, 7)),
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
    VRow(267, OrigMax(1510, HVDR), BendMax(1512, HVDL), BendOut(1536, HVUp, 7)),
    TRow(266, savannah, hillySavannah, hillyOce * 3, hillySavannah, mtainSub, hillyOce * 8),
    VRow(265, Bend(1512, HVUR, 9, 7), BendIn(1514, HVDL, 13), OrigRt(1522, HVDn), OrigRt(1524, HVDR), BendMax(1526, HVDL), OrigMin(1530, HVUR), Orig(1532, HVDL, 4, 5)),
    TRow(264, hillySub, subtrop, mtainSub, hillySavannah, mtainSavannah, mtainSavannah,  hillyOce * 9),
    VRow(263, BendIn(1512, HVUp, 13), BendIn(1514, HVUL, 13), Bend(1522, HVUR, 5, 5), ThreeDown(1524, 13, 11, 10), ThreeUp(1526, 10, 11, 13)),
    TRow(262, mtainSub, sea, Isle5(mtainSub), mtainSub, hillySavannah, Isle5(mtainSub), hillyOce, hillyOce * 4, hillyOce * 5),
    VRow(261, BendIn(1514, HVDR, 13), BendIn(1516, HVDn), BendIn(1518, HVUp), OrigLt(1520, HVDL, 7)),
    TRow(260, sea * 2, hillySub, mtainSavannah, Isle5(mtainSavannah), Isle3(mtainSavannah), hillyOce * 10),
    VRow(259, BendIn(1514, HVUR, 13), BendOut(1516, HVDL, 7), OrigLt(1548, HVDn, 7)),
    //Correct below this line
    TRow(258, sea * 3, hillySavannah, sea, sea, Isle6(mtainSavannah), mtainSavannah, hillySavannah, mtainSavannah, hillySavannah, hillySavannah, sahel * 2,
      deshot, sahel),

    VRow(257, BendIn(1516, HVUR, 13), BendIn(1518, HVUp, 13), OrigRt(1520, HVDL), OrigRt(1524, HVDR, 7), BendIn(1526, HVUp), BendIn(1528, HVDn, 9),
      ThreeDown(1530, 10, 0, 13), ThreeUp(1532, 13, 0, 10), BendIn(1534, HVUp, 13), BendOut(1536, HVDn, 7), BendIn(1538, HVUp, 13), BendOut(1540, HVDn, 7),
      ThreeDown(1542, 13, 6, 0), Bend(1544, HVDn, 6, 7), ThreeDown(1546, 13, 13, 6), BendMax(1548, HVUL)),

    TRow(256, sea * 5, mtainSavannah * 2, sea * 3, Isle10(hillySavannah), mtainSavannah, sahel, hillyDeshot, sahel, deshot, sahel),

    VRow(255, OrigLt(1522, HVDR, 7), BendIn(1524, HVUp, 13), BendOut(1526, HVDn, 7), BendIn(1528, HVUp, 13), BendIn(1530, HVUL, 13),
      BendIn(1524, HVUp, 13), ThreeUp(1546, 13, 0, 6), OrigRt(1548, HVUL ,7)),

    TRow(254, sea * 11, hillySavannah, sahel, deshot * 2, sahel * 2),
    )
  }
  help.run

  { import hexNames.{setRow => str}
    str(262, "" * 2, "Ionian Islands", "" * 2, "Lesbos")
    str(260, "" * 4, "Cyclades", "Samos")
    str(258, "" * 6, "Rhodes")
    str(256, "" * 5, "Crete", "" * 4, "Cyprus")
  }
}