/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 460km.
 * [[Isle10]] 64603.127km² => 78919.609km². Sri Lanka 65610km². */
object Terr460E90 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e90(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(146, SeaIcePerm),
    TRow(144, SeaIceWinter),
    TRow(142, hillyTundra),
    VRow(143, BendIn(3582, HVDn, 10, sea, SeaIceWinter), ThreeDown(3584, 0, 13, 13, SeaIceWinter)),
    TRow(140, tundra, hillyTundra),
    TRow(138, taiga, mtainDepr),
    TRow(136, taiga, hillyTaiga, mtainDepr),
    TRow(134, taiga, mtainDepr, taiga),
    TRow(132, taiga * 2, hillyTaiga),
    TRow(130, savannah * 2, mtainDepr, hillySavannah),
    TRow(128, mtainDepr * 4),
    TRow(126, sahel * 2, mtainDepr, hillyDeshot),
    TRow(124, hillySahel, mtainDepr, hillySahel * 3),
    TRow(122, deshot * 2, hillyDeshot, hillySahel, deshot),
    TRow(120, mtainDepr, hillyDeshot * 2, mtainDepr * 2),
    TRow(118, mtainDepr, hillySahel * 2, mtainDepr * 3),
    TRow(116, deshot, deshot, hillyDeshot, mtainDepr * 3),
    TRow(114, hillyOce, oceanic, hillySavannah, savannah, hillyJungle, mtainDepr),
    TRow(112, hillySavannah * 2, Land(Plain, Tropical), hillySavannah, mtainDepr * 2),
    VRow(111, BendMin(3580, HVDR, 4), BendOut(3582, HVDn, 7)),
    TRow(110, savannah, hillySavannah, sea * 2, hillyJungle, mtainDepr),
    VRow(109, BendOut(3578, HVDR, 7), BendIn(3580, HVUL, 13), OrigMinRevDepr(3590, HVUp)),
    TRow(108, sahel, savannah, sea * 3, mtainDepr, hillySavannah),
    VRow(107, BendOut(3576, HVDR, 7), BendIn(3578, HVUL, 13)),
    TRow(106, savannah, sea * 4, mtainDepr, jungle),
    VRow(105, BendOut(3570, HVDL, 7), Bend(3574, HVDR, 6, 7), ThreeUp(3576, 0, 6, 11), BendIn(3578, HVDL), BendIn(3590, HVDR), OrigMinRevDepr(3592, HVUR), OrigMinRevDepr(3594, HVDL)),
    TRow(104, hillySavannah, hillyJungle, sea * 3, hillyJungle),

    VRow(103, BendIn(3570, HVUR, 13), BendIn(3572, HVUp, 13), ThreeUp(3574, 6, 0, 13), BendIn(3576, HVUp), BendIn(3578, HVUL), BendIn(3590, HVUR, 13),
      BendOut(3592, HVDL, 7)),

    TRow(102, sea * 4, hillyJungle, hillyJungle),
    VRow(101, BendIn(3588, HVUR, 13), BendOut(3590, HVDL, 7), BendIn(3592, HVUR, 13), BendIn(3594, HVUp, 13), OrigMinRevDepr(3596, HVUR)),
    TRow(100, sea * 5, hillyJungle, hillyJungle),
    VRow(99, BendIn(3590, HVUR, 10), OrigRtRevDepr(3592, HVDR, 7), BendIn(3600, HVDL, 7)),
    TRow(98, sea * 6, hillyJungle),
    VRow(97, OrigRtRevDepr(3598, HVDL), BendIn(3600, HVUL)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(104, "", "Sri Lanka")
  }
}