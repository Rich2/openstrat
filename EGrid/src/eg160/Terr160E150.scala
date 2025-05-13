/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** Terrain for 160km 120 west. Created elevations, but may need forests. */
object Terr160E150 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.e150(252, 272)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(272, sea * 2, hillyOce, sea * 11),
      TileRow(270, sea * 2, hillyOce * 3, sea * 10),
      TileRow(268, sea * 2, hillyOce * 3, sea * 10),
      TileRow(266, sea * 2, hillyOce, sea * 12),
      TileRow(264, sea * 2, hillyOce, sea * 12),
      TileRow(262, sea * 2, hillyOce * 2, sea * 12),
      TileRow(260, hillyOce * 3, sea * 13),
      TileRow(258, hillyOce * 3, sea * 13),
      TileRow(256, hillyOce * 3, sea * 14),
    )
  }
  help.run
}