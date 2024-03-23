/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale of 320km. */
object Terr320E90 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e90(124)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(166, SeaIcePerm),
      TRow(164, ice),
      VRow(163, BendIn(3584, HVDR, 13, siceWin)),
      TRow(162, SeaIceWinter, tundra),
      VRow(161, BendOut(3584, HVUL, 7, siceWin)),
      TRow(160, hillyTundra * 2),
      TRow(158, tundra * 3),
      TRow(156, taiga, hillyTaiga * 2),
      TRow(154, taiga * 2, hillyTaiga, taiga),
      TRow(152, taiga * 4),
      TRow(150, taiga * 4),
      TRow(148, taiga * 5),
      TRow(146, taiga * 5),
      TRow(144, taiga, oceanic * 4),
      TRow(142, deshot, oceanic, hillyOce * 3, mtainOld),
      TRow(140, oceanic, mtainOld, hillyDeshot, deshot, mtainOld, hillyOce),
      TRow(138, deshot * 3, hillyDeshot, deshot, hillyDeshot, deshot),
      TRow(136, mtainOld * 2, deshot * 5),
      TRow(134, mtainOld * 3, deshot * 4),
      TRow(132, deshot * 7),
      TRow(130, deshot * 3, hillyDeshot * 5),
      TRow(128, hillyDeshot * 6, hillyOce, mtainOld),
      TRow(126, mtainOld, hillyDeshot * 4, mtainOld * 3),
      TRow(124, oceanic, mtainOld, hillyDeshot * 2, mtainOld * 4, hillyOce),
    )
  }
  help.run
}