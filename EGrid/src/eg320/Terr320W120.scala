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
  { override val rows: RArr[DateRow] = RArr(
    TileRow(166, SeaIcePerm),
    TileRow(164, SeaIcePerm),
    VertRow(163, OrigRt(8704, HVUR, 7, SeaIcePerm)),
    TileRow(162, SeaIceWinter, tundra),
    TileRow(160, tundra * 2),
    TileRow(158, tundra, tundra * 2),
    VertRow(157, BendOut(8698, HVUp, 7, siceWin)),
    TileRow(156, tundra, tundra, tundra),
    TileRow(154, tundra * 4),
    VertRow(153, OrigMin(8702, HVUR, 3, Lake), OrigMin(8704, HVDL, 3, Lake)),
    TileRow(152, taiga * 3, tundra),
    VertRow(151, OrigLt(8708, HVDn, 6, Lake)),
    TileRow(150, mtainDepr, taiga * 3),
    VertRow(149, OrigMin(8706, HVUR, 3, Lake), BendMin(8708, HVUL, 3, Lake)),
    TileRow(148, hillyTaiga * 2, taiga * 3),
    TileRow(146, mtainDepr, taiga * 4),
    TileRow(144, mtainDepr, taiga * 4),
    TileRow(142, sea, mtainDepr * 2, oceanic * 3),
    TileRow(140, sea, mtainDepr * 2, oceanic * 3),
    TileRow(138, sea * 2, mtainDepr * 2, oceanic * 3),
    TileRow(136, sea * 2, hillyOceForest, hillyOce, mtainDepr * 2, hillyDeshot),
    TileRow(134, sea * 2, hillyOce, hillyDeshot, deshot, hillyDeshot * 2),
    TileRow(132, sea * 2, hillyOce * 2, hillyDeshot * 2, mtainDepr),
    TileRow(130, sea * 3, hillyOce, hillyOce, hillyDeshot * 2, mtainDepr),
    TileRow(128, sea * 3, hillyOce, hillyDeshot * 2, deshot, hillyDeshot),
    TileRow(126, sea * 5, hillyOce, hillyDeshot, deshot),
    TileRow(124, sea * 6, hillyOce, hillyOce, hillyDeshot),
    )
  }

  help.run
}