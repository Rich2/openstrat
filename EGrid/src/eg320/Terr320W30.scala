/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg320
import prid._, phex._, egrid._, WTile._

/** 320km hex terrain centred on 30 west. */
object Terr320W30 extends Long320Terrs
{
  override implicit val grid: EGrid320LongFull = EGrid320.w30(124)

  override val terrs: HCenLayer[WTile] =
  { val res: HCenLayer[WTile] = grid.newHCenLayer[WTile](sea)
    def gs(r: Int, cStart: Int, tileValues: Multiple[WTile]*): Unit = { res.setRowEnd(r, cStart, tileValues :_*); () }
    gs(160, 11776, ice, sea)
    gs(158, 11774, ice * 2, sea)
    gs(156, 11772, ice * 2, sea)
    gs(154, 11770, ice, tundra, sea * 2)
    gs(152, 11780, hills * 2)
    res
  }
  override val sTerrs: HSideOptLayer[WSide] =
  { val res: HSideOptLayer[WSide] = grid.newSideOptLayer[WSide]
    res
  }

  override val corners: HCornerLayer = grid.newHVertOffsetLayer
}