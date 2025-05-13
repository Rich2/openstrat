/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 135° west to 105° west, centred on 120° west. Hex tile scale 1 Megametre or 1000km. */
object TerrMegaW120 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w120(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(119, BendOut(8704, HVUp, 7, SeaIcePerm, siceWin), BendIn(8706, HVDn, 6, SeaIcePerm), BendIn(8708, HVDL, 6, SeaIcePerm, siceWin)),
    TileRow(118, tundra),
    TileRow(116, taiga),
    TileRow(114, taiga),
    TileRow(112, taiga * 2),
    VertRow(111, BendIn(8700, HVDR, 13), OrigLt(8702, HVDL, 7)),
    TileRow(110, hillyOce, hillySahel),
    VertRow(109, BendIn(8700, HVUR, 13), BendOut(8702, HVDL, 7)),
    TileRow(108, hillySub, hillySahel),
    VertRow(107, BendIn(8702, HVUR, 13), BendOut(8704, HVDL, 7), OrigMin(8708, HVDn, 2)),
    TileRow(106, sea, hillySahel, hillySahel),
    VertRow(105, BendIn(8704, HVUR, 13), BendIn(8706, HVUp, 13), ThreeUp(8708, 9, 0, 6), Bend(8710, HVDL, 2, 4)),
    VertRow(103, BendIn(8710, HVUR, 13), BendIn(8712, HVUp, 13)),
    TileRow(82, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(106, "" , "Baja")
  }
}