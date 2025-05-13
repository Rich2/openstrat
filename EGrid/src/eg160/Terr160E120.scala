/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** Terrain for 160km 120 west. The terrain here is only very rough first approximation. */
object Terr160E120 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e120(252, 272)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(272, deshot * 3, hillyDeshot * 3, hillyOce, deshot, oceanic * 2, hillyOce * 2, oceanic, hillyOceForest),
      TileRow(270, oceanic * 14, sea),
      TileRow(268, oceanic * 13, sea * 2),
      TileRow(266, oceanic * 12, sea * 3),
      TileRow(264, oceanic * 12, sea * 3),
      TileRow(262, oceanic * 7, sea * 4, hillyOce * 2, sea * 3),
      TileRow(260, oceanic * 9, sea, hillyOce * 3, sea * 3),
      TileRow(258, oceanic * 9, sea * 2, hillyOce * 2, sea * 3),
      TileRow(256, oceanic * 8, sea * 4, hillyOce * 2, sea, hillyOce * 2),
      TileRow(254, oceanic * 8, sea * 6, hillyOce * 3),
      TileRow(252, oceanic * 9, sea * 5, hillyOce * 2, sea),
    )
  }
  help.run
}