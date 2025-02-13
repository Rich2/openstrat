/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 1 megametre or 1000km.
 * Isle4 41440.668km² => 68503.962km². SriLanka 65610 km² */
object TerrMegaE90 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e90(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(118, hillyTundra),
    TileRow(116, hillyTaiga),
    TileRow(114, taiga),
    TileRow(112, hillyDeshot, deshot),
    TileRow(110, deshot, hillyDeshot),
    TileRow(108, hillyDescold, hillySteppe),
    TileRow(106, jungle * 3),
    VertRow(105, BendMin(3582, HVDR), Orig(3584, HVDL, 2, 4), OrigRt(3592, HVDR, 7)),
    TileRow(104, jungle, sea, jungle),
    VertRow(103, BendIn(3580, HVDR, 12), ThreeUp(3582, 0, 12, 13), BendIn(3584, HVDL, 12), Bend(3592, HVDR, 13, 5)),
    TileRow(102, hillyJungle, sea, hillyJungle),

    VertRow(101, BendIn(3578, HVUp, 13), ThreeUp(3580, 12, 0, 8), BendIn(3582, HVUp, 12), BendIn(3584, HVUL, 12), OrigLt(3586, HVDn), OrigMin(3588, HVDR),
      ThreeDown(3590, 0, 6, 6), Bend(3592, HVUL, 13, 5)),

    TileRow(100, sea * 2, jungle),
    VertRow(99, BendIn(3586, HVUR, 13), BendOut(3588, HVDL, 7), BendIn(3590, HVUR), ThreeDown(3592, 6, 10, 8)),
    VertRow(97, BendIn(3588, HVUR, 13), BendIn(3590, HVUp, 13), ThreeUp(3592, 10, 0, 13)),
    TileRow(98, sea * 2, hillyJungle),
    VertRow(95, BendIn(3592, HVDR, 12)),
    VertRow(93, BendOut(3592, HVUR)),
    TileRow(82, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(104, "India south", "", "Burma")
    str(102, "Sri Lanka", "", "Thailand")
  }
}