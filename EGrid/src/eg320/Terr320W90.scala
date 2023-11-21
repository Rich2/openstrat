/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid.phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° west to 75° wast, centred on 90° west. Hex tile scale of 320km. */
object Terr320W90 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w90(124)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, mtain),
      TRow(164, mtain),
      TRow(162, WSeaIce,  mtain),
      TRow(160, Isle(tundra), WSeaIce),
      VRow(156, SetSide(9722)),
      TRow(158, Cape(4, 4, tundra, WSeaIce), Cape(4, 1, tundra, WSeaIce), Cape(0, 2, tundra, WSeaIce)),
      TRow(156, sea, Cape(1, 1, tundra), Cape(3, 2, tundra)),
      TRow(154, tundra * 3, sea),
      TRow(152, tundra * 2, Isle(tundra), Cape(4, 2, tundra)),
      TRow(150, taiga, tundra, sea * 2),
      TRow(148, taiga, tundra, sea * 2, Cape(4, 2, tundra)),
      TRow(146, taiga * 2, sea * 2, taiga),
      TRow(144, taiga * 3, Cape(0, 2, taiga), Cape(4, 2, taiga)),
      VRow(143, Mouth(9720, HVUp, Lake)),
      TRow(142, taiga * 4, Cape(1, 1, taiga), taiga),
      VRow(141, BendAll(9720, HVUR, Lake), Mouth(9722, HVDR, Lake), Mouth(9736, HVDn)),
      TRow(140, land, taiga * 5),
      VRow(139, Mouth(9732, HVDR, Lake)),
      TRow(138, land * 2, taiga, Cape(5, 2, land, Lake), taiga * 3),
      VRow(137, Mouth(9728, HVDn, Lake), BendAll(9730, HVDR, Lake), Mouth(9732, HVUR, Lake)),
      TRow(136, land * 5, lake, taiga),
      VRow(135, BendAll(9730, HVUR, Lake), Mouth(9732, HVDR, Lake), Mouth(9740, HVDL, Lake), Mouth(9742, HVUR, Lake)),
      TRow(134, land * 6, hillyForest),
      TRow(132, desert, land * 4, hillyForest, hilly),
      TRow(130, desert, land * 5, hilly, land),
      TRow(128, desert, land * 3, hilly * 2, land, sea),
      TRow(126, desert, land * 6, sea),
      TRow(124, desert, land * 5, jungle, sea * 2),
    )
  }
  help.run
}