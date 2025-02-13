/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E180 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e180(66)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(146, SeaIcePerm),
    TileRow(144, SeaIcePerm),
    TileRow(142, SeaIceWinter),
    TileRow(140, SeaIceWinter * 2),
    VertRow(139, Orig(6656, HVUR, 2, 4, siceWin), BendIn(6658, HVDn, 13, siceWin), BendIn(6660, HVDL, 13, siceWin)),
    TileRow(138, mtainTundra * 2),
    VertRow(137, BendOut(6660, HVUR, 7, siceWin), BendIn(6662, HVDL, 13, siceWin)),
    TileRow(136, mtainTundra, hillyTundra * 2),
    VertRow(135, Bend(6656, HVDR, 4, 2, siceWin), BendOut(6658, HVDn, 7, siceWin), ThreeDown(6660, 6, 13, 0, siceWin), Bend(6662, HVUL, 13, 2, siceWin)),
    TileRow(134, mtainTundra, siceWin, hillyTundra),

    VertRow(133, Bend(6650, HVDR, 5, 1, siceWin), BendIn(6652, HVDn, 6, siceWin), BendIn(6654, HVUp, 13, siceWin), BendIn(6656, HVUL, 13, siceWin),
      BendIn(6660, HVUR, 13, siceWin), BendOut(6662, HVDL, 6, siceWin)),

    VertRow(131, SetSep(6649, siceWin)),
    TileRow(130, SepB(siceWin)),
    VertRow(89, SetSep(6645)),
    TileRow(88, SepB()),
    VertRow(87, SetSep(6645)),
    VertRow(81, OrigRt(6652, HVDR, 7), OrigLt(6654, HVUL, 7)),
    TileRow(80, sea, hillySubForest),
    VertRow(79, OrigRt(6656, HVDn, 7)),
    TileRow(78, sea, hillySubForest),
    VertRow(77, OrigMin(6652, HVDR), ThreeDown(6654, 6, 0, 13), BendIn(6656, HVUL, 13)),
    TileRow(76, sea, mtainOceForest),
    VertRow(75, OrigLt(6648, HVDn), BendIn(6654, HVUL, 13)),
    TileRow(74, hillyOce),
    VertRow(73, BendIn(6648, HVUR, 7), BendIn(6650, HVUp, 13), BendIn(6652, HVUL, 13)),
    )
  }
  help.run
}