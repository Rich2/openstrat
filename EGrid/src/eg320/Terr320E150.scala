/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

object Terr320E150 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.e150(126)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(160, sea * 2),
      TRow(158, Headland(3, 5, Plains, Tundra), sea * 2),
      TRow(156, tundra * 2, Head2Land(0, Plains, Tundra)),
      TRow(154, tundra * 4),
      TRow(152, tundra, taiga * 3),
      TRow(150, taiga * 4),
      TRow(148, taiga * 3, sea, taiga),
      TRow(146, sea * 3, taiga, sea),
      TRow(144, sea * 3, Head2Land(4, Plains, Taiga), Head2Land(1, Plains, Taiga)),
      TRow(142, taiga * 2, sea * 2, Headland(3, 2, Plains, Taiga), sea),
      TRow(140, taiga, sea * 5),
      TRow(138, Headland(2, 2, Plains, Taiga), sea * 6),
      TRow(136, sea, Headland(3, 5, Hilly, Forest), sea * 5),
      TRow(134, Headland(3, 3, Hilly, Forest), Head2Land(2, Hilly, Forest), sea * 5),
      TRow(132, sea, Headland(3, 0, Hilly), sea * 5),
      TRow(130, Head2Land(5, Hilly), Head1Land(2, Hilly), sea * 6),
      TRow(128, Head2Land(2, Hilly), sea * 7),
    )
  }
  help.run
}
