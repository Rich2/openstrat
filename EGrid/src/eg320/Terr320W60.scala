/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km terrain for 60 west. */
object Terr320W60 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w60(128, 166)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(164, ice),
      TRow(162, Headland(1, 4, Level, IceCap), ice),
      TRow(160, sea, ice),
      TRow(158, sea, ice * 2),
      TRow(156, tundra, sea, ice),
      TRow(154, tundra * 2, sea, ice),
      TRow(152, tundra, sea * 2, ice),
      TRow(150, sea * 3, Headland(2, 4, Level, Tundra)),
      TRow(148, taiga, Headland(3, 5, Mountains), sea * 2, Headland(2, 3, Level, Tundra)),
      VRow(147, MouthDn(10746)),
      TRow(146, taiga, taigaHills, sea * 3),
      TRow(144, taiga * 3, sea * 2),
      TRow(142, taiga * 3, Headland(3, 1, Level, Taiga), sea * 2),
      TRow(140, taiga, Headland(3, 2, Level, Taiga), sea, Headland(1, 1, Level, Taiga), sea * 2),
      VRow(139, MouthDL(10744), VertInDn(10746)),
      TRow(138, taiga * 2, sea, Headland(3, 3, Level, Taiga), Headland(3, 1, Level, Taiga), sea * 2),
      TRow(136, taigaHills, taiga, taiga, Headland(4, 0, Level, Taiga), sea * 3),
      VRow(135, MouthUR(10738, Lake)),
      TRow(134, Headland(2, 2, Hilly, Forest), sea * 6),
      TRow(132, sea * 7),
    )
  }
  help.run
}