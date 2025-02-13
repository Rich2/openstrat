/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
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
  { override val rows: RArr[DateRow] = RArr(
    TileRow(166, mtainIce),
    TileRow(164, mtainIce),
    TileRow(162, SeaIceWinter,  mtainIce),
    TileRow(160, tundra),
    TileRow(158, tundra, tundra, tundra),
    TileRow(156, tundra, tundra, tundra),
    TileRow(154, tundra * 3, siceWin),
    TileRow(152, tundra * 2, Isle10(tundra, siceWin), tundra),
    TileRow(150, taiga, tundra, siceWin * 2),
    VertRow(149, Bend(9726, HVDR, 2, 4, siceWin), BendIn(9728, HVUL, 10, siceWin), OrigLt(9734, HVDn, 7, siceWin)),
    TileRow(148, taiga, tundra, siceWin * 2, lakesTundra),

    VertRow(147, BendOut(9726, HVUR, 7, siceWin), BendOut(9728, HVUp, 7, siceWin), BendIn(9730, HVDn, 13, siceWin), BendIn(9732, HVDL, 13, siceWin),
      BendIn(9734, HVUR, 13, siceWin), BendMin(9736, HVDL, 3, siceWin)),

    TileRow(146, taiga * 2, tundra, siceWin, taiga),
    VertRow(145, BendOut(9732, HVUR, 7, siceWin), ThreeDown(9734, 2, 13, 4, siceWin), BendOut(9736, HVUL, 7, siceWin)),
    TileRow(144, taiga * 3, taiga, taiga),
    VertRow(143, OrigLt(9720, HVDn, 6, Lake), BendIn(9734, HVUR, 13, siceWin), Bend(9736, HVDL, 9, 4, siceWin)),
    TileRow(142, taiga * 4, taiga, taiga),
    VertRow(141, BendIn(9720, HVUR, 7, Lake), OrigMin(9722, HVUL, 3, Lake), OrigRt(9736, HVUp, 7, siceWin)),
    TileRow(140, oceanic, taiga * 5),
    VertRow(139, BendIn(9728, HVDR, 13, lake), BendIn(9730, HVDn, 13, lake), BendIn(9732, HVDL, 13, Lake)),
    TileRow(138, oceanic * 2, taiga, continental, taiga * 3),
    VertRow(137, OrigLt(9728, HVUp, 6, Lake), OrigLt(9730, HVDn, 6, lake), OrigMax(9732, HVUp, lake), Orig(9734, HVDn, 2, 6, lake)),
    TileRow(136, continental * 5, lakesContForest * 2),

    VertRow(135, BendIn(9730, HVUR, 13, Lake), BendIn(9732, HVDL, 13, lake), BendIn(9734, HVUR, 13, lake), Bend(9736, HVUp, 13, 2, lake),
      OrigRt(9738, HVDL, 7, lake), OrigLt(9740, HVUR, 7, lake), OrigRt(9742, HVDL, 6, lake)),

    TileRow(134, oceanic * 6, hillyOceForest),
    VertRow(133, OrigLt(9732, HVUp, 7, lake), Orig(9736, HVDR, 4, 3, lake), BendIn(9738, HVUp, 13, lake),OrigRt(9740, HVDL, 7, lake)),
    TileRow(132, deshot, oceanic * 4, hillyOceForest, hillyOce),
    TileRow(130, deshot, oceanic * 5, hillyOce, oceanic),
    TileRow(128, deshot, oceanic * 3, hillyOce * 2, oceanic, sea),
    TileRow(126, deshot, oceanic * 6, sea),
    TileRow(124, deshot, oceanic * 5, jungle, sea * 2),
    )
  }
  help.run
}