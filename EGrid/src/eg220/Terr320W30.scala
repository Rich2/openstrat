/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° west to 15° wast, centred on 30° west. Hex tile scale of 220km. */
object Terr220W30 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w30(136, 170)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(

    )
  }
  help.run
}
