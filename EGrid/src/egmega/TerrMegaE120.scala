/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

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
      TRow(108, Cape.a(1, 2), Cape.a(2, 4, hilly)),
      TRow(106, Cape.a(2, 2, hillyJungle), sea * 2),
      VRow(103, Mouth(4602, HVUL)),
      TRow(102, Cape.a(5, 4, hillyJungle), sea * 2),
      VRow(101, Mouth(4614, HVDL)),
      TRow(100, Cape.a(4, 2, hillyJungle), Isle(hillyJungle), sea),
      TRow(96, sea, sea, savannah),
      VRow(95, Mouth(4606, HVUR)),
      TRow(94, Cape.a(5, 1, desert), desert, sahel),
      VRow(93, BendIn(4604, HVUR), BendOut(4606, HVDL)),
      TRow(92, Cape.a(3, 2, savannah), desert),
    )
  }
  help.run
}