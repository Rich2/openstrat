/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex.*, egrid.{WTerrSetter, _}, WTiles.*, MultExt.*

/** 640km [[WTile]] terrain for 15° east to 45° east, centred on 30° east. Hex tile area of 709448.010km² .
 *  [[Isle3]] 8660.254km² => 16974.097km². (Crete 8450km²) + (Rhodes 1401km²) = 9851km²
 *  Below 8660.254km²   */
object Terr640E30 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e30(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(129, OrigRt(1534, HVUR, 7), BendIn(1536, HVDn, 9, sea, SeaIceWinter), BendOut(1538, HVUp, 7, SeaIceWinter)),
    TileRow(128, tundra),
    TileRow(126, taiga * 2),
    VertRow(125, OrigRt(1534, HVDn, 7)),
    TileRow(124, continental, taiga),
    VertRow(123, BendOut(1532, HVUp), Bend(1534, HVUL, 4, 2)),
    TileRow(122, continental * 2),
    TileRow(120, continental * 3),
    TileRow(118, continental * 3),
    VertRow(119, OrigLt(1544, HVDn, 6, Lake)),
    VertRow(117, BendIn(1530, HVDL, 11), OrigMax(1536, HVDR), BendIn(1538, HVUp, 13), Orig(1540, HVDL, 7, 6),  BendMin(1544, HVUR, 3, Lake)),
    TileRow(116, hillyOce, hillyOce, hillyOce),
    VertRow(115, SetSep(1529), BendIn(1532, HVDR, 13), BendIn(1534, HVDn, 13), BendIn(1536, HVDL, 13)),
    TileRow(114, sea, mtainSavannah, hillySavannah * 2),
    VertRow(113, BendMin(1528, HVUR, 1), OrigMin(1530, HVUL, 4), BendIn(1532, HVUR, 13), BendIn(1534, HVUp, 13), ThreeUp(1536, 12, 8, 13), Orig(1538, HVUL, 1, 7)),
    TileRow(112, deshot, oceanic, deshot * 2),
    VertRow(111, OrigLt(1538, HVDR), BendIn(1540, HVDL, 8), OrigLt(1546, HVDR, 7)),
    TileRow(110, deshot * 2, deshot, deshot),
    VertRow(109, Bend(1540, HVUR, 6, 1), BendIn(1542, HVDL, 11)),
    TileRow(108, deshot * 3, deshot),
    VertRow(107, Bend(1542, HVUR, 7, 3), Bend(1544, HVDL, 10, 1)),
    TileRow(106, sahel * 3, savannah, hillyDeshot),
    VertRow(105, BendIn(1544, HVUR), Bend(1546, HVUp, 4, 6), Bend(1548, HVDn, 10, 4)),
    TileRow(104, Land(Plain, Savannah, Forest), savannah, savannah, hillySavannah, hillySahel),
    TileRow(102, jungle * 2, Land(Plain, Savannah, Forest), hillySavannah, sahel),
    VertRow(101, OrigMin(1546, HVDn, 5)),
    TileRow(100, jungle * 2, hillyJungle, hillySavannah, hillySavannah),
    VertRow(99, OrigMin(1536, HVDn, 3, lake), BendOut(1544, HVDR), BendIn(1546, HVUL, 13)),
    TileRow(98, hillyJungle * 2, savannah, hillySavannah),
    VertRow(97, OrigMin(1536, HVUp, 3, lake), BendOut(1544, HVUR, 7), BendIn(1546, HVDL, 13)),
    TileRow(96, savannah, jungle * 2, hillySavannah * 2),
    VertRow(95, Bend(1544, HVDR, 13, 6), BendInRt(1546, HVUL, 13, 7)),
    TileRow(94, savannah * 2, hillySavannah, savannah, hillySavannah),
    VertRow(93, Bend(1542, HVDR, 13, 6), Bend(1544, HVUL, 8, 5)),
    TileRow(92, savannah * 2, jungle, hillySavannah),
    VertRow(91, BendOut(1540, HVDR), BendInLt(1542, HVUL, 13, 7)),
    TileRow(90, deshot, savannah * 2),
    VertRow(89, BendOut(1538, HVDR), BendIn(1540, HVUL, 13), OrigLt(1544, HVDR)),
    TileRow(88, sahel, hillySavannah),
    VertRow(87, BendOut(1528, HVDL, 7), BendOut(1536, HVDR, 7), BendIn(1538, HVUL, 13)),
    TileRow(86, hillySavannah * 2),
    VertRow(85, BendIn(1528, HVUR, 13), BendIn(1530, HVUp, 13), BendOut(1532, HVDn, 7), BendIn(1534, HVUp, 13), BendIn(1536, HVUL, 13)),
    TileRow(72, SeaIcePerm),
    TileRow(70, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(126, "Scandanva central")
    str(118, "Balkans", "Crimea")
    str(116, "Greece", "Turkey east")
    str(114, "", "Crete")
  }
}