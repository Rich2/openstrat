/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° West to 15° East, centred on 0° East. Hex tile scale 1 Megametre or 1000km.
 * [[Isle5]] 68503.962km² => 102333.079km². Ireland 84421km². */
object TerrMegaE0 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e0(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(119, BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), BendOut(516, HVUp)),
    TileRow(118, hillyTundra),
    VertRow(117, BendIn(510, HVDR, 13), BendOut(512, HVUL, 7)),
    TileRow(116, hillyTaiga),
    VertRow(115, BendIn(510, HVUR, 13)),
    TileRow(114, SepB(), oceanic),
    VertRow(113, SetSep(511)),
    TileRow(112, oceanic, hillyOce),
    VertRow(111, OrigLt(508, HVDn, 7), BendIn(512, HVDR, 13), BendIn(514, HVDn, 13), BendIn(516, HVDL, 13)),
    TileRow(110, hillyOce, savannah),
    VertRow(109, BendIn(508, HVUR, 10), BendIn(510, HVUp), BendMin(512, HVUL), BendOut(516, HVUR), BendOut(518, HVUp, 3)),
    TileRow(108, hillySavannah, sahel),
    TileRow(106, deshot * 3),
    TileRow(104, sahel, deshot * 2),
    TileRow(102, jungle * 3),
    VertRow(101, BendIn(506, HVUp, 13), OrigMin(508, HVDL), OrigRt(512, HVDR), BendOut(514, HVDL, 7)),
    TileRow(100, sea * 2, jungle),
    VertRow(99, BendIn(514, HVUR, 13), BendOut(516, HVDL)),
    TileRow(98, sea * 2, SepB(), jungle),
    VertRow(97, BendIn(514, HVDR, 13), BendOut(516, HVUL, 7)),
    TileRow(96, sea * 2, hillySavannah),
    VertRow(95, BendIn(514, HVUR, 13), BendOut(516, HVDL, 7)),
    TileRow(94, sea * 2, hillyDeshot),
    VertRow(93, BendIn(514, HVDR, 13), BendOut(516, HVUL, 7)),
    TileRow(92, sea, hillySavannah),
    VertRow(91, BendIn(514, HVUR, 13), OrigRt(516, HVUL, 7)),
    TileRow(84, siceWin),
    TileRow(82, ice),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(116, "Scandanavia west")
    str(114, "Europe middle west")
    str(112, "France", "Alps")
    str(110, "Iberia", "Algeria")
    str(92, "", "The Cape")
  }
}