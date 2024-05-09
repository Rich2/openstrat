/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 640km.  */
object Terr640W90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w90(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(131, OrigLt(9730, HVDR, 7, SeaIcePerm), BendIn(9732, HVDL, 13, SeaIcePerm)),
    TRow(130, hillyTundra),

    VRow(129, ThreeDown(9726, 13, 6, 0, siceWin), ThreeUp(9728, 6, 0, 13, SeaIcePerm), ThreeDown(9730, 13, 13, 6, SeaIcePerm, siceWin, SeaIcePerm),
      BendIn(9732, HVUL, 13, SeaIcePerm)),

    TRow(128, tundra),
    VRow(127, OrigRt(9726, HVUp, 7, siceWin), BendIn(9728, HVDR, 8, siceWin), BendIn(9730, HVUL, 6, siceWin)),
    TRow(126, tundra, tundra),
    VRow(125, Bend(9728, HVUR, 5, 1, siceWin), ThreeDown(9730, 3, 13, 13, siceWin), Bend(9732, HVDn, 4, 5, siceWin), BendMin(9734, HVUp, 3, siceWin)),
    TRow(124, taiga, tundra),
    VRow(123, OrigMax(9730, HVUp, siceWin)),
    TRow(122, taiga * 2),
    TRow(120, taiga * 3),
    VRow(119, OrigLt(9728, HVUR, 6, Lake), OrigMin(9730, HVDL, 3, Lake)),
    TRow(118, steppe, lakesContForest * 2),
    TRow(116, steppe * 2, hillyCont),
    TRow(114, sahel, savannah, oceanic, oceanic),
    TRow(112, savannah, oceanic, jungle, sea),
    VRow(111, Bend(9724, HVDR, 5, 1), BendOut(9726, HVDn, 7), BendIn(9728, HVUp, 13), BendOut(9730, HVDn, 7), BendOut(9732, HVDL, 7), BendIn(9736, HVDL, 13)),
    TRow(110, sahel, sea * 2, jungle),

    VRow(109, Bend(9724, HVUR, 2, 4), BendOut(9726, HVUp, 7), OrigLt(9728, HVDL), BendIn(9730, HVDR, 13), ThreeUp(9732, 13, 13, 0), Bend(9734, HVUp, 13, 3),
      ThreeUp(9736, 0, 13, 13), BendIn(9738, HVDL, 6)),

    TRow(108, hillySavannah, jungle, hillyJungle * 2),
    VRow(107, Bend(9730, HVUR, 13, 3), BendIn(9732, HVUp, 13), BendOut(9734, HVDn), BendIn(9736, HVUp, 13), BendIn(9738, HVUL, 7)),
    TRow(106, sea, hillyJungle * 2),
    VRow(105, OrigLt(9724, HVDR, 7), BendIn(9726, HVUp, 13), Orig(9728, HVDL, 4, 2), OrigRt(9734, HVUR, 7), BendIn(9736, HVDn, 13), BendOut(9738, HVUp, 7)),
    TRow(104, sea * 3, hillyJungle, hillyJungle),
    VRow(103, OrigLt(9730, HVDR, 7), OrigRt(9732, HVUL, 7), Orig(9736, HVDn, 3, 3)),
    TRow(102, sea * 4, mtainDepr),
    VRow(101, BendIn(9734, HVDR), BendMin(9736, HVUL, 5)),
    TRow(100, sea * 4, mtainDepr),
    VRow(99, BendIn(9732, HVDR, 13), BendMin(9734, HVUL, 4)),
    TRow(98, sea * 3, hillySavannah, jungle),
    VRow(97, BendIn(9732, HVUR, 13), BendOut(9734, HVDL, 7)),
    TRow(96, sea * 4, mtainSavannah),
    VRow(95, BendIn(9734, HVUR, 13), BendOut(9736, HVDL, 7)),
    TRow(94, sea * 4, mtainSahel),
    VRow(93, BendIn(9736, HVUR, 13), BendIn(9738, HVDL)),
    VRow(91, BendMin(9738, HVUL, 2)),
    VRow(89, BendIn(9736, HVUR, 13), OrigMin(9738, HVUL, 5)),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(108, "" * 2, "Cuba west", "Cuba east")
  }
}