/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 1 megametre or 1000km. */
object TerrMegaE90 extends LongMegaTerrs
{
  override implicit val grid: EGridMegaLongFull = EGridMega.e90(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, hillyTundra),
      TRow(116, hillyTaiga),
      TRow(114, taiga),
      TRow(112, hillyDesert, desert),
      TRow(110, desert, hillyDesert),
      TRow(108, mtain * 2),
      TRow(106, jungle * 3),
      TRow(104, jungle, sea, jungle),
      TRow(100, sea * 2, Isle(jungle)),
//      TRow(96, SideB(), Hland(2, 1, Hilly()), sea * 2),
    )
  }
  help.run
}