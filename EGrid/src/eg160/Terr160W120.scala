/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg160
import prid._, phex._, egrid._, WTiles._

/** Terrain for 160km 120 west. */
object Terr160W120 extends Long160Terrs
{ override implicit val grid: EGrid160LongFull = EGrid160.w120(312, 322)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners) {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(320, sea * 3, hillyTundra * 2),
      TRow(318, sea, tundra * 2, sea * 2),
      TRow(316, sea, tundra * 2, hillyTundra * 3),
      TRow(314, sea * 2, tundra * 4),
      TRow(312),
    )
  }
  help.run
}