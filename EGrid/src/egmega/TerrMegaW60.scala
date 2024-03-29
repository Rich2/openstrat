/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° west to 45° west, centred on 60° west. Hex tile scale 1 Megametre or 1000km.
 * Isle6 102333.079km² => 142928.020km². Cuba 109884km².
 * Isle5 68503.962km² => 102333.079km². Hispaniola 76192 km². */
object TerrMegaW60 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w60(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(118, ice),
    VRow(117, BendOut(10750, HVUp, 7, siceWin), ThreeUp(10752, 8, 13, 0, siceWin), BendIn(10754, HVDL, 13, siceWin)),
    TRow(116, hillyTundra),
    VRow(115, BendOut(10754, HVUR, 7), BendIn(10756, HVDL, 13)),
    TRow(114, taiga),
    VRow(113, BendIn(10756, HVUL, 13)),
    TRow(112, taiga, sea),
    VRow(111, MouthLt(10752, HVDL, 7), MouthRt(10754, HVUR, 7)),
    VRow(109, MouthRt(10750, HVUp, 7)),
    TRow(108, SepB()),
    VRow(107, SetSep(10749)),
    VRow(105, ThreeDown(10746, 10, 11, 0), ThreeUp(10748, 0, 11, 10), BendIn(10750, HVDL, 11)),
    TRow(104, hillyJungle),
    VRow(103, ThreeUp(10746, 11, 6, 0), BendIn(10748, HVUp, 13), ThreeUp(10750, 0, 6, 11), Bend(10752, HVUp, 4, 2), BendIn(10754, HVDn, 13), BendIn(10756, HVDL, 13)),
    TRow(102, jungle, hillyJungle, sea),
    VRow(101, BendMin(10756, HVUR), BendOut(10758, HVUp, 7), BendIn(10760, HVDn, 13)),
    TRow(100, jungle * 2, jungle),
    TRow(98, jungle * 2, jungle),
    TRow(96, hillySavannah, Land(Plain, Tropical, CivMix), jungle),
    VRow(95, Bend(10760, HVDR, 4, 2)),
    TRow(94, Land(Mountains, Savannah), savannah, CapeOld(2, 1, savannah)),
    VRow(93, BendOut(10758, HVDR)),
    TRow(92, hillySavannah, CapeOld(2, 2, savannah)),
    VRow(91, MouthOld(10754, HVUL)),
    TRow(90, hillySavannah, sea),
    VRow(89, MouthOld(10748, HVUR)),
    TRow(84, siceWin),
    TRow(82, siceWin)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(104, "Hispaniola")
  }
}