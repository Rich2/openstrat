/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 175° east to 175° west, centred on 180° east. Hex tile scale 1 megametre or 1000km.
 * Isle8 274015.850km² <= 165762.674km². Includes New Zealand combined */
object TerrMegaE180 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e180(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    VRow(119, BendIn(6656, HVDR, 13, siceWin), BendIn(6658, HVDn, 13, siceWin), BendOut(6660, HVUp, 7, siceWin)),
    TRow(118, tundra),
    VRow(117, BendIn(6656, HVUR, 13, siceWin), BendIn(6658, HVDL, 6, siceWin)),
    TRow(116,  hillyTundra),
    VRow(115, BendOut(6656, HVDR, 7, siceWin), BendIn(6658, HVUL, 13, siceWin)),
    TRow(114, SepB(), sea),
    TRow(90, Isle8(hillyOce)),
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(90, "New Zealand")
  }
}