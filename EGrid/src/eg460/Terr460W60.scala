/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 460km.
 * Isle10 120974.276km² <= 57981.753km² Hispaniola 76192km². */
object Terr460W60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w60(94)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, ice),
      TRow(144, SepB(), ice),
      VRow(143, SetSep(10751)),
      TRow(142, SepB(), Cape(4, 1, ice)),
      TRow(140, sea, ice),
      VRow(139, Mouth(10754, HVUR)),
      TRow(138, SepB(), Cape(1, 1, hillyTundra), Cape(4, 2, mtain)),
      VRow(137, SetSep(10747), BendOut(10754, HVDL)),
      TRow(136, SepB(), hillyTundra, sea, Cape(4, 1, mtain)),
      VRow(135, Mouth(10748, HVDR), BendOut(10756, HVDL), BendAll(10760, HVDR, SeaIceWinter)),
      TRow(134, sea * 2, mtain),
      VRow(133, Mouth(10752, HVUL), BendIn(10758, HVUp, 6, SeaIceWinter), BendIn(10760, HVUL, 6, SeaIceWinter)),
      TRow(132, taigaLakes, Cape(1, 1, mtain)),
      VRow(131, BendOut(10754, HVUR)),
      TRow(130, taigaLakes * 2, Cape(1, 1, hillyTaiga)),
      VRow(129, BendAll(10754, HVDR), ThreeUp(10756, 0, 13, 6)),
      TRow(128, mtain, mtain, hillyTaiga),
      VRow(127, Mouth(10748, HVDL), BendAll(10750, HVDn, sea, 7), ThreeDown(10752, 13, 0, 6), BendIn(10754, HVUL, 13)),
      TRow(126, hillyTaiga, hillyTaiga, Cape(2, 3, mtain)),
      VRow(125, BendIn(10746, HVDR, 8), Mouth(10748, HVUR), Mouth(10750, HVUp)),
      TRow(124, hillyForest, Land(LandLakes, Taiga, Forest)),
      VRow(123, BendIn(10746, HVUR, 10), BendIn(10748, HVUp, 13), BendIn(10750, HVUL, 13)),
      VRow(113, SetSep(10741)),
      TRow(112, SepB()),
      TRow(110, Isle10(mtain)),
      TRow(108),
      VRow(107, BendOut(10740, HVUp), BendIn(10742, HVDn), BendOut(10744, HVUp, 7), BendIn(10746, HVDn, 13), BendOut(10748, HVUp, 7), BendIn(10750, HVDn, 13), Mouth(10752, HVDR)),
      TRow(106, hillySavannah * 3),
      TRow(104, mtain, savannah, hillyJungle * 2),
      TRow(102, jungle, hillyJungle * 2, jungle, hillyJungle),
      VRow(101, MouthRt(10762, HVDL), BendIn(10764, HVDn, 13), BendIn(10766, HVDL, 13)),
      TRow(100, jungle * 3, hillyJungle, mtain, hillyJungle, jungle),
      VRow(99, Mouth(10766, HVDn)),
      TRow(98, jungle * 4, hillyJungle * 2, jungle),
      TRow(96, jungle * 3, hillyJungle * 3, hillySavannah),
      TRow(94, jungle * 2, hillyJungle, hillySavannah * 2, hillyJungle, hillySavannah),
    )
  }
  help.run
}