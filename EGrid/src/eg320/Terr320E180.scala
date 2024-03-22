/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 165° east to 165° west, centred on 180° east. Hex tile scale of 320km.
 * [[Tile4]] 4243.524km² => 7014.805km². St Lawrence Island 4640.1km².
 * [[Tile3]] 2165.063km² => 4243.524km². Nunivak. 4226.8km2. */
object Terr320E180 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e180(120)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(166, SeaIcePerm),
    TRow(164, SeaIceWinter),
    TRow(162, SeaIceWinter * 2),
    TRow(160, SeaIceWinter * 2),
    TRow(158, SeaIceWinter * 3),
    TRow(156, tundra, hillyTundra, SeaIceWinter),
    TRow(154, tundra * 2, tundra, SeaIceWinter),
    VRow(153, Source(6662, HVDn, 6, 2, siceWin)),
    TRow(152, tundra, hillyTundra, tundra, taiga),

    VRow(151, SourceLt(6656, HVUR, 6, siceWin), BendOut(6658, HVDn, 7, siceWin), ThreeDown(6660, 9, 12, 0, siceWin), ThreeUp(6662, 13, 12, 11, siceWin),
      ThreeDown(6664, 13, 1, 12, siceWin), Source(6666, HVDL, 2, 7, siceWin)),

    TRow(150, tundra * 2, siceWin, tundra),

    VRow(149, BendIn(6650, HVDR, 6, siceWin), BendIn(6652, HVDn, 6, siceWin), BendIn(6654, HVUp, 13, siceWin), SourceRt(6656, HVDL, 7, siceWin),
      BendIn(6660, HVUR, 12, siceWin), ThreeDown(6662, 12, 13, 0, siceWin), ThreeUp(6664, 0, 13, 12, siceWin), BendIn(6666, HVDL, 13, siceWin)),

    TRow(148, tundra, sea * 3, tundra),

    VRow(147, Source(6646, HVDR, 7, 1, siceWin), ThreeDown(6648, 13, 0, 13, siceWin), BendIn(6650, HVUL, 9, siceWin), BendIn(6662, HVUR, 13, siceWin),
      BendIn(6664, HVUp, 13, siceWin), ThreeUp(6666, 12, 0, 13, siceWin)),

    VRow(145, BendIn(6648, HVUL, 13, siceWin), BendOut(6646, HVDR, 7, siceWin)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(150, "" * 3, "St Lawrence Island")
    str(148, "" * 4, "Nunivak")
  }
}