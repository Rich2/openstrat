/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale of 320km. */
object Terr320E150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e150(120)
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
    TRow(158, tundra, SeaIceWinter * 2),
    TRow(156, tundra * 2, tundra),
    TRow(154, tundra * 4),
    TRow(152, tundra, taiga * 3),
    TRow(150, taiga * 4),
    TRow(148, taiga * 3, sea, taiga),
    VRow(147, Source(5642, HVDR, 7, 1, siceWin), ThreeDown(5644, 13, 0, 13, siceWin)),
    TRow(146, sea * 3, taiga, hillyTaiga),
    VRow(145, BendOut(5642, HVDR, 7, siceWin), BendIn(5644, HVUL, 13,siceWin)),
    TRow(144, sea * 3, hillyTaiga * 2),
    VRow(143, BendIn(5640, HVDR, 6, siceWin), BendIn(5642, HVUL, 11, siceWin)),
    TRow(142, taiga * 2, sea * 2, hillyTaiga),
    VRow(141, SourceMin(5638, HVUR, 3, siceWin), BendIn(5640, HVUL, 7, siceWin)),
    TRow(140, taiga),
    TRow(138, mtainTaiga),
    TRow(136, mtainContForest, mtainContForest),
    TRow(134, hillyContForest * 2),
    TRow(132, mtainContForest * 2),
    VRow(131, MouthOld(5626, HVDn)),
    TRow(130, hillyOce, hillyOce),
    TRow(128, hillyOce),
    )
  }
  help.run
}
