/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 1300km [[WTile]] terrain for 75 east to 105 east, centred on 90° east. Tile area 1463582.932km².
 *  Isle9 413061.979km² => 515970.154km². Sumatra 443065kmm². */
object Terr13E90 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e90(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(114, tundra),
    TileRow(112, taiga),
    TileRow(110, mtainTundra),
    TileRow(108, hillyDeshot),
    TileRow(106, mtainTundra * 2),
    TileRow(104, jungle, hillyJungle),
    VertRow(103, OrigLt(3580, HVUL), OrigMax(3584, HVDn), OrigLt(3590, HVDR, 7)),
    TileRow(102, hillyJungle, jungle),
    VertRow(101, OrigLt(3580, HVDR), BendIn(3582, HVUp), ThreeUp(3584, 13, 0, 13), ThreeDown(3586, 10, 7, 0), Bend(3588, HVDn, 7, 4)),
    TileRow(100, sea, Isle9(hillyJungle)),
    VertRow(99, ThreeDown(3588, 7, 11, 0), ThreeUp(3590, 1, 7, 6)),
    VertRow(95, BendOut(3590, HVUL)),
    VertRow(87, OrigRt(3582, HVDR, 7, siceWin), BendIn(3584, HVUp, 7, siceWin), BendOut(3586, HVDn, 7, siceWin), BendIn(3588, HVUp, 13, siceWin)),
    TileRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(100, "", "Sumatra")
  }
}