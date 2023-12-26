/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 460km.  */
object Terr460W90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w90(112)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, Isle(mtain)),
      TRow(142, Isle(mtain)),
      VRow(141, SetSide(9727), ThreeWay(9730)),
      TRow(140, SideB(), hillyTundra, SideB(), hillyTundra),
      VRow(139, BendIn(9726, HVUL), BendOut(9724, HVUp), Mouth(9728, HVDL), ThreeWay(9730)),
      TRow(138, tundra, Cape(0, 3, hillyTundra)),
      VRow(137, BendOut(9730, HVDR)),
      TRow(136, wetTundra, Cape(2, 1, wetTundra), Cape(0, 4, hillyTundra)),
      VRow(135, Mouth(9736, HVDR)),
      TRow(134, wetTundra, sea, Cape(5, 2, wetTundra)),
      TRow(132, wetTaiga, Cape(1, 1, taiga)),
      TRow(130, wetTaiga * 3, Cape(4, 2, WetLand, Taiga, Forest, Sea)),
      VRow(129, Mouth(9734, HVDR)),
      TRow(128, wetTaiga * 2, taiga, wetTaiga),
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
    )
  }
  help.run
}