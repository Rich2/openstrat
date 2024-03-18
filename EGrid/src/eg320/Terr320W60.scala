/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° west to 45° wast, centred on 60° west. Hex tile scale of 320km. */
object Terr320W60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w60(120)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rows: RArr[RowBase] = RArr(
    TRow(166, ice),
    TRow(164, CapeOld(5, 1, ice, SeaIceWinter)),
    TRow(162, CapeOld(4, 1, ice, SeaIceWinter), ice),
    TRow(160, SeaIceWinter, ice),
    TRow(158, sea, ice * 2),
    TRow(156, tundra, sea, ice),
    TRow(154, tundra * 2, sea, ice),
    TRow(152, tundra, sea * 2, ice),
    TRow(150, sea * 3, CapeOld(4, 2, tundra)),
    TRow(148, taiga, CapeOld(5, 3, mtainOld), sea * 2, CapeOld(3, 2, tundra)),
    VRow(147, MouthOld(10746, HVDn)),
    TRow(146, taiga, hillyTaiga, sea * 3),
    TRow(144, taiga * 3, sea * 2),
    TRow(142, taiga * 3, taiga, sea * 2),
    VRow(141, BendMin(10754, HVDR), Source(10756, HVDL, 1, 5), SourceRt(10758, HVDn, 7)),
    TRow(140, taiga, mtainLakesTaiga, hillyLakesTaiga, taiga, sea * 2),

    VRow(139, SourceLt(10746, HVDR, 6, siceWin), BendIn(10748, HVUp, 13, siceWin), BendMax(10750, HVDn, siceWin, sea), ThreeDown(10752, 13, 2, 13),
      BendIn(10754, HVUL), BendIn(10758, HVUR), BendIn(10760, HVDL, 10)),

    TRow(138, taiga * 2, hillyContForest, taiga, taiga, sea * 2),
    VRow(137, Bend(10752, HVUR, 10, 2), ThreeDown(10754, 13, 0, 13)),
    TRow(136, hillyTaiga, taiga, taiga, taiga, sea * 3),

    VRow(135, SourceRt(10738, HVDL, 6, lake), BendOut(10744, HVDR, 7), SourceRt(10746, HVDL, 7), SourceLt(10748, HVUR, 7), BendOut(10750, HVDn, 7),
      BendIn(10752, HVUp, 13), BendIn(10754, HVUL, 13)),

    TRow(134, hillyContForest),
    VRow(133, BendOut(10742, HVDR), BendIn(10744, HVUL, 13)),
    TRow(132, hillyCont),
    VRow(131, SourceLt(10740, HVUR, 7), BendIn(10742, HVUL, 13))
    )
  }
  help.run
}