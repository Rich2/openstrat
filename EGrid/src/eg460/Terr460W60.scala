/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 460km.
 * Isle10 120974.276km² <= 57981.753km² Hispaniola 76192km². */
object Terr460W60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w60(104)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, ice),
      TRow(144, SideB(), ice),
      VRow(143, SetSide(10751)),
      TRow(142, SideB(), Cape(4, 1, ice)),
      TRow(140, sea, ice),
      VRow(139, Mouth(10754, HVUR)),
      TRow(138, SideB(), Cape(1, 1, hillyTundra), Cape(4, 2, mtain)),
      VRow(137, SetSide(10747), BendOut(10754, HVDL)),
      TRow(136, SideB(), hillyTundra, sea, Cape(4, 1, mtain)),
      VRow(135, Mouth(10748, HVDR), BendOut(10756, HVDL), BendAll(10760, HVDR)),
      TRow(134, sea * 2, Cape(2, 3, mtain)),
      VRow(133, Mouth(10752, HVUL)),
      TRow(132, wetTaiga, Cape(1, 1, mtain)),
      VRow(131, BendOut(10754, HVUR)),
      TRow(130, wetTaiga * 2, Cape(1, 1, hillyTaiga)),
      VRow(129, BendAll(10754, HVDR), ThreeWay(10756)),
      TRow(128, mtain, Cape(2, 1, mtain), Cape(1, 2, hillyTaiga)),
      VRow(127, Mouth(10748, HVDL), BendAll(10750, HVDn, sea, 7), ThreeWay(10752)),
      TRow(126, hillyTaiga, hillyTaiga, Cape(2, 3, mtain)),
      VRow(125, BendIn(10746, HVDR, 8), Mouth(10748, HVUR), Mouth(10750, HVUp)),
      TRow(124, hillyForest, Land(WetLand, Taiga, Forest)),
      VRow(123, BendIn(10746, HVUR, 10), BendIn(10748, HVUp, 13), BendIn(10750, HVUL, 13)),
      VRow(113, SetSide(10741)),
      TRow(112, SideB()),
      TRow(110, Isle(mtain)),
    )
  }
  help.run
}