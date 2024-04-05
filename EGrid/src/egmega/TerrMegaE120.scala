/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 1 megametre or 1000km.
 * [[Isle8]] 190288.785km² => 244415.372km². Most of Philippines excluding Luzon and Palawan.
 * [[Isle7]] 142928.020km² => 190288.785km². NZ South Island 145836km². Sulawesi 186216.16km².
 * [[Isle6]] 102333.079km² => 142928.020km². Luzon 109965km², Java 138,800km².
 * [[Isle5]] 68503.962km² => 102333.079km². Lesser Sunda Islands estimate 80000km.
 * [[Isle3]] 21143.198km² => 41440.668km². Taiwan 36197km², */
object TerrMegaE120 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e120(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(119, OrigRt(4610, HVDR, 7, siceWin), BendMin(4612, HVUp, 3, siceWin)),
    TRow(118, hillyTundra),
    TRow(116, hillyTaiga),
    VRow(115, OrigMax(4612, HVDn)),
    TRow(114, taiga),
    TRow(112, taiga, hillyTaiga),
    VRow(111, OrigLt(4608, HVDn), Bend(4612, HVDR, 7, 5), Bend(4614, HVUL, 4, 7)),
    TRow(110, savannah, hillyTaiga),
    VRow(109, BendIn(4608, HVUR), ThreeDown(4610, 0, 10, 7), BendIn(4612, HVUL), BendOut(4614, HVDR)),
    TRow(108, subtrop, hillySub),
    VRow(107, Bend(4608, HVDR, 13, 1), ThreeUp(4610, 13, 13, 8), ThreeDown(4612, 13, 0, 13), BendIn(4614, HVUL, 13)),
    TRow(106, hillyJungle, hillyJungle),
    VRow(105, OrigRt(4604, HVDR, 7), ThreeDown(4606, 13, 10, 13), ThreeUp(4608, 10, 13, 10), ThreeDown(4610, 13, 0, 10), BendIn(4612, HVUL, 13)),
    TRow(104, jungle, hillyJungle),
    VRow(103, Bend(4604, HVDR, 13, 5), ThreeUp(4606, 10, 13, 13), ThreeDown(4608, 10, 8, 13), ThreeUp(4610, 0, 8, 10), BendIn(4612, HVDL, 8)),
    TRow(102, hillyJungle, hillyJungle),

    VRow(101, ThreeDown(4602, 0, 6, 6), Bend(4604, HVUL, 13, 5), BendIn(4606, HVDR, 9), ThreeUp(4608, 8, 9, 0), ThreeDown(4610, 8, 13, 9),
      ThreeUp(4612, 0, 13, 8), BendOut(4614, HVUp, 7), BendIn(4616, HVDn, 13)),

    TRow(100, hillyJungle, hillyJungle, hillyJungle),
    VRow(99, BendIn(4602, HVUR), ThreeDown(4604, 6, 10, 8), ThreeUp(4606, 9, 10, 0), ThreeDown(4608, 9, 11, 10), ThreeUp(4610, 13, 11, 9),
      Bend(4612, HVDL, 11, 7)),

    TRow(98, hillyJungle, mtainJungle, jungle),

    VRow(97, BendIn(4602, HVUp, 13), ThreeUp(4604, 10, 0, 13), ThreeDown(4606, 10, 13, 0), ThreeUp(4608, 11, 13, 10), Bend(4610, HVUp, 11, 7),
      ThreeUp(4612, 13, 5, 11), BendIn(4614, HVUp, 13), Bend(4616, HVDn, 6, 6)),

    TRow(96, sea, hillySavannah, savannah),
    VRow(95, BendIn(4604, HVDR, 12), BendOut(4606, HVUL, 7)),
    TRow(94, deshot, deshot, sahel),
    VRow(93, BendIn(4604, HVUR, 13), BendOut(4606, HVDL, 7)),
    TRow(92, savannah, deshot),
    VRow(91, MouthRt(4606, HVDn, 7), MouthLt(4608, HVDL, 7), Bend(4610, HVDn, 5, 1), BendOut(4612, HVDL, 7)),
    VRow(89, BendIn(4612, HVUR, 13), MouthRt(4614, HVDR, 7)),
    TRow(82, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(112, "Manchuria")
    str(110, "China north", "Korea")
    str(108, "China east", "Japan south")
    str(106, "China south", "Taiwan")
    str(104, "Vietnam", "Luzon")
    str(102,"Borneo north", "Philippines south")
    str(100, "Borneo west", "Sulawesi", "Papua west")
    str(98, "Java", "Lesser Sunda")
  }
}