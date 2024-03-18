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
  {
    override val rows: RArr[RowBase] = RArr(
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
      VRow(137, BendMax(2550, HVDR, lake), Source(2552, HVDL, 7, 4, Lake)),
      TRow(136, sahel, sahel, deshot * 5),
      VRow(135, SourceLt(2550, HVUp, 7, lake)),
      TRow(134, Lake, deshot * 5, mtainOld),
      TRow(132, mtainOld, Lake, deshot * 3, mtainOld * 2),
      TRow(130, mtainOld, CapeOld(1, 1, mtainOld, Lake), CapeOld(5, 1, deshot, Lake), deshot * 3, mtainOld * 2),
      VRow(129, MouthOld(2552, HVDn, 3, Lake)),
      TRow(128, hillyDeshot, deshot * 5, mtainOld * 2),
      TRow(126, deshot, mtainOld, deshot * 5, oceanic),
      TRow(124, deshot, oceanic, mtainOld, deshot * 4, oceanic * 2),
      VRow(123, MouthOld(2546, HVUL)),
      TRow(122, CapeOld(1, 1, deshot), CapeOld(3, 2, hillyDeshot), CapeOld(3, 1, mtainOld), deshot * 3, oceanic, deshot * 2),
      TRow(120, deshot * 2, sea, CapeOld(0, 2, hillyDeshot), sea * 2, hillyDeshot, oceanic, deshot),
      TRow(118, deshot * 4, sea * 3, oceanic * 2),
    )
  }

  help.run
}