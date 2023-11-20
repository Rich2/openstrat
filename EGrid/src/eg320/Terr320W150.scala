/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 165° west to 135° wast, centred on 150° west. Hex tile scale of 320km. */
object Terr320W150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w150(120)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, WSeaIce * 2),
      TRow(158, WSeaIce * 3),
      TRow(156, tundra * 2, Cape(1, 0, tundra, WSeaIce)),
      TRow(154, hillyTaiga * 3, taiga),
      TRow(152, taiga * 3, hillyTaiga),
      TRow(150, hillyTundra, mtain * 2, taiga),
      TRow(148, hillyTaiga * 2, sea * 2, mtain),
      VRow(147, Mouth(7674, HVUR)),
      TRow(146, Cape(1, 5, hillyTundra), sea * 3, mtain),
      TRow(144, Cape(2, 2, hillyTaiga), sea * 4),
      VRow(143, Mouth(7670, HVUL)),
    )
  }
  help.run
}