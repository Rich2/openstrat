/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 640km.  */
object Terr640W90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w90(108)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(130, ice),
      TRow(128, tundra),
      TRow(126, tundra, Isle(tundra)),
      TRow(124, Cape.a(1, 2, taiga), Cape.a(4, 3, tundra)),
      VRow(123, Mouth(9728, HVDL), Mouth(9732, HVDR)),
      TRow(122, taiga * 2),
      TRow(120, taiga * 3),
      VRow(119, Mouth(9728, HVDL, Lake), Mouth(9730, HVUR, Lake), Mouth(9732, HVUp, Lake)),
      TRow(118, taiga, taiga, taiga),
      VRow(117, Mouth(9732, HVDn, Lake), Mouth(9736, HVUL)),
      TRow(116, savannah, savannah, hilly),
      TRow(114, sahel, savannah, land, land),
      TRow(112, savannah, land, jungle, sea),
      TRow(110, sahel, sea * 2, sea),
      TRow(108, jungle, jungle, sea, Cape.a(1, 4, hillyJungle)),
    )
  }
  help.run
}