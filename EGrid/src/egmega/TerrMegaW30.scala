/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° west to 15° west, centred on 30° west. Hex tile scale 1 Megametre or 1000km. */
object TerrMegaW30 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w30(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(116, Hland(4, 1, tundra)),
      TRow(114, SideB(), sea),

      TRow(106, sea * 2, Hland(2, 4, desert)),

      TRow(98, Hland(3, 0, desert), sea * 2),
      VRow(97, BendOut(11774, HVDR)),
      TRow(96, Hland(1, 2), sea * 2),
      VRow(95, BendAll(11772, HVDR)),
      VRow(93, BendOut(11772, HVDR), SetSide(11773)),
      VRow(91, SetSide(11773)),
    )
  }
  help.run
}