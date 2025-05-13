/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

object Terr120E60 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e60(284, 286)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(286, mtainSavannah, sahel, hillySahel, lake * 2),
    TileRow(284, mtainSavannah, hillySahel, savannah, lake * 3),
    )
  }
  help.run
}