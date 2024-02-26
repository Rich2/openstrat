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
      TRow(110, CapeOld(5, 4, hillyOce), sea),
      VRow(109, BendOut(5630, HVDR)),
      TRow(108, SepB(), sea * 2),

      VRow(101, BendOut(5626, HVUp, 7), BendIn(5628, HVDn, 13), BendIn(5630, HVDL, 13)),
      TRow(100, hillyJungle),
      VRow(99, BendOut(5630, HVUR, 7), MouthLt(5632, HVDR, 7)),
      TRow(98, hillyJungle),
      VRow(97, BendIn(5626, HVUp, 13), Bend(5628, HVDn, 6, 6), MouthRt(5630, HVDR, 7)),
      TRow(96, savannah),
      TRow(94, savannah),
      TRow(92, oceForest),
      VRow(91, MouthOld(5632, HVUp)),
      TRow(90, savannah),
      TRow(82, ice)
    )
  }
  help.run
}