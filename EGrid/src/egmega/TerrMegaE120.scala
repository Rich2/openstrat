/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egmega
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 1 megametre or 1000km.
 * [[Isle8]] 190288.785km² => 244415.372km². Most of Philippines excluding Luzon and Palawan.
 * [[Isle7]] 142928.020km² => 190288.785km². Sulawesi 186216.16km².
 * [[Isle6]] 102333.079km² => 142928.020km². Luzon 109965km², Java 138,800km².
 * [[Isle5]] 68503.962km² => 102333.079km². Lesser Sunda Islands estimate.
 * [[Isle3]] 21143.198km² => 41440.668km². Taiwan 36197km², */
object TerrMegaE120 extends LongMegaTerrs
{ override implicit val grid: EGridMegaLongFull = EGridMega.e120(82)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(118, hillyTundra),
      TRow(116, hillyTaiga),
      VRow(115, MouthOld(4612, HVUp)),
      TRow(114, taiga),
      TRow(112, taiga, hillyTaiga),
      TRow(110, savannah, hillyTaiga),
      VRow(109, MouthOld(4608, HVUL), BendIn(4612, HVUL), BendOut(4614, HVDR)),
      TRow(108, subtrop, hillySub),
      VRow(107, Bend(4608, HVDR, 13, 1), ThreeUp(4610, 13, 13, 8), ThreeDown(4612, 13, 0, 13)),
      TRow(106, hillyJungle, hillyJungle),
      VRow(105, MouthRt(4604, HVUL, 7), ThreeDown(4606, 13, 10, 13), ThreeUp(4608, 10, 13, 10), ThreeDown(4610, 13, 0, 10), BendIn(4612, HVUL, 13)),
      TRow(104, jungle, hillyJungle),
      VRow(103, Bend(4604, HVDR, 13, 5), ThreeUp(4606, 10, 13, 13), ThreeDown(4608, 10, 8, 13), ThreeUp(4610, 0, 8, 10), BendIn(4612, HVDL, 8)),
      TRow(102, hillyJungle, hillyJungle),
      VRow(101, ThreeDown(4602, 0, 6, 6), Bend(4604, HVUL, 13, 5), BendIn(4606, HVDR, 9), ThreeUp(4608, 8, 9, 0), BendOut(4614, HVUp, 7), BendIn(4616, HVDn, 13)),
      TRow(100, hillyJungle, hillyJungle, hillyJungle),
      VRow(99, BendIn(4602, HVUR), ThreeDown(4604, 6, 10, 8), ThreeUp(4606, 9, 10, 0)),
      TRow(98, hillyJungle, mtainJungle, jungle),
      VRow(97, BendIn(4602, HVUp, 13), ThreeUp(4604, 10, 0, 13), BendIn(4614, HVUp, 13), Bend(4616, HVDn, 6, 6)),
      TRow(96, sea, sea, savannah),
      VRow(95, MouthOld(4606, HVUR)),
      TRow(94, CapeOld(5, 1, desert), desert, sahel),
      VRow(93, BendIn(4604, HVUR), BendOut(4606, HVDL)),
      TRow(92, CapeOld(3, 2, savannah), desert),
      TRow(82, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str}
    str(106, "", "Taiwan")
    str(104, "", "Luzon")
    str(102,"Borneo north", "Philippines south")
    str(100, "Borneo west", "Sulawesi")
    str(98, "Java", "Lesser Sunda")
  }
}