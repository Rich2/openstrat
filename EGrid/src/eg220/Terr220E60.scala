/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° east to 75° east, centred on 60° east. Hex tile scale of 220km. */
object Terr220E60 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.e60(142, 154)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
    TRow(154, steppe * 2, descold * 8),
    TRow(152, sea, descold * 3, steppe, descold, steppe, descold * 3),
    TRow(150, hillySteppe, sea, descold * 2, steppe, descold * 3, hillySteppe * 3),
    TRow(148, hillySavannah, mtainSavannah, sea, descold * 5, steppe, mtainSteppe, mtainTaiga),
    TRow(146, hillySavannah, sea, hillyDescold, descold * 3, steppe, hillySteppe, mtainSteppe * 2, mtainTundra),
    TRow(144, hillyDeshot, mtainSavannah, sea, hillySteppe, hillyDescold, descold * 2, steppe, hillySteppe, mtainSteppe, mtainTundra),
    TRow(142, hillyDeshot * 2),
    )
  }

  help.run
}