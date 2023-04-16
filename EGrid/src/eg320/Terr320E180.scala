/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km per hex tile terrain centered on 180 east longitude, covering 30 degrees. */
object Terr320E180 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e180(120)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = grid.newSideOptLayer[WSide, WSideSome]
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, sice),
      TRow(164, WSeaIce),
      TRow(162, WSeaIce * 2),
      TRow(160, WSeaIce * 2),
      TRow(158, WSeaIce * 3),
      TRow(156, Hland(3, 5, Level(Tundra), WSeaIce), WSeaIce * 2),
      TRow(154, tundra * 2, Hland(2, 0, Level(Tundra), WSeaIce), WSeaIce),
      TRow(152, tundra, sea, Hland(4, 1, Level(Tundra), WSeaIce), Hland(2, 4, Level(Taiga), WSeaIce)),
      TRow(150, tundra * 2, sea * 2),
      TRow(148, tundra, sea * 4),
      VRow(144, SetSide(6646)),
    )
  }
  help.run
}