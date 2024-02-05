/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 45° west to 15° west, centred on 30° west. Hex tile scale 1 Megametre or 1000km. */
object TerrMegaW30 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.w30(82)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSide, WSideSome] = LayerHSOptSys[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(116, Cape(1, 4, tundra)),
      TRow(114, SepB(), sea),

      TRow(106, sea * 2, Cape(4, 2, desert)),

      TRow(98, Cape(0, 3, desert), sea * 2),
      VRow(97, BendOut(11774, HVDR)),
      TRow(96, Cape(2, 1), sea * 2),
      VRow(95, BendAll(11772, HVDR)),
      VRow(93, BendOut(11772, HVDR), SetSide(11773)),
      VRow(91, SetSide(11773)),
      TRow(82, ice)
    )
  }
  help.run
}