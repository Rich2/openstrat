/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° west to 15° west, centred on 30° west. Hex tile scale 1 Megametre or 1000km.
 * [[Isle6]] 102333.079km² => 142928.020km². Iceland 103125km².
 * [[Isle5]] 68503.962km² => 102333.079km². Ireland 84421km².
 * Isle3 21143.198km² => 21143.198km².
 * Below min 21143.198km². Canaries 7492 km². */
object TerrMegaW30 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w30(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
    VRow(119, SourceRt(11778, HVDR, 7), BendIn(11780, HVDL, 13)),
    TRow(118, mtainIce),
    VRow(117, BendOut(11778, HVDR, 7), BendIn(11780, HVUL, 13)),
    TRow(116, SepB(siceWin), tundra),
    VRow(115, ThreeDown(11776, 13, 11, 13), ThreeUp(11778, 0, 11, 13)),
    TRow(114, oceanic),
    VRow(113, BendIn(11776, HVUR, 11), BendIn(11778, HVUp, 11)),
    VRow(111, MouthLt(11780, HVUp, 7)),
    VRow(109, BendIn(11780, HVUR, 10), BendIn(11782, HVUp)),
    VRow(107, MouthLt(11780, HVUp, 7)),
    TRow(106, sea * 2, deshot),
    VRow(105, BendIn(11778, HVDR, 13), BendOut(11780, HVUL, 7)),
    TRow(104, sea * 2, sahel),
    VRow(103, BendIn(11778, HVUR, 13), BendOut(11780, HVDL, 7)),
    TRow(102, sea * 2, hillyJungle),
    VRow(101, BendOut(11770, HVUp, 7), BendIn(11772, HVDn, 13), BendIn(11774, HVDL, 13), BendIn(11780, HVUR, 13), BendIn(11782, HVUp, 13), MouthMin(11784, HVUR)),
    TRow(100, hillySavannah),
    VRow(99, BendOut(11774, HVUR, 7), BendIn(11776, HVDL, 13)),
    TRow(98, hillySavannah),
    VRow(97, BendOut(11774, HVDR, 7), BendIn(11776, HVUL, 13)),
    TRow(96, hillyJungle),
    VRow(95, Bend(11772, HVDR, 4, 2), BendIn(11774, HVUL, 13)),
    VRow(93, BendOut(11772, HVDR), SetSep(11773)),
    VRow(91, SetSep(11773)),
    TRow(82, ice)
    )
  }
  help.run
}