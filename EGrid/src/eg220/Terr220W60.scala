/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTiles._

/** 220km [[WTile]] terrain for 75° west to 45° west, centred on 60° west.  */
object Terr220W60 extends Long220Terrs
{
  override implicit val grid: EGrid220LongFull = EGrid220.w60(150, 166)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, hillyLakesTaiga, mtainLakes, hillyLakesTaiga),
      TRow(164, taigaLakes, hillyLakesTaiga * 3),
      VRow(163, MouthRt(10758, HVUL, 7), BendIn(10760, HVDL, 13)),
      TRow(162, taigaLakes, hillyLakesTaiga * 5, SideB()),
      VRow(161, BendOut(10758, HVDR, 7), BendIn(10760, HVUL, 13)),
      TRow(160, hillyLakesTaiga * 2, mtainLakes * 2, hillyLakesTaiga * 2),
      VRow(159, MouthRt(10752, HVDL, 7), BendIn(10754, HVDn, 13), BendIn(10756, HVUp, 13), ThreeUp(10758, 0, 7, 7), MouthLt(10760, HVDR, 7)),
      TRow(158, hillyLakesTaiga, mtainLakes, sea * 2, mtain, hillyLakesTaiga),
      VRow(157, MouthLt(10742, HVDL), MouthRt(10744, HVUR, 7)),
      TRow(156, mtainLakes, hillyTaiga, mtain, sea * 2, hillyLakesTaiga * 2),
      VRow(155, Mouth(10748, HVDL), MouthLt(10750, HVUR, 7)),
      TRow(154, taiga, hillyTaiga * 4),
      TRow(152, hillyTaiga, hillyLakesTaiga, taigaLakes, hillyLakesTaiga),
      TRow(150, hillyTaiga),
    )
  }
  help.run
}