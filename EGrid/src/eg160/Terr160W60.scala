/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTile._

object Terr160W60 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w60(314)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(320, sea * 3, ice * 2),
      TRow(318, sea * 3, ice * 2),
      TRow(316, sea * 3, ice * 3),
      TRow(314, mtain, sea * 3, ice * 2),
    )
  }
  help.run
}