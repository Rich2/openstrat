/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

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
  { override val rows: RArr[DateRow] = RArr(
    TileRow(202, SeaIcePerm),
    TileRow(200, SeaIcePerm),
    TileRow(198, SeaIcePerm),
    TileRow(196, SeaIcePerm),
    TileRow(194, Isle10(hillyIce, SeaIceWinter), SeaIceWinter),
    TileRow(192, SeaIceWinter * 2),
    TileRow(190, Isle6(hillyTundra, SeaIceWinter), SeaIceWinter * 2),
    TileRow(188, SeaIceWinter * 3),
    VertRow(183, BendIn(1528, HVDR, 12), BendIn(1530, HVDn, 13), OrigRt(1532, HVUL), OrigRt(1534, HVDR, 7), OrigLt(1536, HVUL)),
    TileRow(182, hillyTundra, hillyTundra, sea * 3),
    VertRow(181, OrigRt(1528, HVUp), OrigRt(1540, HVDR, 7), OrigLt(1542, HVUL, 7)),
    TileRow(180, mtainBoreal, hillyLakesBoreal * 2, hillyTundra, sea),//Checked down to here
    VertRow(179, OrigLt(1544, HVDn, 7)),
    TileRow(178, taiga * 4, tundra),
    VertRow(177, Orig(1530, HVDn, 7, 1, SeaIceWinter), OrigRt(1540, HVDR, 6, siceWin), ThreeDown(1542, 3, 13, 13, siceWin), BendOut(1544, HVUL, 7, siceWin)),
    TileRow(176, taiga, taiga * 2, taiga, taiga, taiga),
    VertRow(175, BendIn(1528, HVDR, 13), Bend(1530, HVUL, 3, 7, SeaIceWinter, sea), OrigMax(1542, HVUp, siceWin)),
    TileRow(174, taiga, taiga, taiga * 4),
    VertRow(173, Bend(1526, HVDR, 12, 5), BendIn(1528, HVUL), OrigMin(1540, HVDR, 3, Lake), BendIn(1542, HVDL, 6, Lake)),
    TileRow(172, taiga, taiga, taiga * 5),
    VertRow(171, Bend(1526, HVUR, 7, 7), BendIn(1528, HVDL, 13), OrigMin(1536, HVUR, 3, Lake), OrigMin(1538, HVDL, 3, Lake), OrigMin(1542, HVUp, 3, Lake)),
    TileRow(170, hillyLakesTaiga, taiga, taiga, taiga * 4),

    VertRow(169, Bend(1526, HVDR, 10, 7), ThreeUp(1528, 3, 4, 13), ThreeDown(1530, 13, 0, 6), BendOut(1532, HVDn, 7, sea, SeaIceWinter),
      BendIn(1534, HVUp, 13, SeaIceWinter), OrigRt(1536, HVDL, 7, SeaIceWinter)),

    TileRow(168, hillyLakesContForest, continental, continental, contForest * 2, taiga * 2),
    VertRow(167, Bend(1524, HVDR, 13, 7), ThreeUp(1526, 6, 13, 13), BendIn(1528, HVUp), BendIn(1530, HVUL)),
    TileRow(166, contForest, continental * 3, contForest * 2, continental, contForest),
    VertRow(165, OrigMax(1522, HVUR), Bend(1524, HVUL, 13, 7)),
    TileRow(164, oceanic, oceanic * 7),
    TileRow(162, oceanic * 9),
    TileRow(160, oceanic * 3, oceForest, oceanic * 5),
    TileRow(158, hillyOce * 2, oceanic * 7),
    TileRow(156, oceanic * 2, mtainDepr, oceanic * 6, deshot),
    VertRow(155, OrigRt(1548, HVDn)),
    TileRow(154, oceanic * 2, hillyOce * 2, continental, steppe * 3, steppe * 2),

    VertRow(153, OrigMin(1536, HVDR), ThreeDown(1538, 13, 13,13), Orig(1540, HVDL, 3, 7), Orig(1544, HVDR, 7, 5), ThreeDown(1546, 13, 0, 6),
      BendIn(1548, HVUL, 13)),

    TileRow(152, hillyOce * 2, continental * 2, steppe, hillySteppe, hillySteppe, hillySubForest, steppe * 2),

    VertRow(151, BendOut(1536, HVDR, 7), ThreeUp(1538, 13, 0, 13), BendIn(1540, HVUp, 13), BendOut(1542, HVDn), BendIn(1544, HVUp, 13), ThreeUp(1546, 13, 0, 13),
      BendOut(1548, HVDL, 7)),

    TileRow(150, hillyOce, hillyOce * 3, hillySteppe, sea * 3, mtainSubForest * 2, hillyOce),

    VertRow(149, BendIn(1514, HVUp, 13), Bend(1516, HVDn, 6, 7), Bend(1518, HVDL, 13, 7), Orig(1534, HVUR, 7, 5), ThreeUp(1536, 0, 13, 13), BendOut(1538, HVUp, 7),
      OrigLt(1540, HVDL, 7), ThreeUp(1548, 13, 13, 0), BendMax(1550, HVUp), BendMax(1552, HVDn), OrigMin(1554, HVUL)),

    TileRow(148, hillySub, hillySub, hillySavannah, hillyOce * 2, hillySubForest, mtainSubForest, hillySubForest, mtainSavannah, hillySavannah, mtainSavannah),
    VertRow(147, Bend(1518, HVUR, 10, 2), BendIn(1520, HVDL, 13), Bend(1528, HVDR, 13, 6), Orig(1530, HVDL, 5, 1), OrigLt(1532, HVUR, 7), OrigRt(1534, HVDL)),
    TileRow(146, hillySub, hillySub * 2, hillySub * 2, hillyDeshot * 3, mtainDepr, hillyDeshot * 2),

    VertRow(145, OrigLt(1514, HVDn), BendOut(1518, HVDR, 7), BendIn(1520, HVUL, 13), OrigMin(1522, HVUR), OrigLt(1524, HVDL), BendIn(1530, HVDL, 13),
      BendMax(1528, HVUR)),

    TileRow(144, hillySub, sea, hillySub, hillySavannah, hillySub * 4, hillyDeshot, oceanic, mtainDepr),

    VertRow(143, BendIn(1514, HVUR, 10), BendIn(1516, HVUp, 13), BendIn(1518, HVUL, 13), Bend(1526, HVDn, 13, 5), BendIn(1528, HVUp, 13), ThreeUp(1530, 7, 13, 13),
      Bend(1532, HVDL, 13, 3), OrigMin(1544, HVDn)),

    TileRow(142, sea * 3, Isle4(mtainSavannah), Isle3(mtainSavannah), mtainSavannah * 2, hillySavannah, hillyTrop, deshot * 3),

    VertRow(141, ThreeUp(1532, 13, 0, 13), BendIn(1534, HVUp, 13), BendOut(1536, HVDn, 7), ThreeDown(1538, 13, 8, 0), Bend(1540, HVDn, 8, 7),
      ThreeDown(1542, 13, 0, 8), ThreeUp(1544, 9, 0, 13)),

    TileRow(140, sea * 3, Isle7(mtainSubForest), sea * 2, hillySavannah, sea, hillySavannah, deshot * 3), VertRow(139, OrigLt(1512, HVUL, 7), OrigRt(1520, HVUR, 7),
      BendIn(1522, HVDn, 11), BendOut(1524, HVUp, 7), ThreeUp(1526, 9, 13, 0), ThreeDown(1528, 9, 0, 13), BendIn(1538, HVUR, 8), BendIn(1540, HVUp, 8),
      BendIn(1542, HVUL, 8)),

    TileRow(138, sea * 2, sahel, deshot, sea * 4, hillyOce, deshot * 3),

    VertRow(137, OrigRt(1514, HVUR), BendIn(1516, HVDn, 13), OrigLt(1518, HVUL, 7), Orig(1528, HVUp, 4, 2), OrigRt(1532, HVDR, 7), BendOut(1534, HVUp, 7),
      OrigLt(1536, HVDL, 7)),

    TileRow(136, deshot * 6, savannah, hillyDeshot, deshot * 5),
    VertRow(135, OrigLt(1540, HVDn), OrigLt(1544, HVDn)),
    TileRow(134, deshot * 6, sahel, deshot, hillyDeshot, hillyDeshot, deshot * 3),
    VertRow(133, BendIn(1540, HVUR, 13), OrigMin(1542, HVUL), OrigMin(1544, HVUp)),
    TileRow(132, deshot * 6, sahel, deshot, sea, deshot * 4),
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