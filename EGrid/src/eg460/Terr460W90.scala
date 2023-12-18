/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 460km.  */
object Terr460W90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w90(112, 130)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(128, tundra),
//      TRow(126, tundra, Isle(tundra)),
        TRow(124, hillySavannah, savannah, hilly, land, land),
      VRow(123, Mouth(9730, HVDn, lake)),
      TRow(122, savannah * 3, mtain, hilly),
      TRow(120, sahel, hillySavannah, savannah, mtain, hilly),
//      VRow(119, Mouth(9728, HVDL, Lake), Mouth(9730, HVUR, Lake), Mouth(9732, HVUp, Lake)),
//      TRow(118, taiga, taiga, taiga),
//      VRow(117, Mouth(9732, HVDn, Lake), Mouth(9736, HVUL)),
//      TRow(116, savannah, savannah, hilly),
//      TRow(114, sahel, savannah, land, land),
//      TRow(112, savannah, Cape(3), jungle, sea),
//      VRow(111, BendAll(9724, HVDR), BendOut(9726, HVDn)),
//      TRow(110, sahel, sea * 2, sea),
//      VRow(109, BendOut(9724, HVUR), BendOut(9726, HVUp), Mouth(9720, HVUR)),
//      TRow(108, jungle, Cape(0, 1, jungle), sea, Isle(hillyJungle)),
//      TRow(106, sea, Cape(3, 2, Hilly, Savannah, Forest, Sea), Land(Hilly, Savannah, Forest)),
//      VRow(105, BendOut(9738, HVUp)),
//      TRow(104, sea * 3, Cape(4, 1, hillyJungle), Cape(0, 1, hillyJungle)),
//      TRow(102, sea * 4, mtain),
//      TRow(100, sea * 4, Cape(5, 1, mtain)),
//      TRow(98, sea * 3, Cape(4, 2, mtain), jungle),
//      TRow(96, sea * 4, Cape(4, 1, mtain)),
    )
  }
  help.run
}