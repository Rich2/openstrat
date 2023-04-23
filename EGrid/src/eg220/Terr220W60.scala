/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTile._

object Terr220W60 extends Long220Terrs {
  override implicit val grid: EGrid220LongFull = EGrid220.w60(154, 162)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(162, taiga * 5),
      TRow(160, taiga * 3, hillyTaiga),
      TRow(158, hillyTaiga),
      TRow(156, hillyTaiga * 2),
      TRow(154, plain),
    )
  }
  help.run
}