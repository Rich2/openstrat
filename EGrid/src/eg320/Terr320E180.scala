/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 165° east to 165° west, centred on 180° east. Hex tile scale of 320km. */
object Terr320E180 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e180(120)
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
      TRow(156, Cape(3, 5, tundra, WSeaIce), WSeaIce * 2),
      TRow(154, tundra * 2, Cape(2, 0, tundra, WSeaIce), WSeaIce),
      TRow(152, tundra, sea, Cape(4, 1, tundra, WSeaIce), Cape(2, 4, taiga, WSeaIce)),
      TRow(150, tundra * 2, sea * 2),
      TRow(148, tundra, sea * 4),
      VRow(144, SetSide(6646)),
     // VRow(143, Mouth(6666, HVUL)),
    )
  }
  help.run
}