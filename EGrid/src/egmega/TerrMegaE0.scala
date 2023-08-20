/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 15° West to 15° East, centred on 0° East. Hex tile scale 1 Megametre or 1000km. */
object TerrMegaE0 extends LongMegaTerrs
{
  override implicit val grid: EGridMegaLongFull = EGridMega.e0(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, sea),
      TRow(116, sea),
      TRow(114, plain),
      TRow(112, plain, hills),
      TRow(110, Hland(3, 3, Hilly), sea),
      VRow(109, Mouth(512, HVUR)),
      VRow(109, Mouth(516, HVUL)),
      TRow(108, hills, desert),
      TRow(106, desert * 3),
      TRow(104, desert * 3),
      TRow(102, jungle * 3),
      TRow(100, sea * 2, jungle),
      TRow(98, sea * 2, jungle),
      TRow(96, sea * 3),
      TRow(94, sea * 2, Hland(2, 4, Level, Desert)),
      TRow(92, sea * 2),
      TRow(90, sea * 2),
      TRow(88, sea * 2),
      TRow(86, sea),
      TRow(84, sea),
      TRow(82, ice),
    )
  }
  help.run
}