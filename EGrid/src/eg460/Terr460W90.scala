/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 460km.  */
object Terr460W90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w90(112, 136)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(130, Land(WetLand, Taiga, Forest) * 3, Cape(4, 2, WetLand, Taiga, Forest, Sea)),
      VRow(129, Mouth(9734, HVDR)),
      TRow(128, Land(WetLand, Taiga, Forest) * 2, taiga, Land(WetLand, Taiga, Forest)),
      VRow(127, Mouth(9728, HVDL, lake), Mouth(9732, HVDR, lake)),
      TRow(126, savannah, taiga, Cape(0, 1, taiga, lake), hillyTaiga),
      VRow(125, BendAll(9730, HVDR, lake), BendOut(9732, HVDn, lake), Mouth(9734, HVDR, lake)),
      TRow(124, hillySavannah, savannah, hilly, land, land),
      VRow(123, Mouth(9730, HVDn, lake)),
      TRow(122, savannah * 3, mtain, hilly),
      TRow(120, sahel, hillySavannah, savannah, mtain, hilly),
      TRow(118, desert, savannah, land * 3),
      VRow(117, Mouth(9730, HVUp, Lake), Mouth(9736, HVUL)),
      TRow(116, savannah, land, Cape(2, 2), sea, Cape(1, 2, jungle)),
      VRow(115, BendOut(9724, HVDR), BendOut(9726, HVDn)),
      TRow(114, hillySahel, Cape(2, 1, hillySavannah)),
      VRow(113, BendOut(9722, HVDR)),
      TRow(112, hillySavannah),
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