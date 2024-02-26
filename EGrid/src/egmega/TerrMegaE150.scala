/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale 1 megametre or 1000km. The Kamchatka peninsula at 270,000 km²
 * passes the 144337km² minimum. */
object TerrMegaE150 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e150(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, hillyTundra),
      TRow(116, hillyTundra),
      VRow(115, MouthOld(5632, HVUp), MouthOld(5634, HVUL), BendIn(5636, HVUp)),
      TRow(114, CapeOld(1, 4, hillyTaiga)),
      TRow(110, CapeOld(5, 4, hillyTemp), sea),
      VRow(109, BendOut(5630, HVDR)),
      TRow(108, SepB(), sea * 2),

      VRow(101, MouthOld(5626, HVDL)),
      TRow(100, CapeOld(0, 2, hillyJungle), sea * 2),
      TRow(98, CapeOld(3, 2, hillyJungle), sea * 2),
      TRow(96, CapeOld(0, 1, savannah), sea * 2),
      TRow(94, savannah, sea * 2),
      TRow(92, tempForest, sea),
      VRow(91, MouthOld(5632, HVUp)),
      TRow(90, CapeOld(2, 3, savannah), sea),
      TRow(82, ice)
    )
  }
  help.run
}