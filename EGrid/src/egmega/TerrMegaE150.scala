/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale 1 megametre or 1000km. The Kamchatka peninsula at 270,000 km²
 * passes the 144337km² minimum. */
object TerrMegaE150 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e150(82)
  override val terrs: HCenLayer[WTile] = HCenLayer[WTile](sea)
  override val sTerrs: HSideOptLayer[WSide, WSideSome] = HSideOptLayer[WSide, WSideSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, hillyTundra),
      TRow(116, hillyTundra),
      VRow(115, Mouth(5632, HVUp), Mouth(5634, HVUL), BendIn(5636, HVUp)),
      TRow(114, Hland(4, 1, hillyTaiga)),
      TRow(110, Hland(4, 5, hills), sea),
      TRow(108, SideB(), sea * 2),

      VRow(101, Mouth(5626, HVDL)),
      TRow(100, Hland(2, 0, hillyJungle), sea * 2),
      TRow(98, Hland(2, 3, hillyJungle), sea * 2),
      TRow(96, Hland(1, 0, savannah), sea * 2),
      TRow(94, savannah, sea * 2),
      TRow(92, forest, sea),
      VRow(91, Mouth(5632, HVUp)),
      TRow(90, Hland(3, 2, savannah), sea),
    )
  }
  help.run
}