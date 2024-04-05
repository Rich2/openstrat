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
  { override val rows: RArr[RowBase] = RArr(
    VRow(147, SourceRt(8708, HVUR, 7, SeaIcePerm)),
    TRow(146, SeaIcePerm),
    TRow(144, SeaIcePerm),
    VRow(143, BendIn(8704, HVDR, 6, siceWin, SeaIcePerm), Bend(8706, HVDn, 7, 2, SeaIcePerm), BendIn(8708, HVDL, 8, SeaIcePerm)),
    TRow(142, hillyTundra),

    VRow(141, BendIn(8702, HVDR, 6, siceWin), ThreeUp(8704, 11, 6, 0, siceWin), BendIn(8706, HVUp, 9 , siceWin),
      ThreeUp(8708, 0, 6, 9, SeaIcePerm, siceWin, siceWin), BendIn(8710, HVDL)),

    TRow(140, tundra, lakesTundra),

    VRow(139, BendIn(8702, HVUR, 8, siceWin), BendIn(8704, HVUp, 13, siceWin), BendIn(8706, HVDn, 13, siceWin), BendOut(8708, HVUp, 7, siceWin),
      BendIn(8710, HVUL)),

    TRow(138, lakesTundra * 2),
    VRow(137, SourceLt(8702, HVUR, 6, lake), SourceLt(8704, HVDL, 6, lake)),
    TRow(136, mtainTundra, lakesTaiga, lakesTundra),
    VRow(135, SourceRt(8706, HVDR, 6, lake), SourceRt(8708, HVUL, 6, lake)),
    TRow(134, mtainDepr, lakesTaiga, lakesTundra),
    TRow(132, mtainDepr, hillyTaiga * 2),
    TRow(130, mtainDepr, hillyTaiga, taiga * 2),
    VRow(129, MouthLt(8696, HVUL, 7), BendOut(8698, HVDL)),
    TRow(128, mtainDepr, mtainDepr, savannah * 2),
    VRow(127, BendIn(8698, HVUR, 13), BendOut(8700, HVDL)),
    TRow(126, sea, SepB(), mtainDepr, mtainDepr * 2),
    VRow(125, BendIn(8698, HVDR, 13), BendOut(8700, HVUL)),
    TRow(124, sea, mtainDepr, hillyDeshot, hillySavannah, hillySahel),
    VRow(123, BendIn(8698, HVUR, 13), BendOut(8700, HVDL)),
    TRow(122, sea, mtainDepr, hillySahel * 2, mtainDepr),
    VRow(121, MouthRt(8700, HVDn, 7)),
    TRow(120, sea * 2, hillySavannah, hillySahel * 2),
    VRow(119, MouthLt(8702, HVUL), BendOut(8704, HVDL)),
    TRow(118, sea * 3, hillySahel, hillyDeshot, hillySahel),
    VRow(117, BendIn(8704, HVUR, 13), BendOut(8706, HVDL), SourceLt(8708, HVDR), Bend(8710, HVDL, 3, 4)),
    TRow(116, sea * 3, hillySahel, hillySahel, hillySahel),
    VRow(115, BendIn(8706, HVUR, 13), BendOut(8708, HVDL), BendIn(8710, HVUR, 13), BendOut(8712, HVDL)),
    TRow(114, sea * 4, hillySahel, mtainDepr),
    VRow(113, BendIn(8708, HVUR, 13), MouthRt(8710, HVDR, 7), BendIn(8712, HVUR, 13), BendOut(8714, HVDL, 7)),
    TRow(112, sea * 5, mtainDepr),
    VRow(111, BendIn(8714, HVUR, 13), BendOut(8716, HVDL)),
    )
  }
  help.run
}