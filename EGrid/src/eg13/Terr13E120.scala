/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 1300km or 1.3 MegaMetres.
 * Isle6 243930.488km² <= 172942.905km². Most of Philippines (300000 km²).
 * Isle4 115771.696km² <= 70034.730km². Most of Luzon (109,965 km2) excluding the south plus Taiwan. */
object Terr13E120 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e120(86)
  override val terrs: LayerHcRefGrid[WTile] = LayerHcRefGrid[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()
  override val hexNames: LayerHcRefGrid[String] = LayerHcRefGrid[String]()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      VRow(115, MouthRt(4610, HVUL, 7, SeaIceWinter), BendOut(4612, HVUp, 7, SeaIceWinter)),
      TRow(114, hillyTundra),
      TRow(112, taiga),
      TRow(110, hillyTaiga),
      VRow(109, BendIn(4610, HVDR, 8), ThreeUp(4612, 13, 8, 0)),
      TRow(108, hillyTempForest),
      TRow(106, hillyTemp),
      TRow(104, Isle4(hillyJungle), sea),
      VRow(103, BendIn(4610, HVDn), BendIn(4612, HVDL, 13)),
      TRow(102, SepB(), sea, hillyJungle),
      VRow(101, BendIn(4604, HVDn), SetSep(4605), Mouth(4608, HVUL, 7, 3), ThreeDown(4610, 7, 7, 7), ThreeUp(4612, 0, 7, 7), BendIn(4614, HVDL, 13)),
      TRow(100, SepB(), hillyJungle, SepB(), hillyJungle),
      VRow(99, BendIn(4604, HVDR), ThreeUp(4606, 1, 7, 6), BendAllOld(4608, HVUp), ThreeUp(4610, 7, 7, 7), BendOut(4612, HVDL), MouthOld(4614, HVDn)),
      TRow(98, hillyJungle * 2),
      VRow(97, BendIn(4604, HVUR, 13), ThreeDown(4606, 7, 7, 0), BendAllOld(4608, HVDn), BendIn(4610, HVUp, 13), BendIn(4612, HVUL, 13)),
      TRow(96, SepB(), desert, sahel),
      VRow(95, BendIn(4604, HVDR, 13), BendOut(4606, HVUL)),
      TRow(94, savannah, desert),
      VRow(93, BendIn(4604, HVUR, 13), MouthRt(4606, HVDR, 7)),// MouthWestRt(4606, 7)),
      VRow(87, MouthOld(4612, HVDL, 3, wice)),
      TRow(86, ice)
    )
  }
  help.run
}