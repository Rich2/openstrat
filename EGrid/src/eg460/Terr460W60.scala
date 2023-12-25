/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° west to 45° west, centred on 60° wast. Hex tile scale 460km.  */
object Terr460W60 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w60(112)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, ice),
      TRow(144, ice),
      TRow(142, ice),
      TRow(140, sea, ice),
      TRow(138, SideB(), Cape(1, 1, hillyTundra), Cape(4, 2, mtain)),
      VRow(137, SetSide(10747), BendOut(10754, HVDL)),
      TRow(136, SideB(), hillyTundra, sea, Cape(4, 1, mtain)),
      VRow(135, Mouth(10748, HVDR), BendOut(10756, HVDL), BendAll(10760, HVDR)),
      TRow(134, sea * 2, Cape(2, 3, mtain)),
      TRow(132, wetTaiga, Cape(1, 1, mtain)),
      VRow(131, BendOut(10754, HVUR)),
      TRow(130, wetTaiga * 2, Cape(1, 1, hillyTaiga)),
      VRow(129, BendAll(10754, HVDR), ThreeWay(10756)),
      TRow(128, mtain, Cape(2, 1, mtain), Cape(1, 2, hillyTaiga)),
      VRow(127, Mouth(10748, HVDL), BendAll(10750, HVDn, sea, 7), ThreeWay(10752)),
      TRow(126, hillyTaiga, hillyTaiga, Cape(2, 3, mtain)),
      VRow(125, Mouth(10748, HVUR), Mouth(10750, HVUp)),
      TRow(124, hillyForest, Cape(2, 4, WetLand, Taiga, Forest, Sea)),
//      TRow(122, taiga, sea),
//      TRow(120, taiga, taiga),
//      VRow(119, Mouth(10750, HVUL), Mouth(10752, HVDR)),
//      TRow(118, Cape(2, 2, taiga), sea),
//      VRow(117, Mouth(10748, HVUL)),
//      VRow(109, SetSide(10745)),
//      TRow(108, SideB()),
//      VRow(105, BendOut(10742, HVUp), BendOut(10746, HVUp), BendAll(10750, HVUp)),
//      TRow(104, Cape(0, 1, hillyJungle), Cape(0, 1, jungle), Cape(0, 2, jungle)),
//      VRow(103, BendOut(10754, HVUR), BendOut(10756, HVUp)),
//      TRow(102, savannah, hillyJungle, jungle, Cape(0, 2, hillyJungle)),
//      VRow(101, BendOut(10760, HVUR), Mouth(10762, HVDR)),
//      TRow(100, jungle * 3, hillyJungle, jungle),
//      TRow(98, jungle * 4, hillyJungle),
//      TRow(96, jungle * 4, hillyJungle),
    )
  }
  help.run
}