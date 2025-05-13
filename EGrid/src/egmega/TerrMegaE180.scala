/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 175° east to 175° west, centred on 180° east. Hex tile scale 1 megametre or 1000km.
 *  [[Isle9]] 244415.372km² => 305307.783km². New Zealand combined 268021km².
 *  [[Isle6]] 102333.079km² => 142928.020km². NZ North Island 111583.*/
object TerrMegaE180 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e180(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    VertRow(119, BendIn(6656, HVDR, 13, siceWin), BendIn(6658, HVDn, 13, siceWin), BendOut(6660, HVUp, 7, siceWin)),
    TileRow(118, tundra),
    VertRow(117, BendIn(6656, HVUR, 13, siceWin), BendIn(6658, HVDL, 6, siceWin)),
    TileRow(116, hillyTundra),
    VertRow(115, BendOut(6656, HVDR, 7, siceWin), BendIn(6658, HVUL, 13, siceWin)),
    TileRow(114, SepB(), sea),
    TileRow(90, Isle9(hillyOce)),
    TileRow(82, SeaIcePerm)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(90, "New Zealand")
  }
}