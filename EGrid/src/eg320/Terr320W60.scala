/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** [[WTile]] terrain for 75° west to 45° wast, centred on 60° west. Hex tile scale of 320km. */
object Terr320W60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w60(120)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[DateRow] = RArr(
    TileRow(166, ice),
    TileRow(164, SeaIceWinter),
    TileRow(162, SeaIceWinter, ice),
    TileRow(160, SeaIceWinter, ice),
    TileRow(158, sea, ice * 2),
    TileRow(156, tundra, sea, ice),
    TileRow(154, tundra * 2, sea, ice),
    TileRow(152, tundra, sea, mtainTundra, ice),
    TileRow(150, sea * 3, tundra),
    TileRow(148, taiga, mtainDepr, sea * 2, tundra),
    VertRow(147, OrigMin(10746, HVUp)),
    TileRow(146, taiga, hillyTaiga, sea * 3),
    TileRow(144, taiga * 3, sea * 2),
    TileRow(142, taiga * 3, taiga, sea * 2),
    VertRow(141, BendMin(10754, HVDR), Orig(10756, HVDL, 1, 5), OrigRt(10758, HVDn, 7)),
    TileRow(140, taiga, mtainLakesTaiga, hillyLakesTaiga, taiga, sea * 2),

    VertRow(139, OrigLt(10746, HVDR, 6, siceWin), BendIn(10748, HVUp, 13, siceWin), BendMax(10750, HVDn, siceWin, sea), ThreeDown(10752, 13, 2, 13),
      BendIn(10754, HVUL), BendIn(10758, HVUR), BendIn(10760, HVDL, 10)),

    TileRow(138, taiga * 2, hillyContForest, taiga, taiga, sea * 2),
    VertRow(137, Bend(10752, HVUR, 10, 2), ThreeDown(10754, 13, 0, 13)),
    TileRow(136, hillyTaiga, taiga, taiga, taiga, sea * 3),

    VertRow(135, OrigRt(10738, HVDL, 6, lake), BendOut(10744, HVDR, 7), OrigRt(10746, HVDL, 7), OrigLt(10748, HVUR, 7), BendOut(10750, HVDn, 7),
      BendIn(10752, HVUp, 13), BendIn(10754, HVUL, 13)),

    TileRow(134, hillyContForest),
    VertRow(133, BendOut(10742, HVDR), BendIn(10744, HVUL, 13)),
    TileRow(132, hillyCont),
    VertRow(131, OrigLt(10740, HVUR, 7), BendIn(10742, HVUL, 13))
    )
  }
  help.run
}