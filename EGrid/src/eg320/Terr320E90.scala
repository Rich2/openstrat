/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale of 320km. */
object Terr320E90 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e90(124)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(166, SeaIcePerm),
      TileRow(164, ice),
      VertRow(163, BendIn(3584, HVDR, 13, siceWin)),
      TileRow(162, SeaIceWinter, tundra),
      VertRow(161, BendOut(3584, HVUL, 7, siceWin)),
      TileRow(160, hillyTundra * 2),
      TileRow(158, tundra * 3),
      TileRow(156, taiga, hillyTaiga * 2),
      TileRow(154, taiga * 2, hillyTaiga, taiga),
      TileRow(152, taiga * 4),
      TileRow(150, taiga * 4),
      TileRow(148, taiga * 5),
      TileRow(146, taiga * 5),
      TileRow(144, taiga, oceanic * 4),
      TileRow(142, deshot, oceanic, hillyOce * 3, mtainDepr),
      TileRow(140, oceanic, mtainDepr, hillyDeshot, deshot, mtainDepr, hillyOce),
      TileRow(138, deshot * 3, hillyDeshot, deshot, hillyDeshot, deshot),
      TileRow(136, mtainDepr * 2, deshot * 5),
      TileRow(134, mtainDepr * 3, deshot * 4),
      TileRow(132, deshot * 7),
      TileRow(130, deshot * 3, hillyDeshot * 5),
      TileRow(128, hillyDeshot * 6, hillyOce, mtainDepr),
      TileRow(126, mtainDepr, hillyDeshot * 4, mtainDepr * 3),
      TileRow(124, oceanic, mtainDepr, hillyDeshot * 2, mtainDepr * 4, hillyOce),
    )
  }
  help.run
}