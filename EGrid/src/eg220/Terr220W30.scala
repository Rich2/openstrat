/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 45° west to 15° wast, centred on 30° west. Hex tile scale of 220km. */
object Terr220W30 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w30(134, 170)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(

    )
  }
  help.run
}
