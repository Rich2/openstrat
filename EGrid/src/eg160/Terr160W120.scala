/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** Terrain for 160km 120 west. */
object Terr160W120 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w120(312, 322)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners) {
    override val rows: RArr[DateRow] = RArr(
      TileRow(320, sea * 3, hillyTundra * 2),
      TileRow(318, sea, tundra * 2, sea * 2),
      TileRow(316, sea, tundra * 2, hillyTundra * 3),
      TileRow(314, sea * 2, tundra * 4),
      TileRow(312),
    )
  }
  help.run
}