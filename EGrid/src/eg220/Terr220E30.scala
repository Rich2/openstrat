/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 15° east to 45° east centred on 30° east. A tile area of 41915.629km².
 * [[Isle10]] 14776.896km² => 18051.555km². Nordauslander 15125km².
 * [[Isle8]] 9209.977km² => 11829.704km². Cyprus 9251km².
 * [[Isle7]] 6917.716km² => 9209.977km². Crete 8450km².
 * [[Isle6]] 4952.921km² => 6917.716km². Edge Island 5073 km².
 * [[Isle4]] 2005.728km² => 3315.591km². Cyclades 2572km².
 * [[Isle3]] 1023.330km² => 2005.728km². Dodecanese west 1200km². */
object Terr220E30 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e30(132)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(202, SeaIcePerm),
    TRow(200, SeaIcePerm),
    TRow(198, SeaIcePerm),
    TRow(196, SeaIcePerm),
    TRow(194, Isle10(hillyIce, SeaIceWinter), SeaIceWinter),
    TRow(192, SeaIceWinter * 2),
    TRow(190, Isle6(hillyTundra, SeaIceWinter), SeaIceWinter * 2),
    TRow(188, SeaIceWinter * 3),
    VRow(183, BendIn(1528, HVDR, 12), BendIn(1530, HVDn, 13), OrigRt(1532, HVUL), OrigRt(1534, HVDR, 7), OrigLt(1536, HVUL)),
    TRow(182, hillyTundra, hillyTundra, sea * 3),
    VRow(181, OrigRt(1528, HVUp), OrigRt(1540, HVDR, 7), OrigLt(1542, HVUL, 7)),
    TRow(180, mtainBoreal, hillyLakesBoreal * 2, hillyTundra, sea),//Checked down to here
    VRow(179, OrigLt(1544, HVDn, 7)),
    TRow(178, taiga * 4, tundra),
    VRow(177, Orig(1530, HVDn, 7, 1, SeaIceWinter), OrigRt(1540, HVDR, 6, siceWin), ThreeDown(1542, 3, 13, 13, siceWin), BendOut(1544, HVUL, 7, siceWin)),
    TRow(176, taiga, taiga * 2, taiga, taiga, taiga),
    VRow(175, BendIn(1528, HVDR, 13), Bend(1530, HVUL, 3, 7, SeaIceWinter, sea), OrigMax(1542, HVUp, siceWin)),
    TRow(174, taiga, taiga, taiga * 4),
    VRow(173, Bend(1526, HVDR, 12, 5), BendIn(1528, HVUL), OrigMin(1540, HVDR, 3, Lake), BendIn(1542, HVDL, 6, Lake)),
    TRow(172, taiga, taiga, taiga * 5),
    VRow(171, Bend(1526, HVUR, 7, 7), BendIn(1528, HVDL, 13), OrigMin(1536, HVUR, 3, Lake), OrigMin(1538, HVDL, 3, Lake), OrigMin(1542, HVUp, 3, Lake)),
    TRow(170, hillyLakesTaiga, taiga, taiga, taiga * 4),

    VRow(169, Bend(1526, HVDR, 10, 7), ThreeUp(1528, 3, 4, 13), ThreeDown(1530, 13, 0, 6), BendOut(1532, HVDn, 7, sea, SeaIceWinter),
      BendIn(1534, HVUp, 13, SeaIceWinter), OrigRt(1536, HVDL, 7, SeaIceWinter)),

    TRow(168, hillyLakesContForest, continental, continental, contForest * 2, taiga * 2),
    VRow(167, Bend(1524, HVDR, 13, 7), ThreeUp(1526, 6, 13, 13), BendIn(1528, HVUp), BendIn(1530, HVUL)),
    TRow(166, contForest, continental * 3, contForest * 2, continental, contForest),
    VRow(165, OrigMax(1522, HVUR), Bend(1524, HVUL, 13, 7)),
    TRow(164, oceanic, oceanic * 7),
    TRow(162, oceanic * 9),
    TRow(160, oceanic * 3, oceForest, oceanic * 5),
    TRow(158, hillyOce * 2, oceanic * 7),
    TRow(156, oceanic * 2, mtainDepr, oceanic * 6, deshot),
    VRow(155, OrigRt(1548, HVDn)),
    TRow(154, oceanic * 2, hillyOce * 2, continental, steppe * 3, steppe * 2),

    VRow(153, OrigMin(1536, HVDR), ThreeDown(1538, 13, 13,13), Orig(1540, HVDL, 3, 7), Orig(1544, HVDR, 7, 5), ThreeDown(1546, 13, 0, 6),
      BendIn(1548, HVUL, 13)),

    TRow(152, hillyOce * 2, continental * 2, steppe, hillySteppe, hillySteppe, hillySubForest, steppe * 2),

    VRow(151, BendOut(1536, HVDR, 7), ThreeUp(1538, 13, 0, 13), BendIn(1540, HVUp, 13), BendOut(1542, HVDn), BendIn(1544, HVUp, 13), ThreeUp(1546, 13, 0, 13),
      BendOut(1548, HVDL, 7)),

    TRow(150, hillyOce, hillyOce * 3, hillySteppe, sea * 3, mtainSubForest * 2, hillyOce),

    VRow(149, BendIn(1514, HVUp, 13), Bend(1516, HVDn, 6, 7), Bend(1518, HVDL, 13, 7), Orig(1534, HVUR, 7, 5), ThreeUp(1536, 0, 13, 13), BendOut(1538, HVUp, 7),
      OrigLt(1540, HVDL, 7), ThreeUp(1548, 13, 13, 0), BendMax(1550, HVUp), BendMax(1552, HVDn), OrigMin(1554, HVUL)),

    TRow(148, hillySub, hillySub, hillySavannah, hillyOce * 2, hillySubForest, mtainSubForest, hillySubForest, mtainSavannah, hillySavannah, mtainSavannah),
    VRow(147, Bend(1518, HVUR, 10, 2), BendIn(1520, HVDL, 13), Bend(1528, HVDR, 13, 6), Orig(1530, HVDL, 5, 1), OrigLt(1532, HVUR, 7), OrigRt(1534, HVDL)),
    TRow(146, hillySub, hillySub * 2, hillySub * 2, hillyDeshot * 3, mtainDepr, hillyDeshot * 2),

    VRow(145, OrigLt(1514, HVDn), BendOut(1518, HVDR, 7), BendIn(1520, HVUL, 13), OrigMin(1522, HVUR), OrigLt(1524, HVDL), BendIn(1530, HVDL, 13),
      BendMax(1528, HVUR)),

    TRow(144, hillySub, sea, hillySub, hillySavannah, hillySub * 4, hillyDeshot, oceanic, mtainDepr),

    VRow(143, BendIn(1514, HVUR, 10), BendIn(1516, HVUp, 13), BendIn(1518, HVUL, 13), Bend(1526, HVDn, 13, 5), BendIn(1528, HVUp, 13), ThreeUp(1530, 7, 13, 13),
      Bend(1532, HVDL, 13, 3), OrigMin(1544, HVDn)),

    TRow(142, sea * 3, Isle4(mtainSavannah), Isle3(mtainSavannah), mtainSavannah * 2, hillySavannah, hillyTrop, deshot * 3),

    VRow(141, ThreeUp(1532, 13, 0, 13), BendIn(1534, HVUp, 13), BendOut(1536, HVDn, 7), ThreeDown(1538, 13, 8, 0), Bend(1540, HVDn, 8, 7),
      ThreeDown(1542, 13, 0, 8), ThreeUp(1544, 9, 0, 13)),

    TRow(140, sea * 3, Isle7(mtainSubForest), sea * 2, hillySavannah, sea, hillySavannah, deshot * 3), VRow(139, OrigLt(1512, HVUL, 7), OrigRt(1520, HVUR, 7),
      BendIn(1522, HVDn, 11), BendOut(1524, HVUp, 7), ThreeUp(1526, 9, 13, 0), ThreeDown(1528, 9, 0, 13), BendIn(1538, HVUR, 8), BendIn(1540, HVUp, 8),
      BendIn(1542, HVUL, 8)),

    TRow(138, sea * 2, sahel, deshot, sea * 4, hillyOce, deshot * 3),

    VRow(137, OrigRt(1514, HVUR), BendIn(1516, HVDn, 13), OrigLt(1518, HVUL, 7), Orig(1528, HVUp, 4, 2), OrigRt(1532, HVDR, 7), BendOut(1534, HVUp, 7),
      OrigLt(1536, HVDL, 7)),

    TRow(136, deshot * 6, savannah, hillyDeshot, deshot * 5),
    VRow(135, OrigLt(1540, HVDn), OrigLt(1544, HVDn)),
    TRow(134, deshot * 6, sahel, deshot, hillyDeshot, hillyDeshot, deshot * 3),
    VRow(133, BendIn(1540, HVUR, 13), OrigMin(1542, HVUL), OrigMin(1544, HVUp)),
    TRow(132, deshot * 6, sahel, deshot, sea, deshot * 4),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(194, "Nordauslandet")
    str(190, "Edge Island")
    str(154, "" * 7, "Mariupol")
    str(142, "" * 3, "Cyclades", "Dodecanese west")
    str(140, "" * 3, "Crete", "" * 2, "Cyprus")
  }
}