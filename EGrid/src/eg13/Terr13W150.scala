/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 1300km [[WTile]] terrain for 75 East to 105 East.
 * Below 35732.005km². Hawaii 28311km². */
object Terr13W150 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w150(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(115, BendOut(7680, HVUp, 7, siceWin), BendIn(7682, HVDn, 6, siceWin, siceWin), BendOut(7684, HVUp, 7, SeaIcePerm, siceWin)),
    TileRow(114, tundra),
    VertRow(113, BendIn(7678, HVDL, 6, siceWin)),
    TileRow(112, hillyTundra),
    VertRow(111, BendIn(7678, HVUL, 13, siceWin)),
    VertRow(109, OrigLt(7682, HVDn, 7)),
    TileRow(92, SepB(), sea),
    TileRow(86, siceWin)
    )
  }
  help.run
}