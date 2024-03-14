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
  { override val rowDatas: RArr[RowBase] = RArr(
    TRow(166, mtainIce),
    TRow(164, mtainIce),
    TRow(162, SeaIceWinter,  mtainIce),
    TRow(160, Isle10(tundra), SeaIceWinter),
    VRow(156, SetSep(9722)),
    TRow(158, CapeOld(4, 4, tundra, SeaIceWinter), CapeOld(4, 1, tundra, SeaIceWinter), CapeOld(0, 2, tundra, SeaIceWinter)),
    TRow(156, siceWin, CapeOld(1, 1, tundra), CapeOld(3, 2, tundra)),
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
    VRow(139, MouthOld(9732, HVDR, 3, Lake)),
    TRow(138, oceanic * 2, taiga, CapeOld(5, 2, oceanic, Lake), taiga * 3),
    VRow(137, MouthOld(9728, HVDn, 3, Lake), BendAllOld(9730, HVDR, Lake), MouthOld(9732, HVUR, 3, Lake)),
    TRow(136, continental * 5, lake, Land(PlainLakes, Continental, Forest)),
    VRow(135, BendAllOld(9730, HVUR, Lake), MouthOld(9732, HVDR, 3, Lake)),
    TRow(134, oceanic * 6, hillyOceForest),
    TRow(132, deshot, oceanic * 4, hillyOceForest, hillyOce),
    TRow(130, deshot, oceanic * 5, hillyOce, oceanic),
    TRow(128, deshot, oceanic * 3, hillyOce * 2, oceanic, sea),
    TRow(126, deshot, oceanic * 6, sea),
    TRow(124, deshot, oceanic * 5, jungle, sea * 2),
    )
  }
  help.run
}