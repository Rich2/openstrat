/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° west to 75° west, centred on 90° wast. Hex tile scale 460km.
 * Isle10 120974.276km² <= 57981.753km² Cuba. */
object Terr460W90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w90(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, Isle10(mtainOld)),
      TRow(142, Isle10(mtainOld)),
      VRow(141, SetSep(9727), ThreeDown(9730, 13, 0, 6)),
      TRow(140, SepB(), hillyTundra, SepB(), hillyTundra),
      VRow(139, BendIn(9726, HVUL), BendOut(9724, HVUp), MouthOld(9728, HVDL), ThreeUp(9730, 0, 6, 6)),
      TRow(138, tundra, CapeOld(0, 3, hillyTundra)),
      VRow(137, BendOut(9730, HVDR)),
      TRow(136, lakesTundra, CapeOld(2, 1, lakesTundra), CapeOld(0, 4, hillyTundra)),
      VRow(135, BendOut(9728, HVDR, 7), MouthOld(9736, HVDR)),
      TRow(134, lakesTundra, sea, CapeOld(5, 2, lakesTundra)),
      VRow(133, BendOut(9728, HVUR, 7), ThreeDown(9730, 0, 7, 7), BendOut(9732, HVUL, 7)),
      TRow(132, lakesTaiga, taiga, lakesTaiga),
      VRow(131, BendIn(9730, HVUR, 13), BendOut(9732, HVDL, 7)),
      TRow(130, lakesTaiga * 3, Land(PlainLakes, Boreal, Forest)),//check
      VRow(129, BendIn(9732, HVUR, 13), MouthOld(9734, HVDR)),
      TRow(128, lakesTaiga * 2, taiga, lakesTaiga),
      VRow(127, MouthOld(9728, HVDL, 3, lake), MouthOld(9732, HVDR, 3, lake)),
      TRow(126, savannah, taiga, CapeOld(0, 1, taiga, lake), hillyTaiga),
      VRow(125, BendAllOld(9730, HVDR, lake), BendOut(9732, HVDn, 6, lake), MouthOld(9734, HVDR, 3, lake)),
      TRow(124, hillySavannah, savannah, hillyTemp, temperate, temperate),
      VRow(123, MouthOld(9730, HVDn, 3, lake)),
      TRow(122, savannah * 3, mtainOld, hillyTemp),
      TRow(120, sahel, hillySavannah, savannah, mtainOld, hillyTemp),
      TRow(118, desert, savannah, temperate * 3),
      VRow(117, MouthOld(9730, HVUp, 3, lake), MouthOld(9736, HVUL)),
      TRow(116, savannah, temperate, CapeOld(2, 2), sea, CapeOld(1, 2, jungle)),
      VRow(115, BendOut(9724, HVDR), BendOut(9726, HVDn)),
      TRow(114, hillySahel, CapeOld(2, 1, hillySavannah)),
      VRow(113, BendOut(9722, HVDR), MouthOld(9730, HVDR), BendOut(9738, HVUp)),
      TRow(112, hillySavannah, sea, CapeOld(5, 2, savannah), sea, CapeOld(3, 4, hillyJungle), CapeOld(0, 4, jungle)),
      VRow(111, BendOut(9716, HVDL), MouthOld(9724, HVDL), BendOut(9726, HVUL), BendOut(9738, HVDn)),
      TRow(110, mtainOld, hillyJungle, savannah, jungle),
      VRow(109, BendIn(9716, HVUR, 13), MouthRt(9718, HVDR), MouthLt(9722, HVUp, 7)),
      TRow(108, sea * 2, mtainOld * 2, hillyJungle),

      VRow(107, BendIn(9722, HVUR, 13), BendIn(9724, HVUp, 13), BendOut(9726, HVDn, 7), MouthRt(9728, HVDR, 7), MouthOld(9734, HVUL), BendIn(9736, HVDL, 10),
        BendIn(9740, HVDR, 10), BendIn(9742, HVDn, 10), BendOut(9744, HVUp)),
      
      TRow(106, sea * 4, mtainOld, sea, hillyJungle),
      VRow(105, MouthOld(9734, HVUp), MouthOld(9736, HVDn), MouthOld(9740, HVDn)),
      TRow(104, sea * 5, mtainOld, hillyJungle),
      VRow(103, BendIn(9734, HVUR, 10), BendIn(9736, HVUp, 10), BendOut(9738, HVDn, 7), MouthOld(9740, HVDR)),
      TRow(102, sea * 6, mtainOld),
      TRow(100, sea * 6, hillyJungle),
      TRow(98, sea * 5, mtainOld, jungle),
      TRow(96, sea * 6, mtainOld),
      TRow(94, sea * 6, mtainOld),
    )
  }
  help.run
}