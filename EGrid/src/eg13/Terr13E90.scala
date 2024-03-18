/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300km [[WTile]] terrain for 75 east to 105 east, centred on 90° east. Tile area 1463582.932km².
 *  Isle9 413061.979km² => 515970.154km². Sumatra 443065kmm². */
object Terr13E90 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e90(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(114, tundra),
    TRow(112, taiga),
    TRow(110, mtainTundra),
    TRow(108, hillyDeshot),
    TRow(106, mtainTundra * 2),
    TRow(104, jungle, hillyJungle),
    VRow(103, SourceLt(3580, HVUL), SourceMax(3584, HVDn), SourceLt(3590, HVDR, 7)),
    TRow(102, hillyJungle, jungle),
    VRow(101, SourceLt(3580, HVDR), BendIn(3582, HVUp), ThreeUp(3584, 13, 0, 13), BendIn(3586, HVDR, 7), BendIn(3588, HVDn, 7), BendIn(3590, HVDL, 7)),
    TRow(100, sea, jungle),
    VRow(99, BendIn(3586, HVUR, 7), ThreeDown(3588, 7, 11, 0), ThreeUp(3590, 1, 7, 6)),
    VRow(95, BendOut(3590, HVUL)),
    VRow(87, SourceRt(3582, HVDR, 7, siceWin), BendIn(3584, HVUp, 7, siceWin), BendOut(3586, HVDn, 7, siceWin), BendIn(3588, HVUp, 13, siceWin)),
    TRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(100, "", "Sumatra")
  }
}