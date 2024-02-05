/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex tile scale 1300km or 1.3 MegaMetres.
 * Isle6 243930.488km² <= 172942.905km². Most of Philippines (300000 km²).
 * Isle4 115771.696km² <= 70034.730km². Most of Luzon (109,965 km2) excluding the south plus Taiwan. */
object Terr13E120 extends Long13Terrs
{ override implicit val grid: EGrid13LongFull = EGrid13.e120(86)
  override val terrs: LayerHcRefSys[WTile] = LayerHcRefSys[WTile](sea)
  override val sTerrs: LayerHSOptSys[WSep, WSepSome] = LayerHSOptSys[WSep, WSepSome]()
  override val corners: HCornerLayer = HCornerLayer()

  val help = new WTerrSetter(grid, terrs, sTerrs, corners)
  {
    override val rowDatas: RArr[RowBase] = RArr(
      TRow(114, hillyTundra),
      TRow(112, taiga),
      TRow(110, hillyTaiga),
      TRow(108, hillyForest),
      TRow(106, hilly),
      TRow(104, Isle4(hillyJungle), sea),
      TRow(102, SepB(), sea, Isle6(hillyJungle)),
      VRow(101, BendIn(4604, HVDn), SetSep(4605)),
      TRow(100, SepB(), hillyJungle, Cape(4, 4, hillyJungle)),
      VRow(99, BendIn(4604, HVDR), ThreeWay(4606), BendAll(4608, HVUp), BendOut(4612, HVDL), Mouth(4614, HVDn)),
      TRow(98, hillyJungle * 2),
      VRow(97, BendIn(4604, HVUR), ThreeDown(4606, 7, 7, 0), BendAll(4608, HVDn), BendIn(4610, HVUp, 13), BendIn(4612, HVUL, 13)),
      TRow(96, SepB(), desert, sahel),
      VRow(95, BendIn(4604, HVDR, 13), BendOut(4606, HVUL)),
      TRow(94, savannah, desert),
      VRow(93),
      VRow(87, Mouth(4612, HVDL, wice)),
      TRow(86, ice)
    )
  }
  help.run
}