/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex.*, egrid.*, WTiles._, MultExt.*

/** [[WTile]] terrain for 45° west to 15° west, centred on 30° west. Hex tile scale 1 Megametre or 1000km.
 * [[Isle6]] 102333.079km² => 142928.020km². Iceland 103125km².
 * [[Isle5]] 68503.962km² => 102333.079km². Ireland 84421km².
 * [[Isle3]] 21143.198km² => 21143.198km².
 * Below min 21143.198km². Canaries 7492 km². */
object TerrMegaW30 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w30(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(119, OrigRt(11778, HVDR, 7), BendIn(11780, HVDL, 13)),
    TileRow(118, mtainIce),
    VertRow(117, BendOut(11778, HVDR, 7), BendIn(11780, HVUL, 13)),
    TileRow(116, SepB(siceWin), tundra),
    VertRow(115, ThreeDown(11776, 13, 11, 13), ThreeUp(11778, 0, 11, 13), BendIn(11780, HVDL, 11)),
    TileRow(114, Isle5(oceanic)),
    VertRow(111, OrigLt(11780, HVDn, 7)),
    VertRow(109, BendIn(11780, HVUR, 10), BendIn(11782, HVUp)),
    VertRow(107, OrigLt(11780, HVDn, 7)),
    TileRow(106, sea * 2, deshot),
    VertRow(105, BendIn(11778, HVDR, 13), BendOut(11780, HVUL, 7)),
    TileRow(104, sea * 2, sahel),
    VertRow(103, BendIn(11778, HVUR, 13), BendOut(11780, HVDL, 7)),
    TileRow(102, sea * 2, hillyJungle),

    VertRow(101, BendOut(11770, HVUp, 7), BendIn(11772, HVDn, 13), BendIn(11774, HVDL, 13), BendIn(11780, HVUR, 13), BendIn(11782, HVUp, 13),
      OrigMin(11784, HVDL)),

    TileRow(100, hillySavannah),
    VertRow(99, BendOut(11774, HVUR, 7), BendIn(11776, HVDL, 13)),
    TileRow(98, hillySavannah),
    VertRow(97, BendOut(11774, HVDR, 7), BendIn(11776, HVUL, 13)),
    TileRow(96, hillyJungle),
    VertRow(95, BendMin(11772, HVDR, 5), BendIn(11774, HVUL, 13)),
    VertRow(93, BendOut(11772, HVDR), SetSep(11773)),
    TileRow(92, SepB()),
    VertRow(91, OrigLt(11772, HVUR, 7)),
    VertRow(85, BendOut(11774, HVUp, 7), BendIn(11776, HVDn, 13)),
    TileRow(84, siceWin),
    TileRow(82, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(114, "Ireland")
  }
}