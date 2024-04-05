/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** 640km [[WTile]] terrain terrain for 75° east to 105° east, centred on 90° east. A hex tile area of 354724.005km².
 *  [[Isle7]] 58543.317km² => 77942.286km². Sri Lanka 65610km². */
object Terr640E90 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e90(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[RowBase] = RArr(
      TRow(130, tundra),
      TRow(128, hillyTundra),
      TRow(126, taiga, Land(Mountains, Boreal, LandFree)),
      TRow(124, taiga * 2),
      TRow(122, oceForest, mtainDepr),
      TRow(120, savannah, sahel, sahel),
      TRow(118, sahel, hillyDeshot, hillyDeshot),
      TRow(116, deshot, deshot, hillyDeshot),
      TRow(114, Land(Mountains, DesertHot, LandFree), hillyDeshot, hillyDeshot, mtainDepr),
      TRow(112, mtainDepr, Land(Mountains, DesertHot, LandFree), mtainDepr, hillyOce),
      TRow(110, savannah, savannah, Land(Hilly, Savannah, Forest), Land(Mountains, Tropical, Forest)),
      TRow(108, hillySavannah, sea, Land(Hilly, Tropical), Land(Hilly, Tropical, Forest)),
      TRow(106, savannah, sea * 2, hillyJungle, hillyJungle),
      VRow(105, Bend(3574, HVDL, 4, 2), Bend(3578, HVDR, 1, 5), BendIn(3580, HVUL, 13), OrigRtRevDepr(3592, HVUL), BendIn(3594, HVDL, 13)),
      TRow(104, savannah, sea * 3, hillyJungle),
      VRow(103, BendIn(3574, HVUR), ThreeDown(3576, 6, 11, 0), ThreeUp(3578, 0, 11, 11), BendIn(3580, HVDL, 11), OrigRtRevDepr(3588, HVDL), BendIn(3590, HVDn, 13),
        BendIn(3592, HVDL, 13), BendOut(3594, HVUR, 7), ThreeDown(3596, 13, 0, 13)),
      TRow(102, hillyJungle, sea * 2, hillyJungle, hillyJungle),
      VRow(101, BendIn(3576, HVUR, 9), BendIn(3578, HVUp, 9), BendIn(3580, HVUL, 9), BendOut(3592, HVUR), ThreeDown(3594, 0, 13, 8)),
      TRow(100, sea * 4, hillyJungle),
      TRow(98, sea * 4, jungle)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(104, "India south")
    str(102, "Sri Lanka")
  }
}