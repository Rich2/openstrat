/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° west to 75° wast, centred on 90° west. Hex tile scale of 320km. */
object Terr320W90 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w90(124)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(166, mtainIce),
    TRow(164, mtainIce),
    TRow(162, SeaIceWinter,  mtainIce),
    TRow(160, tundra),
    TRow(158, tundra, tundra, tundra),
    TRow(156, tundra, tundra, tundra),
    TRow(154, tundra * 3, siceWin),
    TRow(152, tundra * 2, Isle10(tundra, siceWin), tundra),
    TRow(150, taiga, tundra, siceWin * 2),
    VRow(149, Bend(9726, HVDR, 2, 4, siceWin), BendIn(9728, HVUL, 10, siceWin), SourceLt(9734, HVDn, 7, siceWin)),
    TRow(148, taiga, tundra, siceWin * 2, lakesTundra),

    VRow(147, BendOut(9726, HVUR, 7, siceWin), BendOut(9728, HVUp, 7, siceWin), BendIn(9730, HVDn, 13, siceWin), BendIn(9732, HVDL, 13, siceWin),
      BendIn(9734, HVUR, 13, siceWin), BendMin(9736, HVDL, siceWin)),

    TRow(146, taiga * 2, tundra, siceWin, taiga),
    VRow(145, BendOut(9732, HVUR, 7, siceWin), ThreeDown(9734, 2, 13, 4, siceWin), BendOut(9736, HVUL, 7, siceWin)),
    TRow(144, taiga * 3, taiga, taiga),
    VRow(143, SourceLt(9720, HVDn, 6, Lake), BendIn(9734, HVUR, 13, siceWin), Bend(9736, HVDL, 9, 4, siceWin)),
    TRow(142, taiga * 4, taiga, taiga),
    VRow(141, BendIn(9720, HVUR, 7, Lake), MouthOld(9722, HVDR, 3, Lake), SourceRt(9736, HVUp, 7, siceWin)),
    TRow(140, oceanic, taiga * 5),
    VRow(139, BendIn(9728, HVDR, 13, lake), BendIn(9730, HVDn, 13, lake), BendIn(9732, HVDL, 13, Lake)),
    TRow(138, oceanic * 2, taiga, continental, taiga * 3),
    VRow(137, SourceLt(9728, HVUp, 6, Lake), SourceLt(9730, HVDn, 6, lake), SourceMax(9732, HVUp, lake), Source(9734, HVDn, 2, 6, lake)),
    TRow(136, continental * 5, lakesContForest * 2),

    VRow(135, BendIn(9730, HVUR, 13, Lake), BendIn(9732, HVDL, 13, lake), BendIn(9734, HVUR, 13, lake), Bend(9736, HVUp, 13, 2, lake),
      SourceRt(9738, HVDL, 7, lake), SourceLt(9740, HVUR, 7, lake), SourceRt(9742, HVDL, 6, lake)),

    TRow(134, oceanic * 6, hillyOceForest),
    VRow(133, SourceLt(9732, HVUp, 7, lake), Source(9736, HVDR, 4, 3, lake), BendIn(9738, HVUp, 13, lake),SourceRt(9740, HVDL, 7, lake)),
    TRow(132, deshot, oceanic * 4, hillyOceForest, hillyOce),
    TRow(130, deshot, oceanic * 5, hillyOce, oceanic),
    TRow(128, deshot, oceanic * 3, hillyOce * 2, oceanic, sea),
    TRow(126, deshot, oceanic * 6, sea),
    TRow(124, deshot, oceanic * 5, jungle, sea * 2),
    )
  }
  help.run
}