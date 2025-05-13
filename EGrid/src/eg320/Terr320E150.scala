/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale of 320km. */
object Terr320E150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e150(120)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(166, SeaIcePerm),
    TileRow(164, SeaIceWinter),
    TileRow(162, SeaIceWinter * 2),
    TileRow(160, SeaIceWinter * 2),
    TileRow(158, tundra, SeaIceWinter * 2),
    TileRow(156, tundra * 2, tundra),
    TileRow(154, tundra * 4),
    TileRow(152, tundra, taiga * 3),
    TileRow(150, taiga * 4),
    TileRow(148, taiga * 3, sea, taiga),
    VertRow(147, Orig(5642, HVDR, 7, 1, siceWin), ThreeDown(5644, 13, 0, 13, siceWin)),
    TileRow(146, sea * 3, taiga, hillyTaiga),
    VertRow(145, BendOut(5642, HVDR, 7, siceWin), BendIn(5644, HVUL, 13,siceWin)),
    TileRow(144, hillyTaiga, sea * 2, hillyTaiga * 2),
    VertRow(143, BendIn(5640, HVDR, 6, siceWin), BendIn(5642, HVUL, 11, siceWin)),
    TileRow(142, taiga, hillyBoreal, sea * 2, hillyTaiga),
    VertRow(141, OrigMin(5638, HVUR, 3, siceWin), BendIn(5640, HVUL, 7, siceWin)),
    TileRow(140, taiga),
    VertRow(139, Orig(5624, HVDn ,3, 5)),
    TileRow(138, mtainTaiga, hillyContForest),
    VertRow(137, BendMax(5622, HVDR), ThreeUp(5624, 0, 11, 13), ThreeDown(5626, 3, 3, 9), BendIn(5628, HVUL, 13)),
    TileRow(136, mtainContForest, mtainContForest),
    VertRow(135, BendInRt(5620, HVDR, 13, 7), BendMax(5622, HVUL)),
    TileRow(134, hillyContForest * 2),
    VertRow(133, BendIn(5620, HVUR, 13), BendIn(5622, HVUp), Bend(5624, HVDn, 4, 4), ThreeDown(5626, 10, 0, 13)),
    TileRow(132, sea, mtainContForest),
    VertRow(131, BendIn(5616, HVDR, 13), BendIn(5618, HVDn, 13), OrigLt(5626, HVUp, 7)),
    TileRow(130, hillyOce, hillyOce),
    VertRow(129, BendOut(5616, HVUL, 7), OrigRt(5624, HVDL, 7)),
    TileRow(128, hillyOce),
    )
  }
  help.run
}
