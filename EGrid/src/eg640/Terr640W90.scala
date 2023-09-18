/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 640km.  */
object Terr640W90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w90(110)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(130, ice),
      TRow(128, tundra),
      TRow(126, tundra, Isle(tundra)),
      TRow(124, Hland(2, 1, taiga), Hland(3, 4, tundra)),
      VRow(123, Mouth(9728, HVDL), Mouth(9732, HVDR)),
      TRow(122, taiga * 2),
      TRow(120, taiga * 3),
      VRow(119, Mouth(9728, HVDL, Lake)),
      TRow(118, taiga, Hland(2, 0, taiga, Lake), taiga),
      TRow(116, savannah, savannah, hills),
      TRow(114, sahel, savannah, plain, plain),
      TRow(112, savannah, plain, jungle, sea),
      TRow(110, sahel, sea * 3),
    )
  }
  help.run
}