/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 135° west to 115° west, centred on 120° wast. Hex tile scale 640km.  */
object Terr640W120 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w120(84)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(131, OrigRt(8706, HVDR, 7, SeaIcePerm), BendIn(8708, HVDL, 13, SeaIcePerm)),
    TRow(130, hillyTundra),

    VRow(129, BendOut(8702, HVUp, 7, siceWin), BendOut(8704, HVDn, 7, siceWin), ThreeDown(8706, 13, 6, 0, SeaIcePerm, siceWin, siceWin),
      BendIn(8708, HVUL, 13, SeaIcePerm)),

    TRow(128, tundra),
    TRow(126, taiga * 2),
    TRow(124, taiga * 2),
    VRow(123, BendIn(8700, HVDL)),
    TRow(122, hillyTaiga, taiga),
    VRow(121, BendIn(8698, HVDR, 13), BendOut(8700, HVUL, 6)),
    TRow(120, mtainOceForest, hillyTaiga, savannah),
    VRow(119, BendIn(8698, HVUR, 13), BendOut(8700, HVDL, 7)),
    TRow(118, mtainOceForest, mtainSavannah, hillySahel),
    VRow(117, OrigRt(8700,HVUp, 7)),
    TRow(116, sea, hillySavannah, hillyDeshot),
    TRow(114, sea * 2, hillySahel, hillyDeshot),
    TRow(112, sea * 2, hillySahel, hillySahel),
    TRow(110, sea * 3, hillyDeshot),
    VRow(109, OrigLt(8708, HVDR, 7), BendOut(8710, HVDL, 7)),
    TRow(108, sea * 3, mtainSavannah),
    VRow(107, BendIn(8710, HVUR, 13), OrigRt(8712, HVUL, 7))
    )
  }
  help.run
}