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
  { override val rows: RArr[RowBase] = RArr(
    TRow(166, SeaIcePerm),
    TRow(164, SeaIceWinter),
    TRow(162, SeaIceWinter * 2),
    TRow(160, tundra, SeaIceWinter),
    TRow(158, hillyTundra, SeaIceWinter, tundra),
    TRow(156, SeaIceWinter * 2, tundra),
    VRow(155, SetSep(2553, SeaIceWinter)),
    TRow(154, tundra * 4),
    VRow(153, BendIn(2552, HVUL, 9, SeaIceWinter)),
    TRow(152, taiga * 4),
    TRow(150, taiga * 4),
    TRow(148, oceForest, taiga * 4),
    TRow(146, oceForest * 2, taiga * 3),
    TRow(144, oceForest * 5),
    TRow(142, oceanic * 6),
    TRow(140, oceanic * 2, deshot * 3, oceanic),
    TRow(138, oceanic, deshot * 6),
    VRow(137, Bend(2550, HVDR, 10, 7, lake), Source(2552, HVDL, 7, 4, Lake)),
    TRow(136, sahel, sahel, deshot * 5),
    VRow(135, Bend(2550, HVUR, 2, 7, lake), BendIn(2552, HVDL, 13, lake)),
    TRow(134, mtainSavannah, deshot * 5, mtainDepr),
    VRow(133, Bend(2550, HVDR, 13, 2, lake), BendMax(2552, HVUL, lake)),
    TRow(132, hillySavannah, sahel, deshot * 3, mtainDepr * 2),
    VRow(131, BendMax(2550, HVUR, lake), BendMax(2552, HVDL, lake)),
    TRow(130, mtainSahel, hillySavannah, deshot, deshot * 3, mtainDepr * 2),
    VRow(129, SourceMin(2552, HVUp, 3, Lake)),
    TRow(128, hillyDeshot, deshot * 5, mtainDepr * 2),
    TRow(126, sahel, hillyDeshot, deshot * 5, oceanic),
    TRow(124, deshot, sahel, hillyDeshot, deshot * 4, oceanic * 2),
    VRow(123, SourceLt(2546, HVDR), Bend(2548, HVDL, 9, 7)),
    TRow(122, deshot, hillyDeshot, hillyDeshot, hillySahel, deshot * 2, oceanic, deshot * 2),
    VRow(121, Bend(2548, HVUR, 13, 1), Bend(2550, HVUp, 13, 2), BendIn(2552, HVDn, 13), Bend(2554, HVUp, 5, 4), BendMin(2556, HVDn), BendIn(2558, HVDL, 13)),
    TRow(120, deshot * 3, hillyDeshot, hillySahel * 2, hillyDeshot, oceanic, deshot),

    VRow(119, BendMax(2558, HVUR), BendInRt(2560, HVUp, 13, 7), BendOut(2562, HVDn, 7), BendIn(2564, HVUp, 13), BendOut(2566, HVDn, 7), BendIn(2568, HVUp, 13),
      SourceLt(2570, HVDL, 7)),

    TRow(118, deshot * 4, sea * 3, oceanic * 2),
    )
  }

  help.run
}