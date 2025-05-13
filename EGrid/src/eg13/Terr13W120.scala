/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 75 East to 105 East. 1300km per hex tile. */
object Terr13W120 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w120(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(115, BendOut(8704, HVUp, 7, SeaIcePerm, siceWin), BendIn(8706, HVDn, 13, SeaIcePerm), BendIn(8708, HVDL, 6, SeaIcePerm, siceWin)),
    TileRow(114, tundra),
    VertRow(113, BendIn(8708, HVUR, 6, siceWin)),
    TileRow(112, mtainTaiga),
    TileRow(110, hillyTaiga),
    VertRow(109, OrigLt(8702, HVDn, 7)),
    TileRow(108, hillyOceForest),
    VertRow(107, OrigRt(8702, HVUp, 7)),
    TileRow(106, sea, hillyDeshot),
    VertRow(105, OrigLt(8704, HVDR, 7), BendOut(8706, HVDL)),
    TileRow(104, sea, hillySahel),
    VertRow(103, BendIn(8706, HVUR, 13), OrigRt(8708, HVUL, 7)),
    VertRow(91, BendIn(8708, HVDR, 13)),
    VertRow(89, BendIn(8708, HVUR, 13)),
    VertRow(87, OrigRt(8704, HVUR, 7, siceWin), BendIn(8706, HVDn, 13, SeaIceWinter), BendOut(8708, HVUp, 7, SeaIceWinter)),
    TileRow(86, ice)
    )
  }
  help.run
}