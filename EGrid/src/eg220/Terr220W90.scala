/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km terrain for 90 west. */
object Terr220W90 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w90(154, 162)
  override val terrs: LayerHcSys[WTile] = LayerHcSys[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(162, land * 2, taiga * 4, Cape(0, 2, taiga), Cape(4, 1, taiga), taiga),
      TRow(160, land * 2, taiga * 5, Cape(1, 1, taiga), taiga),
      TRow(158, land * 2, taiga * 7),
      TRow(156, land * 2, taiga * 3),
      TRow(154, land * 3, taiga),
    )
  }
  help.run
}