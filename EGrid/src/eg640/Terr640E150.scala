/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.
 * [[Isle8]] 77942.286km² => 100112.536km². Hokkaido 83423.84km². */
object Terr640E150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e150(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(130, siceWin),
    VRow(129, OrigMin(5632, HVDR, 3, siceWin), BendOut(5634, HVUp, 7, siceWin), BendIn(5636, HVDn, 13, siceWin)),
    TRow(128, tundra),
    TRow(126, mtainTundra, hillyTaiga),
    TRow(124, hillyTaiga, hillyTaiga),

    VRow(123, OrigRtRevDepr(5628, HVDL, 7, siceWin), BendIn(5630, HVDn, 13, siceWin), ThreeDown(5632, 11, 13, 13), BendMin(5636, HVDR, 3, siceWin),
      OrigRt(5638, HVDL, 7, siceWin)),

    TRow(122, hillyTaiga, hillyTaiga),
    VRow(121, BendOut(5630, HVDR), ThreeUp(5632, 13, 0, 13), OrigMin(5634, HVUL), OrigLt(5636, HVUp, 7, siceWin)),
    TRow(120, taiga),
    VRow(119, Bend(5628, HVDR, 7, 1), ThreeUp(5630, 0, 13, 6), BendIn(5632, HVDL, 13)),
    TRow(118, hillyOce),
    VRow(117, Bend(5626, HVDR, 10, 7), ThreeUp(5628, 3, 6, 6), ThreeDown(5630, 6, 0, 13), BendIn(5632, HVUL, 13)),
    TRow(116, hillyOce, sea * 2),
    VRow(115, BendOut(5628, HVDR, 7), BendIn(5630, HVUL, 13)),
    TRow(114, hillyOce, sea * 3),
    VRow(113, OrigLt(5626, HVUR, 7), BendIn(5628, HVUL, 13)),
    VRow(99, OrigMin(5622, HVUp)),
    TRow(98, hillyJungle * 2),
    VRow(97, BendOut(5632, HVUR, 7), BendIn(5634, HVDL, 13)),
    TRow(96, sea, jungle, mtainJungle),
    VRow(95, BendOut(5630, HVDn, 7), BendIn(5632, HVUp, 13), BendIn(5634, HVUL, 13)),
    TRow(94, savannah),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(116, "Honshu north")
    str(114, "Honshu middle")
    str(118, "Hokkaido")
  }
}