/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 1300km [[WTile]] terrain for 175° east to 175° west, centred on 180° east. Hex tile area 1.463582932 million km²,
 *  [[Isle7]] 241548.355km² => 321588.046km². New Zealand has a land area of 268021km2*/
object Terr13E180 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e180(86)

  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(115, BendIn(6656, HVDR, 13, siceWin), BendIn(6658, HVDn, 13, siceWin), BendOut(6660, HVUp, 7, siceWin)),
    TileRow(114, hillyTundra),
    VertRow(113, BendIn(6656, HVUR, 13, siceWin), BendIn(6658, HVDL, 6, siceWin)),
    TileRow(112, hillyTundra),
    VertRow(111, BendOut(6656, HVDR, 7, siceWin), BendIn(6658, HVUL, 13, siceWin)),
    VertRow(109, ThreeDown(6654, 13, 0, 8), BendIn(6656, HVUL, 13, siceWin, sea)),
    VertRow(95, BendIn(6652, HVDL, 13)),
    VertRow(93, SetSep(6653)),
    TileRow(92, Isle7(hillyOce)),
    VertRow(87, BendMax(6654, HVDn, siceWin), BendMax(6656, HVUp, siceWin)),
    TileRow(86, siceWin)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(92, "New Zealand")
  }
}