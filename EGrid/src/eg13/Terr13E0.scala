/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15° west to 15° east, centred on 0° east. Hex tile scale 1300km. */
object Terr13E0 extends Long13Terrs
{
  override implicit val grid: EGrid13LongFull = EGrid13.e0(86)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, hillyTundra),
      TRow(112, hillyTaiga),
      TRow(110, plain),
      TRow(108, Hland(3, 3, hills)),
      TRow(106, Hland(2, 5, desert), Hland(1, 0, desert)),
      TRow(104, desert * 2),
      TRow(102, jungle * 2),
      TRow(100, sea, Hland(1, 4, jungle)),
      TRow(98, sea * 2),
      TRow(96, sea, Hland(2, 4, desert)),
      TRow(86, Hland(1, 0, ice))
    )
  }
  help.run
}