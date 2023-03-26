/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km per hex tile terrain centered on 180 east longitude, covering 30 degrees. */
object Terr320E180 extends Long320Terrs{
  override implicit val grid: EGrid320LongFull = EGrid320.e180(128)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(154, tundra * 3, sea),
      TRow(152, tundra, sea, Head4Land(1, Plains, Tundra), Head2Land(4, Plains, Taiga)),
      TRow(150, tundra * 2, sea * 2),
      TRow(148, tundra, sea * 4),
    )
  }
  help.run
}