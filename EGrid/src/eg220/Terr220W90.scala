/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km terrain for 105° west to 75° west centred on 90° west. */
object Terr220W90 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w90(154, 166)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, wetTaiga * 3, taiga * 2, sea * 2, wetTaiga),
      TRow(164, wetTaiga * 3, taiga * 3, taiga * 2),

      VRow(163, MouthRt(9720, HVUp, 7, lake)),
      TRow(162, taiga, wetTaiga, taiga, wetTaiga * 3, taiga, sea * 2),//part checked
      TRow(160, land * 2, taiga * 5, Cape(1, 1, taiga), taiga),//Unchecked
      TRow(158, land * 2, taiga * 7),//Unchecked
      TRow(156, land * 2, taiga * 3, lake, taiga * 4),//Unchecked
      TRow(154, land * 3, taiga * 7),//Unchecked
    )
  }
  help.run
}