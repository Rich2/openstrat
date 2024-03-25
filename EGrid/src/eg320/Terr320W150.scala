/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 165° west to 135° wast, centred on 150° west. Hex tile scale of 320km.
 * [[Tile6]] 10478.907km² => 14635.829km². Kodiak archipelago 13890km². */
object Terr320W150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w150(120)
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

    VRow(157, Bend(7678, HVUp, 2, 4, siceWin), BendIn(7680, HVDn, 12, siceWin), Bend(7682, HVUp, 1, 5, siceWin), BendIn(7684, HVDn, 13, siceWin),
      BendOut(7686, HVUp, 7, siceWin)),

    TRow(156, tundra * 3),
    TRow(154, hillyTaiga * 3, taiga),
    TRow(152, taiga * 3, hillyTaiga),
    VRow(151, ThreeDown(7672, 13, 1, 12, siceWin), Source(7674, HVDL, 2, 7, siceWin)),
    TRow(150, hillyTundra, mtainOld * 2, taiga),
    VRow(149, SetSep(7671, siceWin)),
    TRow(148, hillyTaiga * 2, sea * 2, mtainOld),
    VRow(147, ThreeUp(7670, 12, 0, 13, siceWin), BendIn(7672, HVDR, 13), Source(7674, HVDL, 5, 1)),
    TRow(146, hillyTundra, sea * 3, mtainOld),
    TRow(144, hillyTaiga, sea * 4),
    VRow(143, MouthOld(7670, HVUL)),
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(146, "", "Kodiak Archipelago")
  }
}