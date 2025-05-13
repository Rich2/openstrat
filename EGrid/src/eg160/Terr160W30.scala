/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

object Terr160W30 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w30(276, 320)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(320, ice * 4, sea),
      TileRow(318, ice * 4, sea),
      TileRow(316, ice * 4, sea * 2),
      TileRow(314, ice * 5, sea),
      TileRow(312, ice * 5, sea * 2),
      TileRow(310, ice * 4, sea * 3),
      TileRow(308, ice * 3, sea * 5),
      TileRow(306, ice * 2, sea * 4, hillyOce * 2),
      TileRow(304, ice, sea * 4, tundra * 3),
      TileRow(302, ice, sea * 5, tundra * 3),
      TileRow(300, tundra, sea * 8),
      TileRow(298, tundra, sea * 9),
    )
  }
  help.run
}