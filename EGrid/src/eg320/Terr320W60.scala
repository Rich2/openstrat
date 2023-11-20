/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 75° west to 45° wast, centred on 60° west. Hex tile scale of 320km. */
object Terr320W60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w60(120)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, ice),
      TRow(164, Cape.a(5, 1, ice, WSeaIce)),
      TRow(162, Cape.a(4, 1, ice, WSeaIce), ice),
      TRow(160, WSeaIce, ice),
      TRow(158, sea, ice * 2),
      TRow(156, tundra, sea, ice),
      TRow(154, tundra * 2, sea, ice),
      TRow(152, tundra, sea * 2, ice),
      TRow(150, sea * 3, Cape.a(4, 2, tundra)),
      TRow(148, taiga, Cape.a(5, 3, mtain), sea * 2, Cape.a(3, 2, tundra)),
      VRow(147, Mouth(10746, HVDn)),
      TRow(146, taiga, hillyTaiga, sea * 3),
      TRow(144, taiga * 3, sea * 2),
      TRow(142, taiga * 3, Cape.a(1, 3, taiga), sea * 2),
      TRow(140, taiga, Cape.a(2, 3, taiga), sea, Cape.a(1, 1, taiga), sea * 2),
      VRow(139, Mouth(10744, HVDL), BendAll(10746, HVDn)),
      TRow(138, taiga * 2, sea, Cape.a(3, 3, taiga), Cape.a(1, 3, taiga), sea * 2),
      TRow(136, hillyTaiga, taiga, taiga, Cape.a(0, 4, taiga), sea * 3),
      VRow(135, Mouth(10738, HVUR, Lake)),
      TRow(134, Cape.a(2, 2, hillyForest), sea * 6),
      TRow(132, sea * 7),
    )
  }
  help.run
}