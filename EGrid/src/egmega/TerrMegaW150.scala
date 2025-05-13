/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 165° west to 135° west, centred on 150° west. Hex tile scale 1 Megametre or 1000km.
 * [[Isle3]] 21143.198km² => 41440.668km². Hawaii 28311 km². */
object TerrMegaW150 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w150(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(119, BendOut(7680, HVUp, 7, siceWin), BendIn(7682, HVDn, 13, siceWin), BendOut(7684, HVUp, 7, SeaIcePerm, siceWin)),
    TileRow(118, tundra),
    TileRow(116, SepB(siceWin), hillyTaiga),
    VertRow(115, OrigLt(7680, HVDn, 7)),
    TileRow(114, mtainTundra),
    VertRow(113, BendIn(7680, HVUR, 13), OrigRt(7682, HVUL, 7)),
    VertRow(111, BendIn(7684, HVDR, 13), OrigLt(7686, HVDL, 7)),
    VertRow(109, BendIn(7684, HVUR, 13)),
    TileRow(106, Isle3(mtainJungle)),
    TileRow(82, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(106, "Hawaii")
  }
}