/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 15° West to 15° East, centred on 0° East. Hex tile scale 1 Megametre or 1000km. The Mediterranean has not been given a hex
 * tile. Making 110, 514 an island causes problems. */
object TerrMegaE0 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e0(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(119, BendOut(516, HVUp)),
      TRow(118, Cape(2, 5, hillyTundra)),
      VRow(117, BendOut(512, HVUL)),
      TRow(116, Cape(3, 3, hillyTaiga)),
      VRow(115, Mouth(514, HVUR)),
      TRow(114, land),
      TRow(112, land, hilly),
      TRow(110, Cape(3, 3, hilly), Cape(3, 5, savannah)),
      VRow(109, BendAll(512, HVUL), BendOut(516, HVUR)),
      TRow(108, hilly, desert),
      TRow(106, desert * 3),
      TRow(104, desert * 3),
      TRow(102, jungle * 3),
      TRow(100, sea * 2, jungle),
      TRow(98, sea * 2, jungle),
      TRow(96, sea * 3),
      TRow(94, sea * 2, Cape(2, 4, desert)),
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