/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° west to 105° wast, centred on 120° west. Hex tile scale of 320km. */
object Terr320W120 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w120(122)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(166, SeaIcePerm),
    TRow(164, SeaIcePerm),
    VRow(163, SourceRt(8704, HVUR, 7, SeaIcePerm)),
    TRow(162, SeaIceWinter, tundra),
    TRow(160, tundra * 2),
    TRow(158, tundra, tundra * 2),
    VRow(157, BendOut(8698, HVUp, 7, siceWin)),
    TRow(156, tundra, tundra, tundra),
    TRow(154, tundra * 4),
    VRow(153, MouthOld(8702, HVDL, 3, Lake), MouthOld(8704, HVUR, 3, Lake)),
    TRow(152, taiga * 3, tundra),
    VRow(151, SourceLt(8708, HVDn, 6, Lake)),
    TRow(150, mtainOld, taiga * 3),
    VRow(149, SourceMin(8706, HVUR, Lake), BendMin(8708, HVUL, 3, Lake)),
    TRow(148, hillyTaiga * 2, taiga * 3),
    TRow(146, mtainOld, taiga * 4),
    TRow(144, mtainOld, taiga * 4),
    TRow(142, sea, mtainOld * 2, oceanic * 3),
    TRow(140, sea, mtainOld * 2, oceanic * 3),
    TRow(138, sea * 2, mtainOld * 2, oceanic * 3),
    TRow(136, sea * 2, hillyOceForest, hillyOce, mtainOld * 2, hillyDeshot),
    TRow(134, sea * 2, hillyOce, hillyDeshot, deshot, hillyDeshot * 2),
    TRow(132, sea * 2, hillyOce * 2, hillyDeshot * 2, mtainOld),
    TRow(130, sea * 3, hillyOce, hillyOce, hillyDeshot * 2, mtainOld),
    TRow(128, sea * 3, hillyOce, hillyDeshot * 2, deshot, hillyDeshot),
    TRow(126, sea * 5, hillyOce, hillyDeshot, deshot),
    TRow(124, sea * 6, hillyOce, hillyOce, hillyDeshot),
    )
  }

  help.run
}