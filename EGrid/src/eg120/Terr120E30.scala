/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg120
import prid._, phex._, egrid._, WTile._

object Terr120E30 extends Long120Terrs
{ override implicit val grid: EGrid120LongFull = EGrid120.e30(274)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(350, sea, mtain, hillyTundra * 2, sea * 5),
      TRow(348, Hland(2, 5, Mountains()), tundra * 4, Hland(2, 0, Hilly(Tundra)), sea * 2),
      TRow(346, mtain),

      TRow(314, plain * 10, plain * 6),
      TRow(312, plain * 10, plain * 6),
      TRow(314, plain),

      TRow(286, hills, plain, hills * 3, sea, hills * 2, hills * 13),
      TRow(284, hills, sea * 2, hills * 3, sea * 2, hills * 13),
      TRow(282, hills, sea * 2, hills * 3, sea * 2, hills * 13),
      TRow(280, sea * 4, hills * 2, sea * 2, hills * 14),
      TRow(278, sea * 5, hills, sea * 9, hills * 7),
      TRow(276, sea * 6, hills * 2, sea * 7, hills * 7),
      TRow(274, sea * 8, sea * 5, hills, sea, hills * 7),
    )
  }
  help.run
}