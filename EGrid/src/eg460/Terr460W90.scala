/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 460km.
 * Isle10 120974.276km² <= 57981.753km² Cuba. */
object Terr460W90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w90(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(147, SourceRt(9728, HVUR, 7, SeaIcePerm), BendIn(9730, HVDn, 13, SeaIcePerm), BendOut(9732, HVUp, 7, SeaIcePerm)),
    TRow(146, mtainIce),
    TRow(144, mtainTundra),

    VRow(143, Bend(9726, HVDn, 7, 2, SeaIcePerm), BendIn(9728, HVDL, 8, SeaIcePerm), Source(9730, HVDR, 5, 1, SeaIcePerm),
      BendIn(9732, HVDL, 13, SeaIcePerm, siceWin)),

    TRow(142, hillyTundra),

    VRow(141, SetSep(9727, siceWin), BendIn(9728, HVUR, 6, SeaIcePerm), ThreeDown(9730, 10, 0, 6, siceWin, siceWin, SeaIcePerm),
      ThreeUp(9732, 0, 10, 12, siceWin), BendIn(9734, HVDL, 10, siceWin)),

    TRow(140, hillyTundra, hillyTundra),

    VRow(139, BendOut(9724, HVUp, 7, siceWin), BendMax(9726, HVUL, siceWin), Source(9728, HVUR, 4, 2, siceWin), ThreeUp(9730, 0, 6, 6, siceWin),
      BendIn(9732, HVDL, 8, siceWin)),

    TRow(138, tundra, hillyTundra),
    VRow(137, BendMin(9730, HVDR, siceWin), BendIn(9732, HVUL, 9, siceWin)),
    TRow(136, lakesTundra * 2, hillyTundra),
    VRow(135, BendOut(9728, HVDR, 6, siceWin), BendIn(9730, HVUL, 10, siceWin), BendIn(9732, HVDR, 6, siceWin)),
    TRow(134, lakesTundra, siceWin, lakesTundra),
    VRow(133, BendOut(9728, HVUR, 7, siceWin), ThreeDown(9730, 0, 7, 7, siceWin), BendOut(9732, HVUL, 7,siceWin)),
    TRow(132, lakesTaiga, taiga, lakesTaiga),
    VRow(131, BendIn(9730, HVUR, 13, siceWin), BendOut(9732, HVDL, 7, siceWin)),
    TRow(130, lakesTaiga * 3, lakesTaiga),
    VRow(129, BendIn(9732, HVUR, 13, siceWin), Source(9734, HVUL, 1, 5, siceWin)),
    TRow(128, lakesTaiga * 2, taiga, lakesTaiga),
    VRow(127, SourceRt(9728, HVUR, 7, lake), BendIn(9730, HVDn, 13, lake), MouthOld(9732, HVDR, 3, lake)),
    TRow(126, savannah, taiga, taiga, hillyTaiga),
    VRow(125, Bend(9730, HVDR, 5, 2, lake), BendMin(9732, HVDn, lake), SourceLt(9734, HVUL, 6, lake)),
    TRow(124, hillySavannah, savannah, hillyOce, oceanic, oceanic),
    VRow(123, MouthOld(9730, HVDn, 3, lake)),
    TRow(122, savannah * 3, mtainOld, hillyOce),
    TRow(120, sahel, hillySavannah, savannah, mtainOld, hillyOce),
    TRow(118, deshot, savannah, oceanic * 3),
    VRow(117, MouthOld(9730, HVUp, 3, lake), MouthOld(9736, HVUL)),
    TRow(116, savannah, oceanic, CapeOld(2, 2), sea, CapeOld(1, 2, jungle)),
    VRow(115, BendOut(9724, HVDR), BendOut(9726, HVDn)),
    TRow(114, hillySahel, CapeOld(2, 1, hillySavannah)),
    VRow(113, BendOut(9722, HVDR), MouthOld(9730, HVDR), BendOut(9738, HVUp)),
    TRow(112, hillySavannah, sea, CapeOld(5, 2, savannah), sea, CapeOld(3, 4, hillyJungle), CapeOld(0, 4, jungle)),
    VRow(111, BendOut(9716, HVDL), MouthOld(9724, HVDL), BendOut(9726, HVUL), BendOut(9738, HVDn)),
    TRow(110, mtainOld, hillyJungle, savannah, jungle),
    VRow(109, BendIn(9716, HVUR, 13), MouthRt(9718, HVDR), MouthLt(9722, HVUp, 7)),
    TRow(108, sea * 2, mtainOld * 2, hillyJungle),

    VRow(107, BendIn(9722, HVUR, 13), BendIn(9724, HVUp, 13), BendOut(9726, HVDn, 7), MouthRt(9728, HVDR, 7), MouthOld(9734, HVUL), BendIn(9736, HVDL, 10),
      BendIn(9740, HVDR, 10), BendIn(9742, HVDn, 10), BendOut(9744, HVUp)),

    TRow(106, sea * 4, mtainOld, sea, hillyJungle),
    VRow(105, MouthOld(9734, HVUp), MouthOld(9736, HVDn), MouthOld(9740, HVDn)),
    TRow(104, sea * 5, mtainOld, hillyJungle),
    VRow(103, BendIn(9734, HVUR, 10), BendIn(9736, HVUp, 10), BendOut(9738, HVDn, 7), MouthOld(9740, HVDR)),
    TRow(102, sea * 6, mtainOld),
    TRow(100, sea * 6, hillyJungle),
    TRow(98, sea * 5, mtainOld, jungle),
    TRow(96, sea * 6, mtainOld),
    TRow(94, sea * 6, mtainOld),
    )
  }
  help.run
}