/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km terrain for 105° west to 75° west centred on 90° west. */
object Terr220W90 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w90(150, 166)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, taigaLakes * 3, taiga * 2, sea * 2, taigaLakes),
      VRow(165, MouthRt(9736, HVUL, 7),ThreeDown(9738, 0, 7, 7), BendOut(9740, HVUL, 7)),
      TRow(164, taigaLakes * 3, taiga * 3, taiga * 2),
      VRow(163, MouthRt(9720, HVUp, 7, lake), BendInOut(9738, HVUR, 13, 6), BendInOut(9740, HVDL, 13, 4)),
      TRow(162, taiga, taigaLakes, taiga, taigaLakes * 3, taiga, taigaLakes * 2),
      VRow(161, BendIn(9718, HVDR, 6, lake), BendIn(9720, HVUL, 6, lake), BendInOut(9740, HVUR, 11, 7), MouthLt(9742, HVDR, 7)),
      TRow(160, savannah, taigaLakes, taiga, taigaLakes * 3, taiga * 2, taigaLakes),
      VRow(159, BendIn(9718, HVUR, 6, lake), Mouth(9720, HVDR, lake)),
      TRow(158, savannah * 2, taigaLakes * 3, taiga * 3, taigaLakes),
      TRow(156, savannah * 2, taiga, taigaLakes, hillyLakesTaiga, mtainLakes, hillyLakesTaiga * 2, taigaLakes, hillyLakesTaiga),

      VRow(155, MouthLt(9728, HVDL, 7, lake), BendOut(9730, HVDn, 7, lake), BendInOut(9732, HVUp, 13, 6, lake), BendInOut(9734, HVDn, 9, 6, lake),
        MouthLt(9736, HVDR, 6, lake)),

      TRow(154, savannah * 3, Land(LandLakes, Savannah, LandFree), taiga * 3, mtainLakes, hillyLakesTaiga, mtainLakes),

      VRow(153, Mouth(9732, HVUL, lake), BendIn(9734, HVDL, 13, lake), Mouth(9736, HVUL, lake), ThreeDown(9738, 6, 6, 6, lake),
        BendAll(9740, HVDn, lake, 8, 3), MouthLt(9742, HVDR, 7, lake)),

      TRow(152, hillySavannah, savannah * 2, hillySavannah, savannah * 2, SideB(lake), taiga, forest, Land(LandLakes, Temperate, Forest) * 2),

      VRow(151, BendAll(9732, HVDR, lake, 6, 7), BendIn(9734, HVUL, 13, lake), BendIn(9738, HVUR, 6, lake), MouthLt(9740, HVDR, 7, lake),
        Mouth(9744, HVDL, lake), MouthLt(9746, HVUR, 7, lake)),

      TRow(150, sahel, savannah * 3, hillySavannah, savannah * 3, forest, hillyForest * 2),
    )
  }
  help.run
}