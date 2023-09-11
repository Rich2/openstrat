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
      TRow(118, Hland(1, 1, tundra)),
      TRow(116, Hland(2, 1, taiga)),
      VRow(115, Mouth(9728, HVDL)),
      TRow(114, taiga),
      TRow(112, taiga, taiga),
      TRow(110, savannah, hills),
      VRow(109, Mouth(9732, HVUL)),
      TRow(108, Hland(1, 3), Hland(2, 1)),
      VRow(107, Mouth(9726, HVUL), Mouth(9730, HVUR), Mouth(9732, HVDL)),
      TRow(106, Hland(2, 1, sahel), sea, sea),
      VRow(105, BendOut(9722, HVDL), Mouth(9726, HVDL)),
      TRow(104, Hland(2, 3, hillyJungle), jungle, sea),
      VRow(103, BendOut(9726, HVDn), BendOut(9728, HVDL)),
      TRow(102, sea, Hland(2, 3, jungle), jungle),
      VRow(101, Mouth(9732, HVUR)),
      TRow(100, sea * 2, Hland(2, 4, hillyJungle)),
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