/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

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
      TRow(110, land),
      TRow(108, Cape(3, 3, hilly)),
      TRow(106, Cape(5, 2, savannah), Cape(0, 1, sahel)),
      VRow(105, BendAll(508, HVUL)),
      TRow(104, desert * 2),
      VRow(103, Mouth(508, HVDR)),
      TRow(102, savannah * 2),
      TRow(100, sea, jungle),
      TRow(98, sea * 2),
      VRow(97, Mouth(516, HVUR)),
      TRow(96, sea, Cape(4, 2, desert)),
      VRow(95, Mouth(516, HVDR)),
      TRow(86, Cape(0, 1, ice))
    )
  }
  help.run
}