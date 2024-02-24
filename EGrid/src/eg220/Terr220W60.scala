/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 75° west to 45° west, centred on 60° west.  */
object Terr220W60 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w60(140, 166)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, hillyLakesTaiga, mtainLakesTaiga, hillyLakesTaiga, mtain),
      VRow(165, BendOut(10752, HVUR), MouthLt(10754, HVDR)),
      TRow(164, taigaLakes, hillyLakesTaiga * 3),
      VRow(163, MouthRt(10758, HVUL, 7), BendIn(10760, HVDL, 13)),
      TRow(162, taigaLakes, hillyLakesTaiga * 5, SepB()),
      VRow(161, BendOut(10758, HVDR, 7), BendIn(10760, HVUL, 13)),
      TRow(160, hillyLakesTaiga * 2, mtainLakesTaiga * 2, hillyLakesTaiga * 2),
      VRow(159, BendIn(10752, HVDR, 13), BendIn(10754, HVDn, 13), BendIn(10756, HVUp, 13), ThreeUp(10758, 0, 7, 7), MouthLt(10760, HVDR, 7)),
      TRow(158, hillyLakesTaiga, mtainLakesTaiga, sea * 2, mtain, hillyLakesTaiga),
      VRow(157, MouthLt(10742, HVDL), MouthRt(10744, HVUR, 7), MouthRt(10752, HVDn, 7)),
      TRow(156, mtainLakesTaiga, hillyTaiga, mtain, sea * 2, hillyLakesTaiga * 2),
      VRow(155, MouthOld(10748, HVDL), MouthLt(10750, HVUR, 7)),
      TRow(154, taiga, hillyTaiga * 4),
      TRow(152, hillyTaiga, hillyLakesTaiga, taigaLakes, hillyLakesTaiga),
      VRow(151, BendOut(10740, HVDR, 6), MouthRt(10742, HVUR, 7), MouthLt(10748, HVDL, 7), MouthRt(10750, HVUR, 7)),
      TRow(150, hillyTaiga * 2),
      VRow(149, BendOut(10738, HVDR, 7), BendIn(10740, HVUL, 13)),
      TRow(148, hilly * 2),
      VRow(147, BendAllOld(10732, HVDR), BendOut(10734, HVDn, 7), BendIn(10736, HVUp, 13), BendIn(10738, HVUL, 10)),
      VRow(145, MouthLt(10732, HVDn)),
    )
  }
  help.run
}