/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain terrain for 45° east to 75° east, centred on 60° east. Hex tile scale 460km.  */
object Terr460E60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e60(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(146, SeaIcePerm),
    VertRow(145, BendIn(2558, HVDL, 8, SeaIceWinter)),
    TileRow(144, SeaIceWinter),
    VertRow(143, OrigLt(2558, HVUp, 7, SeaIceWinter), OrigRt(2560, HVUR, 7, SeaIceWinter), BendIn(2562, HVDn, 10, SeaIceWinter), ThreeDown(2564, 0, 13, 13, SeaIceWinter)),
    TileRow(142, ice),
    VertRow(141, BendOut(2562, HVDR, 7, SeaIceWinter), BendIn(2564, HVUL, 13, SeaIceWinter)),
    TileRow(140, mtainTundra, tundra),

    VertRow(139, ThreeDown(2556, 0, 13, 11, SeaIceWinter, SeaIceWinter, sea), BendIn(2558, HVDn, 13, SeaIceWinter), BendIn(2560, HVUp, 8, SeaIceWinter),
      Bend(2562, HVUL, 13, 4, SeaIceWinter)),

    TileRow(138, taiga, tundra),
    TileRow(136, taiga, hillyTaiga, taiga),
    TileRow(134, taiga * 3),
    TileRow(132, taiga, hillyTaiga, taiga),
    TileRow(130, oceanic * 4),
    TileRow(128, savannah, sahel * 2, savannah),
    TileRow(126, sahel, sahel * 3),
    VertRow(125, BendOut(2554, HVDR), OrigMin(2556, HVDL)),
    TileRow(124, hillySavannah, sahel * 3, hillySavannah),
    VertRow(123, Bend(2554, HVUR, 4, 7), BendIn(2556, HVDL, 13)),
    TileRow(122, savannah, deshot, sahel, mtainSavannah * 2),
    VertRow(121, OrigLt(2554, HVUR, 7), BendIn(2556, HVUL, 13)),
    TileRow(120, hillySahel, hillySahel, hillyDeshot, mtainSavannah * 2),
    TileRow(118, hillySahel, hillyDeshot * 4, savannah),
    VertRow(117, OrigLt(2550, HVDn)),
    TileRow(116, hillyDeshot, mtainSahel, hillyDeshot, hillySahel, hillyDeshot, deshot),
    VertRow(115, BendIn(2550, HVUR, 13), BendIn(2552, HVUp, 10), BendIn(2554, HVDn, 13), Bend(2556, HVUp, 3, 3), BendIn(2558, HVDn, 13), BendIn(2560, HVDL, 13)),
    TileRow(114, deshot, deshot, mtainSahel, hillyDeshot, sahel * 2),
    VertRow(113, BendMax(2560, HVUR), ThreeDown(2562, 13, 0, 13), BendOut(2564, HVDn), BendOut(2566, HVDL, 7)),
    TileRow(112, deshot * 2, hillySahel, sea, savannah, savannah),
    VertRow(111, BendOut(2560, HVDR, 7), BendIn(2562, HVUL, 13), BendIn(2566, HVUR, 13), ThreeDown(2568, 13, 13, 0), Orig(2570, HVDL, 6, 2)),
    TileRow(110, deshot, hillyDeshot, deshot, sea * 2, mtainSavannah),
    VertRow(109, BendOut(2554, HVDR), BendOut(2556, HVDn), BendIn(2558, HVUp, 13), BendIn(2560, HVUL, 13), BendIn(2568, HVUR, 13), BendOut(2570, HVDL, 7)),
    TileRow(108, mtainDeshot * 2, sea * 4, hillySavannah),

    VertRow(107, BendIn(2546, HVDn, 13), Bend(2548, HVUp, 12, 7), Bend(2550, HVDn, 10, 7), BendIn(2552, HVUp, 13), BendIn(2554, HVUL, 13), BendIn(2570, HVUR, 13),
      BendOut(2572, HVDL, 7)),

    TileRow(106, hillyDeshot, sea * 5, hillySavannah),
    VertRow(105, BendIn(2572, HVUR, 13), BendOut(2574, HVDL, 7)),
    TileRow(104, deshot),
    VertRow(101, BendOut(2546, HVDR), OrigRt(2548, HVDL)),
    VertRow(99, BendIn(2546, HVUL, 13)),
    VertRow(93, OrigRt(2546, HVUR, 7), OrigLt(2548, HVDL, 7)),
    TileRow(92, hillySavannah),
    VertRow(91, OrigRt(2552, HVDn, 7)),
    TileRow(90, hillySavannah),
    VertRow(89, BendOut(2552, HVUR, 7), BendIn(2554, HVDL, 13)),
    TileRow(88, hillyJungle),
    VertRow(87, BendOut(2552, HVDR, 7), BendIn(2554, HVUL, 13)),
    TileRow(86, hillySavannah),
    VertRow(85, OrigLt(2548, HVDR), BendIn(2550, HVUp, 13), BendIn(2552, HVUL, 13)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(142, "Severny north")
    str(140, "Severny south")
    str(112, "" * 2, "Oman east")
  }
}