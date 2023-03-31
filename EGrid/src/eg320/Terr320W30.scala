/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km hex terrain centred on 30 west. */
object Terr320W30 extends Long320Terrs
{ override implicit val grid: EGrid320LongFull = EGrid320.w30(124, 166)
  override val terrs: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
  override val sTerrs: HSideLayer[WSide] = grid.newSideLayer[WSide](WSideNone)
  override val corners: HCornerLayer = grid.newHVertOffsetLayer

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  { override val rowDatas: RArr[RowBase] = RArr(
      TRow(166, ice),
      TRow(164, ice),
      TRow(162, ice * 2),
      TRow(160, ice, Headland(2, 1, Level, IceCap)),
      TRow(158, ice * 2, sea),
      TRow(156, ice * 2, sea),
      TRow(154, ice, tundra, sea * 2),
      TRow(152, Headland(1, 2, Hilly, IceCap), sea, tundraHills * 2),
      TRow(150, Headland(2, 2, Hilly, IceCap))
    )
  }

  help.run
}