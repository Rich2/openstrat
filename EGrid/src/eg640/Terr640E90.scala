/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** 640km [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. A hex tile area of 354724.005km².
 *  [[Isle7]] 58543.317km² => 77942.286km². Sri Lanka 65610km². */
object Terr640E90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e90(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(130, SepB(SeaIceWinter), tundra),
    VertRow(129, SetSep(3583, SeaIceWinter)),
    TileRow(128, hillyTundra),
    TileRow(126, taiga, Land(Mountains, Boreal, LandFree)),
    TileRow(124, taiga * 2),
    TileRow(122, contForest, mtainDepr),
    TileRow(120, savannah, sahel, sahel),
    TileRow(118, sahel, hillyDeshot, hillyDeshot),
    TileRow(116, deshot, deshot, hillyDeshot),
    TileRow(114, Land(Mountains, DesertHot, LandFree), hillyDeshot, hillyDeshot, mtainDepr),
    TileRow(112, mtainDepr, Land(Mountains, DesertHot, LandFree), mtainDepr, hillyOce),
    TileRow(110, savannah, savannah, Land(Hilly, Savannah, Forest), Land(Mountains, Tropical, Forest)),
    TileRow(108, hillySavannah, sea, Land(Hilly, Tropical), Land(Hilly, Tropical, Forest)),
    VertRow(107, OrigMin(3588, HVDn, 6)),
    TileRow(106, savannah, sea * 2, hillyJungle, hillyJungle),
    VertRow(105, Bend(3574, HVDL, 4, 2), Bend(3578, HVDR, 1, 5), BendIn(3580, HVUL, 13), OrigRt(3588, HVUp, 7), OrigRt(3592, HVDR, 7), BendIn(3594, HVDL, 13)),
    TileRow(104, savannah, sea * 3, hillyJungle),

    VertRow(103, BendIn(3574, HVUR), ThreeDown(3576, 6, 11, 0), ThreeUp(3578, 0, 11, 11), BendIn(3580, HVDL, 11), OrigRt(3588, HVUR), BendIn(3590, HVDn, 13),
      BendIn(3592, HVDL, 13), BendOut(3594, HVUR, 7), ThreeDown(3596, 13, 0, 13)),

    TileRow(102, hillyJungle, sea * 2, hillyJungle, hillyJungle),
    VertRow(101, BendIn(3576, HVUR, 9), BendIn(3578, HVUp, 9), BendIn(3580, HVUL, 9), BendOut(3592, HVUR), ThreeDown(3594, 0, 13, 8), ThreeUp(3596, 0, 10, 13)),
    TileRow(100, sea * 4, hillyJungle),
    VertRow(99, OrigLt(3590, HVDR, 7), BendMin(3592, HVDL, 5), BendIn(3596, HVDL, 8)),
    TileRow(98, sea * 4, jungle),
    VertRow(97, BendIn(3592, HVUR, 13), ThreeDown(3594, 11, 6, 0), BendIn(3596, HVUL, 8)),
    TileRow(72, SeaIcePerm),
    TileRow(70, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(104, "India south")
    str(102, "Sri Lanka", "" * 2, "Sumatra north")
    str(100, "" * 4, "Sumatra middle")
    str(98, "" * 4, "Sumatra south")
  }
}