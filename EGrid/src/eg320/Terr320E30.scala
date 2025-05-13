/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile scale of 320km.
 * [[Tile12]] 45812.743km² => 54126.587km². Spitsbergen-edge 46020km²
 * [[Tile9]] 25028.134km² => 31263.517km². Sicily 25711km².
 * [[Tile7]] 14635.829km² => 19485.571km². Nordauslandet 15125km².
 * [[Tile5]] 7014.805km² => 10478.907km². Crete 8450 km². Cyprus 9251km². */
object Terr320E30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e30(118)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(166, SeaIcePerm),
    TileRow(164, Isle7(Plain, IceCap, LandFree, SeaIceWinter)),
    VertRow(163, ThreeDown(1536, 9, 0, 13, SeaIceWinter)),
    TileRow(162, Land(Mountains, IceCap, LandFree), SeaIceWinter),
    VertRow(161, OrigLt(1534, HVUR, 7, SeaIceWinter), BendIn(1536, HVUL, 13, SeaIceWinter)),
    VertRow(157, OrigRt(1530, HVUR, 7), OrigLt(1532, HVDL, 7), OrigRt(1536, HVDR, 7), OrigLt(1538, HVUL, 7)),
    TileRow(156, hillyTundra, hillyTundra, sea),
    VertRow(155, OrigRt(1542, HVDR, 7, SeaIceWinter), BendIn(1544, HVDL, 11, SeaIceWinter)),
    TileRow(154, hillyTaiga, taiga, hillyLakesTaiga, tundra),

    VertRow(153, OrigRt(1534, HVDn, 7, siceWin), OrigMin(1538, HVUR, 3, SeaIceWinter), BendIn(1540, HVDn, 13, SeaIceWinter),
      ThreeDown(1542, 3, 3, 3, SeaIceWinter), BendIn(1544, HVUL, 9, SeaIceWinter)),

    TileRow(152, taiga, taiga, lakesTaiga, taiga),
    VertRow(151, BendOut(1532, HVDR, 7, sea, siceWin), BendIn(1534, HVUL, 13, siceWin), OrigLt(1542, HVUp, 7, SeaIceWinter)),
    TileRow(150, taiga, lakesTaiga * 3),
    VertRow(149, BendOut(1530, HVDR, 7), BendIn(1532, HVUL, 13), OrigMin(1536, HVDR, 3, Lake), OrigMin(1538, HVUL, 3, Lake)),
    VertRow(147, Bend(1528, HVDR, 13, 3), ThreeUp(1530, 13, 0, 10), BendIn(1532, HVUp, 13), OrigRt(1534, HVDL, 7)),
    TileRow(148, taiga, taiga, taiga * 3),
    TileRow(146, oceanic, taiga * 4),
    VertRow(145, BendOut(1526, HVUp, 7), Bend(1528, HVUL, 2, 7)),
    TileRow(144, oceanic * 5),
    TileRow(142, oceanic * 6),
    TileRow(140, oceanic * 6),
    TileRow(138, mtainDepr * 2, hillyOce, oceanic * 3, deshot),
    VertRow(137, OrigRt(1522, HVDn), Orig(1538, HVDn, 3, 7), BendIn(1542, HVDR), OrigRt(1544, HVDL)),
    TileRow(136, hillyCont, continental * 3, steppe, steppe * 2),
    VertRow(135, BendOut(1522, HVUR, 7), BendIn(1524, HVDL, 13), BendMax(1536, HVDR), ThreeUp(1538, 7, 13, 13), Bend(1540, HVUp, 10, 7), ThreeUp(1542, 7, 13, 7)),
    TileRow(134, mtainContForest, hillyContForest, hillyCont, mtainSubForest, mtainSavannah, mtainSubForest, mtainSavannah),
    VertRow(133, BendIn(1524, HVUR, 8), BendIn(1526, HVDL, 13), OrigMin(1530, HVDn), OrigMax(1536, HVUp)),
    TileRow(132, hillySavannah, hillySub, hillySavannah, mtainSavannah, hillySahel * 3),
    VertRow(131, BendIn(1520, HVDR), BendMax(1524, HVDR), ThreeUp(1526, 0, 6, 13), OrigLt(1528, HVUL), Bend(1530, HVUR, 10, 4), BendIn(1532, HVDL, 12)),
    TileRow(130, hillySavannah, mtainSub, hillySavannah, mtainSavannah, hillyOce * 4),

    VertRow(129, ThreeUp(1520, 10, 6, 0), BendIn(1522, HVUp, 13), ThreeUp(1524, 13, 0, 13), BendIn(1526, HVUp, 10), ThreeDown(1530, 13, 11, 0), ThreeUp(1532, 6, 11, 13),
      BendIn(1534, HVDL, 13), BendIn(1538, HVDR, 11), BendIn(1540, HVDn, 11), Bend(1542, HVDL, 11, 4)),

    TileRow(128, sea * 2, mtainSavannah, sea, hillySavannah, hillySub, deshot * 2),

    VertRow(127, OrigLt(1520, HVUL, 7), BendIn(1524, HVDR, 7), BendIn(1526, HVDn, 10), BendOut(1528, HVUp, 7), ThreeUp(1530, 11, 13, 0),
      ThreeDown(1532, 11, 0, 13), BendIn(1534, HVUL, 11), BendIn(1538, HVUR, 11), ThreeDown(1540, 11, 13, 0), Bend(1542, HVUL, 11, 3)),

    TileRow(126, sea, sahel, sahel, sea * 2, hillySavannah, deshot * 2),
    VertRow(125, OrigMin(1522, HVUR), Bend(1524, HVUL, 4, 2), OrigLt(1532, HVUp), OrigRt(1540, HVUp)),
    TileRow(124, sahel * 4, savannah, deshot * 4),
    VertRow(123, OrigLt(1538, HVDR), OrigMin(1540, HVUL)),
    TileRow(122, deshot * 4, savannah, sea, deshot * 3),
    TileRow(120, deshot * 6, sea, deshot * 2),
    TileRow(118, deshot * 5, hillyDeshot, sea, hillyDeshot, deshot),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(164, "Nordauslandet")
    str(162, "Spitsbergen")
    str(132, "Italy south")
    str(130, "Calabria", "Peloponnesus")
    str(128, "" * 2, "Crete", "", "Cyprus")
    str(126, "" * 5, "Palestine")
  }
}