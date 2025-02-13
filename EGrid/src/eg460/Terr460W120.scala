/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 135° west to 115° west, centred on 120° wast. Hex tile scale 460km.  */
object Terr460W120 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.w120(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(147, OrigRt(8708, HVUR, 7, SeaIcePerm)),
    TileRow(146, SeaIcePerm),
    TileRow(144, SeaIcePerm),
    VertRow(143, BendIn(8704, HVDR, 6, siceWin, SeaIcePerm), Bend(8706, HVDn, 7, 2, SeaIcePerm), BendIn(8708, HVDL, 8, SeaIcePerm)),
    TileRow(142, hillyTundra),

    VertRow(141, BendIn(8702, HVDR, 6, siceWin), ThreeUp(8704, 11, 6, 0, siceWin), BendIn(8706, HVUp, 9 , siceWin),
      ThreeUp(8708, 0, 6, 9, SeaIcePerm, siceWin, siceWin), BendIn(8710, HVDL)),

    TileRow(140, tundra, lakesTundra),

    VertRow(139, BendIn(8702, HVUR, 8, siceWin), BendIn(8704, HVUp, 13, siceWin), BendIn(8706, HVDn, 13, siceWin), BendOut(8708, HVUp, 7, siceWin),
      BendIn(8710, HVUL)),

    TileRow(138, lakesTundra * 2),
    VertRow(137, OrigLt(8702, HVUR, 6, lake), OrigLt(8704, HVDL, 6, lake)),
    TileRow(136, mtainTundra, lakesTaiga, lakesTundra),
    VertRow(135, OrigRt(8706, HVDR, 6, lake), OrigRt(8708, HVUL, 6, lake)),
    TileRow(134, mtainTaiga, lakesTaiga, lakesTundra),
    TileRow(132, mtainTundra, hillyTaiga * 2),
    TileRow(130, mtainTaiga, hillyTaiga, taiga * 2),
    VertRow(129, OrigLt(8696, HVDR, 7), BendOut(8698, HVDL)),
    TileRow(128, mtainTaiga * 2, savannah * 2),
    VertRow(127, BendIn(8698, HVUR, 13), BendOut(8700, HVDL)),
    TileRow(126, sea, SepB(), mtainOceForest, mtainDepr * 2),
    VertRow(125, BendIn(8698, HVDR, 13), BendOut(8700, HVUL)),
    TileRow(124, sea, mtainDepr, hillyDeshot, hillySavannah, hillySahel),
    VertRow(123, BendIn(8698, HVUR, 13), BendOut(8700, HVDL)),
    TileRow(122, sea, mtainDepr, hillySahel * 2, mtainDepr),
    VertRow(121, OrigRt(8700, HVUp, 7)),
    TileRow(120, sea * 2, hillySavannah, hillySahel * 2),
    VertRow(119, OrigLt(8702, HVDR), BendOut(8704, HVDL)),
    TileRow(118, sea * 3, hillySahel, hillyDeshot, hillySahel),
    VertRow(117, BendIn(8704, HVUR, 13), BendOut(8706, HVDL), OrigLt(8708, HVDR), Bend(8710, HVDL, 3, 4)),
    TileRow(116, sea * 3, hillySahel, hillySahel, hillySahel),
    VertRow(115, BendIn(8706, HVUR, 13), BendOut(8708, HVDL), BendIn(8710, HVUR, 13), BendOut(8712, HVDL)),
    TileRow(114, sea * 4, hillySahel, mtainDepr),
    VertRow(113, BendIn(8708, HVUR, 13), OrigRt(8710, HVUL, 7), BendIn(8712, HVUR, 13), BendOut(8714, HVDL, 7)),
    TileRow(112, sea * 5, mtainDepr),
    VertRow(111, BendIn(8714, HVUR, 13), BendOut(8716, HVDL)),
    )
  }
  help.run
}