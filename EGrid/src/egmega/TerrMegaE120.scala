/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 1 megametre or 1000km. */
object TerrMegaE120 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e120(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, hillyTundra),
      TRow(116, hillyTaiga),
      VRow(115, Mouth(4612, HVUp)),
      TRow(114, taiga),
      TRow(112, taiga, hillyTaiga),
      TRow(110, savannah, hillyTaiga),
      VRow(109, Mouth(4608, HVUL), BendIn(4612, HVUL)),
      TRow(108, Hland(2, 1), Hland(4, 2, hills)),
      TRow(106, Hland(2, 2, hillyJungle), sea * 2),
      VRow(103, Mouth(4602, HVUL)),
      TRow(102, Hland(4, 5, hillyJungle), sea * 2),
      VRow(101, Mouth(4614, HVDL)),
      TRow(100, Hland(2, 4, hillyJungle), Isle(hillyJungle), sea),
      TRow(96, sea, sea, savannah),
      VRow(95, Mouth(4606, HVUR)),
      TRow(94, Hland(1, 5, desert), desert, sahel),
      VRow(93, BendIn(4604, HVUR), BendOut(4606, HVDL)),
      TRow(92, Hland(2, 3, savannah), desert),
    )
  }
  help.run
}