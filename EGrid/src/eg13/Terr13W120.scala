/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75 East to 105 East. 1300km per hex tile. */
object Terr13W120 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w120(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(115, BendOut(8704, HVUp, 7, SeaIcePerm, siceWin), BendIn(8706, HVDn, 13, SeaIcePerm), BendIn(8708, HVDL, 6, SeaIcePerm, siceWin)),
    TRow(114, tundra),
    VRow(113, BendIn(8708, HVUR, 6, siceWin)),
    TRow(112, mtainTaiga),
    TRow(110, hillyTaiga),
    VRow(109, SourceLt(8702, HVDn, 7)),
    TRow(108, hillyOceForest),
    VRow(107, SourceRt(8702, HVUp, 7)),
    TRow(106, sea, hillyDeshot),
    VRow(105, SourceLt(8704, HVDR, 7), BendOut(8706, HVDL)),
    TRow(104, sea, hillySahel),
    VRow(103, BendIn(8706, HVUR, 13), SourceRt(8708, HVUL, 7)),
    VRow(91, BendIn(8708, HVDR, 13)),
    VRow(89, BendIn(8708, HVUR, 13)),
    VRow(87, SourceRt(8704, HVUR, 7, siceWin), BendIn(8706, HVDn, 13, SeaIceWinter), BendOut(8708, HVUp, 7, SeaIceWinter)),
    TRow(86, ice)
    )
  }
  help.run
}