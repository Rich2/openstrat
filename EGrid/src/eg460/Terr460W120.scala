/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 135° west to 115° west, centred on 120° wast. Hex tile scale 460km.  */
object Terr460W120 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w120(106)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(142, Isle(hillyTundra)),
      //VRow(141, VertRightsLeft(8708)),
      TRow(140, Isle(tundra), Cape(0, 2, wetTundra)),
      VRow(139, BendOut(8708, HVUp), BendIn(8710, HVUL)),
      TRow(138, wetTundra, Cape(0, 1, wetTundra)),
      VRow(137, Mouth(8702, HVDL, lake), Mouth(8704, HVUR, lake)),
      TRow(136, mtain, wetTaiga, wetTundra),
      VRow(135, Mouth(8706, HVUL, lake), Mouth(8708, HVDR, lake)),
      TRow(134, mtain, wetTaiga, wetTundra),
      TRow(132, mtain, hillyTaiga * 2),
      TRow(130, Cape(4, 1, mtain), hillyTaiga, taiga * 2),
      VRow(129, BendOut(8698, HVDL)),
      TRow(128, Cape(4, 1, mtain), mtain, savannah * 2),
      VRow(127, BendOut(8700, HVDL)),
      TRow(126, sea, SideB(), mtain, mtain * 2),
      VRow(125, BendOut(8700, HVUL)),
      TRow(124, sea, Cape(4, 2, mtain), hillyDesert, hillySavannah, hillySahel),
      VRow(123, BendOut(8700, HVDL)),
      TRow(122, sea, Cape(4, 1, mtain), hillySahel * 2, mtain),
      VRow(121, BendIn(8702, HVDL)),
      TRow(120, sea * 2, Cape(4, 1, hillySavannah), hillySahel * 2),
      VRow(119, BendOut(8704, HVDL)),
      TRow(118, sea * 3, Cape(4, 1, hillySahel), hillyDesert, hillySahel),
      VRow(117, BendOut(8706, HVDL), Mouth(8708, HVUL), BendAll(8710, HVDL)),
      TRow(116, sea * 3, Cape(4, 1, hillySahel), Cape(4, 1, hillySahel), hillySahel),
      VRow(115, BendOut(8708, HVDL), BendOut(8712, HVDL)),
      TRow(114, sea * 4, Cape(4, 1, hillySahel), Cape(4, 1, mtain)),
      VRow(113, Mouth(8710, HVDR), BendOut(8714, HVDL)),
      TRow(112, sea * 5, Cape(4, 1, mtain)),
//      TRow(110, sea * 3, Cape(3, 2, hillyDesert)),
//      VRow(109, Mouth(8712, HVUR)),
//      TRow(108, sea * 3, Cape(3, 3, Land(Mountains, Savannah))),
    )
  }
  help.run
}