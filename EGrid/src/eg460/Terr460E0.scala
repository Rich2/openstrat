/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 460km. A hex tile area of 354724.005km².
 *  Isle 234173.269km² <= 112236.892km².
 *  Isle10 120974.276km² <= 57981.753km². Ireland
 *  Isle8 57981.753km² <= 35075.382km². Sardinia. Sicily no hex avaliabe
 *  Isle3 8768.845km² <= 4473.900km² Balearic Islands 5040km², Corsica no hex available. */
object Terr460E0 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e0(94)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      VRow(143, BendIn(512, HVDL, 13), MouthOld(510, HVUL)),
      VRow(141, BendIn(512, HVUL, 13)),
      VRow(137, MouthLt(514, HVUp)),
      TRow(136, sea * 2, hillyTaiga),
      VRow(135, MouthOld(506, HVUR), BendIn(512, HVDR, 13), BendOut(514, HVUL), BendAll(520, HVDR)),
      TRow(134, sea, mtain, land),
      VRow(133, BendIn(506, HVDR, 13), MouthLt(508, HVUR), BendIn(512, HVUR, 13), BendOut(514, HVDL)),
      TRow(132, hilly, sea, forest),
      VRow(131, BendIn(504, HVDR), ThreeUp(506, 7, 7, 0), BendOut(508, HVDL), BendIn(514, HVUR, 13), BendIn(516, HVUp, 13), BendIn(518, HVUL)),
      TRow(130, land, hilly, sea, land),
      VRow(129, BendIn(504, HVUR, 10), MouthRt(506, HVDR), MouthRt(508, HVDn), MouthRt(514, HVUp)),
      TRow(128, sea, land, land, hilly),
      VRow(127, MouthOld(508, HVUp), MouthLt(510, HVUL, 7), BendIn(512, HVUp, 9), BendIn(514, HVUL, 6)),
      TRow(126, sea, land, land, hilly),
      VRow(125, MouthOld(506, HVDL), ThreeUp(508, 13, 13, 0), MouthOld(510, HVDR), MouthOld(518, HVUp)),
      TRow(124, sea, hilly, hilly, hilly, hilly),

      VRow(123, MouthLt(504, HVUp, 7), BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), ThreeDown(516, 13, 13, 13), ThreeUp(518, 13, 0, 10),
        BendInOut(520, HVDL, 3, 7), BendOut(524, HVDL)),

      TRow(122, hillySavannah, sahel, hillySavannah, hillySavannah, hillySavannah),

      VRow(121, MouthRt(504, HVDn, 7), BendIn(510, HVDR, 13), ThreeUp(512, 13, 13, 0), BendInOut(514, HVUp, 13, 3), ThreeUp(516, 13, 8, 13),
        BendIn(518, HVUp, 10), ThreeUp(520, 6, 3, 3), BendIn(522, HVDL, 11), MouthOld(524, HVDn)),

      TRow(120, sea, hilly, hillySavannah, hillySavannah, hillySavannah),
      VRow(119, BendIn(506, HVUR, 13), BendIn(508, HVUp, 13), BendIn(510, HVUL, 13), MouthLt(522, HVDn, 7)),
      TRow(118, sea, hillySahel, hillyDesert, desert, desert, sahel),
      VRow(117, BendIn(500, HVDn, 13), ThreeDown(502, 0, 10, 13), MouthLt(504, HVUR)),
      TRow(116, hillyDesert, desert * 5),
      VRow(115, BendIn(500, HVUp, 13), BendIn(502, HVUL, 13)),
      TRow(114, desert * 4, hillyDesert, desert),
      TRow(112, desert * 6),
      TRow(110, desert * 6),
      TRow(108, savannah, sahel, savannah, sahel, desert * 2, sahel),
      TRow(106, savannah, savannah * 2, jungle, savannah * 3),
      VRow(105, BendOut(498, HVDL, 7)),
      TRow(104, jungle, hillyJungle * 4, savannah, hillySavannah),
      VRow(103, BendIn(498, HVUR, 13), BendIn(500, HVUp, 13), MouthOld(502, HVUR), MouthOld(520, HVUp)),
      TRow(102, sea * 5, hillyJungle, jungle),
      VRow(101, BendIn(518, HVDR, 13), BendOut(520, HVUL)),
      TRow(100, sea * 5, hillyJungle * 2),
      VRow(99, BendIn(518, HVUR, 13), BendOut(520, HVDL)),
      TRow(98,sea * 5, mtain, hillyJungle),
      VRow(97, BendIn(520, HVUR, 13), BendOut(522, HVDL, 7)),
      TRow(96, sea * 6, hillyJungle),
      VRow(95, BendIn(522, HVUR, 13), MouthRt(524, HVDR)),
      TRow(94, sea * 6, mtain),
    )
  }
  help.run
}