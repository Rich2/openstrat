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
  { override val rows: RArr[RowBase] = RArr(
    TRow(146, SeaIcePerm),
    TRow(144, SeaIcePerm),
    TRow(142, SeaIceWinter),
    TRow(140, SeaIceWinter * 2),
    VRow(139, Orig(6656, HVUR, 2, 4, siceWin), BendIn(6658, HVDn, 13, siceWin), BendIn(6660, HVDL, 13, siceWin)),
    TRow(138, mtainTundra * 2),
    VRow(137, BendOut(6660, HVUR, 7, siceWin), BendIn(6662, HVDL, 13, siceWin)),
    TRow(136, mtainTundra, hillyTundra * 2),
    VRow(135, Bend(6656, HVDR, 4, 2, siceWin), BendOut(6658, HVDn, 7, siceWin), ThreeDown(6660, 6, 13, 0, siceWin), Bend(6662, HVUL, 13, 2, siceWin)),
    TRow(134, mtainTundra, siceWin, hillyTundra),

    VRow(133, Bend(6650, HVDR, 5, 1, siceWin), BendIn(6652, HVDn, 6, siceWin), BendIn(6654, HVUp, 13, siceWin), BendIn(6656, HVUL, 13, siceWin),
      BendIn(6660, HVUR, 13, siceWin), BendOut(6662, HVDL, 6, siceWin)),

    VRow(131, SetSep(6649, siceWin)),
    TRow(130, SepB(siceWin)),
    VRow(89, SetSep(6645)),
    TRow(88, SepB()),
    VRow(87, SetSep(6645)),
    VRow(81, OrigRt(6652, HVDR, 7), OrigLt(6654, HVUL, 7)),
    TRow(80, sea, hillySubForest),
    VRow(79, OrigRt(6656, HVDn, 7)),
    TRow(78, sea, hillySubForest),
    VRow(77, OrigMin(6652, HVDR), ThreeDown(6654, 6, 0, 13), BendIn(6656, HVUL, 13)),
    TRow(76, sea, mtainOceForest),
    VRow(75, OrigLt(6648, HVDn), BendIn(6654, HVUL, 13)),
    TRow(74, hillyOce),
    VRow(73, BendIn(6648, HVUR, 7), BendIn(6650, HVUp, 13), BendIn(6652, HVUL, 13)),
    )
  }
  help.run
}