/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 135° east to 165° east, centred on 150° east. Hex tile scale 1300km or 1.3Megametre. A hex tile area of 1.463582932 million km².
 * A maximum Isle area of 966193.420km²
 * Isle8 321588.046km² => 413061.979km². Japan combined 377973 km². */
object Terr13E150 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e150(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(115, BendOut(5632, HVUp, 7, SeaIceWinter), BendIn(5634, HVDn, 13, SeaIceWinter), BendIn(5636, HVDL, 13, SeaIceWinter)),
      TRow(114, hillyTundra),
      VRow(113, BendIn(5636, HVUR, 13, SeaIceWinter)),
      TRow(112, hillyTundra),
      VRow(111, BendOut(5636, HVDR, 7, SeaIceWinter)),
      TRow(110, hillyTaiga),
      VRow(109, BendIn(5630, HVDR, 8), ThreeUp(5632, 13, 8, 0), ThreeDown(5634, 13, 0, 8), BendIn(5636, HVUL, 13, SeaIceWinter, sea)),
      TRow(108, hillyForest),
      VRow(107, BendIn(5630, HVUR, 8), BendIn(5632, HVUp, 8), BendIn(5634, HVUL, 8)),
      TRow(106, sea, sea),
      VRow(103, BendIn(5628, HVDL, 13)),
      TRow(102, SepB()),
      VRow(101, SetSep(5628)),
      TRow(100, SepB(), sea * 2),
      VRow(99, BendOut(5628, HVDL), MouthOld(5630, HVDn)),
      TRow(98, CapeOld(3, 2, hillyJungle), sea),
      TRow(96, CapeOld(0, 2, hilly), sea),
      VRow(95, MouthOld(5634, HVDn)),
      TRow(94, savannah, sea),
      VRow(87, MouthOld(5632, HVDL, 3, wice), MouthOld(5636, HVDR, 3, wice)),
      TRow(86, CapeOld(0, 1, ice, wice))
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(108, "Japan")
  }
}