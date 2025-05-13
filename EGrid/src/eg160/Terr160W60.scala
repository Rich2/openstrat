/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

object Terr160W60 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w60(314, 320)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(320, sea * 3, ice * 2),
      TileRow(318, sea * 3, ice * 2),
      TileRow(316, sea * 3, ice * 3),
      TileRow(314, mtainDepr, sea * 3, ice * 2),
    )
  }
  help.run
}