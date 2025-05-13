/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 460km [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile area 183250.975km².
 * [[Isle4]] 8768.845km² => 14495.438km². Jamaica 10,991 km². */
object Terr460W90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w90(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(147, OrigRt(9728, HVUR, 7, SeaIcePerm), BendIn(9730, HVDn, 13, SeaIcePerm), BendOut(9732, HVUp, 7, SeaIcePerm)),
    TileRow(146, mtainIce),
    TileRow(144, mtainTundra),

    VertRow(143, Bend(9726, HVDn, 7, 2, SeaIcePerm), BendIn(9728, HVDL, 8, SeaIcePerm), Orig(9730, HVDR, 5, 1, SeaIcePerm),
      BendIn(9732, HVDL, 13, SeaIcePerm, siceWin)),

    TileRow(142, hillyTundra),

    VertRow(141, SetSep(9727, siceWin), BendIn(9728, HVUR, 6, SeaIcePerm), ThreeDown(9730, 10, 0, 6, siceWin, siceWin, SeaIcePerm),
      ThreeUp(9732, 0, 10, 12, siceWin), BendIn(9734, HVDL, 10, siceWin)),

    TileRow(140, hillyTundra, hillyTundra),

    VertRow(139, BendOut(9724, HVUp, 7, siceWin), BendMax(9726, HVUL, siceWin), Orig(9728, HVUR, 4, 2, siceWin), ThreeUp(9730, 0, 6, 6, siceWin),
      BendIn(9732, HVDL, 8, siceWin)),

    TileRow(138, tundra, hillyTundra),
    VertRow(137, BendMin(9730, HVDR, 3, siceWin), BendIn(9732, HVUL, 9, siceWin)),
    TileRow(136, lakesTundra * 2, hillyTundra),
    VertRow(135, BendOut(9728, HVDR, 6, siceWin), BendIn(9730, HVUL, 10, siceWin), BendIn(9732, HVDR, 6, siceWin)),
    TileRow(134, lakesTundra, siceWin, lakesTundra),
    VertRow(133, BendOut(9728, HVUR, 7, siceWin), ThreeDown(9730, 0, 7, 7, siceWin), BendOut(9732, HVUL, 7,siceWin)),
    TileRow(132, lakesTaiga, taiga, lakesTaiga),
    VertRow(131, BendIn(9730, HVUR, 13, siceWin), BendOut(9732, HVDL, 7, siceWin)),
    TileRow(130, lakesTaiga * 3, lakesTaiga),
    VertRow(129, BendIn(9732, HVUR, 13, siceWin), Orig(9734, HVUL, 1, 5, siceWin)),
    TileRow(128, lakesTaiga * 2, taiga, lakesTaiga),
    VertRow(127, OrigRt(9728, HVUR, 7, lake), BendIn(9730, HVDn, 13, lake), OrigMin(9732, HVUL, 3, lake)),
    TileRow(126, savannah, taiga, taiga, hillyTaiga),
    VertRow(125, Bend(9730, HVDR, 5, 2, lake), BendMin(9732, HVDn, 3, lake), OrigLt(9734, HVUL, 6, lake)),
    TileRow(124, hillySavannah, savannah, hillyOce, oceanic, oceanic),
    VertRow(123, OrigMin(9730, HVUp, 3, lake), OrigMin(9740, HVDn, 4)),
    TileRow(122, savannah * 3, mtainContForest, hillyOce),
    VertRow(121, OrigMin(9738, HVUR, 2), BendIn(9740, HVUL, 9)),
    TileRow(120, sahel, hillySavannah, savannah, mtainSubForest, hillySub),
    TileRow(118, deshot, savannah, oceanic * 3),
    VertRow(117, OrigRt(9730, HVDn), OrigMax(9736, HVDR),BendIn(9738, HVDL, 13)),
    TileRow(116, savannah, subtrop * 2, sea, jungle),

    VertRow(115, BendOut(9724, HVDR, 7), BendOut(9726, HVDn, 7), BendIn(9728, HVUp, 13), BendIn(9730, HVUL, 13), BendIn(9732, HVDR, 13), OrigMin(9734, HVDL, 5),
      OrigLt(9738, HVUp, 7)),

    TileRow(114, hillySahel, hillySavannah, sea * 2, lakesJungle),

    VertRow(113, BendOut(9722, HVDR, 7), BendIn(9724, HVUL, 13), BendIn(9726, HVDR, 13), BendIn(9728, HVDn, 13), OrigLt(9730, HVUL, 7), BendIn(9732, HVUR, 13),
      BendIn(9734, HVUp, 13), Bend(9736, HVDn, 4, 7), BendMin(9738, HVUp, 5), BendIn(9740, HVDn, 13), BendIn(9742, HVDL, 13)),

    TileRow(112, hillySavannah, sea, savannah, sea, hillyJungle, jungle),

    VertRow(111, BendOut(9716, HVDL), OrigRt(9724, HVUR), BendOut(9726, HVUL, 7), OrigRt(9732, HVDn, 7), OrigLt(9734, HVDR, 7), ThreeDown(9736, 13, 12, 0),
      Bend(9738, HVDn, 12, 5), ThreeDown(9740, 6, 6, 12), ThreeUp(9742, 0, 6, 6)),

    TileRow(110, mtainJungle, hillyJungle, savannah, jungle, sea, hillyJungle),

    VertRow(109, BendIn(9716, HVUR, 10), OrigRt(9718, HVUL, 7), OrigLt(9722, HVDn, 7), OrigLt(9732, HVUp, 7), BendIn(9736, HVUR, 12), BendIn(9738, HVUp, 12),
      BendIn(9740, HVUL, 12)), TileRow(108, sea * 2, mtainJungle * 2, hillyJungle),

    VertRow(107, BendIn(9722, HVUR, 13), BendIn(9724, HVUp, 13), BendOut(9726, HVDn, 7), OrigRt(9728, HVUL, 7), BendInLt(9736, HVDR, 13, 7),
      BendIn(9738, HVDn, 13), ThreeDown(9740, 0, 13, 13), OrigLt(9742, HVDL, 7)),

    TileRow(106, sea * 4, mtainJungle, hillyJungle, hillyJungle),
    VertRow(105, OrigMin(9734, HVDn), Orig(9736, HVUp, 3, 7), OrigMin(9738, HVDn), Orig(9740, HVUp, 5, 7)),
    TileRow(104, sea * 5, mtainJungle, hillyJungle),
    VertRow(103, BendIn(9734, HVUR, 10), BendIn(9736, HVUp, 10), ThreeUp(9738, 13, 0, 9), OrigMin(9740, HVUL)),
    TileRow(102, sea * 6, mtainJungle),
    TileRow(100, sea * 6, hillyJungle),
    TileRow(98, sea * 5, mtainJungle, jungle),
    TileRow(96, sea * 6, mtainSavannah),
    TileRow(94, sea * 6, mtainSahel),
    VertRow(79, BendIn(9736, HVDR, 13), BendOut(9738, HVUL, 7)),
    TileRow(78, sea * 4, hillyOce),
    VertRow(77, BendIn(9736, HVUR, 13), OrigRt(9738, HVUL)),
    VertRow(73, OrigLt(9734, HVDn, 7)),
    TileRow(72, sea * 3, mtainOce),
    VertRow(71, BendIn(9734, HVUR, 13), OrigMin(9736, HVUL, 5)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(112, "" * 4, "Cuba west", "Cuba east")
    str(110, "" * 5, "Jamaica")
  }
}