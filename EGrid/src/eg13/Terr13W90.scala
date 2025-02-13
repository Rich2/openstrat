/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300km [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile area 1.463582932 million km².
 * Isle6 172942.905km² => 243930.488km². (Cuba 105806km²) + (Hispaniola 76479km²) + (Jamaica	11188km²) = 193473km². */
object Terr13W90 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w90(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(115, BendIn(9728, HVDR, 6, siceWin), BendIn(9730, HVDn, 13, siceWin), BendIn(9732, HVDL, 13, siceWin)),
    TileRow(114, SepB(siceWin), tundra),
    VertRow(113, BendIn(9728, HVUR, 7, siceWin), BendOut(9730, HVUp, 6, siceWin), ThreeUp(9732, 4, 13, 2, siceWin)),
    TileRow(112, taiga),
    TileRow(110, taiga),
    TileRow(108, hillyCont),
    TileRow(106, savannah, subtrop),
    VertRow(105, Orig(9728, HVDR, 6, 7), ThreeDown(9730, 8, 10, 7), BendIn(9732, HVDn, 10), BendIn(9734, HVDL, 10)),
    TileRow(104, hillyJungle, hillyJungle),
    VertRow(103, OrigRt(9724, HVUL, 7), Bend(9730, HVUR, 10, 4), Bend(9732, HVUp, 10, 4), ThreeUp(9734, 0, 10, 8)),
    TileRow(102, sea, hillyJungle),
    VertRow(101, OrigLt(9728, HVDR, 7), BendOut(9730, HVDL, 7)),
    TileRow(100, sea, hillyJungle),
    VertRow(99, BendIn(9728, HVDR, 13), BendOut(9730, HVUL, 6)),
    TileRow(98, sea, mtainSavannah),
    VertRow(97, BendIn(9728, HVUR, 13), OrigRt(9730, HVUL, 7)),
    VertRow(91, BendIn(9728, HVDR, 13), OrigMin(9730, HVDL, 1)),
    TileRow(90, mtainSteppe),
    VertRow(89, BendIn(9728, HVUR, 13), OrigRt(9730, HVUL, 7)),
    VertRow(87, BendIn(9726, HVDn, 13, siceWin), BendOut(9728, HVUp, 7, siceWin), BendIn(9730, HVDn, 13, siceWin), BendIn(9732, HVUp, 13, siceWin)),
    TileRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(108, "US North West")
    str(106, "US South Central", "US Deep South")
    str(104, "Yucatan", "Cuba")
  }
}