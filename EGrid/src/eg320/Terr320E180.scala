/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km per hex tile terrain centered on 180 east longitude, covering 30 degrees. */
object Terr320E180 extends Long320Terrs{
  override implicit val grid: EGrid320LongFull = EGrid320.e180(128, 164)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(156, Headland(3, 5, Plains, Tundra)),
      TRow(154, tundra * 2, Headland(2, 0, Plains, Tundra), sea),
      TRow(152, tundra, sea, Headland(4, 1, Plains, Tundra), Headland(2, 4, Plains, Taiga)),
      TRow(150, tundra * 2, sea * 2),
      TRow(148, tundra, sea * 4),
    )
  }
  help.run
}