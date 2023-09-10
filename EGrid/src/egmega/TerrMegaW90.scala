/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTile._

/** [[WTile]] terrain for 105° west to 75° west, centred on 90° west. Hex tile scale 1 Megametre or 1000km. */
object TerrMegaW90 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w90(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
//      TRow(118, sea),
//      TRow(116, taiga),
      TRow(114, taiga),
      TRow(112, taiga, taiga),
      TRow(110, savannah, hills),
      VRow(109, Mouth(9732, HVUL)),
      TRow(108, Hland(1, 3), Hland(2, 1)),
      VRow(107, Mouth(9726, HVUL), Mouth(9730, HVUR), Mouth(9732, HVDL)),
      TRow(106, Hland(2, 1, sahel), sea, sea),
      //VRow(105, Mouth(1540, HVUR) VertIn(1542, HVDL)),
      TRow(104, Hland(2, 3, hillyJungle), jungle, sea),
//      VRow(103, VertIn(1542, HVUR)),
//      TRow(102, jungle * 2, hills),
//      TRow(100, jungle * 2, plain),
//      TRow(98, jungle * 2, sea),
//      TRow(96, plain * 2, Hland(3, 1)),
//      VRow(95, Mouth(1538, HVUL)),
//      TRow(94, desert, Hland(2, 1), Hland(2, 4)),
//      TRow(92, Hland(2, 2, Hilly()), sea),
//      TRow(90, sea * 2),
//      TRow(88, sea * 2),
//      TRow(86, sea),
//      TRow(84, sea),
//      TRow(82, ice),
    )
  }
  help.run
}