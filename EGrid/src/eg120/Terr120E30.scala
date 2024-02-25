/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._, phex._, egrid._, WTiles._

object Terr120E30 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e30(274)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(350, sea, mtainOld, hillyTundra * 2, sea * 5),
      TRow(348, CapeOld(5, 2, mtainOld), tundra * 4, CapeOld(0, 2, hillyTundra), sea * 2),
      TRow(346, mtainOld),

      TRow(314, level * 10, level * 6),
      TRow(312, level * 10, level * 6),
      TRow(314, level),

      TRow(286, hilly, level, hilly * 3, sea, hilly * 2, hilly * 13),
      TRow(284, hilly, sea * 2, hilly * 3, sea * 2, hilly * 13),
      TRow(282, hilly, sea * 2, hilly * 3, sea * 2, hilly * 13),
      TRow(280, sea * 4, hilly * 2, sea * 2, hilly * 14),
      TRow(278, sea * 5, hilly, sea * 9, hilly * 7),
      TRow(276, sea * 6, hilly * 2, sea * 7, hilly * 7),
      TRow(274, sea * 8, sea * 5, hilly, sea, hilly * 7),
    )
  }
  help.run
}