/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15 West to 15 East. So one of the principles of these terrain grids is that tiles and tile sides should be specified
 *  according to objective geographical criteria, not political considerations. */
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
//      VRow(109, Mouth(512, HVUR)),
//      VRow(109, Mouth(516, HVUL)),
      TRow(108, Hland(3, 3, Hilly())),
      TRow(106, desert * 2),
      TRow(104, desert * 2),
      TRow(102, jungle * 2),
      TRow(100, sea, jungle),
      TRow(98, sea * 2),
      TRow(96, sea, Hland(2, 4, Level(Desert))),
//      TRow(94, sea * 2, Hland(2, 4, Level(Desert))),
//      TRow(92, sea * 2),
//      TRow(90, sea * 2),
//      TRow(88, sea * 2),
//      TRow(86, sea),
//      TRow(84, sea),
    )
  }
  help.run
}