/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 135° west to 115° west, centred on 120° wast. Hex tile scale 460km.  */
object Terr460W120 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w120(90)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(146, SeaIcePerm),
      TRow(144, SeaIceWinter),
      TRow(142, Isle10(hillyTundra)),
      VRow(141, BendIn(8710, HVDL)),
      TRow(140, Isle10(tundra), Cape(0, 1, tundraLakes)),
      VRow(139, BendOut(8708, HVUp), BendIn(8710, HVUL)),
      TRow(138, tundraLakes, Cape(0, 1, tundraLakes)),
      VRow(137, MouthOld(8702, HVDL, 3, lake), MouthOld(8704, HVUR, 3, lake)),
      TRow(136, mtain, taigaLakes, tundraLakes),
      VRow(135, MouthOld(8706, HVUL, 3, lake), MouthOld(8708, HVDR, 3, lake)),
      TRow(134, mtain, taigaLakes, tundraLakes),
      TRow(132, mtain, hillyTaiga * 2),
      TRow(130, Cape(4, 1, mtain), hillyTaiga, taiga * 2),
      VRow(129, BendOut(8698, HVDL)),
      TRow(128, Cape(4, 1, mtain), mtain, savannah * 2),
      VRow(127, BendOut(8700, HVDL)),
      TRow(126, sea, SepB(), mtain, mtain * 2),
      VRow(125, BendOut(8700, HVUL)),
      TRow(124, sea, Cape(4, 2, mtain), hillyDesert, hillySavannah, hillySahel),
      VRow(123, BendOut(8700, HVDL)),
      TRow(122, sea, Cape(4, 1, mtain), hillySahel * 2, mtain),
      VRow(121, BendIn(8702, HVDL)),
      TRow(120, sea * 2, Cape(4, 1, hillySavannah), hillySahel * 2),
      VRow(119, BendOut(8704, HVDL)),
      TRow(118, sea * 3, Cape(4, 1, hillySahel), hillyDesert, hillySahel),
      VRow(117, BendOut(8706, HVDL), MouthOld(8708, HVUL), BendAllOld(8710, HVDL)),
      TRow(116, sea * 3, Cape(4, 1, hillySahel), Cape(4, 1, hillySahel), hillySahel),
      VRow(115, BendOut(8708, HVDL), BendOut(8712, HVDL)),
      TRow(114, sea * 4, Cape(4, 1, hillySahel), Cape(4, 1, mtain)),
      VRow(113, MouthOld(8710, HVDR), BendOut(8714, HVDL)),
      TRow(112, sea * 5, Cape(4, 1, mtain)),
    )
  }
  help.run
}