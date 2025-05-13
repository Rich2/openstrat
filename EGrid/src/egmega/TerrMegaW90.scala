/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 1 Mm [[WTile]] terrain for 105° west to 75° west, centred on 90° west. Hex tile scale 1 Megametre or 1000km. Hex tile area of 866025.403 km².
 *  [[Isle6]] 102333.079km² => 142928.020km². Cuba 109884km².
 *  [[Isle5]] 68503.962km² => 102333.079km². Hispaniola 76192 km².
 *  [[Isle3]] 21143.198km² => 41440.668km².
 *  Below 21143.198km², Jamaica 10991km². */
object TerrMegaW90 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w90(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(119, BendIn(9728, HVDR, 5, siceWin), BendIn(9730, HVDn, 6, siceWin), BendIn(9732, HVDL, 9, siceWin)),
    TileRow(118, tundra),
    VertRow(117, BendIn(9728, HVUR, 6, siceWin), BendOut(9730, HVUp, 7, siceWin)),
    TileRow(116, taiga),
    TileRow(114, taiga),
    TileRow(112, taiga, taiga),
    TileRow(110, steppe, hillyOce),
    VertRow(109, OrigRt(9734, HVDn, 7)),
    TileRow(108, subtrop * 2),
    VertRow(107, BendOut(9728, HVDR, 7), Orig(9730, HVDL, 2, 4), BendIn(9732, HVDR, 10), ThreeUp(9734, 0, 11, 10)),
    TileRow(106, sahel, sea, Isle6(hillyJungle)),
    VertRow(105, Bend(9722, HVDL, 2, 4), OrigLt(9728,HVUp, 7), BendIn(9732, HVUR, 10), ThreeDown(9734, 10, 11, 0), ThreeUp(9736, 0, 11, 10)),
    TileRow(104, hillyJungle, jungle, sea),
    VertRow(103, BendIn(9722, HVUR, 13), BendIn(9724, HVUp, 13), BendOut(9726, HVDn), BendOut(9728, HVDL), OrigMin(9732, HVUR, 2), ThreeUp(9734, 11, 6, 0)),
    TileRow(102, sea, jungle, jungle),
    VertRow(101, BendIn(9728, HVUR, 13), ThreeDown(9730, 13, 13, 0), Orig(9732, HVDL, 7, 6)),
    TileRow(100, sea * 2, hillyJungle),
    VertRow(99, OrigRt(9730, HVUp, 7)),
    TileRow(98, sea * 2, hillyJungle),
    VertRow(89, OrigLt(9730, HVDn, 7)),
    TileRow(88, sea, hillySteppe),
    VertRow(87, BendIn(9728, HVDR, 13), BendOut(9730, HVUL, 7)),
    TileRow(86, hillyOce),
    VertRow(85, BendIn(9728, HVUR, 13), BendMax(9730, HVUp), Bend(9732, HVDn, 13, 4)),
    TileRow(82, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(108, "Mississippi", "US South")
    str(106, "" * 2, "Cuba")
    str(104, "", "Guatemala")
  }
}