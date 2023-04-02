/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg220
import prid.phex._, egrid._, WTile._

/** 220km terrain for 90 west. */
object Terr220W90 extends Long220Terrs
{ override implicit val grid: EGrid220LongFull = EGrid220.w90(154, 162)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(162, plain * 2, taiga * 4, Headland(2, 0, Level, Taiga), Headland(1, 4, Level, Taiga), taiga),
      TRow(160, plain * 2, taiga * 5, Headland(1, 1, Level, Taiga), taiga),
      TRow(158, plain * 2, taiga * 7),
      TRow(156, plain * 2, taiga * 3),
      TRow(154, plain * 3, taiga),
    )
  }
  help.run
}