/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 165° east to 165° west, centred on 180° east. Hex tile scale of 320km.
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
    TRow(156, tundra, SeaIceWinter * 2),
    TRow(154, tundra * 2, tundra, SeaIceWinter),
    VRow(153, Source(6662, HVDn, 6, 2, siceWin)),
    TRow(152, tundra, hillyTundra, tundra, taiga),
    VRow(151, ThreeUp(6662, 13, 13, 11)),
    TRow(150, tundra * 2, sea, tundra),
    TRow(148, tundra, sea * 3, tundra),
    VRow(144, SetSep(6646))
    )
  }
  help.run
}