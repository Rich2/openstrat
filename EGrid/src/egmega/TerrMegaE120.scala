/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 75° east to 105° east, centred on 90° east. Hex tile scale 1 megametre or 1000km. */
object TerrMegaE120 extends LongMegaTerrs
{
  override implicit val grid: EGridMegaLongFull = EGridMega.e120(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, hillyTundra),
      TRow(116, hillyTaiga),
      TRow(114, taiga),
      TRow(112, taiga, hillyTaiga),
      TRow(110, savannah, hillyTaiga),
      TRow(108, plain, hills),
      TRow(100, Hland(2, 4, hillyJungle), sea * 2),
      TRow(96, sea, sea, savannah),
      TRow(94, Hland(1, 5, desert), desert, sahel),
      TRow(92, Hland(2, 3, savannah), desert),
    )
  }
  help.run
}