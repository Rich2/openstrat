/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** 460km [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile area 183250.975km².
 * [[Isle4]] 8768.845km² => 14495.438km². Jamaica 10,991 km². */
object Terr460W90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w90(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(147, OrigRt(9728, HVUR, 7, SeaIcePerm), BendIn(9730, HVDn, 13, SeaIcePerm), BendOut(9732, HVUp, 7, SeaIcePerm)),
    TRow(146, mtainIce),
    TRow(144, mtainTundra),

    VRow(143, Bend(9726, HVDn, 7, 2, SeaIcePerm), BendIn(9728, HVDL, 8, SeaIcePerm), Orig(9730, HVDR, 5, 1, SeaIcePerm),
      BendIn(9732, HVDL, 13, SeaIcePerm, siceWin)),

    TRow(142, hillyTundra),

    VRow(141, SetSep(9727, siceWin), BendIn(9728, HVUR, 6, SeaIcePerm), ThreeDown(9730, 10, 0, 6, siceWin, siceWin, SeaIcePerm),
      ThreeUp(9732, 0, 10, 12, siceWin), BendIn(9734, HVDL, 10, siceWin)),

    TRow(140, hillyTundra, hillyTundra),

    VRow(139, BendOut(9724, HVUp, 7, siceWin), BendMax(9726, HVUL, siceWin), Orig(9728, HVUR, 4, 2, siceWin), ThreeUp(9730, 0, 6, 6, siceWin),
      BendIn(9732, HVDL, 8, siceWin)),

    TRow(138, tundra, hillyTundra),
    VRow(137, BendMin(9730, HVDR, 3, siceWin), BendIn(9732, HVUL, 9, siceWin)),
    TRow(136, lakesTundra * 2, hillyTundra),
    VRow(135, BendOut(9728, HVDR, 6, siceWin), BendIn(9730, HVUL, 10, siceWin), BendIn(9732, HVDR, 6, siceWin)),
    TRow(134, lakesTundra, siceWin, lakesTundra),
    VRow(133, BendOut(9728, HVUR, 7, siceWin), ThreeDown(9730, 0, 7, 7, siceWin), BendOut(9732, HVUL, 7,siceWin)),
    TRow(132, lakesTaiga, taiga, lakesTaiga),
    VRow(131, BendIn(9730, HVUR, 13, siceWin), BendOut(9732, HVDL, 7, siceWin)),
    TRow(130, lakesTaiga * 3, lakesTaiga),
    VRow(129, BendIn(9732, HVUR, 13, siceWin), Orig(9734, HVUL, 1, 5, siceWin)),
    TRow(128, lakesTaiga * 2, taiga, lakesTaiga),
    VRow(127, OrigRt(9728, HVUR, 7, lake), BendIn(9730, HVDn, 13, lake), OrigMin(9732, HVUL, 3, lake)),
    TRow(126, savannah, taiga, taiga, hillyTaiga),
    VRow(125, Bend(9730, HVDR, 5, 2, lake), BendMin(9732, HVDn, 3, lake), OrigLt(9734, HVUL, 6, lake)),
    TRow(124, hillySavannah, savannah, hillyOce, oceanic, oceanic),
    VRow(123, OrigMin(9730, HVUp, 3, lake), OrigMin(9740, HVDn, 4)),
    TRow(122, savannah * 3, mtainDepr, hillyOce),
    VRow(121, OrigMin(9738, HVUR, 2), BendIn(9740, HVUL, 9)),
    TRow(120, sahel, hillySavannah, savannah, mtainDepr, hillyOce),
    TRow(118, deshot, savannah, oceanic * 3),
    VRow(117, OrigRt(9730, HVDn), OrigMax(9736, HVDR),BendIn(9738, HVDL, 13)),
    TRow(116, savannah, subtrop * 2, sea, jungle),

    VRow(115, BendOut(9724, HVDR, 7), BendOut(9726, HVDn, 7), BendIn(9728, HVUp, 13), BendIn(9730, HVUL, 13), BendIn(9732, HVDR, 13), OrigMin(9734, HVDL, 5),
      OrigLt(9738, HVUp, 7)),

    TRow(114, hillySahel, hillySavannah, sea * 2, lakesJungle),

    VRow(113, BendOut(9722, HVDR, 7), BendIn(9724, HVUL, 13), BendIn(9726, HVDR, 13), BendIn(9728, HVDn, 13), OrigLt(9730, HVUL, 7), BendIn(9732, HVUR, 13),
      BendIn(9734, HVUp, 13), Bend(9736, HVDn, 4, 7), BendMin(9738, HVUp, 5), BendIn(9740, HVDn, 13), BendIn(9742, HVDL, 13)),

    TRow(112, hillySavannah, sea, savannah, sea, hillyJungle, jungle),

    VRow(111, BendOut(9716, HVDL), OrigRt(9724, HVUR), BendOut(9726, HVUL, 7), OrigLt(9734, HVDR, 7), ThreeDown(9736, 13, 12, 0), Bend(9738, HVDn, 12, 5),
      ThreeDown(9740, 6, 6, 12), ThreeUp(9742, 0, 6, 6)),

    TRow(110, mtainJungle, hillyJungle, savannah, jungle, sea, hillyJungle),
    VRow(109, BendIn(9716, HVUR, 10), OrigRt(9718, HVUL, 7), OrigLt(9722, HVDn, 7), BendIn(9736, HVUR, 12), BendIn(9738, HVUp, 12), BendIn(9740, HVUL, 12)),
    TRow(108, sea * 2, mtainDepr * 2, hillyJungle),

    VRow(107, BendIn(9722, HVUR, 13), BendIn(9724, HVUp, 13), BendOut(9726, HVDn, 7), OrigRt(9728, HVUL, 7), OrigMin(9734, HVDR), BendIn(9736, HVDL, 10),
      BendIn(9740, HVDR, 10), BendIn(9742, HVDn, 10), BendOut(9744, HVUp)),

    TRow(106, sea * 4, mtainDepr, sea, hillyJungle),
    VRow(105, OrigMin(9734, HVDn), OrigMin(9736, HVUp), OrigMin(9740, HVUp)),
    TRow(104, sea * 5, mtainDepr, hillyJungle),
    VRow(103, BendIn(9734, HVUR, 10), BendIn(9736, HVUp, 10), BendOut(9738, HVDn, 7), OrigMin(9740, HVUL)),
    TRow(102, sea * 6, mtainDepr),
    TRow(100, sea * 6, hillyJungle),
    TRow(98, sea * 5, mtainDepr, jungle),
    TRow(96, sea * 6, mtainDepr),
    TRow(94, sea * 6, mtainDepr),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(112, "" * 4, "Cuba west", "Cuba east")
    str(110, "" * 5, "Jamaica")
  }
}