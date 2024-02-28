/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package eg13
import prid._, phex._, egrid._, WTiles._

/** 1300km [[WTile]] terrain for 105° east to 135° east, centred on 120° east. Hex area 1463582.932km².
 * Isle13 893300.129km² => 1041945.271km². (Borneo 748168km²) + (Sulawesi 180681km²) = 928849km²
 * Isle12 756089.229km² => 893300.129km².
 * Isle9 413061.979km² => 515970.154km². Sumatra 443065kmm².
 * Isle6 243930.488km² <= 172942.905km². Most of Philippines (300000 km²).
 * Isle5 115771.696km² => 172942.905km². Java 138794km².
 * Isle4 70034.730km² => 115771.696km². Most of Luzon (109,965 km2) excluding the south plus Taiwan, Lesser Sunda Islands estimate 80000km, */
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
      TRow(108, hillyOceForest),
      TRow(106, hillyOce),
      TRow(104, Isle4(hillyJungle), sea),
      VRow(103, BendIn(4610, HVDn), BendIn(4612, HVDL, 13)),
      TRow(102, sea, hillyJungle),
      VRow(101, BendIn(4604, HVDn), BendIn(4606, HVDL, 7), Mouth(4608, HVUL, 7, 3), ThreeDown(4610, 7, 7, 7), ThreeUp(4612, 0, 7, 7), BendIn(4614, HVDL, 13)),
      TRow(100, SepB(), hillyJungle, SepB(), hillyJungle),
      VRow(99, ThreeDown(4604, 7, 12, 0), ThreeUp(4606, 1, 12, 6), ThreeDown(4608, 0, 12, 11), ThreeUp(4610, 7, 12, 7), Bend(4612, HVDL, 12, 2), MouthOld(4614, HVDn)),
      TRow(98, hillyJungle * 2),

      VRow(97, BendIn(4604, HVUR, 13), ThreeDown(4606, 12, 7, 0), ThreeUp(4608, 12, 90, 11), BendIn(4610, HVUp, 13), BendIn(4612, HVUL, 13),
        BendIn(4612, HVUp, 13), Bend(4614, HVDn, 13, 7)),

      TRow(96, SepB(), desert, sahel),
      VRow(95, BendIn(4604, HVDR, 13), BendOut(4606, HVUL)),
      TRow(94, savannah, desert),
      VRow(93, BendIn(4604, HVUR, 13), MouthRt(4606, HVDR, 7)),
      VRow(91, MouthLt(4610, HVUL, 7), BendIn(4612, HVUp, 13)),
      VRow(87, BendOut(4606, HVDn, 7, siceWin), BendIn(4608, HVUp, 13, siceWin), Bend(4610, HVDn, 6, 7, siceWin), BendIn(4612, HVUp, 13, siceWin)),
      TRow(86, ice)
    )
  }
  help.run

  { import hexNames.{ setRow => str }
    str(100, "Borneo")
    str(98, "Java", "Lesser Sunda")
  }
}