/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 135° west to 105° wast, centred on 120° west. */
object Terr220W120 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w120(150, 152)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(152, sea * 3, mtainDepr * 5, hillySahel, mtainDepr),
    VRow(151, OrigLt(8696, HVDn, 7)),
    TRow(150, sea * 3, mtainDepr, hillySavannah, hillySahel * 3, mtainDepr, sahel, hillyDeshot),
    )
  }

  help.run
}
