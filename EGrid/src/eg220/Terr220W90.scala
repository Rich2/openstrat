/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km terrain for 105° west to 75° west centred on 90° west. */
object Terr220W90 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w90(142, 166)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(166, lakesTaiga * 3, taiga * 2, sea * 2, lakesTaiga),
      VertRow(165, OrigRt(9736, HVDR, 7),ThreeDown(9738, 0, 7, 7), BendOut(9740, HVUL, 7)),
      TileRow(164, lakesTaiga * 3, taiga * 3, taiga * 2),
      VertRow(163, OrigRt(9720, HVDn, 7, lake), Bend(9738, HVUR, 13, 6), Bend(9740, HVDL, 13, 4)),
      TileRow(162, taiga, lakesTaiga, taiga, lakesTaiga * 3, taiga, lakesTaiga * 2),
      VertRow(161, BendIn(9718, HVDR, 6, lake), BendIn(9720, HVUL, 6, lake), Bend(9740, HVUR, 11, 7), OrigLt(9742, HVUL, 7)),
      TileRow(160, savannah, lakesTaiga, taiga, lakesTaiga * 3, taiga * 2, lakesTaiga),
      VertRow(159, BendIn(9718, HVUR, 6, lake), OrigMin(9720, HVUL, 3, lake)),
      TileRow(158, savannah * 2, lakesTaiga * 3, taiga * 3, lakesTaiga),
      TileRow(156, savannah * 2, taiga, lakesTaiga, hillyLakesTaiga, mtainLakesTaiga, hillyLakesTaiga * 2, lakesTaiga, hillyLakesTaiga),

      VertRow(155, Orig(9728, HVUR, 7, 3, lake), BendOut(9730, HVDn, 7, lake), Bend(9732, HVUp, 13, 6, lake), Bend(9734, HVDn, 9, 6, lake),
        OrigLt(9736, HVUL, 6, lake)),

      TileRow(154, savannah * 3, Land(PlainLakes, Savannah, LandFree), taiga * 3, mtainLakesTaiga, hillyLakesTaiga, mtainLakesTaiga),

      VertRow(153, OrigMin(9732, HVDR, 3, lake), BendIn(9734, HVDL, 13, lake), OrigMin(9736, HVDR, 3, lake), ThreeDown(9738, 6, 6, 6, lake),
        Bend(9740, HVDn, 8, 3, lake), OrigLt(9742, HVUL, 7, lake)),

      TileRow(152, hillySavannah, savannah * 2, hillySavannah, savannah * 2, SepB(lake), taiga, oceForest, Land(PlainLakes, Oceanic, Forest) * 2),

      VertRow(151, Bend(9732, HVDR, 6, 7, lake), BendIn(9734, HVUL, 13, lake), BendIn(9738, HVUR, 6, lake), OrigLt(9740, HVUL, 7, lake),
        OrigMin(9744, HVUR, 3, lake), BendIn(9746, HVDn, 13, lake), OrigMin(9748, HVUL, 3, lake)),

      TileRow(150, sahel, savannah * 3, hillySavannah, savannah * 3, oceForest, hillyOceForest * 2),

      VertRow(149, Orig(9732, HVUp, 4, 7, lake), OrigMin(9738, HVUR, 3,lake), BendIn(9740, HVDn, 13, lake), BendIn(9742, HVUp, 13, lake),
        OrigRt(9744, HVDL, 6, lake)),

      TileRow(148, sahel * 2, savannah, hillySavannah * 3, savannah * 3, hillyOceForest, mtainDepr),
      VertRow(147, BendMin(9752, HVDR)),
      TileRow(146, sahel, savannah * 2, hillySavannah * 2, savannah * 3, mtainDepr * 2, hillyOce),
      VertRow(145, OrigLt(9752, HVUp)),
      TileRow(144, sahel * 2, savannah, hillySavannah, hillyOceForest * 2, savannah, hillyOceForest, mtainDepr * 2, hillyOce),
      TileRow(142, hillyDeshot, sahel * 2, hillySavannah, mtainDepr),
    )
  }
  help.run
}