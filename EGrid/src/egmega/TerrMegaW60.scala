/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 75° west to 45° west, centred on 60° west. Hex tile scale 1 Megametre or 1000km.
 * [[Isle6]] 102333.079km² => 142928.020km². Cuba 109884km².
 * [[Isle5]] 68503.962km² => 102333.079km². Hispaniola 76192 km². */
object TerrMegaW60 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w60(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(118, ice),
    VertRow(117, BendOut(10750, HVUp, 7, siceWin), ThreeUp(10752, 8, 13, 0, siceWin), BendIn(10754, HVDL, 13, siceWin)),
    TileRow(116, hillyTundra),
    VertRow(115, BendOut(10754, HVUR, 7), BendIn(10756, HVDL, 13)),
    TileRow(114, taiga),
    VertRow(113, BendIn(10756, HVUL, 13)),
    TileRow(112, taiga, sea),
    VertRow(111, OrigLt(10752, HVUR, 7), OrigRt(10754, HVDL, 7)),
    VertRow(109, OrigRt(10750, HVDn, 7)),
    TileRow(108, SepB()),
    VertRow(107, SetSep(10749)),
    VertRow(105, ThreeDown(10746, 10, 11, 0), ThreeUp(10748, 0, 11, 10)),
    TileRow(104, Isle5(hillyJungle)),
    VertRow(103, ThreeUp(10746, 11, 6, 0), ThreeUp(10750, 0, 6, 11), Bend(10752, HVUp, 4, 2), BendIn(10754, HVDn, 13), BendIn(10756, HVDL, 13)),
    TileRow(102, jungle, hillyJungle, sea),
    VertRow(101, BendMin(10756, HVUR), BendOut(10758, HVUp, 7), BendIn(10760, HVDn, 13)),
    TileRow(100, jungle * 2, jungle),
    TileRow(98, jungle * 2, jungle),
    TileRow(96, hillySavannah, Land(Plain, Tropical, CivMix), jungle),
    VertRow(95, BendMin(10760, HVDR, 5)),
    TileRow(94, mtainSavannah, savannah, savannah),
    VertRow(93, BendOut(10758, HVDR, 7), BendIn(10760, HVUL, 8)),
    TileRow(92, hillySavannah, savannah),
    VertRow(91, OrigLt(10756, HVUR, 7), BendIn(10758, HVUL, 13)),
    TileRow(90, hillySavannah, sea),
    VertRow(85, BendMax(10750, HVUp), Bend(10752, HVDn, 13, 4), BendOut(10754, HVUp, 7)),
    TileRow(84, siceWin),
    TileRow(82, siceWin)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(104, "Hispaniola")
  }
}