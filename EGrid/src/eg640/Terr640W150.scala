/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain terrain for 165° west to 135° west, centred on 150° wast. Hex tile scale 640km.
 * [[Isle5]] 28059.223km² => 41915.629km². Hawaii 28311 km² */
object Terr640W150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.w150(70)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(130, siceWin),
    VertRow(129, ThreeDown(7678, 0, 8, 13, siceWin), BendIn(7680, HVDn, 13, siceWin), BendOut(7682, HVUp, 7, siceWin), BendOut(7684, HVDn, 7, siceWin)),
    TileRow(128, tundra),
    VertRow(127, SetSep(7677, siceWin)),
    TileRow(126, hillyTundra, taiga),
    VertRow(125, BendIn(7676, HVUL, 13, SeaIceWinter)),
    TileRow(124, hillyTundra, mtainDepr),
    VertRow(123, ThreeDown(7676, 13, 13, 0, siceWin), Orig(7678, HVDL, 4, 2, siceWin), Bend(7680, HVDR, 1,  5), BendOut(7682, HVDn, 7), BendIn(7684, HVDL)),
    TileRow(122, hillyTundra, sea),
    VertRow(121, BendIn(7676, HVUR, 13, sea, siceWin), BendIn(7678, HVUp, 13), BendIn(7680, HVUL, 13)),
    TileRow(108, Isle5(mtainDepr)),
    TileRow(74, SeaIceWinter * 2),
    TileRow(72, SeaIceWinter),
    TileRow(70, SeaIcePerm)
    )
  }
  help.run
}