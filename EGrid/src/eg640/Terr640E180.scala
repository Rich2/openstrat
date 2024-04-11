/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E180 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e180(94)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(130, siceWin),
    VRow(129, BendOut(6654, HVUp, 7, siceWin), BendIn(6656, HVDn, 13, siceWin), ThreeDown(6658, 0, 8, 13, siceWin)),
    TRow(128, tundra),
    VRow(127, BendOut(6658, HVUR), BendIn(6660, HVDL, 8, siceWin)),
    TRow(126, hillyTundra, tundra),
    VRow(125, OrigLtRevDepr(6656, HVUL, 7, SeaIceWinter), ThreeDown(6658, 13, 13, 0, SeaIceWinter), BendIn(6660, HVUL, 13, SeaIceWinter)),
    TRow(124, siceWin, hillyTundra),
    VRow(123, BendMin(6652, HVDR, 3, siceWin), OrigRt(6654, HVDL, 7, siceWin), BendIn(6658, HVUR, 13, siceWin), ThreeDown(6660, 13, 13, 0, siceWin),
      Orig(6662, HVDL, 4, 2, siceWin)),
    TRow(122, SepB()),
    )
  }
  help.run
}