/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 460km.
 * [[Isle10]] 64603.127km² => 78919.609km². Sri Lanka 65610km². */
object Terr460E90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e90(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(146, SeaIcePerm),
    TileRow(144, SeaIceWinter),
    TileRow(142, hillyTundra),
    VertRow(143, BendIn(3582, HVDn, 10, sea, SeaIceWinter), ThreeDown(3584, 0, 13, 13, SeaIceWinter)),
    TileRow(140, tundra, hillyTundra),
    TileRow(138, taiga, mtainTundra),
    TileRow(136, taiga, hillyTaiga * 2),
    TileRow(134, taiga, hillyTaiga, taiga),
    TileRow(132, taiga * 2, hillyTaiga),
    TileRow(130, steppe * 2, hillyTaiga, hillySteppe),
    TileRow(128, mtainSteppe * 4),
    TileRow(126, sahel * 2, mtainSteppe, hillyDeshot),
    TileRow(124, hillySahel, mtainSteppe, hillySahel * 3),
    TileRow(122, deshot * 2, hillyDeshot, hillySahel, deshot),
    TileRow(120, mtainSteppe, hillyDeshot * 2, mtainSteppe * 2),
    TileRow(118, mtainJungle, hillySahel * 2, mtainSavannah * 3),
    TileRow(116, deshot, deshot, hillyDeshot, mtainJungle * 3),
    TileRow(114, hillyOce, oceanic, hillySavannah, savannah, hillyJungle, mtainJungle),
    TileRow(112, hillySavannah * 2, Land(Plain, Tropical), hillySavannah, mtainJungle * 2),
    VertRow(111, BendMin(3580, HVDR, 4), BendOut(3582, HVDn, 7)),
    TileRow(110, savannah, hillySavannah, sea * 2, hillyJungle, mtainJungle),
    VertRow(109, BendOut(3578, HVDR, 7), BendIn(3580, HVUL, 13), OrigMin(3590, HVDn)),
    TileRow(108, sahel, savannah, sea * 3, mtainJungle, hillySavannah),
    VertRow(107, BendOut(3576, HVDR, 7), BendIn(3578, HVUL, 13)),
    TileRow(106, savannah, sea * 4, mtainJungle, jungle),
    VertRow(105, BendOut(3570, HVDL, 7), Bend(3574, HVDR, 6, 7), ThreeUp(3576, 0, 6, 11), BendIn(3578, HVDL), BendIn(3590, HVDR), OrigMin(3592, HVDL)),
    TileRow(104, hillySavannah, hillyJungle, sea * 3, hillyJungle),

    VertRow(103, BendIn(3570, HVUR, 13), BendIn(3572, HVUp, 13), ThreeUp(3574, 6, 0, 13), BendIn(3576, HVUp), BendIn(3578, HVUL), BendIn(3590, HVUR, 13),
      BendOut(3592, HVDL, 7)),

    TileRow(102, sea * 4, hillyJungle, hillyJungle),
    VertRow(101, BendIn(3588, HVUR, 13), BendOut(3590, HVDL, 7), BendIn(3592, HVUR, 13), BendIn(3594, HVUp, 13), OrigMin(3596, HVDL)),
    TileRow(100, sea * 5, hillyJungle, hillyJungle),
    VertRow(99, BendIn(3590, HVUR, 10), OrigRt(3592, HVUL, 7), BendIn(3600, HVDL, 7)),
    TileRow(98, sea * 6, hillyJungle),
    VertRow(97, OrigRt(3598, HVUR), BendIn(3600, HVUL)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(104, "", "Sri Lanka")
  }
}