/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300km [[WTile]] terrain for 75 East to 105 East.
 * Below 35732.005km². Hawaii 28311km². */
object Terr13W150 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.w150(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
    VRow(115, BendOut(7680, HVUp, 7, siceWin), BendIn(7682, HVDn, 6, siceWin, siceWin), BendOut(7684, HVUp, 7, SeaIcePerm, siceWin)),
    TRow(114, tundra),
    VRow(113, BendIn(7678, HVDL, 6, siceWin)),
    TRow(112, hillyTundra),
    VRow(111, BendIn(7678, HVUL, 13, siceWin)),
    VRow(109, SourceLt(7682, HVDn, 7)),
    TRow(92, SepB(), sea),
    TRow(86, siceWin)
    )
  }
  help.run
}