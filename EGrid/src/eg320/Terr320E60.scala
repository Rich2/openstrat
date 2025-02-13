/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° east to 75° east, centred on 60° east. Hex tile scale of 320km. */
object Terr320E60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e60(118)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(166, SeaIcePerm),
    TileRow(164, SepB(SeaIceWinter), SeaIceWinter),
    TileRow(162, SeaIceWinter * 2),
    TileRow(160, tundra, SeaIceWinter),
    TileRow(158, hillyTundra, SeaIceWinter, tundra),
    TileRow(156, SeaIceWinter * 2, tundra),
    VertRow(155, SetSep(2553, SeaIceWinter)),
    TileRow(154, tundra * 4),
    VertRow(153, BendIn(2552, HVUL, 9, SeaIceWinter)),
    TileRow(152, taiga * 4),
    TileRow(150, taiga * 4),
    TileRow(148, oceForest, taiga * 4),
    TileRow(146, oceForest * 2, taiga * 3),
    TileRow(144, oceForest * 5),
    TileRow(142, oceanic * 6),
    TileRow(140, oceanic * 2, deshot * 3, oceanic),
    TileRow(138, oceanic, deshot * 6),
    VertRow(137, Bend(2550, HVDR, 10, 7, lake), Orig(2552, HVDL, 7, 4, Lake)),
    TileRow(136, sahel, sahel, deshot * 5),
    VertRow(135, Bend(2550, HVUR, 2, 7, lake), BendIn(2552, HVDL, 13, lake)),
    TileRow(134, mtainSavannah, deshot * 5, mtainDepr),
    VertRow(133, Bend(2550, HVDR, 13, 2, lake), BendMax(2552, HVUL, lake)),
    TileRow(132, hillySavannah, sahel, deshot * 3, mtainDepr * 2),
    VertRow(131, BendMax(2550, HVUR, lake), BendMax(2552, HVDL, lake)),
    TileRow(130, mtainSahel, hillySavannah, deshot, deshot * 3, mtainDepr * 2),
    VertRow(129, OrigMin(2552, HVUp, 3, Lake)),
    TileRow(128, hillyDeshot, deshot * 5, mtainDepr * 2),
    TileRow(126, sahel, hillyDeshot, deshot * 5, oceanic),
    TileRow(124, deshot, sahel, hillyDeshot, deshot * 4, oceanic * 2),
    VertRow(123, OrigLt(2546, HVDR), Bend(2548, HVDL, 9, 7)),
    TileRow(122, deshot, hillyDeshot, hillyDeshot, hillySahel, deshot * 2, oceanic, deshot * 2),
    VertRow(121, Bend(2548, HVUR, 13, 1), Bend(2550, HVUp, 13, 2), BendIn(2552, HVDn, 13), Bend(2554, HVUp, 5, 4), BendMin(2556, HVDn), BendIn(2558, HVDL, 13)),
    TileRow(120, deshot * 3, hillyDeshot, hillySahel * 2, hillyDeshot, oceanic, deshot),

    VertRow(119, BendMax(2558, HVUR), BendInRt(2560, HVUp, 13, 7), BendOut(2562, HVDn, 7), BendIn(2564, HVUp, 13), BendOut(2566, HVDn, 7), BendIn(2568, HVUp, 13),
      OrigLt(2570, HVDL, 7)),

    TileRow(118, deshot * 4, sea * 3, oceanic * 2),
    )
  }

  help.run

  { import hexNames.{setRow => str}
    str(158, "Severny south")
    str(160, "Severny north")
  }

}