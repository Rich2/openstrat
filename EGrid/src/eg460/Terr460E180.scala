/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg460
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 460km.  */
object Terr460E180 extends Long460Terrs
{ override implicit val grid: EGrid460LongFull = EGrid460.e180(82)
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
    )
  }
  help.run
}