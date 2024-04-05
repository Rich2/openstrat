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
    override val rows: RArr[RowBase] = RArr(
      TRow(166, lakesTaiga * 3, taiga * 2, sea * 2, lakesTaiga),
      VRow(165, MouthRt(9736, HVUL, 7),ThreeDown(9738, 0, 7, 7), BendOut(9740, HVUL, 7)),
      TRow(164, lakesTaiga * 3, taiga * 3, taiga * 2),
      VRow(163, MouthRt(9720, HVUp, 7, lake), Bend(9738, HVUR, 13, 6), Bend(9740, HVDL, 13, 4)),
      TRow(162, taiga, lakesTaiga, taiga, lakesTaiga * 3, taiga, lakesTaiga * 2),
      VRow(161, BendIn(9718, HVDR, 6, lake), BendIn(9720, HVUL, 6, lake), Bend(9740, HVUR, 11, 7), MouthLt(9742, HVDR, 7)),
      TRow(160, savannah, lakesTaiga, taiga, lakesTaiga * 3, taiga * 2, lakesTaiga),
      VRow(159, BendIn(9718, HVUR, 6, lake), OrigMinRevDepr(9720, HVDR, 3, lake)),
      TRow(158, savannah * 2, lakesTaiga * 3, taiga * 3, lakesTaiga),
      TRow(156, savannah * 2, taiga, lakesTaiga, hillyLakesTaiga, mtainLakesTaiga, hillyLakesTaiga * 2, lakesTaiga, hillyLakesTaiga),

      VRow(155, Mouth(9728, HVDL, 7, 3, lake), BendOut(9730, HVDn, 7, lake), Bend(9732, HVUp, 13, 6, lake), Bend(9734, HVDn, 9, 6, lake),
        MouthLt(9736, HVDR, 6, lake)),

      TRow(154, savannah * 3, Land(PlainLakes, Savannah, LandFree), taiga * 3, mtainLakesTaiga, hillyLakesTaiga, mtainLakesTaiga),

      VRow(153, OrigMinRevDepr(9732, HVUL, 3, lake), BendIn(9734, HVDL, 13, lake), OrigMinRevDepr(9736, HVUL, 3, lake), ThreeDown(9738, 6, 6, 6, lake),
        Bend(9740, HVDn, 8, 3, lake), MouthLt(9742, HVDR, 7, lake)),

      TRow(152, hillySavannah, savannah * 2, hillySavannah, savannah * 2, SepB(lake), taiga, oceForest, Land(PlainLakes, Oceanic, Forest) * 2),

      VRow(151, Bend(9732, HVDR, 6, 7, lake), BendIn(9734, HVUL, 13, lake), BendIn(9738, HVUR, 6, lake), MouthLt(9740, HVDR, 7, lake),
        OrigMinRevDepr(9744, HVDL, 3, lake), BendIn(9746, HVDn, 13, lake), OrigMinRevDepr(9748, HVDR, 3, lake)),

      TRow(150, sahel, savannah * 3, hillySavannah, savannah * 3, oceForest, hillyOceForest * 2),
      VRow(149, Mouth(9732, HVDn, 4, 7, lake), OrigMinRevDepr(9738, HVDL, 3,lake), BendIn(9740, HVDn, 13, lake), BendIn(9742, HVUp, 13, lake), MouthRt(9744, HVUR, 6, lake)),
      TRow(148, sahel * 2, savannah, hillySavannah * 3, savannah * 3, hillyOceForest, mtainDepr),
      VRow(147, BendMin(9752, HVDR)),
      TRow(146, sahel, savannah * 2, hillySavannah * 2, savannah * 3, mtainDepr * 2, hillyOce),
      VRow(145, MouthLt(9752, HVDn)),
      TRow(144, sahel * 2, savannah, hillySavannah, hillyOceForest * 2, savannah, hillyOceForest, mtainDepr * 2, hillyOce),
      TRow(142, hillyDeshot, sahel * 2, hillySavannah, mtainDepr),
    )
  }
  help.run
}