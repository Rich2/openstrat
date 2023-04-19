/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

object Terr320E90 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e90(124)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, hillyTundra * 2),
      TRow(158, tundra * 3),
      TRow(156, taiga, hillyTaiga * 2),
      TRow(154, taiga * 2, hillyTaiga, taiga),
      TRow(152, taiga * 4),
      TRow(150, taiga * 4),
      TRow(148, taiga * 5),
      TRow(146, taiga * 5),
      TRow(144, taiga, plain * 4),
      TRow(142, desert, plain, hills * 3, mtain),
      TRow(140, plain, mtain, hillyDesert, desert, mtain, hills),
      TRow(138, desert * 3, hillyDesert, desert, hillyDesert, desert),
      TRow(136, mtain * 2, desert * 5),
      TRow(134, mtain * 3, desert * 4),
      TRow(132, desert * 7),
      TRow(130, desert * 3, hillyDesert * 5),
      TRow(128, hillyDesert * 6, hills, mtain),
      TRow(126, mtain, hillyDesert * 4, mtain * 3),
      TRow(124, plain, mtain, hillyDesert * 2, mtain * 4, hills),
    )
  }
  help.run
}