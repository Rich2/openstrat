/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 165° west to 135° wast, centred on 150° west. Hex tile scale of 320km.
 * [[Tile6]] 10478.907km² => 14635.829km². Kodiak archipelago 13890km². */
object Terr320W150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w150(120)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(166, SeaIcePerm),
    TileRow(164, SeaIceWinter),
    TileRow(162, SeaIceWinter * 2),
    TileRow(160, SeaIceWinter * 2),
    TileRow(158, SeaIceWinter * 3),

    VertRow(157, Bend(7678, HVUp, 2, 4, siceWin), BendIn(7680, HVDn, 12, siceWin), Bend(7682, HVUp, 1, 5, siceWin), BendIn(7684, HVDn, 13, siceWin),
      BendOut(7686, HVUp, 7, siceWin)),

    TileRow(156, tundra * 3),
    TileRow(154, hillyTaiga * 3, taiga),
    TileRow(152, taiga * 3, hillyTaiga),
    VertRow(151, ThreeDown(7672, 13, 1, 12, siceWin), Orig(7674, HVDL, 2, 7, siceWin)),
    TileRow(150, hillyTundra, mtainDepr * 2, taiga),
    VertRow(149, SetSep(7671, siceWin)),
    TileRow(148, hillyTaiga * 2, sea * 2, mtainDepr),
    VertRow(147, ThreeUp(7670, 12, 0, 13, siceWin), BendIn(7672, HVDR, 13), Orig(7674, HVDL, 5, 1)),
    TileRow(146, hillyTundra, sea * 3, mtainDepr),
    TileRow(144, hillyTaiga, sea * 4),
    VertRow(143, OrigMin(7670, HVDR)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(146, "", "Kodiak Archipelago")
  }
}