/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 460km.
 * Isle10 120974.276km² <= 57981.753km² Cuba. */
object Terr460W90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w90(94)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, Isle10(mtain)),
      TRow(142, Isle10(mtain)),
      VRow(141, SetSep(9727), ThreeDown(9730, 13, 0, 6)),
      TRow(140, SepB(), hillyTundra, SepB(), hillyTundra),
      VRow(139, BendIn(9726, HVUL), BendOut(9724, HVUp), MouthOld(9728, HVDL), ThreeUp(9730, 0, 6, 6)),
      TRow(138, tundra, Cape(0, 3, hillyTundra)),
      VRow(137, BendOut(9730, HVDR)),
      TRow(136, tundraLakes, Cape(2, 1, tundraLakes), Cape(0, 4, hillyTundra)),
      VRow(135, BendOut(9728, HVDR, 7), MouthOld(9736, HVDR)),
      TRow(134, tundraLakes, sea, Cape(5, 2, tundraLakes)),
      VRow(133, BendOut(9728, HVUR, 7), ThreeDown(9730, 0, 7, 7), BendOut(9732, HVUL, 7)),
      TRow(132, taigaLakes, taiga, taigaLakes),
      VRow(131, BendIn(9730, HVUR, 13), BendOut(9732, HVDL, 7)),
      TRow(130, taigaLakes * 3, Land(LandLakes, Taiga, Forest)),//check
      VRow(129, BendIn(9732, HVUR, 13), MouthOld(9734, HVDR)),
      TRow(128, taigaLakes * 2, taiga, taigaLakes),
      VRow(127, MouthOld(9728, HVDL, 3, lake), MouthOld(9732, HVDR, 3, lake)),
      TRow(126, savannah, taiga, Cape(0, 1, taiga, lake), hillyTaiga),
      VRow(125, BendAllOld(9730, HVDR, lake), BendOut(9732, HVDn, 6, lake), MouthOld(9734, HVDR, 3, lake)),
      TRow(124, hillySavannah, savannah, hilly, land, land),
      VRow(123, MouthOld(9730, HVDn, 3, lake)),
      TRow(122, savannah * 3, mtain, hilly),
      TRow(120, sahel, hillySavannah, savannah, mtain, hilly),
      TRow(118, desert, savannah, land * 3),
      VRow(117, MouthOld(9730, HVUp, 3, lake), MouthOld(9736, HVUL)),
      TRow(116, savannah, land, Cape(2, 2), sea, Cape(1, 2, jungle)),
      VRow(115, BendOut(9724, HVDR), BendOut(9726, HVDn)),
      TRow(114, hillySahel, Cape(2, 1, hillySavannah)),
      VRow(113, BendOut(9722, HVDR), MouthOld(9730, HVDR), BendOut(9738, HVUp)),
      TRow(112, hillySavannah, sea, Cape(5, 2, savannah), sea, Cape(3, 4, hillyJungle), Cape(0, 4, jungle)),
      VRow(111, MouthOld(9724, HVDL), BendOut(9726, HVUL), BendOut(9738, HVDn)),
      TRow(110, mtain, hillyJungle, savannah, jungle),
      TRow(108, sea * 3, mtain, hillyJungle),
      VRow(107, MouthOld(9734, HVUL), BendIn(9736, HVDL, 10), BendIn(9740, HVDR, 10), BendIn(9742, HVDn, 10), BendOut(9744, HVUp)),
      TRow(106, sea * 4, mtain, sea, hillyJungle),
      VRow(105, MouthOld(9734, HVUp), MouthOld(9736, HVDn), MouthOld(9740, HVDn)),
      TRow(104, sea * 5, mtain, hillyJungle),
      VRow(103, BendIn(9734, HVUR, 10), BendIn(9736, HVUp, 10), BendOut(9738, HVDn, 7), MouthOld(9740, HVDR)),
      TRow(102, sea * 6, mtain),
      TRow(100, sea * 6, hillyJungle),
      TRow(98, sea * 5, mtain, jungle),
      TRow(96, sea * 6, mtain),
      TRow(94, sea * 6, mtain),
    )
  }
  help.run
}