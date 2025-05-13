/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex.*, egrid.*, WTiles.*, MultExt.*

/** 220km [[WTile]] terrain for 75° west to 45° west, centred on 60° west.  */
object Terr220W60 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w60(140, 166)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rows: RArr[DateRow] = RArr(
      TileRow(166, hillyLakesTaiga, mtainLakesTaiga, hillyLakesTaiga, mtainDepr),
      VertRow(165, BendOut(10752, HVUR), OrigLt(10754, HVUL)),
      TileRow(164, lakesTaiga, hillyLakesTaiga * 3),
      VertRow(163, OrigRt(10758, HVDR, 7), BendIn(10760, HVDL, 13)),
      TileRow(162, lakesTaiga, hillyLakesTaiga * 5, SepB()),
      VertRow(161, BendOut(10758, HVDR, 7), BendIn(10760, HVUL, 13)),
      TileRow(160, hillyLakesTaiga * 2, mtainLakesTaiga * 2, hillyLakesTaiga * 2),
      VertRow(159, BendIn(10752, HVDR, 13), BendIn(10754, HVDn, 13), BendIn(10756, HVUp, 13), ThreeUp(10758, 0, 7, 7), OrigLt(10760, HVUL, 7)),
      TileRow(158, hillyLakesTaiga, mtainLakesTaiga, sea * 2, mtainDepr, hillyLakesTaiga),
      VertRow(157, OrigLt(10742, HVUR), OrigRt(10744, HVDL, 7), OrigRt(10752, HVUp, 7)),
      TileRow(156, mtainLakesTaiga, hillyTaiga, mtainDepr, sea * 2, hillyLakesTaiga * 2),
      VertRow(155, OrigMin(10748, HVUR), OrigLt(10750, HVDL, 7)),
      TileRow(154, taiga, hillyTaiga * 4),
      TileRow(152, hillyTaiga, hillyLakesTaiga, lakesTaiga, hillyLakesTaiga),
      VertRow(151, BendOut(10740, HVDR, 6), OrigRt(10742, HVDL, 7), OrigLt(10748, HVUR, 7), OrigRt(10750, HVDL, 7)),
      TileRow(150, hillyTaiga * 2),
      VertRow(149, BendOut(10738, HVDR, 7), BendIn(10740, HVUL, 13)),
      TileRow(148, hillyOce * 2),
      VertRow(147, BendMin(10732, HVDR), BendOut(10734, HVDn, 7), BendIn(10736, HVUp, 13), BendIn(10738, HVUL, 10)),
      VertRow(145, OrigLt(10732, HVUp)),
    )
  }
  help.run
}