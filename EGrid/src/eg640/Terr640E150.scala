/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg640
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 640km.  */
object Terr640E150 extends Long640Terrs
{ override implicit val grid: EGrid640LongFull = EGrid640.e150(96)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(130, siceWin),
    VRow(129, SourceMin(5632, HVDR, siceWin), BendOut(5634, HVUp, 7, siceWin), BendIn(5636, HVDn, 13, siceWin)),
    TRow(128, tundra),
    TRow(126, mtainTundra, hillyTaiga),
    TRow(124, hillyTaiga, hillyTaiga),
    VRow(123, MouthRt(5628, HVDL, 7, siceWin), BendIn(5630, HVDn, 13, siceWin), ThreeDown(5632, 11, 13, 13), BendMin(5636, HVDR, siceWin), SourceRt(5638, HVDL, 7, siceWin)),
    TRow(122, hillyTaiga, hillyTaiga),
    VRow(121, ThreeUp(5632, 13, 0, 13), SourceMin(5634, HVUL), SourceLt(5636, HVUp, 7, siceWin)),
    TRow(120, taiga, sea * 2),
    TRow(118, Isle10(hillyOce)),
    VRow(117, Bend(5626, HVDR, 10, 7)),
    TRow(116, hillyOce, sea * 2),
    VRow(115),
    TRow(114, hillyOce, sea * 3),
    VRow(99, MouthOld(5622, HVDn)),
    TRow(98, hillyJungle),
    )
  }
  help.run
}